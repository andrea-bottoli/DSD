package dsd.controller;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dsd.calculations.MathEngine;
import dsd.controller.mathEngineTask.*;
import dsd.model.CalculatedData;
import dsd.model.RawData;
import dsd.model.calculation.*;
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
		this.mnPylonsForces = new PylonForces();
		this.moPylonsForces = new PylonForces();
	}
	
	@Override
	public void run() {
		StartCalculations();		
	}
	
	/*
	 * This is the method that starts the whole calculation
	 */
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
	
	/*
	 * This methods calculate all the values for 10 minutes grouped data
	 */
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

	/*
	 * This methods calculate all the values for 1 hour grouped data
	 */
	/**
	 * This method manages all the elaborations to calculate the
	 * 1hour values from the 10min values.
	 */
	private void Calculate1hour()
	{
		//TO-DO
	}
	
	/*
	 * This methods calculate all the values for 1 day grouped data
	 */
	/**
	 * This method manages all the elaborations to calculate the
	 * 1day values from the 1hour values.
	 */
	private void Calculate1day()
	{
		//TO-DO
	}
	
	/*
	 * Get parameters from DB
	 */
	/**
	 * This method allows to read and load parameters
	 */
	private void ReadParameters()
	{
		//TO_DO
	}
	
	/*
	 * Get data from DB
	 */
	/**
	 * This method allows to read and load the RawData
	 * for any time interval
	 */
	private void ReadRawData()
	{
		//TO_DO
	}
	
	/*
	 * Get grouped data from DB
	 */
	/**
	 * This method allows to read and load all kind of
	 * GroupedData (10min, 1hour, 1day) for any
	 * time interval
	 */
	private void ReadGroupedData()
	{
		//TO-DO
	}
	
	/*
	 * Calculates the mean values from ANE, IDRO and SONAR, then sonar statistics
	 */
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
	
	
	/*
	 * This method calculates the Plank Forces
	 */
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
				pool.submit(new PlankWindForcesTask(this));
				//WATER PUSH
				pool.submit(new PlankWaterForcesTask(this));
				//WEIGHT PRESSURE
				pool.submit(new PlankWeightForcesTask(this));
				
				pool.shutdown();
			}
			while(!pool.awaitTermination(60, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		//WIND PUSH
		CalculatePlankWindForces();
		
		//WATER PUSH
		CalculatePlankWaterForces();
		
		//STRUCTURE WEIGHT
		CalculatePlankWeightForces();
		*/
	}
	
	/*
	 * This method calculates the four components
	 * of wind force on the planking
	 */
	/**
	 * This method calculates the four components
	 * of wind force on the planking: Svplank,
	 * Sva1traf, Sva2traf, Sva3traf
	 */
	private void CalculatePlankWindForces(){
		
		float lEffectiveWindSpeed;
		float lWindPushOnPlank,lWindPushOnA1traf, lWindPushOnA2traf, lWindPushOnA3traf;
		
		/*##############################
		 *CHANGE 5 WITH ALPHA
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lEffectiveWindSpeed = MathEngine.EffectiveWindSpeed(this.instrumentsData.getAne2(), this.instrumentsData.getAne4(), 5);
		
		/*##############################
		 *CHANGE 1 WITH Cdwi, 2 with RhoAir, 3 with Aplank
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWindPushOnPlank = MathEngine.WindPushOnPlank(1, 2, 3, lEffectiveWindSpeed);
		
		/*##############################
		 *CHANGE 1 WITH Cdwi, 2 with RhoAir, 3 with Beta1, 4 with Atraf
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWindPushOnA1traf = MathEngine.WindPushOnA1TrafficCombination(1, 2, 3, 4, lEffectiveWindSpeed);
		
		/*##############################
		 *CHANGE 1 WITH Cdwi, 2 with RhoAir, 3 with Beta1, 4 with Atraf
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWindPushOnA2traf = MathEngine.WindPushOnA2TrafficCombination(1, 2, 3, 4, lEffectiveWindSpeed);
		
		/*##############################
		 *CHANGE 1 WITH Cdwi, 2 with RhoAir, 3 with Beta2, 4 with Atraf
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWindPushOnA3traf = MathEngine.WindPushOnA3TrafficCombination(1, 2, 3, 4, lEffectiveWindSpeed);
		
		plankForces.setWindPushOnPlank(lWindPushOnPlank);
		plankForces.setWindPushOnA1TrafficCombination(lWindPushOnA1traf);
		plankForces.setWindPushOnA2TrafficCombination(lWindPushOnA2traf);
		plankForces.setWindPushOnA3TrafficCombination(lWindPushOnA3traf);
	}
	
	/*
	 * This method calculates the three components
	 * of water force on the planking
	 */
	/**
	 * This method calculates the three components
	 * of water force on the planking: Q Flow rate,
	 * V Water speed, Sw Water Push
	 */
	private void CalculatePlankWaterForces() {
		
		float lFlowRate=0, lWaterSpeed=0, lAs=0, lHs=0, lBs=0, lSwater=0;
		
		/*##############################
		 *CHANGE 17 WITH Hwater1, 22 WITH Hwater2 and 25.3 WITH Hmax
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		if(this.instrumentsData.getIdro1()<17)
		{
			/*##############################
			 *CHANGE 1 WITH a1, 2 WITH b1 and 3 with c1
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, this.instrumentsData.getIdro1(), 2, 3);
		}else if(this.instrumentsData.getIdro1()<22)
		{
			/*##############################
			 *CHANGE 1 WITH a2, 2 WITH b2 and 3 with c2
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, this.instrumentsData.getIdro1(), 2, 3);			
		}else if(this.instrumentsData.getIdro1()<25.3)
		{
			/*##############################
			 *CHANGE 1 WITH a3, 2 WITH b3 and 3 with c3
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, this.instrumentsData.getIdro1(), 2, 3);
		}
		this.plankForces.setFlowRate(lFlowRate);
		/*##############################
		 *CHANGE 1 WITH a, 2 WITH b and 3 with c
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWaterSpeed = MathEngine.WaterSpeed(1, this.instrumentsData.getIdro1(), 2, 3);
		
		/*##############################
		 *CHANGE 1 WITH bottom_ref
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		if(this.instrumentsData.getSonar1()<1)
		{
			lHs=this.instrumentsData.getIdro2() - 1;
		}else
		{
			lHs=this.instrumentsData.getIdro2() - this.instrumentsData.getSonar1();
		}
		
		/*##############################
		 *CHANGE 1 WITH Cspan, 2 WITH Cd0, 3 WITH RHOwater
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lBs = 1;
		lAs = lBs*lHs;
		lSwater = MathEngine.HydrodynamicThrustWithOutDebris(2, 3, lAs, lWaterSpeed);
		plankForces.setHydrodynamicThrustWithOutDebris(lSwater);
		
		/*##############################
		 *CHANGE 666 WITH Dpylon, 2WITH Cd1, 3 WITH RHOwater, 4 WITH BetaA
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lBs = 2*666;
		lAs = lBs*lHs;
		lSwater = MathEngine.HydrodynamicThrustWithDebris(2, 3, lAs, 4, lWaterSpeed);
		plankForces.setHydrodynamicThrustWithDebris(lSwater);
	}
	
	/*
	 * This method calculates the component
	 * of weight of the planking
	 */
	/**
	 * This method calculates the component
	 * of weight of the planking: PPplank
	 */
	private void CalculatePlankWeightForces() {
		
		float lPstack;
		
		/*##############################
		 *CHANGE 1 WITH Ppu, 2 WITH Ptp, 3 WITH Ppy, 4 WITH Hbeam
		 *PARAMETERS IS MISSING
		 *############################# 
		 */
		lPstack=MathEngine.StackWeight(1, 2, 3, 4, 5, this.instrumentsData.getSonar1());
		
		/*##############################
		 *CHANGE 1 WITH Pplank
		 *PARAMETERS IS MISSING
		 *############################# 
		 */
		plankForces.setPlankWeight(1);
		plankForces.setStackWeight(lPstack);
	}
	
	
	/*
	 * This method calculates the Line Forces
	 */
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
				pool1.submit(new MatrixFillTask(this, 0));
				pool2.submit(new MatrixFillTask(this, 1));
				
				//Start the calculations of the all combinations for each line
				pool1.submit(new CombinationsCalculationTask(this, 0));
				pool2.submit(new CombinationsCalculationTask(this, 1));
				
				pool1.shutdown();
				pool2.shutdown();
			}
			while(!(pool1.awaitTermination(60, TimeUnit.SECONDS) && pool2.awaitTermination(60, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	/*
	 * This method calculates the Pylon Forces
	 */
	/**
	 * This method calculates the values of the forces that
	 * are acting on a single pylon.
	 */
	private void CalculatePylonForces()
	{
		//TO-DO
	}
	
	/*
	 * This method calculates the Risk Factor
	 */
	/**
	 * This method calculates the value of the risk factor
	 */
	private void CalculateRiskFactor()
	{
		//TO-DO
	}
	
	/*
	 * This method detects the most stressed pylon
	 */
	/**
	 * This method calculates the what is the most
	 * stressed pylon
	 */
	private void DetectMostStressedPylon()
	{
		//TO-DO
	}
	
	/*
	 * Fills the values in the right structure
	 * Results are stored in the variable that represents the grouped data of the DB
	 */
	/**
	 * This method allow to store a whole row of 
	 * calculated data into the correct tables.
	 */
	private void StoreCalculatedValues()
	{
		//TO_DO
	}
	
	/*
	 * Writes the calculated data on the DB
	 */
	private void WriteOnDB()
	{
		// TO-DO
	}

	
	
	//GETTERS & SETTERS
	/**
	 * @return the plankForces
	 */
	public PlankForces getPlankForces() {
		return plankForces;
	}
	
	/**
	 * @return the Mantova Forces Matrix
	 */
	public LineForcesMatrix getMantovaLineForcesMatrix() {
		return mnLineMatrix;
	}
	
	/**
	 * @return the Modena Forces Matrix
	 */
	public LineForcesMatrix getModenaLineForcesMatrix() {
		return moLineMatrix;
	}
	
	/**
	 * @return the Mantova Line Forces
	 */
	public LineForces getMantovaLineForces() {
		return mnLineForces;
	}
	
	/**
	 * @return the Modena Line Forces
	 */
	public LineForces getModenaLineForces() {
		return moLineForces;
	}

	/**
	 * @return the Mantova Pylons Forces
	 */
	public PylonForces getMantovaPylonsForces() {
		return mnPylonsForces;
	}
	
	/**
	 * @return the Modena Pylons Forces
	 */
	public PylonForces getModenaPylonsForces() {
		return moPylonsForces;
	}

	/**
	 * @return the instrumentsData
	 */
	public InstrumentsData getInstrumentsData() {
		return instrumentsData;
	}

	/**
	 * @return the mnLineMatrix
	 */
	public LineForcesMatrix getMantovaLineMatrix() {
		return mnLineMatrix;
	}

	/**
	 * @return the moLineMatrix
	 */
	public LineForcesMatrix getModenaLineMatrix() {
		return moLineMatrix;
	}
	
}
