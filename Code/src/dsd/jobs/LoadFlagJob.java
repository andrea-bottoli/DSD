package dsd.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dsd.controller.RawDataController;
import dsd.controller.CalculatedDataController;
import dsd.controller.JobController;
import dsd.controller.ParsedInputFilesController;
import dsd.model.enums.eCalculatedDataType;
import dsd.model.enums.eFileType;

public class LoadFlagJob  implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		long inputSensorFlag = 0;
		long imageMnFlag = 0;
		long imageMoFlag = 0;
		long tenMinFlag = 0;
		long oneHourFlag = 0;
		long oneDayFlag = 0;
		GregorianCalendar gc = new GregorianCalendar();
		
		System.out.println("QUARTZ JOB ONLY ON START UP -- LOADING FLAGS FROM DB");
		
		/*
		 * Loading from DB the maximum timestamp for each tables, parsed file, that we don't need
		 * to redo the whole operations/calculations over those data
		 */
		inputSensorFlag = ParsedInputFilesController.GetMaxTimestamp(eFileType.Analog);
		imageMnFlag = ParsedInputFilesController.GetMaxTimestamp(eFileType.Mantova);
		imageMoFlag = ParsedInputFilesController.GetMaxTimestamp(eFileType.Modena);
		tenMinFlag = CalculatedDataController.GetMaxTimestamp(eCalculatedDataType.TenMinutes);
		oneHourFlag = CalculatedDataController.GetMaxTimestamp(eCalculatedDataType.OneHour);
		oneDayFlag = CalculatedDataController.GetMaxTimestamp(eCalculatedDataType.OneDay);
	
		if((tenMinFlag == 0) || (oneHourFlag == 0) || (oneDayFlag ==0))
		{
			gc.setTime(new Date(RawDataController.GetMinTimestamp()));
			
			gc.set(Calendar.MINUTE, 0);
			gc.set(Calendar.SECOND, 0);
			
			if(tenMinFlag == 0){
				tenMinFlag = gc.getTimeInMillis();
			}
			if(oneHourFlag == 0){
				oneHourFlag = gc.getTimeInMillis();
			}
			if(oneDayFlag == 0){
				oneDayFlag = gc.getTimeInMillis();
			}
		}		
		
		JobController.setParserTimeStamps(inputSensorFlag, imageMnFlag, imageMoFlag);
		JobController.setMathEngineTimeStamps(tenMinFlag, oneHourFlag, oneDayFlag);
	}
}
