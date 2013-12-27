/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package dsd.controller.mathEngineTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dsd.controller.CalculationsController;
import dsd.controller.ParametersController;
import dsd.controller.RawDataController;
import dsd.model.CalculatedData;
import dsd.model.RawData;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.LineForces;
import dsd.model.calculation.LineForcesMatrix;
import dsd.model.calculation.PlankForces;
import dsd.model.calculation.PylonForces;
import dsd.model.calculation.SafetyFactor;
import dsd.model.calculation.WorstCase;
import dsd.model.enums.eCalculatedDataType;

public class CalculationsControllerTask implements Runnable{

	//Variables to be instantiated
	private int minutesOffset;
	private int hoursOffset;
	private int daysOffset;
	
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
	
	//Variable that tracks the number of iteration to perform all the calculations needed.
	private int iterationNumber;

	
	//Constructor
	public CalculationsControllerTask(CalculationsController calculationsController, eCalculatedDataType dataType, long flag)
	{
		this.minutesOffset = 0;
		this.hoursOffset = 0;
		this.daysOffset = 0;
		this.calculationsController = calculationsController;
		this.sampleSize = 0;
		this.dataType = dataType;
		this.lastTimestamp = flag;
		
		this.iterationNumber = 0;
		
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
		startCalculations();		
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
			this.minutesOffset = 10;
			this.hoursOffset = 0;
			this.daysOffset = 0;
			break;
		
		case OneHour:
			this.sampleSize = 3600;
			this.minutesOffset = 0;
			this.hoursOffset = 1;
			this.daysOffset = 0;
			break;
			
		case OneDay:
			this.sampleSize = 86400;
			this.minutesOffset = 0;
			this.hoursOffset = 0;
			this.daysOffset = 1;
			break;
		}
	}
	
	
	/**
	 * This method starts the whole elaboration on the calculated data table
	 */
	public void startCalculations()
	{
		try
		{	
			
			//Loading parameters
			loadParameters();
			
			//setup the number of iteration
			setUpNumberOfIteration();
			
			//start calculations
			calculate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method allows to read and load parameters
	 */
	private void loadParameters()
	{
		ParametersController.IntializeCurrentParemeters();
	}
	
	/**
	 * This method setup the number of iterations to do
	 */
	private void setUpNumberOfIteration()
	{
		GregorianCalendar startDate = new GregorianCalendar();
				
		long startTimestamp = 0;
		long endTimestamp = 0;
		
		if(this.lastTimestamp == 0){
			startDate.setTime(new Date(RawDataController.GetMinTimestamp()));
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			
			startTimestamp = startDate.getTimeInMillis();
		}else{
			startTimestamp = this.lastTimestamp;
		}
		
		endTimestamp = RawDataController.GetMaxTimestamp();
//		System.out.println("start: "+new Date(startTimestamp));
//		System.out.println("end: "+new Date(endTimestamp));
		
		if((startTimestamp == 0) || (endTimestamp == 0)){
			this.iterationNumber = 0;
		}else{
			this.iterationNumber = (int) (((endTimestamp - startTimestamp)/1000)/this.sampleSize);
		}
//		System.out.println("NUMBER OF ITERATIONS TO DO for"+this.dataType +" : "+this.iterationNumber);
	}
	
	/**
	 * This method manages all the elaborations to calculate the 10minuts
	 * values from the RawData values.
	 */
	private void calculate()
	{
		try
		{
			if(this.iterationNumber > 0){
				for(int i=0; i < this.iterationNumber; i++){

					//clear the list that will contains the outputs
					clearCalculatedDataList();
					
					/*
					 * Read RawData from database
					 */
					readRawData();

					if(!this.rawData.isEmpty())
					{								
						this.instrumentsData.setTimestamp(this.lastTimestamp);
						
						/*
						 * Start calculations for one line of the DB
						 */
						calculateMeanValues(this.rawData);
						calculatePlankForces();
						calculateLineForces();
						calculatePylonForces();
						calculateWorstCases();
						calculateSafetyFactor();
						storeCalculatedValues();
						writeOnDB(false);
					}else
					{
						/*
						 * The whole sample is empty
						 */
						storeEmptyValues();
						writeOnDB(true);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	/**
	 * This method allows to read and load the RawData
	 * for any time interval
	 * @param lastRawDataTimestamp2 
	 */
	private void readRawData()
	{
		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate;
		
		if(this.lastTimestamp == 0){
			startDate.setTime(new Date(RawDataController.GetMinTimestamp()));
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
		}else{
			startDate.setTime(new Date(this.lastTimestamp));
		}
		
		endDate = (GregorianCalendar)startDate.clone();
		
		endDate.add(Calendar.MINUTE, this.minutesOffset);
		endDate.add(Calendar.HOUR, this.hoursOffset);
		endDate.add(Calendar.DATE, this.daysOffset);
		this.lastTimestamp = endDate.getTimeInMillis();
		endDate.add(Calendar.SECOND, -1);
		
//		System.out.println("start date["+this.dataType+"]: "+startDate.getTime());
//		System.out.println("end date["+this.dataType+"]: "+endDate.getTime());
		
		
		if(this.rawData != null){
			this.rawData.clear();
		}
		this.rawData = RawDataController.GetAllForPeriod(startDate, endDate);
		
//		System.out.println("***lunghezza dati: ["+this.rawData.size()+"]");
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
	private void calculateMeanValues(ArrayList<RawData> localRawData)
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
	private void calculatePlankForces()
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
	private void calculateLineForces()
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
	private void calculatePylonForces()
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
	private void calculateWorstCases()
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
	private void calculateSafetyFactor()
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
	private void storeEmptyValues()
	{
		CalculatedData cd = new CalculatedData();
		
		/*
		 * Store temp values of temp variables into the CaclulatdData variable
		 */
		//SETTED ANEMOMETER
		cd.setWindSpeed(0);
		cd.setWindSpeedMax(0);
		cd.setWindDirection(0);
		cd.setWindDirectionMax(0);
		
		//SETTED HYDROMETER
		cd.setHydrometer(0);
		cd.setHydrometerVariance(0);
		cd.setWaterSpeed(0);
		cd.setWaterFlowRate(0);
		
		//SETTED SONAR
		cd.setSonar(0);
		cd.setSonarVariance(0);
		cd.setSonarPercCorrect(0);
		cd.setSonarPercWrong(0);
		cd.setSonarPercOutOfWater(0);
		cd.setSonarPercError(1);
		cd.setSonarPercUncertain(0);
		
		//SETTED SAFETY FACTOR
		cd.setSafetyFactor00(0);
		cd.setSafetyFactor01(0);
		cd.setSafetyFactor10(0);
		cd.setSafetyFactor11(0);
		
		//SETTED TIMESTAMP
		cd.setTimestamp(this.lastTimestamp);
		
		this.calculatedData.add(cd);
	}
	
	/**
	 * This method allow to store a whole row of 
	 * calculated data into the correct tables.
	 */
	private void storeCalculatedValues()
	{
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
		cd.setTimestamp(this.lastTimestamp);
		
		this.calculatedData.add(cd);
	}
	
	
	
	/**
	 * Return the list of results that have to be stored into the DB
	 */
	private void writeOnDB(boolean emptyRow)
	{
		this.calculationsController.StoreResults(this.calculatedData, this.worstCaseList, this.lastTimestamp, this.dataType, emptyRow);
	}
	
	
	/**
	 * This method clears the list of calculated data
	 */
	private void clearCalculatedDataList()
	{
		calculatedData.clear();
	}
}

