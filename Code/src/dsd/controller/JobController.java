package dsd.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ListIterator;

import dsd.calculations.TimeCalculations;
import dsd.dao.FilesDAO;
import dsd.model.enums.eFileType;

public class JobController
{

	private static ArrayList<File> analogFilesToBeParsed = new ArrayList<File>();
	private static ArrayList<File> sonarFilesToBeParsed = new ArrayList<File>();
	private static ArrayList<File> imageMnFilesToBeParsed = new ArrayList<File>();
	private static ArrayList<File> imageMoFilesToBeParsed = new ArrayList<File>();
	
	private static GregorianCalendar inputSensorsCalendar = new GregorianCalendar(1800, 01, 01);
	private static GregorianCalendar imageCalendar = new GregorianCalendar(1800, 01, 01);
	
	private static boolean calculations = Boolean.FALSE;
	private static boolean exit = Boolean.FALSE;
	
	private static CalculationsController calculationController = new CalculationsController(0, 0, 0);
	
	
	/**
	 * This method allows to set the timestamps for the 10min data, 1hour data and 1day data
	 * into the calculation controller main task.
	 * 
	 * @param analogMilliseconds the timestamp for analog gregorian calendar to be set
	 * @param sonarMilliseconds the timestamp for sonar gregorian calendar to be set
	 * @param imageMnMilliseconds the timestamp for mantova image gregorian calendar to be set
	 */
	public static void setParserTimeStamps(long inputSensorsMilliseconds, long imageMilliseconds)
	{
		inputSensorsCalendar.setTime(new Date(inputSensorsMilliseconds));
		imageCalendar.setTime(new Date(imageMilliseconds));
	}
	
	/**
	 * This method allows to set the timestamps for the 10min data, 1hour data and 1day data
	 * into the calculation controller main task.
	 * 
	 * @param timeStamp10min the timestamp for 10 minutes data
	 * @param timeStamp1hour the timestamp for 1 hour data
	 * @param timeStamp1day the timestamp for 1 day data
	 */
	public static void setMathEngineTimeStamps(long timeStamp10min, long timeStamp1hour, long timeStamp1day)
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
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
	}

	/**
	 * This method Check if there are new files to be parsed
	 */
	private static void checkForNewData()
	{
		try
		{
			ArrayList<File> analogFileList = FilesDAO.getNewAneFiles(inputSensorsCalendar);
			ArrayList<File> sonarFileList = FilesDAO.getNewSonoFiles(inputSensorsCalendar);
			ArrayList<File> imgMnList = new ArrayList<File>();
			ArrayList<File> imgMoList = new ArrayList<File>();
			String analogFileName;
			String sonarFileName;
			String analogTimestamp;
			String sonarTimestamp;
			String mantovaFileName;
			String modenaFileName;
			String mantovaTimestamp;
			String modenaTimestamp;
			int i;

			if (!(analogFileList.isEmpty() || sonarFileList.isEmpty()))
			{
				exit = Boolean.FALSE;
				i = 0;
				calculations = Boolean.FALSE;
				analogFilesToBeParsed.clear();
				sonarFilesToBeParsed.clear();

				while ((i < Math.min(analogFileList.size(), sonarFileList.size())) && (exit != Boolean.TRUE))
				{
					System.out.println("\ti:" + i + "\nexit:" + exit + "\nmin: "+ Math.min(analogFileList.size(), sonarFileList.size()));
					analogFileName = analogFileList.get(i).getName();
					sonarFileName = sonarFileList.get(i).getName();

					analogTimestamp = analogFileName.substring(6, analogFileName.length() - 4);
					sonarTimestamp = sonarFileName.substring(5, sonarFileName.length() - 4);

					if (analogTimestamp.compareTo(sonarTimestamp) == 0)
					{
						analogFilesToBeParsed.add(analogFileList.get(i));
						sonarFilesToBeParsed.add(sonarFileList.get(i));
						i++;
						inputSensorsCalendar = TimeCalculations.LabViewTimestampsToGregCalendar(Long.parseLong(analogTimestamp));
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
			
			
			imgMnList = FilesDAO.getMantovaImages(imageCalendar, imgMnList);
			imgMoList = FilesDAO.getModenaImages(imageCalendar, imgMoList);

			if(!(imgMnList.isEmpty() || imgMoList.isEmpty()))
			{
				exit = Boolean.FALSE;
				i = 0;
				imageMnFilesToBeParsed.clear();
				imageMoFilesToBeParsed.clear();
				
				while ((i < Math.min(imageMnFilesToBeParsed.size(), imageMoFilesToBeParsed.size())) && (exit != Boolean.TRUE))
				{
					mantovaFileName = imgMnList.get(i).getName();
					modenaFileName = imgMoList.get(i).getName();

					mantovaTimestamp = mantovaFileName.substring(7, mantovaFileName.length() - 4);
					modenaTimestamp = modenaFileName.substring(6, modenaFileName.length() - 4);

					if (mantovaTimestamp.compareTo(modenaTimestamp) == 0)
					{
						imageMnFilesToBeParsed.add(imgMnList.get(i));
						imageMoFilesToBeParsed.add(imgMoList.get(i));
						i++;
						inputSensorsCalendar = (GregorianCalendar) TimeCalculations.PictureTimeToGregCalendar(mantovaTimestamp);
					}
					else
					{
						exit = Boolean.TRUE;
					}
				}
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
		ListIterator<File> imgMnIt = imageMnFilesToBeParsed.listIterator();
		ListIterator<File> imgMoIt = imageMoFilesToBeParsed.listIterator();

		for (int i = 0; i < Math.min(analogFilesToBeParsed.size(), sonarFilesToBeParsed.size()); i++)
		{
			ParserControler.ParseInputFile(analogFilesToBeParsed.get(i), eFileType.Analog);
			ParserControler.ParseInputFile(sonarFilesToBeParsed.get(i), eFileType.Sonar);
			/*
			 * Saving of the inputSensorCalendar into DB
			 * TODO
			 */
		}
		
		while(imgMnIt.hasNext() || imgMoIt.hasNext())
		{
			if(imgMnIt.hasNext()){
				ParserControler.ParseInputFile(imgMnIt.next(), eFileType.Mantova);
			}
			
			if(imgMnIt.hasNext()){
				ParserControler.ParseInputFile(imgMnIt.next(), eFileType.Modena);
			}
			
			/*
			 * Saving of the imageCalendar into DB
			 * TODO
			 */
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
