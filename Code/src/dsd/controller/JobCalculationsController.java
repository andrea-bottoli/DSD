package dsd.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class JobCalculationsController {

	private static boolean enableCalculation = false;
	
	private static CalculationsController calculationController = new CalculationsController(0, 0, 0);
	
	
	/**
	 * This method allows to set the timestamps for the 10min data, 1hour data and 1day data
	 * into the calculation controller main task.
	 * 
	 * @param timeStamp10min the timestamp for 10 minutes data
	 * @param timeStamp1hour the timestamp for 1 hour data
	 * @param timeStamp1day the timestamp for 1 day data
	 */
	public static void setMathEngineTimeStamps(long timeStamp10min, long timeStamp1hour, long timeStamp1day)
	{
		if((timeStamp10min == 0) && (timeStamp1hour == 0) && (timeStamp1day == 0)){
			enableCalculation = false;
		}else
		{
			enableCalculation = true;
		}
		calculationController.setTimeStamps(timeStamp10min, timeStamp1hour, timeStamp1day);
	}
	
	
	public static void CheckAndElaborate()
	{
//		startCalculations();
	}
	
	
	/**
	 * This method start the calculation process
	 */
	private static void startCalculations()
	{
		GregorianCalendar gc = new GregorianCalendar();
		long count = 0;
		
		if(!enableCalculation){
			count = RawDataController.GetCount();
			
			if(count > 0){				
				gc.setTime(new Date(RawDataController.GetMinTimestamp()));
				gc.set(Calendar.HOUR_OF_DAY, 0);
				gc.set(Calendar.MINUTE, 0);
				gc.set(Calendar.SECOND, 0);
				
				setMathEngineTimeStamps(gc.getTimeInMillis(), gc.getTimeInMillis(), gc.getTimeInMillis());
			}
		}
		
		if(enableCalculation){
			System.out.println("-> It starts the calculations");
			
			calculationController.StartCalculations();
		}
	}
	
	
	/**
	 * This method resets the flags of the threads
	 */
	public static void resetFlags(){
		calculationController.resetFlags();
	}
}
