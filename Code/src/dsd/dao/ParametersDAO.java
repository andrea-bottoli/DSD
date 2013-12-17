package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dsd.model.Parameter;
import dsd.model.enums.eParameterCategory;

public class ParametersDAO
{

	private static String tableNameParameters = "parameters";
	private static String tableNameParameterData = "parameter_data";
	private static String[] tableParametersFields = new String[]{"name", "abbreviation", "unit", "constant",
			"category"};
	private static String[] tableParameterDataFields = new String[]{"parameters_id", "value", "user_id",
			"timestamp"};

	public static int InsertNewParameterValues(List<Parameter> listOfParamters)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			int counter = 0;
			for (Parameter parameter : listOfParamters)
			{
				try
				{
					counter += DAOProvider.InsertRowSecure(tableNameParameterData,
							StringUtils.join(tableParameterDataFields, ','), con,
							PrepareValuesForInsert(parameter));
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

	public static List<Parameter> GetValidParameters(Calendar cal)
	{
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			ArrayList<Parameter> parametersList = new ArrayList<Parameter>();
			try
			{
				Object[] parameters = new Object[1];
				parameters[0] = new Timestamp(cal.getTimeInMillis());

				ResultSet results = DAOProvider.SelectTableSecure(
				// table part
						tableNameParameters + " join " + tableNameParameterData + " on "
								+ tableNameParameters + ".ID = " + tableNameParameterData + "."
								+ tableParameterDataFields[0],
						// select part
						tableNameParameterData + "." + "ID" + " as " + tableNameParameterData + "_" + "ID"
								+ ", " + tableNameParameterData + "." + tableParameterDataFields[0] + " as "
								+ tableNameParameterData + "_" + tableParameterDataFields[0] + ", "
								+ tableNameParameterData + "." + tableParameterDataFields[1] + " as "
								+ tableNameParameterData + "_" + tableParameterDataFields[1] + ", "
								+ tableNameParameterData + "." + tableParameterDataFields[2] + " as "
								+ tableNameParameterData + "_" + tableParameterDataFields[2] + ", "
								+ tableNameParameterData + "." + tableParameterDataFields[3] + " as "
								+ tableNameParameterData + "_" + tableParameterDataFields[3] + ", "
								+ "parameters.* ",
						// where part
						" (parameters_id, timestamp) in (select parameters_id,  max(timestamp) "
								+ " from parameter_data " + " where timestamp < ? "
								+ " group by parameters_id " + "); ",
						// order by part
						"", con, parameters);
				while (results.next())
				{
					Parameter parameter = new Parameter();
					parameter.setParameterID(results.getLong("ID"));
					parameter.setAbbreviation(results.getString(tableParametersFields[1]));
					parameter.setCategory(eParameterCategory.getParameterCategory(results
							.getInt(tableParametersFields[4])));
					parameter.setName(results.getString(tableParametersFields[0]));
					parameter.setParameterDataID(results.getLong(tableNameParameterData + "_ID"));
					parameter.setTimestamp(results.getTimestamp(
							tableNameParameterData + "_" + tableParameterDataFields[3]).getTime());
					parameter.setUnit(results.getString(tableParametersFields[2]));
					parameter.setUserID(results.getInt(tableNameParameterData + "_"
							+ tableParameterDataFields[2]));
					parameter.setValue(results.getFloat(tableNameParameterData + "_"
							+ tableParameterDataFields[1]));
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
				Object[] parameters = new Object[1];
				parameters[0] = new Long(parameterID);

				ResultSet results = DAOProvider.SelectTableSecure(
				// table part
						tableNameParameters + " join " + tableNameParameterData + " on "
								+ tableNameParameters + ".ID = " + tableNameParameterData + "."
								+ tableParameterDataFields[0],
						// select part
						tableNameParameterData + "." + "ID" + " as " + tableNameParameterData + "_" + "ID"
								+ ", " + tableNameParameterData + "." + tableParameterDataFields[0] + " as "
								+ tableNameParameterData + "_" + tableParameterDataFields[0] + ", "
								+ tableNameParameterData + "." + tableParameterDataFields[1] + " as "
								+ tableNameParameterData + "_" + tableParameterDataFields[1] + ", "
								+ tableNameParameterData + "." + tableParameterDataFields[2] + " as "
								+ tableNameParameterData + "_" + tableParameterDataFields[2] + ", "
								+ tableNameParameterData + "." + tableParameterDataFields[3] + " as "
								+ tableNameParameterData + "_" + tableParameterDataFields[3] + ", "
								+ "parameters.* ",
						// where part
						" parameters_id = ? ",
						// order by part
						"", con, parameters);
				while (results.next())
				{
					Parameter parameter = new Parameter();
					parameter.setParameterID(results.getLong("ID"));
					parameter.setAbbreviation(results.getString(tableParametersFields[1]));
					parameter.setCategory(eParameterCategory.getParameterCategory(results
							.getInt(tableParametersFields[4])));
					parameter.setName(results.getString(tableParametersFields[0]));
					parameter.setParameterDataID(results.getLong(tableNameParameterData + "_ID"));
					parameter.setTimestamp(results.getTimestamp(
							tableNameParameterData + "_" + tableParameterDataFields[3]).getTime());
					parameter.setUnit(results.getString(tableParametersFields[2]));
					parameter.setUserID(results.getInt(tableNameParameterData + "_"
							+ tableParameterDataFields[2]));
					parameter.setValue(results.getFloat(tableNameParameterData + "_"
							+ tableParameterDataFields[1]));
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

	private static Object[] PrepareValuesForInsert(Parameter dataTuple)
	{
		Object[] valueArray = new Object[4];
		valueArray[0] = new Long(dataTuple.getParameterID());
		valueArray[1] = new Float(dataTuple.getValue());
		valueArray[2] = new Long(dataTuple.getUserID());
		valueArray[3] = new Timestamp(dataTuple.getTimestamp());
		return valueArray;
	}
}
