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
package dsd.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dsd.controller.JobCalculationsController;
import dsd.controller.RawDataController;
import dsd.controller.CalculatedDataController;
import dsd.controller.JobParserController;
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
		
		System.out.println("LOADING FLAG JOB ONLY ON START UP -- LOADING FLAGS FROM DB");
		
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
			
			if(gc.getTimeInMillis() > 0)
			{
				gc.set(Calendar.HOUR_OF_DAY, 0);
				gc.set(Calendar.MINUTE, 0);
				gc.set(Calendar.SECOND, 0);
			}
			
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
		
		JobParserController.setParserTimeStamps(inputSensorFlag, imageMnFlag, imageMoFlag);
		JobCalculationsController.setMathEngineTimeStamps(tenMinFlag, oneHourFlag, oneDayFlag);
	}
}
