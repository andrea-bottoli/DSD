/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brcic, Dzana Kujan, Nikola Radisavljevic, Jorn Tillmanns, Miraldi Fifo
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import dsd.calculations.InputConversion;
import dsd.calculations.TimeCalculations;
import dsd.model.ParsedInputFile;
import dsd.model.RawData;
import dsd.model.enums.eFileType;
import dsd.model.enums.eSonarType;

public class ParserControler
{
	
	/*
	 * parse the sonar file or analog file and update the raw data
	 */
	public static void ParseInputFile(File file, eFileType fileType)
	{
		if (ParsedInputFilesController.IsAlreadyParsed(file.getName()))
		{
			return;
		}
		List<RawData> rawDataList = new ArrayList<RawData>();
		boolean successfullyParsed = false;
		ParsedInputFile parsedInputFile = new ParsedInputFile();
		parsedInputFile.setName(file.getName());
		parsedInputFile.setStoredPath(GetStoredPath(file, fileType));
		parsedInputFile.setTimestamp(GetFileTimestamp(file, fileType));
		parsedInputFile.setFileType(fileType);
		ParserControler parserController = new ParserControler();
		switch (fileType)
		{
			case Sonar :
				successfullyParsed = ParseSonarData(file, rawDataList);
				break;
			case Analog :
				successfullyParsed = ParseAnalogData(file, rawDataList);
				break;
			case Mantova :
				successfullyParsed = TransferImageToSite(file, new File(parserController.ReturnRootPath()
						+ parsedInputFile.getStoredPath()));
				break;
			case Modena :
				successfullyParsed = TransferImageToSite(file, new File(parserController.ReturnRootPath()
						+ parsedInputFile.getStoredPath()));
				break;
			default :
				throw new IllegalArgumentException("File type not recognized");
		}
		parsedInputFile.setSuccessfullyParsed(successfullyParsed);
		ParsedInputFilesController.StoreFileData(parsedInputFile);
		if (fileType.equals(eFileType.Analog) || fileType.equals(eFileType.Sonar))
		{
			if (rawDataList.size() > 0)
			{
				RawDataController.InsertOrUpdateRawData(rawDataList, fileType);
			}
			else
			{
				System.out.println("File " + file.getName() + " returned ZERO data!");
			}
		}
		System.out.println(file.getName() + " parsed = " + successfullyParsed);
	}

