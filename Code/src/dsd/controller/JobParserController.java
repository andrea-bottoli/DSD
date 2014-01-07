/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
package dsd.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ListIterator;

import dsd.calculations.TimeCalculations;
import dsd.dao.FilesDAO;
import dsd.model.enums.eFileType;

public class JobParserController
{

	private static ArrayList<File> analogFilesToBeParsed = new ArrayList<File>();
	private static ArrayList<File> sonarFilesToBeParsed = new ArrayList<File>();
	private static ArrayList<File> imageMnFilesToBeParsed = new ArrayList<File>();
	private static ArrayList<File> imageMoFilesToBeParsed = new ArrayList<File>();
	
	private static GregorianCalendar inputSensorsCalendar = new GregorianCalendar(1800, 01, 01);
	private static GregorianCalendar imageMnCalendar = new GregorianCalendar(1800, 01, 01);
	private static GregorianCalendar imageMoCalendar = new GregorianCalendar(1800, 01, 01);
	
	private static boolean enable = Boolean.FALSE;
	private static boolean exit = Boolean.FALSE;
	
	
	/**
	 * THis method sets the source path into the file and parser dao
	 * @param path source path to be setted
	 */
	public static void setPath(String path)
	{
		FilesDAO.setSourcePath(path);
	}
	
	
	/**
	 * This method allows to set the timestamps for the 10min data, 1hour data and 1day data
	 * into the calculation controller main task.
	 * 
	 * @param analogMilliseconds the timestamp for analog gregorian calendar to be set
	 * @param sonarMilliseconds the timestamp for sonar gregorian calendar to be set
	 * @param imageMnMilliseconds the timestamp for mantova image gregorian calendar to be set
	 */
	public static void setParserTimeStamps(long inputSensorsMilliseconds, long imageMnMilliseconds, long imageMoMilliseconds)
	{
		inputSensorsCalendar.setTime(new Date(inputSensorsMilliseconds));
		imageMnCalendar.setTime(new Date(imageMnMilliseconds));
		imageMoCalendar.setTime(new Date(imageMoMilliseconds));
	}
	
	/**
	 * This method Check if there are new files to be parsed, then start the parsing process and at the
	 * end start the calculation process.
	 */
	public static void CheckAndParse()
	{
		checkForNewData();
		startParsing();
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
			String analogNextFileName;
			String sonarNextFileName;
			String analogNextTimestamp;
			String sonarNextTimestamp;
			String mantovaFileName;
			String modenaFileName;
			String mantovaTimestamp;
			String modenaTimestamp;
			int i;
			
			enable = false;
			
			System.out.println("-> It's checking for new data");
			
			if (!(analogFileList.isEmpty() || sonarFileList.isEmpty()))
			{
				exit = Boolean.FALSE;
				i = 0;
				analogFilesToBeParsed.clear();
				sonarFilesToBeParsed.clear();
				while ((i < Math.min(analogFileList.size(), sonarFileList.size())) && (exit != Boolean.TRUE))
				{
					analogFileName ="";
					sonarFileName ="";
					analogTimestamp ="";
					sonarTimestamp ="";
					analogNextFileName ="";
					sonarNextFileName ="";
					analogNextTimestamp ="";
					sonarNextTimestamp ="";
					
					analogFileName = analogFileList.get(i).getName();
					sonarFileName = sonarFileList.get(i).getName();

					analogTimestamp = analogFileName.substring(6, analogFileName.length() - 4);
					sonarTimestamp = sonarFileName.substring(5, sonarFileName.length() - 4);
					
					if (analogTimestamp.compareTo(sonarTimestamp) == 0)
					{
						analogFilesToBeParsed.add(analogFileList.get(i));
						sonarFilesToBeParsed.add(sonarFileList.get(i));
						enable = true;
						i++;
						inputSensorsCalendar = TimeCalculations.LabViewTimestampsToGregCalendar(Long.parseLong(analogTimestamp));
					}
					else
					{
						try
						{
							analogNextFileName = analogFileList.get(i+1).getName();
							sonarNextFileName = sonarFileList.get(i+1).getName();
	
							analogNextTimestamp = analogNextFileName.substring(6, analogNextFileName.length() - 4);
							sonarNextTimestamp = sonarNextFileName.substring(5, sonarNextFileName.length() - 4);
							
							if(analogNextTimestamp.equals(sonarNextTimestamp) && analogNextFileName!="" && sonarNextFileName!="")
							{
								analogFilesToBeParsed.add(analogFileList.get(i));
								sonarFilesToBeParsed.add(sonarFileList.get(i));
								enable = true;
								i++;
								inputSensorsCalendar = TimeCalculations.LabViewTimestampsToGregCalendar(Long.parseLong(analogTimestamp));
							}
							else
							{
								exit = Boolean.TRUE;
							}
						}
						catch(IndexOutOfBoundsException e)
						{
							exit = Boolean.TRUE;
						}
					}
				}
			}
			
//			imgMnList = FilesDAO.getMantovaImages(imageMnCalendar, imgMnList);
//			imgMoList = FilesDAO.getModenaImages(imageMoCalendar, imgMoList);

			if(!imgMnList.isEmpty())
			{
				exit = Boolean.FALSE;
				i = 0;
				imageMnFilesToBeParsed.clear();
				
				while (i < imgMnList.size())
				{
					mantovaFileName = imgMnList.get(i).getName();
					mantovaTimestamp = mantovaFileName.substring(7, mantovaFileName.length() - 4);

					imageMnFilesToBeParsed.add(imgMnList.get(i));
					enable = true;
					i++;
					imageMnCalendar = (GregorianCalendar) TimeCalculations.PictureTimeToGregCalendar(mantovaTimestamp);
				}
			}
			
			
			
			if(!imgMoList.isEmpty())
			{
				exit = Boolean.FALSE;
				i = 0;
				imageMoFilesToBeParsed.clear();
				
				while (i < imgMoList.size())
				{
					modenaFileName = imgMoList.get(i).getName();
					modenaTimestamp = modenaFileName.substring(6, modenaFileName.length() - 4);

					imageMoFilesToBeParsed.add(imgMoList.get(i));
					enable = true;
					i++;
					imageMoCalendar = (GregorianCalendar) TimeCalculations.PictureTimeToGregCalendar(modenaTimestamp);
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
		
		if(enable)
		{
			System.out.println("-> It's parsing");
			
			for (int i = 0; i < Math.min(analogFilesToBeParsed.size(), sonarFilesToBeParsed.size()); i++)
			{			
				ParserControler.ParseInputFile(analogFilesToBeParsed.get(i), eFileType.Analog);
				ParserControler.ParseInputFile(sonarFilesToBeParsed.get(i), eFileType.Sonar);
			}
			
			while(imgMnIt.hasNext() || imgMoIt.hasNext())
			{
				if(imgMnIt.hasNext()){
					ParserControler.ParseInputFile(imgMnIt.next(), eFileType.Mantova);
				}
				
				if(imgMnIt.hasNext()){
					ParserControler.ParseInputFile(imgMnIt.next(), eFileType.Modena);
				}
			}			
		}
	}
}
