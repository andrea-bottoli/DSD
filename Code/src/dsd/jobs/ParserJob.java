/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko BrÄ�iÄ‡, Dzana Kujan, Nikola Radisavljevic, JÃ¶rn Tillmanns, Miraldi Fifo
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
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import dsd.controller.JobParserController;

@DisallowConcurrentExecution
public class ParserJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("PARSER JOB EVERY 60 seconds");
		
		int count = 0;
		
		List<JobExecutionContext> jobs;
		
		try {
			jobs = arg0.getScheduler().getCurrentlyExecutingJobs();
			
			for (JobExecutionContext job : jobs) {
				if (job.getTrigger().equals(arg0.getTrigger())) {
					count++;
	            }else if (job.getTrigger().getPriority() < arg0.getTrigger().getPriority())
	            {
	            	GregorianCalendar c = new GregorianCalendar();
	        		c.setTime(new Date());
	        		System.out.print("["+c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+" - "
										+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+"]: ");
	            	System.out.println("There are other more priority jobs with me, so leaving");
	                return;
	            }
	        }
			
			if(count > 1){
				GregorianCalendar c = new GregorianCalendar();
        		c.setTime(new Date());
        		System.out.print("["+c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+" - "
									+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+"]: ");
				System.out.println("There's another instance running, so leaving");
				return;
			}
			
			JobParserController.CheckAndParse();
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
