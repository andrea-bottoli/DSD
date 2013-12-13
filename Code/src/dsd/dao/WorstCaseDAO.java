package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dsd.model.WorstPylonCase;
import dsd.model.calculation.Pylon;

public class WorstCaseDAO
{
	private static String tableName00 = "worst_case_00";
	private static String tableName01 = "worst_case_01";
	private static String tableName10 = "worst_case_10";
	private static String tableName11 = "worst_case_11";
	
	private static String[] fields = new String[]{"pylon_number", "N", "Tx", "Ty", "Mx", "My", "M",
			"cs", "comb_number", "timestamp"};

	public static int InsertCalculatedData(List<WorstPylonCase> listOfData, boolean traffic, boolean debris)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			try
			{
				String tableName = GetTableNameForDataType(traffic, debris);
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

	public static int UpdateCalculatedData(List<WorstPylonCase> listOfData, boolean traffic, boolean debris)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				String tableName = GetTableNameForDataType(traffic, debris);
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

	public static ArrayList<WorstPylonCase> GetAllForPeriod(Calendar startDate, Calendar endDate, boolean traffic, boolean debris)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			ArrayList<WorstPylonCase> worstCaseDataList = new ArrayList<WorstPylonCase>();
			try
			{
				String tableName = GetTableNameForDataType(traffic, debris);
				Object[] parameters = new Object[2];
				parameters[0] = new Timestamp(startDate.getTimeInMillis());
				parameters[1] = new Timestamp(endDate.getTimeInMillis());
				ResultSet results = DAOProvider.SelectTableSecure(tableName, "*",
						" timestamp >= ? and timestamp <= ? ", "", con, parameters);
				while (results.next())
				{
					WorstPylonCase dataTuple = new WorstPylonCase(results.getInt(fields[0]));
					dataTuple.setID(results.getLong("ID"));
					dataTuple.setComboNumber(results.getInt(fields[8]));
					dataTuple.setSafetyFactor(results.getFloat(fields[7]));
					dataTuple.setTimestamp(results.getTimestamp(fields[9]).getTime());
					
					Pylon pylon = new Pylon(results.getInt(fields[0]));
					pylon.setN(results.getFloat(fields[1]));
					pylon.setTx(results.getFloat(fields[2]));
					pylon.setTy(results.getFloat(fields[3]));
					pylon.setMx(results.getFloat(fields[4]));
					pylon.setMy(results.getFloat(fields[5]));
					pylon.setM(results.getFloat(fields[6]));
					
					dataTuple.setPylon(pylon);
					
					worstCaseDataList.add(dataTuple);
				}
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
			con.close();
			return worstCaseDataList;
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return null;

	}
	
	public static long GetMaxTimestamp(boolean traffic, boolean debris)
	{
		long timestamp = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				String tableName = GetTableNameForDataType(traffic, debris);
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " max(timestamp) ", "", "",
						con, null);
				while (results.next())
				{
					timestamp = results.getTimestamp(1).getTime();
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
		return timestamp;
	}
	
	public static long GetCount(boolean traffic, boolean debris)
	{
		long count = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				String tableName = GetTableNameForDataType(traffic, debris);
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

	private static Object[][] PrepareMultipleValuesForInsert(List<WorstPylonCase> listOfData)
	{
		Object[][] valueArray = new Object[listOfData.size()][10];
		for (int i = 0; i < listOfData.size(); i++)
		{
			valueArray[i][0] = new Integer(listOfData.get(i).getPylonNumber());
			valueArray[i][1] = new Float(listOfData.get(i).getN());
			valueArray[i][2] = new Float(listOfData.get(i).getTx());
			valueArray[i][3] = new Float(listOfData.get(i).getTy());
			valueArray[i][4] = new Float(listOfData.get(i).getMx());
			valueArray[i][5] = new Float(listOfData.get(i).getMy());
			valueArray[i][6] = new Float(listOfData.get(i).getM());
			valueArray[i][7] = new Float(listOfData.get(i).getSafetyFactor());
			valueArray[i][8] = new Integer(listOfData.get(i).getComboNumber());
			valueArray[i][9] = new Timestamp(listOfData.get(i).getTimestamp());
		}
		return valueArray;
	}

	private static Object[][] PrepareMultipleValuesForUpdate(List<WorstPylonCase> listOfData)
	{
		Object[][] valueArray = new Object[listOfData.size()][11];
		for (int i = 0; i < listOfData.size(); i++)
		{
			valueArray[i][0] = new Long(listOfData.get(i).getID());
			valueArray[i][1] = new Integer(listOfData.get(i).getPylonNumber());
			valueArray[i][2] = new Float(listOfData.get(i).getN());
			valueArray[i][3] = new Float(listOfData.get(i).getTx());
			valueArray[i][4] = new Float(listOfData.get(i).getTy());
			valueArray[i][5] = new Float(listOfData.get(i).getMx());
			valueArray[i][6] = new Float(listOfData.get(i).getMy());
			valueArray[i][7] = new Float(listOfData.get(i).getM());
			valueArray[i][8] = new Float(listOfData.get(i).getSafetyFactor());
			valueArray[i][9] = new Integer(listOfData.get(i).getComboNumber());
			valueArray[i][10] = new Timestamp(listOfData.get(i).getTimestamp());
		}
		return valueArray;
	}

	private static String[] PrepareColumnsForUpdate()
	{
		String[] columnsArray = new String[11];
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
		return columnsArray;
	}
	
	private static String GetTableNameForDataType(boolean traffic, boolean debris)
	{
		if (!traffic && !debris)
		{
			return tableName00;
		}
		else if (!traffic && debris)
		{
			return tableName01;
		}
		else if (traffic && !debris)
		{
			return tableName10;
		}
		else if (traffic && debris)
		{
			return tableName11;
		}
		else
		{
			throw new IllegalArgumentException("Unrecognized data type");
		}
	}
}
