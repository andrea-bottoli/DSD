package dsd.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dsd.calculations.TimeCalculations;
import dsd.controller.mathEngineTask.*;
import dsd.model.CalculatedData;
import dsd.model.Parameter;
import dsd.model.RawData;
import dsd.model.calculation.*;

public class CalculationsController implements Runnable {
	
	//Variables to be instantiated
	
	//Variables to store data into data base
	private CalculatedDataController calculatedDataController = null;	// --> Has to be instanced or is a input ??
	
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
	
	//Variable in which store the calculations results
	private ArrayList<CalculatedData> calculatedData = null;
	
	//Variables to store grouped data after reading/loading from DB
	private ArrayList<RawData> rawData = null;
	
	/*
	 * Variable that tracks the timestamps to know till where the system
	 * has analyzed the data for each source
	 */
	private long last10minTimestamp;
	private long last1hourTimestamp;
	private long last1dayTimestamp;

	
	//Constructor
	public CalculationsController()
	{
		this.last10minTimestamp = 0;
		this.last1hourTimestamp = 0;
		this.last1dayTimestamp = 0;		
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
		
		ParametersController.IntializeCurrentParemeters();
	}
	
	/*
	 * ######################################################
	 * ITS ONLY FOR DEBUGGING AND INTERNAL TEST
	 * #####################################################
	 */
	public CalculationsController(ArrayList<RawData> lista, ArrayList<Parameter> param)
	{
		this.last10minTimestamp = 0;
		this.last1hourTimestamp = 0;
		this.last1dayTimestamp = 0;		
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
		
//		ParametersController.IntializeCurrentParemeters();
//		ParametersController.set(param);
		this.rawData = lista;
	}
	
	@Override
	public void run() {
		StartCalculations();		
	}
	
	
	/**
	 * This method starts the whole elaborations on all
	 * the tables that represent the grouped data
	 * (10min, 1hour, 1day)
	 */
	public void StartCalculations()
	{
		try
		{
			//clear the list that will contains the outputs
			clearCalculatedDataList();
			
			//Loding parameters
			LoadParameters();
			
			//start calculations
			Calculate10mins();
			Calculate1hour();
			Calculate1day();
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
	private void Calculate10mins()
	{
		//Local variables
		int sizeSample = 600;
		RawData	rd = null;
		ArrayList<RawData> localRawData = new ArrayList<RawData>();
		ListIterator<RawData> globalIterator = this.rawData.listIterator();
		
		try
		{
			
			/*
			 * Think about managing requests lack of time
			 * from last timestamp to the last data available
			 */
			
			ReadRawData(this.last10minTimestamp);
			
			do
			{
				localRawData.clear();
				
				for(int i = 0; i < sizeSample; i++)
				{
					rd = globalIterator.next();
					localRawData.add(rd);
				}
				
				if(checkSampleSize(localRawData,sizeSample))
				{
					this.last10minTimestamp = rd.getTimestamp();
					this.instrumentsData.setTimestamp(this.last10minTimestamp);
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
	 * This method manages all the elaborations to calculate the
	 * 1hour values from the 10min values.
	 */
	private void Calculate1hour()
	{
		//Local variables
		int sizeSample = 3600;
		RawData	rd = null;
		ArrayList<RawData> localRawData = new ArrayList<RawData>();
		ListIterator<RawData> globalIterator = this.rawData.listIterator();
		
		try
		{
			
			/*
			 * Think about managing requests lack of time
			 * from last timestamp to the last data available
			 */
			
//			ReadRawData(this.last1hourTimestamp);
			
			/*
			 * Remember to do the validation of input 
			 * prepare the index for the cycles
			 * and prepare the lists of data
			 */
			
			do
			{
				localRawData.clear();
				
				for(int i = 0; i < sizeSample; i++)
				{
					rd = globalIterator.next();
					localRawData.add(rd);
				}
				
				if(checkSampleSize(localRawData,sizeSample))
				{
					this.last1hourTimestamp = rd.getTimestamp();
					this.instrumentsData.setTimestamp(this.last1hourTimestamp);
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
	 * This method manages all the elaborations to calculate the
	 * 1day values from the 1hour values.
	 */
	private void Calculate1day()
	{
		//Local Variables
		int sizeSample = 86400;
		RawData	rd = null;
		ArrayList<RawData> localRawData = new ArrayList<RawData>();
		ListIterator<RawData> globalIterator = this.rawData.listIterator();
		
		try
		{
			
			/*
			 * Think about managing requests lack of time
			 * from last timestamp to the last data available
			 */
			
			ReadRawData(this.last1dayTimestamp);
			
			/*
			 * Remember to do the validation of input 
			 * prepare the index for the cycles
			 * and prepare the lists of data
			 */
			
			do
			{
				localRawData.clear();
				
				for(int i = 0; i < sizeSample; i++)
				{
					rd = globalIterator.next();
					localRawData.add(rd);
				}
				
				if(checkSampleSize(localRawData,sizeSample))
				{
					this.last1dayTimestamp = rd.getTimestamp();
					this.instrumentsData.setTimestamp(this.last1dayTimestamp);
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
	
	private boolean checkSampleSize(ArrayList<RawData> localRawData, int size)
	{
		if(localRawData.size()==size)
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
	private void ReadRawData(long lastRawDataTimestamp)
	{
		this.rawData = RawDataController.GetAllForPeriod(TimeCalculations.LabViewTimestampsToGregCalendar(lastRawDataTimestamp), new GregorianCalendar());
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
						
				//WIND PUSH
				pool.submit(new InstrumentsAnemometerDataTask(localRawData, this.instrumentsData));
				//WATER PUSH
				pool.submit(new InstrumentsHydroDataTask(localRawData, this.instrumentsData));
				//WEIGHT PRESSURE
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
						
				pool.submit(new WorstCasesTask(this.mnPylonsForces, this.worstCase00));
				pool.submit(new WorstCasesTask(this.moPylonsForces, this.worstCase00));
				pool.submit(new WorstCasesTask(this.mnPylonsForces, this.worstCase01));
				pool.submit(new WorstCasesTask(this.moPylonsForces, this.worstCase01));
				pool.submit(new WorstCasesTask(this.mnPylonsForces, this.worstCase10));
				pool.submit(new WorstCasesTask(this.moPylonsForces, this.worstCase10));
				pool.submit(new WorstCasesTask(this.mnPylonsForces, this.worstCase11));
				pool.submit(new WorstCasesTask(this.moPylonsForces, this.worstCase11));
				
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
		//TO-DO		
		
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
		System.out.println("7)Bsd0: "+plankForces.getBsWithDebris());
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
	}
	
	
	
	/**
	 * Write the results of calculations into the DB
	 */
	private void WriteOnDB()
	{
		// TO-DO
	}
	
	
	/**
	 * This method clears the list of calculated data
	 */
	private void clearCalculatedDataList()
	{
		calculatedData.clear();
	}
	
	public void resetFlags()
	{
		this.last10minTimestamp = 0;
		this.last1hourTimestamp = 0;
		this.last1dayTimestamp = 0;
	}
}
