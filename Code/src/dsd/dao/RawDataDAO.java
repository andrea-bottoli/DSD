package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sun.jmx.snmp.Timestamp;

import dsd.model.RawData;
import dsd.model.enums.eSonarType;

public class RawDataDAO
{

	private static String tableName = "sensor_data_raw";
	private static String[] fields = new String[]{"wind_speed", "wind_direction", "hydrometer",
			"sonar", "sonar_type", "timestamp"};

	public static int InsertData(List<RawData> listOfData)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			for (RawData rawData : listOfData)
			{
				try
				{
					counter += DAOProvider.InsertRowSecure(tableName, StringUtils.join(fields, ','),
							con, PrepareValuesForInsert(rawData));
				}
				catch (Exception exc)
				{

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

	public static ArrayList<RawData> GetAllForPeriod(Date startDate, Date endDate)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			ArrayList<RawData> rawDataList = new ArrayList<RawData>();
			try
			{
				ResultSet results = DAOProvider.SelectTableSecure(tableName, "*", "", "", con, null);
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
		Object[] valueArray = new Object[1];
		valueArray[0] = new Integer(0);
		return valueArray;
	}
}
