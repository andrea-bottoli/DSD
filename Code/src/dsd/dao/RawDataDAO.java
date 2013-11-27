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

	private static Object[][] PrepareMultipleValuesForInsert(List<RawData> listOfData)
	{
		Object[][] valueArray = new Object[listOfData.size()][6];
		for (int i = 0; i < listOfData.size(); i++)
		{
			valueArray[i][0] = new Float(listOfData.get(i).getWindSpeed());
			valueArray[i][1] = new Float(listOfData.get(i).getWindDirection());
			valueArray[i][2] = new Float(listOfData.get(i).getHydrometer());
			valueArray[i][3] = new Float(listOfData.get(i).getSonar());
			valueArray[i][4] = new Integer(listOfData.get(i).getSonarType() == null ? 0 : listOfData.get(i).getSonarType()
					.getCode());
			valueArray[i][5] = new Timestamp(listOfData.get(i).getTimestamp());
		}
		return valueArray;
	}

	public static int UpdateRawData(List<RawData> listOfData, eFileType fileType)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			for (RawData rawData : listOfData)
			{
				try
				{
					Object[] wherePartParameters = new Object[1];
					wherePartParameters[0] = new Long(rawData.getRawDataID());
					counter += DAOProvider.UpdateRowSecure(tableName, PrepareColumnsForUpdate(fileType),
							" ID = ? ", con, PrepareValuesForUpdate(rawData, fileType), wherePartParameters);
				}
				catch (Exception exc)
				{
					exc.printStackTrace();
				}
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
						" timestamp > ? and timestamp < ? ", "", con, parameters);
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

	private static Object[] PrepareValuesForInsert(RawData dataTuple)
	{
		Object[] valueArray = new Object[6];
		valueArray[0] = new Float(dataTuple.getWindSpeed());
		valueArray[1] = new Float(dataTuple.getWindDirection());
		valueArray[2] = new Float(dataTuple.getHydrometer());
		valueArray[3] = new Float(dataTuple.getSonar());
		valueArray[4] = new Integer(dataTuple.getSonarType() == null ? 0 : dataTuple.getSonarType().getCode());
		valueArray[5] = new Timestamp(dataTuple.getTimestamp());
		return valueArray;
	}

	private static String[] PrepareColumnsForUpdate(eFileType fileType)
	{
		String[] columnsArray = null;
		if (fileType.equals(eFileType.Sonar))
		{
			columnsArray = new String[2];
			columnsArray[0] = fields[3];
			columnsArray[1] = fields[4];
		}
		else if (fileType.equals(eFileType.Analog))
		{
			columnsArray = new String[3];
			columnsArray[0] = fields[0];
			columnsArray[1] = fields[1];
			columnsArray[2] = fields[2];
		}
		else
		{
			throw new IllegalArgumentException(
					"Only known file types that update raw data are Sonar and Analog!");
		}
		return columnsArray;
	}

	private static Object[] PrepareValuesForUpdate(RawData rawData, eFileType fileType)
	{
		Object[] valueArray = null;
		if (fileType.equals(eFileType.Sonar))
		{
			valueArray = new Object[2];
			valueArray[0] = new Float(rawData.getSonar());
			valueArray[1] = new Integer(rawData.getSonarType().getCode());
		}
		else if (fileType.equals(eFileType.Analog))
		{
			valueArray = new Object[3];
			valueArray[0] = new Float(rawData.getWindSpeed());
			valueArray[1] = new Float(rawData.getWindDirection());
			valueArray[2] = new Float(rawData.getHydrometer());
		}
		else
		{
			throw new IllegalArgumentException(
					"Only known file types that update raw data are Sonar and Analog!");
		}
		return valueArray;
	}
}
