package dsd.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dsd.model.RawData;

public class RawDataDAO
{

	private String tableName = "sensor_data_raw";
	private String[] fields = new String[]{"wind_speed", "wind_direction", "hydrometer", "sonar",
			"sonar_type"};

	public int InsertData(List<RawData> listOfData)
	{
		int counter = 0;
		for (RawData rawData : listOfData)
		{
			try
			{
				counter += DAOProvider.InsertRow(tableName, StringUtils.join(fields, ','),
						PrepareValuesForInsert(rawData));
			}
			catch (Exception exc)
			{

			}
		}
		return counter;
	}

	public List<RawData> GetAllForPeriod(Date startDate, Date endDate)
	{
		List<RawData> rawDataList = new ArrayList<RawData>();
		try
		{
			ResultSet results = DAOProvider.SelectTable(tableName, "*", "", "");
			while (results.next())
			{
				RawData dataTuple = new RawData();
				dataTuple.setRawDataID(results.getInt("ID"));
				dataTuple.setWindSpeed(results.getFloat(fields[0]));
				dataTuple.setWindDirection(results.getFloat(fields[1]));
				dataTuple.setHydrometer(results.getFloat(fields[2]));
				dataTuple.setSonar(results.getFloat(fields[3]));
				dataTuple.setSonarType(results.getInt(fields[4]));
				dataTuple.setTimestamp(results.getTimestamp("timestamp"));
				rawDataList.add(dataTuple);
			}
		}
		catch (Exception exc)
		{

		}
		return rawDataList;
	}

	private String PrepareValuesForInsert(RawData dataTuple)
	{
		return "(" + dataTuple.getWindSpeed() + "," + dataTuple.getWindDirection() + ","
				+ dataTuple.getHydrometer() + "," + dataTuple.getSonar() + "," + dataTuple.getSonarType()
				+ ",";
	}
}
