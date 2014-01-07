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

import dsd.model.RawData;
import dsd.model.enums.eFileType;
import dsd.model.enums.eSonarType;

public class RawDataDAO
{

	private static String tableName = "sensor_data_raw";
	private static String[] fields = new String[]{"wind_speed", "wind_direction", "hydrometer", "sonar",
			"sonar_type", "timestamp"};

	public static int InsertRawData(List<RawData> listOfData)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			try
			{
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

	public static int UpdateRawData(List<RawData> listOfData, eFileType fileType)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
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

	public static ArrayList<RawData> GetAllForPeriod(Calendar startDate, Calendar endDate)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			ArrayList<RawData> rawDataList = new ArrayList<RawData>();
			try
			{
				Object[] parameters = new Object[2];
				parameters[0] = new Timestamp(startDate.getTimeInMillis());
				parameters[1] = new Timestamp(endDate.getTimeInMillis());
				ResultSet results = DAOProvider.SelectTableSecure(tableName, "*",
						" timestamp >= ? and timestamp <= ? ", "", con, parameters);
				while (results.next())
				{
					RawData dataTuple = new RawData();
					dataTuple.setRawDataID(results.getInt("ID"));
					dataTuple.setWindSpeed(results.getFloat(fields[0]));
					dataTuple.setWindDirection(results.getFloat(fields[1]));
					dataTuple.setHydrometer(results.getFloat(fields[2]));
					dataTuple.setSonar(results.getFloat(fields[3]));
					dataTuple.setSonarType(eSonarType.getSonarType(results.getInt(fields[4])));
					dataTuple.setTimestamp(results.getTimestamp(fields[5]).getTime());
					rawDataList.add(dataTuple);
				}
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
			con.close();
			return rawDataList;
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return null;

	}
	
	public static long GetMinTimestamp()
	{
		long timestamp = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " min(timestamp) ", "", "",
						con, null);
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
			timestamp = 0;
		}
		return timestamp;
	
	}
	
	public static long GetMaxTimestamp()
	{
		long timestamp = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " max(timestamp) ", "", "",
						con, null);
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
	
	public static long GetCount()
	{
		long count = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
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

	private static Object[][] PrepareMultipleValuesForInsert(List<RawData> listOfData)
	{
		Object[][] valueArray = new Object[listOfData.size()][6];
		for (int i = 0; i < listOfData.size(); i++)
		{
			valueArray[i][0] = new Float(listOfData.get(i).getWindSpeed());
			valueArray[i][1] = new Float(listOfData.get(i).getWindDirection());
			valueArray[i][2] = new Float(listOfData.get(i).getHydrometer());
			valueArray[i][3] = new Float(listOfData.get(i).getSonar());
			valueArray[i][4] = new Integer(listOfData.get(i).getSonarType() == null ? 0 : listOfData.get(i)
					.getSonarType().getCode());
			valueArray[i][5] = new Timestamp(listOfData.get(i).getTimestamp());
		}
		return valueArray;
	}

	private static Object[][] PrepareMultipleValuesForUpdate(List<RawData> listOfData)
	{
		Object[][] valueArray = new Object[listOfData.size()][7];
		for (int i = 0; i < listOfData.size(); i++)
		{
			valueArray[i][0] = new Float(listOfData.get(i).getRawDataID());
			valueArray[i][1] = new Float(listOfData.get(i).getWindSpeed());
			valueArray[i][2] = new Float(listOfData.get(i).getWindDirection());
			valueArray[i][3] = new Float(listOfData.get(i).getHydrometer());
			valueArray[i][4] = new Float(listOfData.get(i).getSonar());
			valueArray[i][5] = new Integer(listOfData.get(i).getSonarType() == null ? 0 : listOfData.get(i)
					.getSonarType().getCode());
			valueArray[i][6] = new Timestamp(listOfData.get(i).getTimestamp());
		}
		return valueArray;
	}

	private static String[] PrepareColumnsForUpdate()
	{
		String[] columnsArray = new String[7];
		columnsArray[0] = "ID";
		columnsArray[1] = fields[0];
		columnsArray[2] = fields[1];
		columnsArray[3] = fields[2];
		columnsArray[4] = fields[3];
		columnsArray[5] = fields[4];
		columnsArray[6] = fields[5];
		return columnsArray;
	}

}
