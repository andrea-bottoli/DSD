package dsd.controller;

import java.io.*;
import java.util.*;

import javax.el.MethodNotFoundException;

import org.apache.commons.lang3.StringUtils;

import dsd.calculations.TimeCalculations;
import dsd.model.RawData;
import dsd.model.eFileType;
import dsd.model.eSonarType;

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
				default:
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
							data.setSonar(Float
									.parseFloat(line.trim().substring(1, line.trim().length() - 1)));
							data.setSonarType(eSonarType.UncertainData);
						}
					}
					else
					{
						data.setSonar(Float.parseFloat(line.trim().substring(1, line.trim().length())));
						data.setSonarType(eSonarType.CorrectData);
					}
				}
				else
				{
					data.setSonar(Float.parseFloat(line.trim().substring(0, line.trim().length())));
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
		while ((line = br.readLine()) != null)
		{
			RawData data = new RawData();
			String[] inputs = StringUtils.split(line);
			double windSpeed = Double.parseDouble(inputs[0]);
			double windDirection = Double.parseDouble(inputs[2]);
			double hydrometer = Double.parseDouble(inputs[1]);
			long timestamp = (long)Double.parseDouble(inputs[3]);

			data.setWindSpeed((float) windSpeed);
			data.setWindDirection((float) windDirection);
			data.setHydrometer((float) hydrometer);
			data.setTimestamp(TimeCalculations.LabViewTimestampGregToMiliSeconds(timestamp));
			rawDataList.add(data);
			data = new RawData();
		}
	}
}
