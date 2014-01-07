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
package dsd.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import dsd.model.enums.eFileType;

public class FilesDAO
{

	// Just for the moment as dirty solution!!
	private static String anaFileDir = "";
	private static String sonarFileDir = "";
	private static String pictureModenaFileDir = "";
	private static String pictureMantovaFileDir = "";
	
	public static void setSourcePath(String path)
	{
		anaFileDir = path;
		sonarFileDir = path;
		pictureModenaFileDir = path;
		pictureMantovaFileDir = path;
	}
	
	
	
	public static ArrayList<File> getNewAneFiles(GregorianCalendar date)
	{
		ArrayList<File> returnList = new ArrayList<File>();

		File file = new File(anaFileDir);
		File[] fileArray = file.listFiles();
		
		for (int i = 0; i < fileArray.length; i++)
		{
			String fileName = fileArray[i].getName();
			if (fileName.substring(0, 6).equals("analog"))
			{
				String timestamp = fileName.substring(6, fileName.length() - 4);
				if (date.before(dsd.calculations.TimeCalculations.LabViewTimestampsToGregCalendar(Long.parseLong(timestamp))))
				{
					returnList.add(fileArray[i]);
				}
			}
		}

		return returnList;

	}

	public static ArrayList<File> getNewSonoFiles(GregorianCalendar date)
	{
		ArrayList<File> returnList = new ArrayList<File>();

		File file = new File(sonarFileDir);
		File[] fileArray = file.listFiles();

		for (int i = 0; i < fileArray.length; i++)
		{
			String fileName = fileArray[i].getName();
			if (fileName.substring(0, 5).equals("sonar"))
			{
				String timestamp = fileName.substring(5, fileName.length() - 4);
				if (date.before(dsd.calculations.TimeCalculations.LabViewTimestampsToGregCalendar(Long
						.parseLong(timestamp))))
				{
					returnList.add(fileArray[i]);
				}
			}
		}

		return returnList;

	}

	/**
	 * Returns all Images newer than given date. Use getMantovaImages() and
	 * getModenaImages() inside.
	 * 
	 * @param date
	 * @return
	 */
	public static ArrayList<File> getNewImages(GregorianCalendar date)
	{
		ArrayList<File> returnList = new ArrayList<File>();

		returnList = getMantovaImages(date, returnList);
		returnList = getModenaImages(date, returnList);

		return returnList;
	}

	public static ArrayList<File> getMantovaImages(GregorianCalendar date, ArrayList<File> fileList)
	{
		String cameraType = eFileType.Mantova.toString();
		File file = new File(pictureMantovaFileDir);
		File[] fileArray = file.listFiles();

		for (int i = 0; i < fileArray.length; i++)
		{
			String fileName = fileArray[i].getName();
			if (fileName.substring(0, cameraType.length()).equalsIgnoreCase(cameraType))
			{
				String timestamp = fileName.substring(cameraType.length(), fileName.length() - 4);
				if (date.before(dsd.calculations.TimeCalculations.PictureTimeToGregCalendar(timestamp)))
					;
				{
					fileList.add(fileArray[i]);
				}
			}
		}

		return fileList;
	}

	public static ArrayList<File> getModenaImages(GregorianCalendar date, ArrayList<File> fileList)
	{
		String cameraType = eFileType.Modena.toString();
		File file = new File(pictureModenaFileDir);
		File[] fileArray = file.listFiles();

		for (int i = 0; i < fileArray.length; i++)
		{
			String fileName = fileArray[i].getName();
			if (fileName.substring(0, cameraType.length()).equalsIgnoreCase(cameraType))
			{
				String timestamp = fileName.substring(cameraType.length(), fileName.length() - 4);
				if (date.before(dsd.calculations.TimeCalculations.PictureTimeToGregCalendar(timestamp)))
					;
				{
					fileList.add(fileArray[i]);
				}
			}
		}

		return fileList;
	}

}
