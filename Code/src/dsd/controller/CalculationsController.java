package dsd.controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dsd.controller.mathEngineTask.CalculationsControllerTask;
import dsd.model.CalculatedData;
import dsd.model.WorstCase;
import dsd.model.enums.eCalculatedDataType;

public class CalculationsController implements Runnable {
	
	//Variables to be instantiated
	
	/*
	 * Variable that tracks the timestamps to know till where the system
	 * has analyzed the data for each source
	 */
	private long last10minTimestamp;
	private long last1hourTimestamp;
	private long last1dayTimestamp;
	
	/*
	 * Variables to store the results of calcualtions
	 */
	private ArrayList<CalculatedData> resultsList10min = null;
	private ArrayList<CalculatedData> resultsList1hour = null;
	private ArrayList<CalculatedData> resultsList1day = null;
	
	@SuppressWarnings("unused")
	private ArrayList<WorstCase> worstCaseList = null;
	
	//Constructor
	public CalculationsController(long  last10minTimestamp, long last1hourTimestamp, long last1dayTimestamp)
	{
		this.last10minTimestamp = last10minTimestamp;
		this.last1hourTimestamp = last1hourTimestamp;
		this.last1dayTimestamp = last1dayTimestamp;		
				
		ParametersController.IntializeCurrentParemeters();
	}
		
	
	@Override
	public void run() {
		StartCalculations();
	}
	
	
	/**
	 * This method start the threads in charge to calculate in parallel
	 * the values of 10min, 1hour and 1day.
	 * 
	 * Wait the finish of all the three threads.
	 */
	private void StartCalculations()
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
				//10min calculation task
				pool.submit(new CalculationsControllerTask(this, eCalculatedDataType.OneHour ,this.last1hourTimestamp));
				//10min calculation task
				pool.submit(new CalculationsControllerTask(this, eCalculatedDataType.OneDay ,this.last1dayTimestamp));
				
				pool.shutdown();
				
				exit = pool.awaitTermination(10, TimeUnit.MINUTES);
			}
			while(!exit && trigger<3);
			
			if(exit){
				this.WriteOnDB();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Store the results of calculations
	 */
	public void StoreResults(ArrayList<CalculatedData> resultsList, ArrayList<WorstCase> worstCaseList, eCalculatedDataType dataType)
	{
		switch (dataType)
		{
		case TenMinutes:
			this.resultsList10min = resultsList;
			this.worstCaseList = worstCaseList;
			break;
		
		case OneHour:
			this.resultsList1hour = resultsList;
			break;
			
		case OneDay:
			this.resultsList1day = resultsList;
			break;
		}
	}
	
	private void WriteOnDB()
	{
		CalculatedDataController.InsertCalculatedData(this.resultsList10min, eCalculatedDataType.TenMinutes);
		/*
		 * TODO storing of worst case list
		 */
		CalculatedDataController.InsertCalculatedData(this.resultsList1hour, eCalculatedDataType.OneHour);
		CalculatedDataController.InsertCalculatedData(this.resultsList1day, eCalculatedDataType.OneDay);
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
