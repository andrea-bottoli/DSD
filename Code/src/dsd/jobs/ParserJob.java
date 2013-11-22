package dsd.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dsd.controller.ParserControler;
import dsd.controller.StartParser;

public class ParserJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		StartParser.CheckAndStart();
		System.out.println("QUARTZ JOB TEST EVERY 5 seconds");
	}

}
