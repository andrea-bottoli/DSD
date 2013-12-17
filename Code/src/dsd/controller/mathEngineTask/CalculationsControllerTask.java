package dsd.controller.mathEngineTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dsd.controller.CalculationsController;
import dsd.controller.ParametersController;
import dsd.controller.RawDataController;
import dsd.model.CalculatedData;
import dsd.model.RawData;
import dsd.model.WorstPylonCase;
import dsd.model.calculation.Combination;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.LineForces;
import dsd.model.calculation.LineForcesMatrix;
import dsd.model.calculation.PlankForces;
import dsd.model.calculation.Pylon;
import dsd.model.calculation.PylonCombination;
import dsd.model.calculation.PylonForces;
import dsd.model.calculation.SafetyFactor;
import dsd.model.calculation.WorstCase;
import dsd.model.enums.eCalculatedDataType;

public class CalculationsControllerTask implements Runnable{

	//Variables to be instantiated
	
	//Reference to the master controller
	private CalculationsController calculationsController = null;
	
	//Variables from technical instruments
	private InstrumentsData instrumentsData =null;
	
	//Variable for each kind of forces
	private PlankForces plankForces = null;
	private LineForcesMatrix mnLineMatrix = null;
	private LineForcesMatrix moLineMatrix = null;
	private LineForces mnLineForces = null;
	private LineForces moLineForces = null;
	private PylonForces mnPylonsForces = null;
	private PylonForces moPylonsForces = null;
	private SafetyFactor safetyFactor = null;
	
	private WorstCase worstCase00 = null;
	private WorstCase worstCase01 = null;
	private WorstCase worstCase10 = null;
	private WorstCase worstCase11 = null;
	
	private ArrayList<WorstCase> worstCaseList = null;
	
	//Variable in which store the calculations results
	private ArrayList<CalculatedData> calculatedData = null;
	
	//Variables to store grouped data after reading/loading from DB
	private ArrayList<RawData> rawData = null;
	
	//Variable that represent the amount of data that made the raw data sample to make calculations
	private int sampleSize;
	
	//Varaible that represent the type of data that have to be calculate
	private eCalculatedDataType dataType;
	
	/*
	 * Variable that tracks the timestamps to know till where the system
	 * has analyzed the data for each source
	 */
	private long lastTimestamp;

	
	//Constructor
	public CalculationsControllerTask(CalculationsController calculationsController, eCalculatedDataType dataType, long flag)
	{
		this.calculationsController = calculationsController;
		this.sampleSize = 0;
		this.dataType = dataType;
		this.lastTimestamp = flag;	
		this.calculatedData = new ArrayList<CalculatedData>();
		this.instrumentsData = new InstrumentsData();
		this.plankForces = new PlankForces();
		this.mnLineMatrix = new LineForcesMatrix();
		this.moLineMatrix = new LineForcesMatrix();
		this.mnLineForces = new LineForces();
		this.moLineForces = new LineForces();
		this.mnPylonsForces = new PylonForces(mnLineForces, 0);
		this.moPylonsForces = new PylonForces(moLineForces, 1);
		this.safetyFactor = new SafetyFactor();
		this.worstCase00 = new WorstCase(Boolean.FALSE, Boolean.FALSE);
		this.worstCase01 = new WorstCase(Boolean.FALSE, Boolean.TRUE);
		this.worstCase10 = new WorstCase(Boolean.TRUE, Boolean.FALSE);
		this.worstCase11 = new WorstCase(Boolean.TRUE, Boolean.TRUE);
		
		this.worstCaseList = new ArrayList<WorstCase>();
		worstCaseList.add(worstCase00);
		worstCaseList.add(worstCase01);
		worstCaseList.add(worstCase10);
		worstCaseList.add(worstCase11);
				
		ParametersController.IntializeCurrentParemeters();
		this.setUpSampleSize();
	}
	
	@Override
	public void run() {
		StartCalculations();		
	}
	
	
	
