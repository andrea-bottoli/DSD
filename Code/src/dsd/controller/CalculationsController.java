package dsd.controller;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dsd.controller.mathEngineTask.*;
import dsd.model.CalculatedData;
import dsd.model.RawData;
import dsd.model.calculation.*;
import dsd.model.enums.eParameter;
import dsd.model.enums.eSonarType;

public class CalculationsController implements Runnable{
	
	//Variables to be instantiated
	
	//Variables to read/store data from sources
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
	
	//Variable in which store the calculations results
	private CalculatedData calculatedData = null;
	
	//Variables to store grouped data after reading/loading from DB
	private ArrayList<RawData> rawData = null;
	private ArrayList<RawData> tenMinData = null;
	private ArrayList<RawData> oneHourData = null;
	private ArrayList<RawData> oneDayData = null;
	
	/*
	 * Variable that tracks the timestamps to know till where the system
	 * has analyzed the data for each source
	 */
	private long lastRawDataTimestamp;
	private long last10minDataTimestamp;
	private long last1hourDataTimestamp;
	
	//Constructor
	public CalculationsController()
	{
		this.calculatedData = new CalculatedData();
		this.instrumentsData = new InstrumentsData();
		this.plankForces = new PlankForces();
		this.mnLineMatrix = new LineForcesMatrix();
		this.moLineMatrix = new LineForcesMatrix();
		this.mnLineForces = new LineForces();
		this.moLineForces = new LineForces();
		this.mnPylonsForces = new PylonForces(mnLineForces, 0);
		this.moPylonsForces = new PylonForces(moLineForces, 1);
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
			//Loding parameters
			ReadParameters();
			
			//start calculations
			Calculate10mins();
			Calculate1hour();
			Calculate1day();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//TO-DO
	}
	
