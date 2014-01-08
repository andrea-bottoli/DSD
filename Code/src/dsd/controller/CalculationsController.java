/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brcic, Dzana Kujan, Nikola Radisavljevic, Jorn Tillmanns, Miraldi Fifo
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
package dsd.controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dsd.controller.mathEngineTask.CalculationsControllerTask;
import dsd.model.CalculatedData;
import dsd.model.calculation.WorstCase;
import dsd.model.enums.eCalculatedDataType;

public class CalculationsController {
	
	//Variables to be instantiated
	
	/*
	 * Variable that tracks the timestamps to know till where the system
	 * has analyzed the data for each source
	 * 
	 * The timestamps are based on Date.Milliseconds()
	 */
	private long last10minTimestamp;
	private long last1hourTimestamp;
	private long last1dayTimestamp;
	
	//Constructor
	public CalculationsController(long  last10minTimestamp, long last1hourTimestamp, long last1dayTimestamp)
	{
		this.setTimeStamps(last10minTimestamp, last1hourTimestamp, last1dayTimestamp);	
		
		ParametersController.IntializeCurrentParemeters();
	}
	
	
	/**
	 * This method allows to set the timestamps for the 10min data, 1hour data and 1day data.
	 * 
	 * @param timeStamp10min the timestamp for 10 minutes data
	 * @param timeStamp1hour the timestamp for 1 hour data
	 * @param timeStamp1day the timestamp for 1 day data
	 */
	public void setTimeStamps(long timeStamp10min, long timeStamp1hour, long timeStamp1day)
	{
		this.last10minTimestamp = timeStamp10min;
		this.last1hourTimestamp = timeStamp1hour;
		this.last1dayTimestamp = timeStamp1day;
	}	
	
	/**
	 * This method start the threads in charge to calculate in parallel
	 * the values of 10min, 1hour and 1day.
	 * 
	 * Wait the finish of all the three threads.
	 */
	public void StartCalculations()
	{
		ExecutorService pool = null;
		int trigger = 0;
		boolean exit = false;
		
		try
		{
			do
			{
				trigger++;
				
				pool = Executors.newFixedThreadPool(3);
				
				//10min calculation task
				pool.submit(new CalculationsControllerTask(this, eCalculatedDataType.TenMinutes ,this.last10minTimestamp));
				//1hour calculation task
				pool.submit(new CalculationsControllerTask(this, eCalculatedDataType.OneHour ,this.last1hourTimestamp));
				//1day calculation task
				pool.submit(new CalculationsControllerTask(this, eCalculatedDataType.OneDay ,this.last1dayTimestamp));
				
				pool.shutdown();
				
				exit = pool.awaitTermination(24, TimeUnit.HOURS);
			}
			while(!exit && trigger<3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Store the results of calculations
	 */
	public void StoreResults(ArrayList<CalculatedData> resultsList, ArrayList<WorstCase> worstCaseList, long timeStamp, eCalculatedDataType dataType, boolean emptyRow)
	{
		
		switch (dataType)
		{
		case TenMinutes:
			this.last10minTimestamp = timeStamp;
			if(!emptyRow){
				WriteCalculatedDataAndWorstCaseOnDB(resultsList, worstCaseList, dataType);
			}else{
				WriteCalculatedDataOnDB(resultsList, dataType);
			}
			break;
		
		case OneHour:
			this.last1hourTimestamp = timeStamp;
			WriteCalculatedDataOnDB(resultsList, dataType);
			break;
			
		case OneDay:
			this.last1dayTimestamp = timeStamp;
			WriteCalculatedDataOnDB(resultsList, dataType);
			break;
		}
	}


	/**
	 * This method store on the db the information for a table
	 */
	private void WriteCalculatedDataOnDB(ArrayList<CalculatedData> resultsList, eCalculatedDataType dataType)
	{
		CalculatedDataController.InsertCalculatedData(resultsList, dataType);
	}
	
	/**
	 * This method store on the db the information for a worst case
	 */
	private void WriteWorstCaseOnDB(ArrayList<WorstCase> worstCaseList)
	{	
		for(WorstCase wc : worstCaseList)
		{
			WorstCaseController.UpdateWorstCaseData(wc.getWorstList(), wc.getTraffic(), wc.getDebris());
		}
	}
	
	/**
	 * This method store on the db the information for a worst case
	 */
	private void WriteCalculatedDataAndWorstCaseOnDB(ArrayList<CalculatedData> resultsList, ArrayList<WorstCase> worstCaseList, eCalculatedDataType dataType)
	{
		WriteCalculatedDataOnDB(resultsList,dataType);
		WriteWorstCaseOnDB(worstCaseList);
	}

	
	/**
	 * Method to reset the flags
	 */
	public void resetFlags()
	{
		this.last10minTimestamp = 0;
		this.last1hourTimestamp = 0;
		this.last1dayTimestamp = 0;
	}
}
