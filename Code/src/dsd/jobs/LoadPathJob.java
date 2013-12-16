package dsd.jobs;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dsd.controller.JobController;

public class LoadPathJob implements Job{
	
	private static final String configPath = "EngineConfig"+File.separator;
	private static final String configFileName = "source_path.cfg";
	
	private static final File configPathFile = new File(configPath);
	private static final File configFile = new File(configPath+configFileName);
	
	private static final String defaultSourcePath = "Sources"+File.separator;
	
	private static File defaultSourcePathFile = new File(defaultSourcePath);
	
	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		loadPath();		
	}

	
	/**
	 * This method check if the file passed in input exists.
	 * 
	 * @param f  file that has to be checked
	 * @return Return true if the file exists, false otherwise
	 */
	private boolean checkIfExists(File f) {
		
		if(f.exists()){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * This method create new folders with the path passed in input
	 * @param path the path directory that has to be created
	 */
	private void createDirectory(File path) {
		path.mkdirs();
	}
	
	
	/**
	 * This method create a new file with the file passed in input
	 * @param file the file that has to be created
	 */
	private void createFile(File file) {
		try {
			file.createNewFile();
			
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			
			pw.println("#####################################################\n"
					+"#													#\n"
					+"# Lines that start with '#' are comments and		#\n"
					+"#		are not readed by the program				#\n"
					+"#													#\n"
					+"# DO NOT WRITE NOTHING BEFORE THESE COMMENTS		#\n"
					+"#			THAT ARE NO COMMENTS  !!!				#\n"
					+"#													#\n"
					+"#####################################################");
			pw.println("#");
			pw.println("# THAT IS THE PATH IN WHICH THE PROGRAM CAN FIND THE SOURCE FILES SENT BY THE SERVER ON THE BRIDGE\n"
					+ "# IF NOT PRESENT WILL BE SETTED A DEFAULT PATH THAT IS './Source'");
			
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method set the default path 
	 * @param file file in which set the default path
	 */
	private void setDefaultPath(File file) {
		
		PrintWriter pw;
		boolean autoFlush = true;
		boolean append = true;
		
		try {
			pw = new PrintWriter(new FileWriter(file, append), autoFlush);
			
			pw.print(defaultSourcePathFile.getAbsolutePath());
			
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * This method loads the path of sources into the engine from the configuration file
	 */
	private void loadPath()
	{
		String line;
		boolean exit = false;
				
		System.out.println("QUARTZ JOB ONLY ON START UP -- LOADING PATH FROM CONFIG FILE");
		
		/*
		 * Check if the folder path exists. If not, the path is created automatically
		 */
		if(!checkIfExists(configPathFile))
		{
			createDirectory(configPathFile);
		}
		
		if(!checkIfExists(defaultSourcePathFile))
		{
			createDirectory(defaultSourcePathFile);
		}
		
		/*
		 * Check if the file exists. If not, the file is created automatically
		 */
		if(!checkIfExists(configFile))
		{
			createFile(configFile);
			setDefaultPath(configFile);
			JobController.setPath(defaultSourcePath);
		}else{
			try {
				BufferedReader br = new BufferedReader(new FileReader(configFile));
				
				line =  null;
				line = br.readLine();
				
				while((line != null) && (exit != true)){
					if(line.startsWith("#")){
						line = new String(br.readLine());
					}else{
						exit = true;
					}
				}
				
				br.close();
				
				if(line == null){
					/*
					 * The path is missing in the file
					 * has to be setted to the default path
					 */
					setDefaultPath(configFile);
					line = defaultSourcePath;
				}
				
				JobController.setPath(line);				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
