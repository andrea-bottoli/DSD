package dsd.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.el.MethodNotFoundException;

import org.apache.commons.lang3.StringUtils;

import dsd.calculations.InputConversion;
import dsd.calculations.TimeCalculations;
import dsd.dao.PicturesDAO;
import dsd.model.Picture;
import dsd.model.RawData;
import dsd.model.enums.eCameraType;
import dsd.model.enums.eFileType;
import dsd.model.enums.eSonarType;

public class ParserControler
{
	/*
	 * parse the sonar file or analog file and update the raw data
	 */
	public static void ParseInputFile(File file, eFileType fileType)
	{
		BufferedReader br = null;
		List<RawData> rawDataList = new ArrayList<RawData>();
		try
		{
			br = new BufferedReader(new FileReader(file.getPath()));
			switch (fileType)
			{
				case Sonar :
					ReadSonarData(br, rawDataList);
					break;
				case Analog :
					ReadAnalogData(br, rawDataList);
					break;
				default :
					throw new MethodNotFoundException();
			}
			// TO-DO call method for inserting the data in the database
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (br != null)
					br.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/*
	 * Read sonar data file values and prepare the list of raw data
	 */
	private static void ReadSonarData(BufferedReader br, List<RawData> rawDataList) throws IOException
	{
		RawData data = new RawData();
		String line = null;
		boolean timestampIsLastReadLine = true;
		while ((line = br.readLine()) != null)
		{
			boolean lineStartsWithWhitespace = Character.isWhitespace(line.charAt(0));
			if (lineStartsWithWhitespace)
			{
				data.setTimestamp(TimeCalculations.LabViewTimestampGregToMiliSeconds(Long.parseLong(line
						.trim())));
				timestampIsLastReadLine = true;
				rawDataList.add(data);
				data = new RawData();
			}

			if (!lineStartsWithWhitespace && timestampIsLastReadLine)
			{
				if (line.trim().startsWith("R"))
				{
					if (line.trim().endsWith("E"))
					{
						if (line.contains("99.99"))
						{
							data.setSonar(99.99f);
							data.setSonarType(eSonarType.SonarOutOfWaterData);
						}
						else
						{
							data.setSonar((float)InputConversion.sonarConversion(Double.parseDouble(line.trim().substring(1, line.trim().length() - 1))));
							data.setSonarType(eSonarType.UncertainData);
						}
					}
					else
					{
						data.setSonar((float)InputConversion.sonarConversion(Double.parseDouble(line.trim().substring(1, line.trim().length()))));
						data.setSonarType(eSonarType.CorrectData);
					}
				}
				else
				{
					data.setSonar((float)InputConversion.sonarConversion(Double.parseDouble(line.trim().substring(0, line.trim().length()))));
					data.setSonarType(eSonarType.WrongData);
				}
				timestampIsLastReadLine = false;
			}
			else if (lineStartsWithWhitespace)
			{
				data.setSonar(0.0f);
				data.setSonarType(eSonarType.ErrorData);
			}
			else if (!lineStartsWithWhitespace || timestampIsLastReadLine)
			{
				System.out.println("Unexpected behaviour on line:");
			}
		}
	}

	/*
	 * Read analog data file values and prepare the list of raw data
	 */
	private static void ReadAnalogData(BufferedReader br, List<RawData> rawDataList) throws IOException
	{
		String line = null;
		double windSpeed = 0;
		double windDirection = 0;
		double hydrometer = 0;
		long timestamp = 0;
		
		while ((line = br.readLine()) != null)
		{
			RawData data = new RawData();
			String[] inputs = StringUtils.split(line);
			windSpeed = InputConversion.windSpeedConversion(Double.parseDouble(inputs[0]));
			windDirection = InputConversion.windDirectionConversion(Double.parseDouble(inputs[2]));
			hydrometer = InputConversion.waterLevelConversion(Double.parseDouble(inputs[1]));
			timestamp = (long) Double.parseDouble(inputs[3]);

			data.setWindSpeed((float) windSpeed);
			data.setWindDirection((float) windDirection);
			data.setHydrometer((float) hydrometer);
			data.setTimestamp(TimeCalculations.LabViewTimestampGregToMiliSeconds(timestamp));
			rawDataList.add(data);
			data = new RawData();
		}
	}

	private static void ReadImageData(GregorianCalendar date)
	{
		ArrayList<File> fileList = dsd.dao.FilesDAO.getNewImages(date);
		ArrayList<Picture> picList = new ArrayList<Picture>();
		Iterator<File> fileIterator = fileList.iterator();
		while (fileIterator.hasNext())
		{
			File item = fileIterator.next();
			Picture newPicture = new Picture();
			newPicture.setPath(item.getAbsolutePath());
			if (item.getName().substring(0, eCameraType.Mantova.toString().length())
					.equalsIgnoreCase(eCameraType.Mantova.toString()))
			{
				newPicture.setCamera(eCameraType.Mantova.getCode());
				newPicture.setTimestamp(TimeCalculations.PictureTimestampToGregToMiliSeconds(item.getName()
						.substring(eCameraType.Mantova.toString().length(), item.getName().length() - 4)));
			}
			else if (item.getName().substring(0, eCameraType.Modena.toString().length())
					.equalsIgnoreCase(eCameraType.Modena.toString()))
			{
				newPicture.setCamera(eCameraType.Modena.getCode());
				newPicture.setTimestamp(TimeCalculations.PictureTimestampToGregToMiliSeconds(item.getName()
						.substring(eCameraType.Modena.toString().length(), item.getName().length() - 4)));
			}
			picList.add(newPicture);

		}

		PicturesDAO.setPictures(picList);

	}
}
