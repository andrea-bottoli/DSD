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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dsd.model.CalculatedData;
import dsd.model.enums.eCalculatedDataType;

public class CalculatedDataDAO
{
	private static String tableName10min = "sensor_data_10_min";
	private static String tableName1hour = "sensor_data_1_hour";
	private static String tableName1day = "sensor_data_1_day";
	
	private static String[] fields = new String[]{"wind_speed", "wind_direction", "wind_speed_max", "wind_direction_max", "hydrometer", "hydrometer_variance", "sonar",
			"sonar_variance", "sonar_perc_correct", "sonar_perc_wrong", "sonar_perc_outOfWater", "sonar_perc_error", "sonar_perc_uncertain", "safety_factor_00", 
			"safety_factor_01", "safety_factor_10", "safety_factor_11", "water_speed", "water_flow_rate", "timestamp"};

	public static int InsertCalculatedData(List<CalculatedData> listOfData, eCalculatedDataType dataType)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			try
			{
				String tableName = GetTableNameForDataType(dataType);
				counter += DAOProvider.InsertRowsSecure(tableName, StringUtils.join(fields, ','), con,
						PrepareMultipleValuesForInsert(listOfData));
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
			con.close();
			return counter;
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return 0;
	}

	public static int UpdateCalculatedData(List<CalculatedData> listOfData, eCalculatedDataType dataType)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				String tableName = GetTableNameForDataType(dataType);
				DAOProvider.UpdateRowsSecure(tableName, PrepareColumnsForUpdate(), con,
						PrepareMultipleValuesForUpdate(listOfData));
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
			con.close();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return 0;
	}

	public static ArrayList<CalculatedData> GetAllForPeriod(Calendar startDate, Calendar endDate, eCalculatedDataType dataType)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			ArrayList<CalculatedData> calculatedDataList = new ArrayList<CalculatedData>();
			try
			{
				String tableName = GetTableNameForDataType(dataType);
				Object[] parameters = new Object[2];
				parameters[0] = new Timestamp(startDate.getTimeInMillis());
				parameters[1] = new Timestamp(endDate.getTimeInMillis());
				ResultSet results = DAOProvider.SelectTableSecure(tableName, "*",
						" timestamp >= ? and timestamp <= ? ", "", con, parameters);
				while (results.next())
				{
					CalculatedData dataTuple = new CalculatedData();
					dataTuple.setCalulcatedDataID(results.getLong("ID"));
					dataTuple.setWindSpeed(results.getFloat(fields[0]));
					dataTuple.setWindDirection(results.getFloat(fields[1]));
					dataTuple.setWindSpeedMax(results.getFloat(fields[2]));
					dataTuple.setWindDirectionMax(results.getFloat(fields[3]));
					dataTuple.setHydrometer(results.getFloat(fields[4]));
					dataTuple.setHydrometerVariance(results.getFloat(fields[5]));
					dataTuple.setSonar(results.getFloat(fields[6]));
					dataTuple.setSonarVariance(results.getFloat(fields[7]));
					dataTuple.setSonarPercCorrect(results.getFloat(fields[8]));
					dataTuple.setSonarPercWrong(results.getFloat(fields[9]));
					dataTuple.setSonarPercOutOfWater(results.getFloat(fields[10]));
					dataTuple.setSonarPercError(results.getFloat(fields[11]));
					dataTuple.setSonarPercUncertain(results.getFloat(fields[12]));
					dataTuple.setSafetyFactor00(results.getFloat(fields[13]));
					dataTuple.setSafetyFactor01(results.getFloat(fields[14]));
					dataTuple.setSafetyFactor10(results.getFloat(fields[15]));
					dataTuple.setSafetyFactor11(results.getFloat(fields[16]));
					dataTuple.setWaterSpeed(results.getFloat(fields[17]));
					dataTuple.setWaterFlowRate(results.getFloat(fields[18]));
					dataTuple.setTimestamp(results.getTimestamp(fields[19]).getTime());
					calculatedDataList.add(dataTuple);
				}
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
			con.close();
			return calculatedDataList;
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return null;

	}
	
	public static long GetMaxTimestamp(eCalculatedDataType eDataType)
	{
		long timestamp = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				String tableName = GetTableNameForDataType(eDataType);
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " max(timestamp) ", "", "",con, null);
				while (results.next())
				{
					timestamp = results.getTimestamp(1).getTime();
				}
			}
			catch (Exception exc)
			{
//				exc.printStackTrace();
				timestamp = 0;
			}
			con.close();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return timestamp;
	}
	
	public static long GetCount(eCalculatedDataType eDataType)
	{
		long count = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				String tableName = GetTableNameForDataType(eDataType);
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " count(*) ", "", "",
						con, null);
				while (results.next())
				{
					count = results.getLong(1);
				}
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
			con.close();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return count;
	}

	private static Object[][] PrepareMultipleValuesForInsert(List<CalculatedData> listOfData)
	{
		Object[][] valueArray = new Object[listOfData.size()][20];
		for (int i = 0; i < listOfData.size(); i++)
		{
			valueArray[i][0] = new Float(listOfData.get(i).getWindSpeed());
			valueArray[i][1] = new Float(listOfData.get(i).getWindDirection());
			valueArray[i][2] = new Float(listOfData.get(i).getWindSpeedMax());
			valueArray[i][3] = new Float(listOfData.get(i).getWindDirectionMax());
			valueArray[i][4] = new Float(listOfData.get(i).getHydrometer());
			valueArray[i][5] = new Float(listOfData.get(i).getHydrometerVariance());
			valueArray[i][6] = new Float(listOfData.get(i).getSonar());
			valueArray[i][7] = new Float(listOfData.get(i).getSonarVariance());
			valueArray[i][8] = new Float(listOfData.get(i).getSonarPercCorrect());
			valueArray[i][9] = new Float(listOfData.get(i).getSonarPercWrong());
			valueArray[i][10] = new Float(listOfData.get(i).getSonarPercOutOfWater());
			valueArray[i][11] = new Float(listOfData.get(i).getSonarPercError());
			valueArray[i][12] = new Float(listOfData.get(i).getSonarPercUncertain());
			valueArray[i][13] = new Float(listOfData.get(i).getSafetyFactor00());
			valueArray[i][14] = new Float(listOfData.get(i).getSafetyFactor01());
			valueArray[i][15] = new Float(listOfData.get(i).getSafetyFactor10());
			valueArray[i][16] = new Float(listOfData.get(i).getSafetyFactor11());
			valueArray[i][17] = new Float(listOfData.get(i).getWaterSpeed());
			valueArray[i][18] = new Float(listOfData.get(i).getWaterFlowRate());
			valueArray[i][19] = new Timestamp(listOfData.get(i).getTimestamp());
		}
		return valueArray;
	}

	private static Object[][] PrepareMultipleValuesForUpdate(List<CalculatedData> listOfData)
	{
		Object[][] valueArray = new Object[listOfData.size()][21];
		for (int i = 0; i < listOfData.size(); i++)
		{
			valueArray[i][0] = new Float(listOfData.get(i).getCalulcatedDataID());
			valueArray[i][1] = new Float(listOfData.get(i).getWindSpeed());
			valueArray[i][2] = new Float(listOfData.get(i).getWindDirection());
			valueArray[i][3] = new Float(listOfData.get(i).getWindSpeedMax());
			valueArray[i][4] = new Float(listOfData.get(i).getWindDirectionMax());
			valueArray[i][5] = new Float(listOfData.get(i).getHydrometer());
			valueArray[i][6] = new Float(listOfData.get(i).getHydrometerVariance());
			valueArray[i][7] = new Float(listOfData.get(i).getSonar());
			valueArray[i][8] = new Float(listOfData.get(i).getSonarVariance());
			valueArray[i][9] = new Float(listOfData.get(i).getSonarPercCorrect());
			valueArray[i][10] = new Float(listOfData.get(i).getSonarPercWrong());
			valueArray[i][11] = new Float(listOfData.get(i).getSonarPercOutOfWater());
			valueArray[i][12] = new Float(listOfData.get(i).getSonarPercError());
			valueArray[i][13] = new Float(listOfData.get(i).getSonarPercUncertain());
			valueArray[i][14] = new Float(listOfData.get(i).getSafetyFactor00());
			valueArray[i][15] = new Float(listOfData.get(i).getSafetyFactor01());
			valueArray[i][16] = new Float(listOfData.get(i).getSafetyFactor10());
			valueArray[i][17] = new Float(listOfData.get(i).getSafetyFactor11());
			valueArray[i][18] = new Float(listOfData.get(i).getWaterSpeed());
			valueArray[i][19] = new Float(listOfData.get(i).getWaterFlowRate());
			valueArray[i][20] = new Timestamp(listOfData.get(i).getTimestamp());
		}
		return valueArray;
	}

	private static String[] PrepareColumnsForUpdate()
	{
		String[] columnsArray = new String[21];
		columnsArray[0] = "ID";
		columnsArray[1] = fields[0];
		columnsArray[2] = fields[1];
		columnsArray[3] = fields[2];
		columnsArray[4] = fields[3];
		columnsArray[5] = fields[4];
		columnsArray[6] = fields[5];
		columnsArray[7] = fields[6];
		columnsArray[8] = fields[7];
		columnsArray[9] = fields[8];
		columnsArray[10] = fields[9];
		columnsArray[11] = fields[10];
		columnsArray[12] = fields[11];
		columnsArray[13] = fields[12];
		columnsArray[14] = fields[13];
		columnsArray[15] = fields[14];
		columnsArray[16] = fields[15];
		columnsArray[17] = fields[16];
		columnsArray[18] = fields[17];
		columnsArray[19] = fields[18];
		columnsArray[20] = fields[19];
		return columnsArray;
	}
	
	private static String GetTableNameForDataType(eCalculatedDataType dataType)
	{
		switch (dataType)
		{
			case TenMinutes :
				return tableName10min;
			case OneHour :
				return tableName1hour;
			case OneDay :
				return tableName1day;
			default :
				throw new IllegalArgumentException("Unrecognized data type");
		}
	}
}
