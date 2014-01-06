/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, J�rn Tillmanns, Miraldi Fifo
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

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import dsd.controller.JobCalculationsController;


public class CalculationsJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("CALCULATIONS JOB EVERY 60 seconds");
		
		int count = 0;
		
		List<JobExecutionContext> jobs;
		
		try {
			jobs = arg0.getScheduler().getCurrentlyExecutingJobs();
			
			for (JobExecutionContext job : jobs) {
				if (job.getTrigger().equals(arg0.getTrigger())) {
					count++;
	            }else if (job.getTrigger().getPriority() < arg0.getTrigger().getPriority())
	            {
	            	System.out.println("There are other more priority jobs with me, so leaving");
	                return;
	            }
	        }
			
			if(count > 1){
				System.out.println("There's another instance running, so leaving");
				return;
			}
			
			JobCalculationsController.CheckAndElaborate();
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
