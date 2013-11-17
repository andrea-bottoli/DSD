package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sun.jmx.snmp.Timestamp;

import dsd.model.RawData;
import dsd.model.eSonarType;

public class RawDataDAO
{

	private static String tableName = "sensor_data_raw";
	private static String[] insertableFields = new String[]{"wind_speed", "wind_direction", "hydrometer",
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
					counter += DAOProvider.InsertRow(tableName, StringUtils.join(insertableFields, ','),
							PrepareValuesForInsert(rawData), con);
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
				ResultSet results = DAOProvider.SelectTable(tableName, "*", "", "", con);
				while (results.next())
				{
					RawData dataTuple = new RawData();
					dataTuple.setRawDataID(results.getInt("ID"));
					dataTuple.setWindSpeed(results.getFloat(insertableFields[0]));
					dataTuple.setWindDirection(results.getFloat(insertableFields[1]));
					dataTuple.setHydrometer(results.getFloat(insertableFields[2]));
					dataTuple.setSonar(results.getFloat(insertableFields[3]));
					dataTuple.setSonarType(eSonarType.getSonarType(results.getInt(insertableFields[4])));
					dataTuple.setTimestamp(results.getTimestamp(insertableFields[5]).getTime());
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

	private static String PrepareValuesForInsert(RawData dataTuple)
	{
		return "(" + dataTuple.getWindSpeed() + "," + dataTuple.getWindDirection() + ","
				+ dataTuple.getHydrometer() + "," + dataTuple.getSonar() + "," + dataTuple.getSonarType()
				+ "," + new Timestamp(dataTuple.getTimestamp()).toString() + ")";
	}
}