	private static String GetStoredPath(File file, eFileType fileType)
	{
		String storedFolderPathForPictures = "ParsedImages" + File.separatorChar;
		String storedFolderPathForTextFiles = "ParsedTextFiles" + File.separatorChar;

		switch (fileType)
		{
			case Sonar :
				return storedFolderPathForTextFiles + file.getName();
			case Analog :
				return storedFolderPathForTextFiles + file.getName();
			case Mantova :
				return storedFolderPathForPictures + file.getName();
			case Modena :
				return storedFolderPathForPictures + file.getName();
			default :
				throw new IllegalArgumentException("File type not recognized");
		}
	}
	private static long GetFileTimestamp(File file, eFileType fileType)
	{
		switch (fileType)
		{
			case Sonar :
				return TimeCalculations.LabViewTimestampGregToMiliSeconds(Long.parseLong(file.getName()
						.substring(fileType.toString().length(), file.getName().length() - 4)));
			case Analog :
				return TimeCalculations.LabViewTimestampGregToMiliSeconds(Long.parseLong(file.getName()
						.substring(fileType.toString().length(), file.getName().length() - 4)));
			case Mantova :
				return TimeCalculations.PictureTimestampToGregToMiliSeconds(file.getName().substring(
						fileType.toString().length(), file.getName().length() - 4));
			case Modena :
				return TimeCalculations.PictureTimestampToGregToMiliSeconds(file.getName().substring(
						fileType.toString().length(), file.getName().length() - 4));
			default :
				throw new IllegalArgumentException("File type not recognized");
		}
	}
	/*
	 * Read sonar data file values and prepare the list of raw data
	 */
	private static boolean ParseSonarData(File file, List<RawData> rawDataList)
	{
		BufferedReader br = null;
		int lineCounter = 0;
		try
		{
			br = new BufferedReader(new FileReader(file.getPath()));
			RawData data = new RawData();
			String line = null;
			boolean timestampIsLastReadLine = true;
			while ((line = br.readLine()) != null)
			{
				lineCounter++;
				if (line.length() == 0)
					continue;
				boolean lineStartsWithWhitespace = Character.isWhitespace(line.charAt(0));
				if (lineStartsWithWhitespace)
				{
					data.setTimestamp(TimeCalculations.LabViewTimestampGregToMiliSeconds(Long.parseLong(line
							.trim())));
					if (!(rawDataList.size() > 0 && rawDataList.get(rawDataList.size() - 1).getTimestamp() == data
							.getTimestamp()))
						rawDataList.add(data);
					else
						System.out
								.println(String
										.format("Touple on line: %s skipped because of same timestamp as the previous touple\n",
												lineCounter));
					timestampIsLastReadLine = true;
					data = new RawData();
				}

				if (!lineStartsWithWhitespace && timestampIsLastReadLine)
				{
					String[] lineData = StringUtils.split(line, '\t');
					if (lineData.length > 1)
					{
						if (lineData[0].trim().startsWith("R"))
						{
							if (lineData[0].trim().endsWith("E"))
							{
								if (lineData[0].contains("99.99"))
								{
									data.setSonar(99.99f);
									data.setSonarType(eSonarType.SonarOutOfWaterData);
								}
								else
								{
									data.setSonar((float) InputConversion.sonarConversion(Double
											.parseDouble(lineData[0].trim().substring(1,
													lineData[0].trim().length() - 1))));
									data.setSonarType(eSonarType.UncertainData);
								}
							}
							else
							{
								data.setSonar((float) InputConversion.sonarConversion(Double
										.parseDouble(lineData[0].trim().substring(1,
												lineData[0].trim().length()))));
								data.setSonarType(eSonarType.CorrectData);
							}
						}
						else
						{
							if (lineData[0].trim().length() == 0 || lineData[0].trim().equals("E1"))
							{
								data.setSonar(0.0f);
								data.setSonarType(eSonarType.ErrorData);
							}
							else
							{
								data.setSonar(0.0f);
								data.setSonarType(eSonarType.WrongData);
							}
						}
						data.setTimestamp(TimeCalculations.LabViewTimestampGregToMiliSeconds(Long
								.parseLong(lineData[1].trim())));
						timestampIsLastReadLine = true;
						rawDataList.add(data);
						data = new RawData();
					}
					else
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
									data.setSonar((float) InputConversion.sonarConversion(Double
											.parseDouble(line.trim().substring(1, line.trim().length() - 1))));
									data.setSonarType(eSonarType.UncertainData);
								}
							}
							else
							{
								data.setSonar((float) InputConversion.sonarConversion(Double.parseDouble(line
										.trim().substring(1, line.trim().length()))));
								data.setSonarType(eSonarType.CorrectData);
							}
						}
						else
						{
							if (line.trim().length() == 0 || line.trim().equals("E1"))
							{
								data.setSonar(0.0f);
								data.setSonarType(eSonarType.ErrorData);
							}
							else
							{
								data.setSonar(0.0f);
								data.setSonarType(eSonarType.WrongData);
							}
						}
						timestampIsLastReadLine = false;
					}
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
			return true;
		}
		catch (Exception e)
		{
			System.out.println(String.format("Error on line: %s\n", lineCounter));
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
		return false;
	}
	/*
	 * Read analog data file values and prepare the list of raw data
	 */
	private static boolean ParseAnalogData(File file, List<RawData> rawDataList)
	{
		BufferedReader br = null;
		int lineCounter = 0;
		try
		{
			br = new BufferedReader(new FileReader(file.getPath()));
			String line = null;
			double windSpeed = 0;
			double windDirection = 0;
			double hydrometer = 0;
			long timestamp = 0;

			while ((line = br.readLine()) != null)
			{
				RawData data = new RawData();
				String[] inputs = StringUtils.split(line);
				if (inputs.length < 4)
					continue;
				windSpeed = InputConversion.windSpeedConversion(Double.parseDouble(inputs[0]));
				windDirection = InputConversion.windDirectionConversion(Double.parseDouble(inputs[2]));
				hydrometer = InputConversion.waterLevelConversion(Double.parseDouble(inputs[1]));
				timestamp = (long) Double.parseDouble(inputs[3]);

				data.setWindSpeed((float) windSpeed);
				data.setWindDirection((float) windDirection);
				data.setHydrometer((float) hydrometer);
				data.setTimestamp(TimeCalculations.LabViewTimestampGregToMiliSeconds(timestamp));

				if (!(rawDataList.size() > 0 && rawDataList.get(rawDataList.size() - 1).getTimestamp() == data
						.getTimestamp()))
					rawDataList.add(data);
				else
					System.out.println(String.format(
							"Touple on line: %s skipped because of same timestamp as the previous touple\n",
							lineCounter));
			}
			return true;
		}
		catch (Exception e)
		{
			System.out.println(String.format("Error on line: %s\n", lineCounter));
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
		return false;
	}

	@SuppressWarnings("resource")
	private static boolean TransferImageToSite(File inFile, File outFile)
	{
		if (!outFile.exists())
		{
			try
			{
				outFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}

		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try
		{
			inChannel = new FileInputStream(inFile).getChannel();
			outChannel = new FileOutputStream(outFile).getChannel();
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = inChannel.size();
			long position = 0;
			while (position < size)
			{
				position += inChannel.transferTo(position, maxCount, outChannel);
			}
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (inChannel != null)
					inChannel.close();
				if (outChannel != null)
					outChannel.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return false;
	}

	private String ReturnRootPath()
	{
		// URL returned
		URL r = this.getClass().getResource("/");

		// path decoded
		String decoded = null;
		try
		{
			decoded = URLDecoder.decode(r.getFile(), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (decoded.startsWith("/"))
			decoded = decoded.replaceFirst("/", "");
		return decoded.replaceAll("WEB-INF/classes/", "");
	}
}
