package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sun.jmx.snmp.Timestamp;

import dsd.model.Parameter;
import dsd.model.enums.eParameterCategory;

public class ParametersDAO {
	
	private static String tableNameParameters = "parameters";
	private static String tableNameParameterData = "parameter_data";
	private static String[] tableParametersFields = new String[]{"name", "abbreviation", "unit",
			"constant", "category"};
	private static String[] tableParameterDataFields = new String[]{"parameters_id", "value", "user_id",
		"timestamp"};

	public static int InsertParameters(List<Parameter> listOfParamters)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			for (Parameter rawData : listOfParamters)
			{
				try
				{
					counter += DAOProvider.InsertRow(tableNameParameters, StringUtils.join(tableParametersFields, ','),
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
	
	public static int UpdateParameters(List<Parameter> listOfParamters)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			for (Parameter rawData : listOfParamters)
			{
				try
				{
					counter += DAOProvider.InsertRow(tableNameParameters, StringUtils.join(tableParametersFields, ','),
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

	public static List<Parameter> GetValidParameters(long timestamp)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			ArrayList<Parameter> parametersList = new ArrayList<Parameter>();
			try
			{
				ResultSet results = DAOProvider.SelectTable(
						// table part
						tableNameParameters + " join " + tableNameParameterData + " on " +
						tableNameParameters + ".ID = " + tableNameParameterData + "." + tableParameterDataFields[0] ,
						// select part
						tableNameParameterData + "." + "ID" + " as " + tableNameParameterData + "_" + "ID" + ", " +
						tableNameParameterData + "." + tableParameterDataFields[0] + " as " + tableNameParameterData + "_" + tableParameterDataFields[0] + ", " +
						tableNameParameterData + "." + tableParameterDataFields[1] + " as " + tableNameParameterData + "_" + tableParameterDataFields[1] + ", " +
						tableNameParameterData + "." + tableParameterDataFields[2] + " as " + tableNameParameterData + "_" + tableParameterDataFields[2] + ", " +
						tableNameParameterData + "." + tableParameterDataFields[3] + " as " + tableNameParameterData + "_" + tableParameterDataFields[3] + ", " +
						"parameters.* ", 
						// where part
						" where (parameters_id, timestamp) in (select parameters_id,  max(timestamp) " +
																" from parameter_data " +
																" where timestamp < '" + new Timestamp(timestamp).toString() + "'" +
																" group by parameters_id " +
																"); ", 
						// order by part
						"", 
						con);
				while (results.next())
				{
					Parameter parameter = new Parameter();
					parameter.setParameterID(results.getLong("ID"));
					parameter.setAbbreviation(results.getString(tableParametersFields[1]));
					parameter.setCategory(eParameterCategory.getParameterCategory(results.getInt(tableParametersFields[4])));
					parameter.setName(results.getString(tableParametersFields[0]));
					parameter.setParameterDataID(results.getLong(tableNameParameterData + "_ID"));
					parameter.setTimestamp(results.getTimestamp(tableNameParameterData + "_" + tableParameterDataFields[3]).getTime());
					parameter.setUnit(results.getString(tableParametersFields[2]));
					parameter.setUserID(results.getInt(tableNameParameterData + "_" + tableParameterDataFields[2]));
					parameter.setValue(results.getFloat(tableNameParameterData + "_" + tableParameterDataFields[1]));
					parametersList.add(parameter);
				}
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
			con.close();
			return parametersList;
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return null;
	}
	
	public static List<Parameter> GetParameterHistory(long parameterID)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			ArrayList<Parameter> parametersList = new ArrayList<Parameter>();
			try
			{
				ResultSet results = DAOProvider.SelectTable(
						// table part
						tableNameParameters + " join " + tableNameParameterData + " on " +
						tableNameParameters + ".ID = " + tableNameParameterData + "." + tableParameterDataFields[0] ,
						// select part
						tableNameParameterData + "." + "ID" + " as " + tableNameParameterData + "_" + "ID" + ", " +
						tableNameParameterData + "." + tableParameterDataFields[0] + " as " + tableNameParameterData + "_" + tableParameterDataFields[0] + ", " +
						tableNameParameterData + "." + tableParameterDataFields[1] + " as " + tableNameParameterData + "_" + tableParameterDataFields[1] + ", " +
						tableNameParameterData + "." + tableParameterDataFields[2] + " as " + tableNameParameterData + "_" + tableParameterDataFields[2] + ", " +
						tableNameParameterData + "." + tableParameterDataFields[3] + " as " + tableNameParameterData + "_" + tableParameterDataFields[3] + ", " +
						"parameters.* ", 
						// where part
						" where parameters_id = " + parameterID, 
						// order by part
						"", 
						con);
				while (results.next())
				{
					Parameter parameter = new Parameter();
					parameter.setParameterID(results.getLong("ID"));
					parameter.setAbbreviation(results.getString(tableParametersFields[1]));
					parameter.setCategory(eParameterCategory.getParameterCategory(results.getInt(tableParametersFields[4])));
					parameter.setName(results.getString(tableParametersFields[0]));
					parameter.setParameterDataID(results.getLong(tableNameParameterData + "_ID"));
					parameter.setTimestamp(results.getTimestamp(tableNameParameterData + "_" + tableParameterDataFields[3]).getTime());
					parameter.setUnit(results.getString(tableParametersFields[2]));
					parameter.setUserID(results.getInt(tableNameParameterData + "_" + tableParameterDataFields[2]));
					parameter.setValue(results.getFloat(tableNameParameterData + "_" + tableParameterDataFields[1]));
					parametersList.add(parameter);
				}
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
			con.close();
			return parametersList;
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return null;
	}

	private static String PrepareValuesForInsert(Parameter dataTuple)
	{
		return "";
	}
}
