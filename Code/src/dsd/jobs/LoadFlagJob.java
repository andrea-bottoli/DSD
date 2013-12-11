package dsd.jobs;

import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dsd.controller.JobController;

public class LoadFlagJob  implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		long inputSensorFlag = 0;
		long imageFlag = 0;
		long tenMinFlag = 0;
		long oneHourFlag = 0;
		long oneDayFlag = 0;
		
		ArrayList<Long> list = new ArrayList<Long>();
		
		System.out.println("QUARTZ JOB ONLY ON START UP -- LOADING FLAGS FROM DB");
		
		/*
		 * TODO LOADING FROM DATABASE THE FLAGS
		 */
//		list =  .........  ;
//		
//		inputSensorFlag = list.get(0);
//		imageFlag = list.get(1);
//		tenMinFlag = list.get(2);
//		oneHourFlag = list.get(3);
//		oneDayFlag = list.get(4);
		
		
		
		JobController.setParserTimeStamps(inputSensorFlag, imageFlag);
		JobController.setMathEngineTimeStamps(tenMinFlag, oneHourFlag, oneDayFlag);
	}
}
