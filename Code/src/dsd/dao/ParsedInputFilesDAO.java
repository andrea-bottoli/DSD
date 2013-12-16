package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import dsd.model.ParsedInputFile;
import dsd.model.enums.eFileType;

public class ParsedInputFilesDAO
{
	private static String tableName = "parsed_input_files";
	private static String[] tableFields = new String[]{"name", "type", "stored_path", "successfully_parsed",
			"timestamp"};

	public static int InsertParsedInputFile(List<ParsedInputFile> listOfParsedInputFile)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			for (ParsedInputFile parsedInputFile : listOfParsedInputFile)
			{
				try
				{
					counter += DAOProvider.InsertRowSecure(tableName, StringUtils.join(tableFields, ','),
							con, PrepareValuesForInsert(parsedInputFile));
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

	public static int UpdateParsedInputFile(List<ParsedInputFile> listOfParsedInputFile)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
				try
				{
					throw new NotImplementedException();
//					counter += DAOProvider.UpdateRowSecure(tableName, tableFields, "parameter", con,
//							PrepareValuesForInsert(parameter), null);
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

	public static boolean IsAlreadyParsed(String fileName)
	{
		boolean isAlreadyParsed = false;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				Object[] parameters = new Object[1];
				parameters[0] = fileName;
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " count(*) ", " name = ? ", "",
						con, parameters);
				while (results.next())
				{
					isAlreadyParsed = results.getInt(1) == 1 ? true : false;
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
		return isAlreadyParsed;
	}
	
	public static String FetchStoredPath(eFileType fileType, Calendar date)
	{
		String storedPath = null;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				Object[] parameters = new Object[3];
				parameters[0] = new Integer(fileType.getCode());
				parameters[1] = new Integer(fileType.getCode());
				parameters[2] = new Timestamp(date.getTimeInMillis());
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " stored_path ", " type = ? and timestamp = (select max(timestamp) from parsed_input_files where type = ? and timestamp <= ?) ", "",
						con, parameters);
				while (results.next())
				{
					storedPath = results.getString(1);
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
		return storedPath;
	}
	
	public static long GetMaxTimestamp(eFileType fileType)
	{
		long timestamp = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				Object[] parameters = new Object[1];
				parameters[0] = new Integer(fileType.getCode());
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " max(timestamp) ", " type = ? ", "",
						con, parameters);
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
	
	public static long GetCount(eFileType fileType)
	{
		long count = 0;
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				Object[] parameters = new Object[1];
				parameters[0] = new Integer(fileType.getCode());
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " count(*) ", " type = ? ", "",
						con, parameters);
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
	
	private static Object[] PrepareValuesForInsert(ParsedInputFile dataTuple)
	{
		Object[] valueArray = new Object[5];
		valueArray[0] = dataTuple.getName();
		valueArray[1] = new Integer(dataTuple.getFileType().getCode());
		valueArray[2] = dataTuple.getStoredPath();
		valueArray[3] = new Integer(dataTuple.isSuccessfullyParsed() ? 1 : 0);
		valueArray[4] = new Timestamp(dataTuple.getTimestamp());
		return valueArray;
	}
}
