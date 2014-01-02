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
		System.out.println("CALCULATIONS JOB EVERY 30 seconds");
		
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