	/**
	 * This method setup the correct sample size based on the data type 
	 * in input.
	 * 10min data type --> 600.
	 * 1h	 data type --> 3600
	 * 1d	 data type --> 86400
	 */
	private void setUpSampleSize()
	{
		switch (dataType)
		{
		case TenMinutes:
			this.sampleSize = 600;
			break;
		
		case OneHour:
			this.sampleSize = 3600;
			break;
			
		case OneDay:
			this.sampleSize = 86400;
			break;
		}
	}
	
	
	/**
	 * This method starts the whole elaboration on the calculated data table
	 */
	public void StartCalculations()
	{
		try
		{	
			
			//Loading parameters
			LoadParameters();
			
			//start calculations
			Calculate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This method manages all the elaborations to calculate the 10minuts
	 * values from the RawData values.
	 */
	private void Calculate()
	{
		//Local variables
		int i;
		RawData	rd = null;
		ArrayList<RawData> localRawData = new ArrayList<RawData>();
		ListIterator<RawData> globalIterator;
		
		try
		{
			
			/*
			 * Read RawData from database
			 */
			ReadRawData();
			
			globalIterator = this.rawData.listIterator();
			
			do
			{
				i = 0;
				localRawData.clear();
				
				while((i< this.sampleSize) && (globalIterator.hasNext()))
				{
					rd = globalIterator.next();
					localRawData.add(rd);
					i++;
				}
				
				if(checkSampleSize(localRawData))
				{
					//clear the list that will contains the outputs
					clearCalculatedDataList();
					
					this.lastTimestamp = rd.getTimestamp();
					this.instrumentsData.setTimestamp(this.lastTimestamp);
					/*
					 * Start calculations for one line of the DB
					 */
					CalculateMeanValues(localRawData);
					CalculatePlankForces();
					CalculateLineForces();
					CalculatePylonForces();
					CalculateWorstCases();
					CalculateSafetyFactor();
					StoreCalculatedValues();
					WriteOnDB();
				}
			}
			while (globalIterator.hasNext());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param localRawData: list of data to be check the size
	 * @return Boolean. Return true if the list matches the local variable sampleSize, false if doesn't match.
	 */
	private boolean checkSampleSize(ArrayList<RawData> localRawData)
	{
		if(localRawData.size()==this.sampleSize)
		{
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * This method allows to read and load parameters
	 */
	private void LoadParameters()
	{
		ParametersController.IntializeCurrentParemeters();
	}
	
	/**
	 * This method allows to read and load the RawData
	 * for any time interval
	 * @param lastRawDataTimestamp2 
	 */
	private void ReadRawData()
	{
		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();
		
//		System.out.println("ts: "+this.lastTimestamp);
		
		startDate.setTime(new Date(this.lastTimestamp));
		
//		startDate.set(2011, 2, 11, 00, 00, 0);//2011-03-23 16:46:00
		
//		endDate.set(2011, 7, 20, 01, 00, 00);//2011-03-23 17:56:30
		
//		System.out.println("end date: "+endDate.getTime());
//		System.out.println("start date: "+startDate.getTime());
		
		
		if(this.rawData != null){
			this.rawData.clear();
		}
		this.rawData = RawDataController.GetAllForPeriod(startDate, endDate);
		System.out.println("lunghezza dati: "+this.rawData.size());
	}
	
	/**
	 * @param localRawData: is the list of value on which has to be performed the operation to calculate the mean values.
	 * @param size: size of the list of data
	 * 
	 * This method calculates the mean values of the specific attributes
	 * ANE1, ANE2, ANE3, ANE4, IDRO1, IDRO2, SONAR1, SONAR2, SONAR3, SONAR4,
	 * SONAR5, SONAR6 and SONAR7. For sonar values, there are also some
	 * statistics that have to be calculated.
	 */
	private void CalculateMeanValues(ArrayList<RawData> localRawData)
	{
		//THINK ABOUT DO THIS WITH THREADS !!!!
		ExecutorService pool = null;
		
		try {
			do
			{
				pool = Executors.newFixedThreadPool(3);
						
				//ANEMOMETER
				pool.submit(new InstrumentsAnemometerDataTask(localRawData, this.instrumentsData));
				//HYDROMETER
				pool.submit(new InstrumentsHydroDataTask(localRawData, this.instrumentsData));
				//ECHO-SOUNDER
				pool.submit(new InstrumentsSonarDataTask(localRawData, this.instrumentsData));
				
				pool.shutdown();
			}
			while(!pool.awaitTermination(60, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method calculates the values of the forces that
	 * are acting on the plank.
	 */
	private void CalculatePlankForces()
	{		
		//THINK ABOUT DO THIS WITH THREADS !!!!
		ExecutorService pool = null;
		
		try {
			do
			{
				pool = Executors.newFixedThreadPool(3);
						
				//WIND PUSH
				pool.submit(new PlankWindForcesTask(this.instrumentsData, this.plankForces));
				//WATER PUSH
				pool.submit(new PlankWaterForcesTask(this.instrumentsData, this.plankForces));
				//WEIGHT PRESSURE
				pool.submit(new PlankWeightForcesTask(this.instrumentsData, this.plankForces));
				
				pool.shutdown();
			}
			while(!pool.awaitTermination(60, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method calculates the values of the forces that
	 * are acting on the single line of pylons.
	 * 
	 * Execute the task to caluclate the LineForcesMatrix and LineForces
	 * for each line of pylons.
	 */
	private void CalculateLineForces()
	{	
		//POOL1 for MANTOVA LINE
		ExecutorService pool1 = null;
		//POOL2 for MODENA LINE
		ExecutorService pool2 = null;
		
		try {
			do
			{
				pool1 = Executors.newFixedThreadPool(1);
				pool2 = Executors.newFixedThreadPool(1);
				
				//Start the Calculations and filling the matrix for each line
				pool1.submit(new MatrixFillTask(this.instrumentsData, this.plankForces, this.mnLineMatrix, 0));
				pool2.submit(new MatrixFillTask(this.instrumentsData, this.plankForces, this.moLineMatrix, 1));
				
				//Start the calculations of the all combinations for each line
				pool1.submit(new LineCombinationsTask(this.plankForces, this.mnLineMatrix, this.mnLineForces));
				pool2.submit(new LineCombinationsTask(this.plankForces, this.moLineMatrix, this.moLineForces));
				
				pool1.shutdown();
				pool2.shutdown();
			}
			while(!(pool1.awaitTermination(60, TimeUnit.SECONDS) && pool2.awaitTermination(60, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method calculates the values of the forces that
	 * are acting on a single pylon.
	 */
	private void CalculatePylonForces()
	{
		ExecutorService pool = null;
		
		try
		{
			do
			{
				pool = Executors.newFixedThreadPool(2);
				
				//Mantova pylons
				pool.submit(new PylonCombinationTask(this.instrumentsData, this.mnLineForces, this.mnPylonsForces));
				//Modena pylons
				pool.submit(new PylonCombinationTask(this.instrumentsData, this.moLineForces, this.moPylonsForces));
				
				pool.shutdown();
			}
			while(!pool.awaitTermination(120, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * This method calculates the worst cases for each pylon
	 * in each type of load combination.
	 */
	private void CalculateWorstCases()
	{
		ExecutorService pool = null;
		
		try {
			do
			{
				pool = Executors.newFixedThreadPool(8);
						
				pool.submit(new WorstCasesTask(this.mnPylonsForces, this.worstCase00, this.lastTimestamp));
				pool.submit(new WorstCasesTask(this.moPylonsForces, this.worstCase00, this.lastTimestamp));
				pool.submit(new WorstCasesTask(this.mnPylonsForces, this.worstCase01, this.lastTimestamp));
				pool.submit(new WorstCasesTask(this.moPylonsForces, this.worstCase01, this.lastTimestamp));
				pool.submit(new WorstCasesTask(this.mnPylonsForces, this.worstCase10, this.lastTimestamp));
				pool.submit(new WorstCasesTask(this.moPylonsForces, this.worstCase10, this.lastTimestamp));
				pool.submit(new WorstCasesTask(this.mnPylonsForces, this.worstCase11, this.lastTimestamp));
				pool.submit(new WorstCasesTask(this.moPylonsForces, this.worstCase11, this.lastTimestamp));
				
				pool.shutdown();
			}
			while(!pool.awaitTermination(60, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method calculates the value of the risk factor
	 */
	private void CalculateSafetyFactor()
	{
		ExecutorService pool = null;
		
		try {
			do
			{
				pool = Executors.newFixedThreadPool(4);
						
				pool.submit(new SafetyFactorTask(this.worstCase00, this.safetyFactor));
				pool.submit(new SafetyFactorTask(this.worstCase01, this.safetyFactor));
				pool.submit(new SafetyFactorTask(this.worstCase10, this.safetyFactor));
				pool.submit(new SafetyFactorTask(this.worstCase11, this.safetyFactor));
				
				pool.shutdown();
			}
			while(!pool.awaitTermination(60, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * This method allow to store a whole row of 
	 * calculated data into the correct tables.
	 */
	private void StoreCalculatedValues()
	{
		/*
		 * ##################################################################
		 * ####															#####
		 * ####						FOR DEBUGGING 						#####
		 * ####															#####
		 * ##################################################################
		 * 
		 */
		
		/*
		 * TEMP CODE TO DEBUG THE RESULTS OF EACH CALCULATIONS
		 */
		//INSTUMENTS DATA
		/*
		System.out.println("------------------------------INSTRUMENTS DATA------------------------------");
		System.out.println("ANE1\tANE2\tANE3\tANE4\t\t"
						+ "IDRO1\tIDRO2\t\t"
						+ "SONAR1\tSONAR2\tSONAR3\tSONAR4\tSONAR5\tSONAR6\tSONAR7");
		System.out.println(instrumentsData.getAne1()+"\t"+ instrumentsData.getAne2() +"\t"+instrumentsData.getAne3()+"\t"+instrumentsData.getAne4()+"\t\t"
				+ instrumentsData.getIdro1()+"\t"+instrumentsData.getIdro2()+"\t\t"
				+ instrumentsData.getSonar1()+"\t"+instrumentsData.getSonar2()+"\t"+instrumentsData.getSonar3()+"\t"+instrumentsData.getSonar4()+"\t"
				+ instrumentsData.getSonar5()+"\t"+instrumentsData.getSonar6()+"\t"+instrumentsData.getSonar7());
		System.out.println("\n\n");
		
		//PLANK FORCES
		System.out.println("------------------------------PLANK FORCES------------------------------");
		System.out.println("\n# WIND");
		System.out.println("1)Svplank: "+plankForces.getWindPushOnPlank());
		System.out.println("2)SvA1: "+plankForces.getWindPushOnA1TrafficCombination());
		System.out.println("3)SvA2: "+plankForces.getWindPushOnA2TrafficCombination());
		System.out.println("4)SvA3: "+plankForces.getWindPushOnA3TrafficCombination());
		System.out.println("\n# WATER");
		System.out.println("1)Q: "+plankForces.getFlowRate());
		System.out.println("2)V: "+plankForces.getWaterSpeed());
		System.out.println("3)Svd0: "+plankForces.getHydrodynamicThrustWithOutDebris());
		System.out.println("4)Svd1: "+plankForces.getHydrodynamicThrustWithDebris());
		System.out.println("5)hs: "+plankForces.getHs());
		System.out.println("6)Bsd0: "+plankForces.getBsWithoutDebris());
		System.out.println("7)Bsd1: "+plankForces.getBsWithDebris());
		System.out.println("\n# WEIGHT");
		System.out.println("1)Pplank: "+plankForces.getPlankWeight());
		System.out.println("2)Pstack: "+plankForces.getStackWeight());
		System.out.println("3)Pstruct: "+plankForces.getStructureWeight());
		System.out.println("\n\n");
		
		//LINE FORCES
		System.out.println("------------------------------LINE FORCES------------------------------");
		System.out.println("\n###############");
		System.out.println("## MANTOVA line");
		System.out.println("###############");
		for(Combination c : mnLineForces.getComboList())
		{
			System.out.println("_________");
			System.out.println("Combo number: "+c.getCombinationNumber());
			System.out.println("T: "+c.getTraffic());
			System.out.println("D: "+c.getDebris());
			System.out.println("N: "+c.getN());
			System.out.println("Tx: "+c.getTx());
			System.out.println("Ty: "+c.getTy());
			System.out.println("qy: "+c.getQy());
			System.out.println("Mx: "+c.getMx());
			System.out.println("_________");
			System.out.println("\n");
		}
		
		System.out.println("\n###############");
		System.out.println("## MODENA line");
		System.out.println("###############");
		for(Combination c : mnLineForces.getComboList())
		{
			System.out.println("_________");
			System.out.println("Combo number: "+c.getCombinationNumber());
			System.out.println("T: "+c.getTraffic());
			System.out.println("D: "+c.getDebris());
			System.out.println("N: "+c.getN());
			System.out.println("Tx: "+c.getTx());
			System.out.println("Ty: "+c.getTy());
			System.out.println("qy: "+c.getQy());
			System.out.println("Mx: "+c.getMx());
			System.out.println("_________");
			System.out.println("\n");
		}
		
		
		System.out.println("------------------------------PYLON FORCES------------------------------");
		System.out.println("\n###############");
		System.out.println("## MANTOVA line");
		System.out.println("###############");
		for(PylonCombination c : mnPylonsForces.getPylonComboList())
		{
			System.out.println("_________");
			System.out.println("Combo number: "+c.getCombination().getCombinationNumber());
			System.out.println("T: "+c.getCombination().getTraffic());
			System.out.println("D: "+c.getCombination().getDebris());
			for(Pylon p : c.getPylonList())
			{
				System.out.println("\tpylon number: "+p.getPylonNumber());
				System.out.println("\tN: "+p.getN());
				System.out.println("\tTx: "+p.getTx());
				System.out.println("\tTy: "+p.getTy());
				System.out.println("\tMx: "+p.getMx());
				System.out.println("\tMy: "+p.getMy());
			}
			System.out.println("_________");
			System.out.println("\n");
		}
		
		System.out.println("\n###############");
		System.out.println("## MODENA line");
		System.out.println("###############");
		for(PylonCombination c : moPylonsForces.getPylonComboList())
		{
			System.out.println("_________");
			System.out.println("Combo number: "+c.getCombination().getCombinationNumber());
			System.out.println("T: "+c.getCombination().getTraffic());
			System.out.println("D: "+c.getCombination().getDebris());
			for(Pylon p : c.getPylonList())
			{
				System.out.println("\tpylon number: "+p.getPylonNumber());
				System.out.println("\tN: "+p.getN());
				System.out.println("\tTx: "+p.getTx());
				System.out.println("\tTy: "+p.getTy());
				System.out.println("\tMx: "+p.getMx());
				System.out.println("\tMy: "+p.getMy());
			}
			System.out.println("_________");
			System.out.println("\n");
		}
		
		
		System.out.println("------------------------------WORST CASES------------------------------");
		System.out.println("\n###############");
		System.out.println("## Worst Case 00");
		System.out.println("###############");
		for(WorstPylonCase wpc : worstCase00.getWorstList())
		{
			System.out.println("_________");
			System.out.println("P_Number: "+wpc.getPylonNumber());
			System.out.println("N: "+wpc.getN());
			System.out.println("M: "+wpc.getM());
			System.out.println("combo number: "+wpc.getComboNumber());
			System.out.println("_________");
		}
		
		System.out.println("\n###############");
		System.out.println("## Worst Case 01");
		System.out.println("###############");
		for(WorstPylonCase wpc : worstCase01.getWorstList())
		{
			System.out.println("_________");
			System.out.println("P_Number: "+wpc.getPylonNumber());
			System.out.println("N: "+wpc.getN());
			System.out.println("M: "+wpc.getM());
			System.out.println("combo number: "+wpc.getComboNumber());
			System.out.println("_________");
		}
		
		System.out.println("\n###############");
		System.out.println("## Worst Case 10");
		System.out.println("###############");
		for(WorstPylonCase wpc : worstCase10.getWorstList())
		{
			System.out.println("_________");
			System.out.println("P_Number: "+wpc.getPylonNumber());
			System.out.println("N: "+wpc.getN());
			System.out.println("M: "+wpc.getM());
			System.out.println("combo number: "+wpc.getComboNumber());
			System.out.println("_________");
		}
		
		System.out.println("\n###############");
		System.out.println("## Worst Case 11");
		System.out.println("###############");
		for(WorstPylonCase wpc : worstCase11.getWorstList())
		{
			System.out.println("_________");
			System.out.println("P_Number: "+wpc.getPylonNumber());
			System.out.println("N: "+wpc.getN());
			System.out.println("M: "+wpc.getM());
			System.out.println("combo number: "+wpc.getComboNumber());
			System.out.println("_________");
		}
		
		
		System.out.println("------------------------------SAFETY FACTOR------------------------------");
		System.out.println("\n###############");
		System.out.println("## SAFETY FACTOR 00  ->" + safetyFactor.getSafetyFactorCombo00().getValue());
		System.out.println("## SAFETY FACTOR 01  ->" + safetyFactor.getSafetyFactorCombo01().getValue());
		System.out.println("## SAFETY FACTOR 10  ->" + safetyFactor.getSafetyFactorCombo10().getValue());
		System.out.println("## SAFETY FACTOR 11  ->" + safetyFactor.getSafetyFactorCombo11().getValue());
		*/
		/*
		 * ##################################################################
		 * ####															#####
		 * ####						END DEBUGGING 						#####
		 * ####															#####
		 * ##################################################################
		 * 
		 */
		
		
		CalculatedData cd = new CalculatedData();
		
		/*
		 * Store temp values of temp variables into the CaclulatdData variable
		 */
		//SETTED ANEMOMETER
		cd.setWindSpeed(this.instrumentsData.getAne1());
		cd.setWindSpeedMax(this.instrumentsData.getAne2());
		cd.setWindDirection(this.instrumentsData.getAne3());
		cd.setWindDirectionMax(this.instrumentsData.getAne4());
		
		//SETTED HYDROMETER
		cd.setHydrometer(this.instrumentsData.getIdro1());
		cd.setHydrometerVariance(this.instrumentsData.getIdro2());
		cd.setWaterSpeed(this.plankForces.getWaterSpeed());
		cd.setWaterFlowRate(this.plankForces.getFlowRate());
		
		//SETTED SONAR
		cd.setSonar(this.instrumentsData.getSonar1());
		cd.setSonarVariance(this.instrumentsData.getSonar2());
		cd.setSonarPercCorrect(this.instrumentsData.getSonar3());
		cd.setSonarPercWrong(this.instrumentsData.getSonar4());
		cd.setSonarPercOutOfWater(this.instrumentsData.getSonar5());
		cd.setSonarPercError(this.instrumentsData.getSonar6());
		cd.setSonarPercUncertain(this.instrumentsData.getSonar7());
		
		//SETTED SAFETY FACTOR
		cd.setSafetyFactor00(this.safetyFactor.getSafetyFactorCombo00().getValue());
		cd.setSafetyFactor01(this.safetyFactor.getSafetyFactorCombo01().getValue());
		cd.setSafetyFactor10(this.safetyFactor.getSafetyFactorCombo10().getValue());
		cd.setSafetyFactor11(this.safetyFactor.getSafetyFactorCombo11().getValue());
		
		//SETTED TIMESTAMP
		cd.setTimestamp(this.instrumentsData.getTimestamp());
		
		this.calculatedData.add(cd);
		System.out.println(this.calculatedData.size());
	}
	
	
	
	/**
	 * Return the list of results that have to be stored into the DB
	 */
	private void WriteOnDB()
	{
		this.calculationsController.StoreResults(this.calculatedData, this.worstCaseList, this.lastTimestamp, this.dataType);
	}
	
	
	/**
	 * This method clears the list of calculated data
	 */
	private void clearCalculatedDataList()
	{
		calculatedData.clear();
	}
}
