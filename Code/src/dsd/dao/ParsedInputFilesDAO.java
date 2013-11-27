package dsd.dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dsd.model.ParsedInputFile;

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
			for (ParsedInputFile parameter : listOfParsedInputFile)
			{
				try
				{
					counter += DAOProvider.UpdateRowSecure(tableName, tableFields, "parameter", con,
							PrepareValuesForInsert(parameter), null);
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
