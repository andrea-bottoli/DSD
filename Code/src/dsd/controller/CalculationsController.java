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
	/**
	 * This method starts the whole elaborations on all
	 * the tables that represent the grouped data
	 * (10min, 1hour, 1day)
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
	/**
	 * This method manages all the elaborations to calculate the 10minuts
	 * values from the RawData values.
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
	private void CalculateMeanValues(ArrayList<RawData> localRawData, int size)
	{
		//TO_DO
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
		//TO-DO
		float effectiveWindSpeed;
		//CHANGE 5 WITH ALPHA
		effectiveWindSpeed = MathEngine.EffectiveWindSpeed(this.instrumentsDataANE[1], this.instrumentsDataANE[4], 5);
		//PARAMETERS ARE MISSING
		
	}
	
	/*
	 * This method calculates the Line Forces
	 */
	/**
	 * This method calculates the values of the forces that
	 * are acting on the single line of pylons.
	 */
	private void CalculateLineForces()
	{
		//TO-DO
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
	
}