	/**
	 * This method manages all the elaborations to calculate the 10minuts
	 * values from the RawData values.
	 */
	private void Calculate10mins()
	{
		//Local variables
		RawData	rd = null;
		ArrayList<RawData> localRawData = new ArrayList<RawData>();
		ListIterator<RawData> globalIterator = this.rawData.listIterator();
		
		try
		{
			
			/*
			 * Think about managing requests lack of time
			 * from last timestamp to the last data available
			 */
			
			ReadRawData();
			
			/*
			 * Remember to do the validation of input 
			 * prepare the index for the cycles
			 * and prepare the lists of data
			 */
			
			do
			{
				localRawData.clear();
				
				for(int i = 0; i < 600; i++)
				{
					rd = globalIterator.next();
					localRawData.add(rd);
				}
				
				this.lastRawDataTimestamp = rd.getTimestamp();
				
				/*
				 * Start calculations for one line of the DB
				 */
				CalculateMeanValues(localRawData, localRawData.size());
				CalculatePlankForces();
				CalculateLineForces();
				CalculatePylonForces();
				CalculateRiskFactor();
				DetectMostStressedPylon();
				StoreCalculatedValues();
				WriteOnDB();
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
		//TO-DO
	}
	
	/**
	 * This method manages all the elaborations to calculate the
	 * 1day values from the 1hour values.
	 */
	private void Calculate1day()
	{
		//TO-DO
	}
	
	/**
	 * This method allows to read and load parameters
	 */
	private void ReadParameters()
	{
		//TO_DO
	}
	
	/**
	 * This method allows to read and load the RawData
	 * for any time interval
	 */
	private void ReadRawData()
	{
		//TO_DO
	}
	
	/**
	 * This method allows to read and load all kind of
	 * GroupedData (10min, 1hour, 1day) for any
	 * time interval
	 */
	private void ReadGroupedData()
	{
		//TO-DO
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
	private void CalculateMeanValues(ArrayList<RawData> localRawData, int sampleSize)
	{
		float sumWindSpeed,sumWindDirection, sumWaterLevel, sumWaterVariance, sumRiverBottomLevel, sumRiverBottomLevelVariance;
		float meanWindSpeed, maxWindSpeed, meanWindDirection,maxWindDirection;
		float meanWaterLevel, varianceWaterLevel;
		float meanRiverBottomLevel, varianceRiverBottomLevel, percUtilizedData12OverWholeSample;
		float percWrongData3OverWholeSample, percOutWaterData4OverWholeSample, percErrorData5OverWholeSample;
		float percUncertainData2Over12Sample;
		int numbCertainValue, numbUncertainValue, numbWrongValue, numbOutOfWaterValue, numbErrorValue;
		
		maxWindSpeed=0;
		maxWindDirection=0;
		sumWindSpeed=0;
		sumWindDirection=0;
		sumWaterLevel=0;
		sumWaterVariance=0;
		sumRiverBottomLevel=0;
		sumRiverBottomLevelVariance=0;
		numbCertainValue=0;
		numbUncertainValue=0;
		numbWrongValue=0;
		numbOutOfWaterValue=0;
		numbErrorValue=0;
		
		for(RawData rawData : localRawData)
		{
			
			//Anemometer operations
			sumWindSpeed = sumWindSpeed + rawData.getWindSpeed();
			sumWindDirection = sumWindDirection + rawData.getWindDirection();
			if(rawData.getWindSpeed()>maxWindSpeed)
			{
				maxWindSpeed=rawData.getWindSpeed();
				maxWindDirection=rawData.getWindDirection();
			}
			
			//Hydrometer operations
			sumWaterLevel = sumWaterLevel + rawData.getHydrometer();
			
			//Sonar operations
			if(rawData.getSonarType().equals(eSonarType.CorrectData))
			{
				sumRiverBottomLevel = sumRiverBottomLevel + rawData.getSonar();
				numbCertainValue++;
			}else if(rawData.getSonarType().equals(eSonarType.UncertainData))
			{
				sumRiverBottomLevel = sumRiverBottomLevel + rawData.getSonar();
				numbUncertainValue++;
			}else if(rawData.getSonarType().equals(eSonarType.WrongData))
			{
				numbWrongValue++;
			}else if(rawData.getSonarType().equals(eSonarType.SonarOutOfWaterData))
			{
				numbOutOfWaterValue++;
			}else if(rawData.getSonarType().equals(eSonarType.ErrorData))
			{
				numbErrorValue++;
			}
		}
		
		//calculation of mean values for all instruments plus sonar statistics
		//Anemometer
		meanWindSpeed = sumWindSpeed/sampleSize;
		meanWindDirection = sumWindDirection/sampleSize;
		
		//Hydrometer
		meanWaterLevel = sumWaterLevel/sampleSize;
		
		//sonar
		meanRiverBottomLevel = sumRiverBottomLevel/sampleSize;
		percUtilizedData12OverWholeSample = (numbCertainValue+numbUncertainValue)/sampleSize;
		percWrongData3OverWholeSample = numbWrongValue/sampleSize;
		percOutWaterData4OverWholeSample = numbOutOfWaterValue/sampleSize;
		percErrorData5OverWholeSample = numbErrorValue/sampleSize;
		percUncertainData2Over12Sample = numbUncertainValue/(numbCertainValue+numbUncertainValue);
		
		//Calculation of variances
		for(RawData rawData : localRawData)
		{
			sumWaterVariance += Math.pow((meanWaterLevel-rawData.getHydrometer()), 2);
			if(rawData.getSonarType() == eSonarType.CorrectData || rawData.getSonarType() == eSonarType.UncertainData)
			{
				sumRiverBottomLevelVariance += Math.pow((meanRiverBottomLevel-rawData.getSonar()), 2);
			}
		}
		
		varianceWaterLevel = sumWaterVariance/sampleSize;
		varianceRiverBottomLevel = sumRiverBottomLevelVariance/(numbCertainValue+numbUncertainValue);
		
		//Results are saved into class variables
		//Anemometer
		this.instrumentsData.setAne1(meanWindSpeed);
		this.instrumentsData.setAne2(maxWindSpeed);
		this.instrumentsData.setAne3(fixWindDirection(meanWindDirection));
		this.instrumentsData.setAne4(fixWindDirection(maxWindDirection));
		
		//Hydrometer
		this.instrumentsData.setIdro1(meanWaterLevel);
		this.instrumentsData.setIdro2(varianceWaterLevel);
		
		//Sonar
		this.instrumentsData.setSonar1(meanRiverBottomLevel);
		this.instrumentsData.setSonar2(varianceRiverBottomLevel);
		this.instrumentsData.setSonar3(percUtilizedData12OverWholeSample);
		this.instrumentsData.setSonar4(percWrongData3OverWholeSample);
		this.instrumentsData.setSonar5(percOutWaterData4OverWholeSample);
		this.instrumentsData.setSonar6(percErrorData5OverWholeSample);
		this.instrumentsData.setSonar7(percUncertainData2Over12Sample);
	}
	
	/**
	 * This method calculate the right direction of the wind
	 * The value WindDirection in RawData file is the direction
	 * from which the wind comes.
	 * 
	 * This method returns the direction to which the wind goes.
	 * 
	 * @param 
	 * @return
	 */
	private float fixWindDirection(float windOrigin)
	{
		if(windOrigin < 180)
		{
			return (windOrigin + 180);
		}else 
		{
			return (windOrigin - 180);
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
				pool.submit(new PlankWindForcesTask(instrumentsData, plankForces));
				//WATER PUSH
				pool.submit(new PlankWaterForcesTask(instrumentsData, plankForces));
				//WEIGHT PRESSURE
				pool.submit(new PlankWeightForcesTask(instrumentsData, plankForces));
				
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
				pool1.submit(new MatrixFillTask(instrumentsData, plankForces, mnLineMatrix, 0));
				pool2.submit(new MatrixFillTask(instrumentsData, plankForces, moLineMatrix, 1));
				
				//Start the calculations of the all combinations for each line
				pool1.submit(new LineCombinationsTask(mnLineMatrix, mnLineForces, plankForces));
				pool2.submit(new LineCombinationsTask(moLineMatrix, moLineForces, plankForces));
				
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
				pool.submit(new PylonCombinationTask(instrumentsData, mnLineForces, mnPylonsForces));
				//Modena pylons
				pool.submit(new PylonCombinationTask(instrumentsData, moLineForces, moPylonsForces));

				
				pool.shutdown();
			}
			while(!pool.awaitTermination(120, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method calculates the value of the risk factor
	 */
	private void CalculateRiskFactor()
	{
		//TO-DO
	}
	
	/**
	 * This method calculates the what is the most
	 * stressed pylon
	 */
	private void DetectMostStressedPylon()
	{
		//TO-DO
	}
	
	/**
	 * This method allow to store a whole row of 
	 * calculated data into the correct tables.
	 */
	private void StoreCalculatedValues()
	{
		//TO-DO
		
		/*
		 * TEMP CODE TO DEBUG THE RESULTS OF EACH CALCULATIONS
		 */
		//INSTUMENTS DATA
		System.out.println("--------INSTRUMENTS DATA-------");
		System.out.println("ANE1\tANE2\tANE3\tANE4\t\t"
						+ "IDRO1\tIDRO2\t\t"
						+ "SONAR1\tSONAR2\tSONAR3\tSONAR4\tSONAR5\tSONAR6\tSONAR7");
		System.out.println(instrumentsData.getAne1()+"\t"+ instrumentsData.getAne2() +"\t"+instrumentsData.getAne3()+"\t"+instrumentsData.getAne4()+"\t\t"
				+ instrumentsData.getIdro1()+"\t"+instrumentsData.getIdro2()+"\t\t"
				+ instrumentsData.getSonar1()+"\t"+instrumentsData.getSonar2()+"\t"+instrumentsData.getSonar3()+"\t"+instrumentsData.getSonar4()+"\t"
				+ instrumentsData.getSonar5()+"\t"+instrumentsData.getSonar6()+"\t"+instrumentsData.getSonar7());
		System.out.println("\n\n");
		
		//PLANK FORCES
		System.out.println("--------PLANK FORCES-------");
		System.out.println("\n#WIND");
		System.out.println("1)Svplank: "+plankForces.getWindPushOnPlank());
		System.out.println("2)SvA1: "+plankForces.getWindPushOnA1TrafficCombination());
		System.out.println("3)SvA2: "+plankForces.getWindPushOnA2TrafficCombination());
		System.out.println("4)SvA3: "+plankForces.getWindPushOnA3TrafficCombination());
		System.out.println("\n#WATER");
		System.out.println("1)Q: "+plankForces.getFlowRate());
		System.out.println("2)V: "+plankForces.getWaterSpeed());
		System.out.println("3)Svd0: "+plankForces.getHydrodynamicThrustWithOutDebris());
		System.out.println("4)Svd1: "+plankForces.getHydrodynamicThrustWithDebris());
		System.out.println("5)hs: "+plankForces.getHs());
		System.out.println("6)Bsd0: "+plankForces.getBsWithoutDebris());
		System.out.println("7)Bsd0: "+plankForces.getBsWithDebris());
		System.out.println("\n#WEIGHT");
		System.out.println("1)Pplank: "+plankForces.getStructureWeight());
		System.out.println("2)Pstack: "+plankForces.getStackWeight());
		System.out.println("\n\n");
		
		//LINE FORCES
		System.out.println("--------LINE FORCES-------");
		System.out.println("\n#MANTOVA line");
		for(Combination c : mnLineForces.getComboList())
		{
			System.out.println("_________");
			System.out.println("Combo number: "+c.getCombinationNumber());
			System.out.println("T: "+c.isThereTraffic());
			System.out.println("D: "+c.areThereDebris());
			System.out.println("N: "+c.getN());
			System.out.println("Tx: "+c.getTx());
			System.out.println("Ty: "+c.getTy());
			System.out.println("qy: "+c.getQy());
			System.out.println("Mx: "+c.getMx());
			System.out.println("_________");
			System.out.println("\n");
		}
		
		System.out.println("\n#MODENA line");
		for(Combination c : mnLineForces.getComboList())
		{
			System.out.println("_________");
			System.out.println("Combo number: "+c.getCombinationNumber());
			System.out.println("T: "+c.isThereTraffic());
			System.out.println("D: "+c.areThereDebris());
			System.out.println("N: "+c.getN());
			System.out.println("Tx: "+c.getTx());
			System.out.println("Ty: "+c.getTy());
			System.out.println("qy: "+c.getQy());
			System.out.println("Mx: "+c.getMx());
			System.out.println("_________");
			System.out.println("\n");
		}
		
		
		System.out.println("--------PYLON FORCES-------");
		System.out.println("\n#MANTOVA line");
		for(PylonCombination c : mnPylonsForces.getPylonComboList())
		{
			System.out.println("_________");
			System.out.println("Combo number: "+c.getCombination().getCombinationNumber());
			System.out.println("T: "+c.getCombination().isThereTraffic());
			System.out.println("D: "+c.getCombination().areThereDebris());
			for(Pylon p : c.getPylonList())
			{
				System.out.println("\tpylon number: "+p.getPylonNumber());
				System.out.println("\tN: "+p.getN());
				System.out.println("\tTx: "+p.getTx());
				System.out.println("\tTy: "+p.getTy());
				System.out.println("\tqy: "+p.getQy());
				System.out.println("\tMx: "+p.getMx());
			}
			System.out.println("_________");
			System.out.println("\n");
		}
		
		System.out.println("\n#MODENA line");
		for(PylonCombination c : mnPylonsForces.getPylonComboList())
		{
			System.out.println("_________");
			System.out.println("Combo number: "+c.getCombination().getCombinationNumber());
			System.out.println("T: "+c.getCombination().isThereTraffic());
			System.out.println("D: "+c.getCombination().areThereDebris());
			for(Pylon p : c.getPylonList())
			{
				System.out.println("\tpylon number: "+p.getPylonNumber());
				System.out.println("\tN: "+p.getN());
				System.out.println("\tTx: "+p.getTx());
				System.out.println("\tTy: "+p.getTy());
				System.out.println("\tqy: "+p.getQy());
				System.out.println("\tMx: "+p.getMx());
			}
			System.out.println("_________");
			System.out.println("\n");
		}
	}
	
	/*
	 * Writes the calculated data on the DB
	 */
	private void WriteOnDB()
	{
		// TO-DO
	}	
}
