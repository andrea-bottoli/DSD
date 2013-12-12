package dsd.jobs;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import dsd.controller.JobController;

@DisallowConcurrentExecution
public class ParserJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("QUARTZ JOB TEST EVERY 60 seconds");
		
		List<JobExecutionContext> jobs;
		 
		try {
			jobs = arg0.getScheduler().getCurrentlyExecutingJobs();
			
			for (JobExecutionContext job : jobs) {
	            if (job.getTrigger().equals(arg0.getTrigger()) && !job.getJobInstance().equals(this)) {
	                System.out.println("There's another instance running, so leaving");
	                return;
	            }
	        }
			
			JobController.CheckAndStart();
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
