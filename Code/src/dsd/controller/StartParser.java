package dsd.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import dsd.calculations.TimeCalculations;
import dsd.dao.FilesDAO;
import dsd.model.enums.eFileType;

public class StartParser
{

	private static ArrayList<File> analogFileToBeParsed = new ArrayList<File>();
	private static ArrayList<File> sonarFileToBeParsed = new ArrayList<File>();
	private static GregorianCalendar analogCalendar = new GregorianCalendar(1800, 01, 01);
	private static GregorianCalendar sonarCalendar = new GregorianCalendar(1800, 01, 01);
	private static GregorianCalendar imageCalendar = new GregorianCalendar(1800, 01, 01);
	private static boolean calculations = Boolean.FALSE;
	private static boolean exit = Boolean.FALSE;
	private static CalculationsController calculationController = new CalculationsController(0, 0, 0);

	/**
	 * This method allows to set the timestamps for the 10min data, 1hour data and 1day data
	 * into the calculation controller main task.
	 * 
	 * @param timeStamp10min the timestamp for 10 minutes data
	 * @param timeStamp1hour the timestamp for 1 hour data
	 * @param timeStamp1day the timestamp for 1 day data
	 */
	public static void setTimeStamps(long timeStamp10min, long timeStamp1hour, long timeStamp1day)
	{
		calculationController.setTimeStamps(timeStamp10min, timeStamp1hour, timeStamp1day);
	}
	/**
	 * This method Check if there are new files to be parsed, then start the parsing process and at the
	 * end start the calculation process.
	 */
	public static void CheckAndStart()
	{
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		checkForNewData();
		System.out.println("survived to check for new data");
		startParsing();
		System.out.println("survived to parsing");
		startCalculations();
		System.out.println("survived to calculations");
	}

	/**
	 * This method Check if there are new files to be parsed
	 */
	private static void checkForNewData()
	{
		try
		{
			ArrayList<File> analogFileList = FilesDAO.getNewAneFiles(analogCalendar);
			ArrayList<File> sonarFileList = FilesDAO.getNewSonoFiles(sonarCalendar);
			ArrayList<File> imgList = null;
			String analogFileName;
			String sonarFileName;
			String analogTimestamp;
			String sonarTimestamp;
			int i = 0;

			if (!(analogFileList.isEmpty() || sonarFileList.isEmpty()))
			{
				exit = Boolean.FALSE;
				calculations = Boolean.FALSE;
				analogFileToBeParsed.clear();
				sonarFileToBeParsed.clear();

				while ((i < Math.min(analogFileList.size(), sonarFileList.size())) && (exit != Boolean.TRUE))
				{
					System.out.println("\ti:" + i + "\nexit:" + exit + "\nmin: "+ Math.min(analogFileList.size(), sonarFileList.size()));
					analogFileName = analogFileList.get(i).getName();
					sonarFileName = sonarFileList.get(i).getName();

					analogTimestamp = analogFileName.substring(6, analogFileName.length() - 4);
					sonarTimestamp = sonarFileName.substring(5, sonarFileName.length() - 4);

					if (analogTimestamp.compareTo(sonarTimestamp) == 0)
					{
						analogFileToBeParsed.add(analogFileList.get(i));
						sonarFileToBeParsed.add(sonarFileList.get(i));
						i++;
						analogCalendar = TimeCalculations.LabViewTimestampsToGregCalendar(Long.parseLong(analogTimestamp));
						sonarCalendar = TimeCalculations.LabViewTimestampsToGregCalendar(Long.parseLong(sonarTimestamp));
						calculations = Boolean.TRUE;
						System.out.println("\tin if true    i:" + i);
					}
					else
					{
						exit = Boolean.TRUE;
						System.out.println("\tin if false");
					}
				}
			}
			
			
			imgList = FilesDAO.getNewImages(imageCalendar);

			if(!imgList.isEmpty())
			{
				//Call the method to parse the images
			}
			
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method start the parsing process
	 */
	private static void startParsing()
	{

		for (int i = 0; i < Math.min(analogFileToBeParsed.size(), sonarFileToBeParsed.size()); i++)
		{
			ParserControler.ParseInputFile(analogFileToBeParsed.get(i), eFileType.Analog);
			ParserControler.ParseInputFile(sonarFileToBeParsed.get(i), eFileType.Sonar);
		}
	}
	
	/**
	 * This method start the calculation process
	 */
	private static void startCalculations()
	{
		Thread thread;

		if (calculations)
		{
			// call the calculations controller
			thread = new Thread(calculationController);
			thread.start();
		}
	}
}
