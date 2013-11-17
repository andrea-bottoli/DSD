package dsd.controller;

import java.util.ArrayList;
import java.util.ListIterator;

import dsd.calculations.MathEngine;
import dsd.model.CalculatedData;
import dsd.model.LineForces;
import dsd.model.PlankForces;
import dsd.model.PylonForces;
import dsd.model.RawData;

public class CalculationsController {
	
	//Variables to be instantiated
	private CalculatedData calculatedData = null;
	private PlankForces plankForces = null;
	private LineForces lineForces = null;
	private PylonForces pylonForces = null;
	
	//Variables to store raw data
	private ArrayList<RawData> rawData = null;
	private ArrayList<RawData> tenMinData = null;
	private ArrayList<RawData> oneHourData = null;
	private ArrayList<RawData> oneDayData = null;
	
	//Variables for timestamps
	private float lastRawDataTimestamp;
	private float last10minDataTimestamp;
	private float last1hourDataTimestamp;
	
	//VariableForCalculatedData
	
	//Variables from technical instruments
	private float[] instrumentsDataANE = null;
	private float[] instrumentsDataIDRO = null;
	private float[] instrumentsDataSONAR = null;
	
	//Constructor
	public CalculationsController()
	{
		this.calculatedData = new CalculatedData();
		this.plankForces = new PlankForces();
		this.lineForces = new LineForces();
		this.pylonForces = new PylonForces();
		this.instrumentsDataANE = new float[4];
		this.instrumentsDataIDRO = new float[2];
		this.instrumentsDataSONAR = new float[7];
	}
	
	/*
	 * This is the method that starts the whole calculation
	 */
	public void StartCalculations()
	{
		try
		{
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
	private void Calculate10mins()
	{
		//Local variables
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
					localRawData.add(globalIterator.next());
				}
				
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
	private void Calculate1hour()
	{
		//TO-DO
	}
	
	/*
	 * This methods calculate all the values for 1 day grouped data
	 */
	private void Calculate1day()
	{
		//TO-DO
	}
	
	/*
	 * Get data from DB
	 */
	private void ReadRawData()
	{
		//TO_DO
	}
	
	/*
	 * Get grouped data from DB
	 */
	private void ReadGroupedData()
	{
		//TO-DO
	}
	
	/*
	 * Calculates the mean values from ANE, IDRO and SONAR, then sonar statistics
	 */
	private void CalculateMeanValues(ArrayList<RawData> localRawData, int size)
	{
		//TO_DO
	}
	
	/*
	 * This method calculates the Plank Forces
	 */
	private void CalculatePlankForces()
	{
		//TO-DO
		float effectiveWindSpeed;
		//CHANGE 5 WITH ALPHA
		effectiveWindSpeed = MathEngine.EffectiveWindSpeed(this.instrumentsDataANE[1], this.instrumentsDataANE[4], 5);
		//PARAMETERS ARE MISSING
		
	}
	
	/*
	 * This method calculates the Line Forces
	 */
	private void CalculateLineForces()
	{
		//TO-DO
	}
	
	/*
	 * This method calculates the Pylon Forces
	 */
	private void CalculatePylonForces()
	{
		//TO-DO
	}
	
	/*
	 * This method calculates the Risk Factor
	 */
	private void CalculateRiskFactor()
	{
		//TO-DO
	}
	
	/*
	 * This method detects the most stressed pylon
	 */
	private void DetectMostStressedPylon()
	{
		//TO-DO
	}
	
	/*
	 * Fills the values in the right structure
	 * Results are stored in the variable that represents the grouped data of the DB
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
	
}
