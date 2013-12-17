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
		System.out.println("QUARTZ JOB EVERY 60 seconds");
		
		int count = 0;
		
		List<JobExecutionContext> jobs;
		
		try {
			jobs = arg0.getScheduler().getCurrentlyExecutingJobs();
			
			for (JobExecutionContext job : jobs) {
				
//				System.out.println(arg0.getTrigger().toString());
//				System.out.println("job trigger name : "+job.getTrigger().getClass().getName());
//				System.out.println("arg0 trigger name : "+arg0.getTrigger().getClass().getName());
				
				
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
			
			JobController.CheckAndStart();
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
