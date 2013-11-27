package dsd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAOProvider
{

	private static DataSource dataSource;

	private DAOProvider()
	{

	}

	/**
	 * 
	 * @return
	 */
	public static DataSource getDataSource()
	{
		try
		{
			if (dataSource == null)
			{
				Context ctx = new InitialContext();
				dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/RTBMconnection");
			}
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
		return dataSource;
	}

	/**
	 * This is the secure Version of SelectTable. To use this function in the
	 * right way, you use ? instead of parameters and give the parameters
	 * instead as string into the parameter-array.
	 * 
	 * For Example: Instead of : "SELECT * FROM tb1 WHERE user= 'foo' and name
	 * ='bar' use "SELECT * FROM tb1 WHERE user= ? and name = ?" and as
	 * parameters[0]='foo' and parameters[1]='bar'
	 * 
	 * The order in the parameters are important!!
	 * 
	 * @param table
	 * @param select
	 * @param where
	 * @param order
	 * @param con
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet SelectTableSecure(String table, String select, String where, String order,
			Connection con, Object[] parameters) throws SQLException
	{
		ResultSet resultSet = null;
		try
		{
			PreparedStatement command = con.prepareStatement(String.format("select %s from %s %s %s", select,
					table, (where.trim().equals("") ? "" : "where " + where), (order.trim().equals("")
							? ""
							: "order by " + order)));

			for (int i = 0; i < parameters.length; i++)
			{
				SetParameter(command, parameters[i], i + 1);
			}

			resultSet = command.executeQuery();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * the delete row function done in a secure way
	 * 
	 * @param table
	 * @param where
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int DeleteRowSecure(String table, String where, Connection con, Object[] parameters)
			throws SQLException
	{
		try
		{
			PreparedStatement command = con.prepareStatement(String.format("delete from %s %s", table, (where
					.trim().equals("") ? "" : "where " + where)));

			for (int i = 0; i < parameters.length; i++)
			{
				SetParameter(command, parameters[i], i + 1);
			}

			return command.executeUpdate();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * the insert row function done in a secure way
	 * 
	 * @param table
	 * @param fields
	 * @param con
	 * @param valueArray
	 * @return
	 * @throws SQLException
	 */
	public static int InsertRowSecure(String table, String fields, Connection con, Object[] valueArray)
			throws SQLException
	{
		try
		{
			String values = "";
			if (valueArray.length > 0)
			{
				values = "?";
			}
			for (int i = 1; i < valueArray.length; i++)
			{
				values += ",?";
			}

			PreparedStatement command = con.prepareStatement(String.format("insert into %s (%s) values (%s)",
					table, fields, values));

			for (int i = 0; i < valueArray.length; i++)
			{
				SetParameter(command, valueArray[i], i + 1);
			}

			command.executeUpdate();

			command = con.prepareStatement(String.format("select Max(ID) from %s", table));
			ResultSet rs = command.executeQuery();
			rs.next();

			return rs.getInt(1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	public static int InsertRowsSecure(String table, String fields, Connection con, Object[][] valueArray)
			throws SQLException
	{
		try
		{
			String values = "(";
			if (valueArray[0].length > 0)
			{
				values += "?";
			}
			for (int i = 1; i < valueArray[0].length; i++)
			{
				values += ",?";
			}
			values += ")";
			String rows = "";
			for (int j = 0; j < valueArray.length; j++)
			{
				rows += " " + values;
				if (j != valueArray.length - 1)
					rows += " , ";
			}

			PreparedStatement command = con.prepareStatement(String.format("insert into %s (%s) values %s",
					table, fields, rows));

			for (int i = 0; i < valueArray.length; i++)
			{
				for (int j = 0; j < valueArray[i].length; j++)
				{
					SetParameter(command, valueArray[i][j], i * valueArray[i].length + j + 1);
				}
			}
			command.executeUpdate();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * the update row method done in a secure way
	 * 
	 * @param table
	 * @param updateColumns
	 * @param where
	 * @param con
	 * @param valueArray
	 * @param wherePartParameters
	 * @return
	 * @throws SQLException
	 */
	public static int UpdateRowSecure(String table, String[] updateColumns, String where, Connection con,
			Object[] valueArray, Object[] wherePartParameters) throws SQLException
	{
		try
		{
			if (updateColumns.length != valueArray.length || updateColumns.length == 0)
				throw new IllegalArgumentException(
						"The size of updateColumns and valueArray parameters should be the same!");

			String set = "";
			for (int i = 0; i < valueArray.length; i++)
			{
				set += updateColumns[i] + " = ?";
				if (i != valueArray.length - 1)
					set += ", ";
			}

			PreparedStatement command = con.prepareStatement(String.format("update %s set %s %s", table, set,
					(where.trim().equals("") ? "" : " where " + where)));

			for (int i = 0; i < valueArray.length; i++)
			{
				SetParameter(command, valueArray[i], i + 1);
			}
			for (int i = 0; i < wherePartParameters.length; i++)
			{
				SetParameter(command, wherePartParameters[i], valueArray.length + i + 1);
			}

			return command.executeUpdate();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * calls the correct method for setting the command parameter depending on
	 * parameter type
	 * 
	 * @param command
	 * @param object
	 * @param parameterIndex
	 * @throws SQLException
	 */
	private static void SetParameter(PreparedStatement command, Object object, int parameterIndex)
			throws SQLException
	{
		if (object instanceof Timestamp)
		{
			command.setTimestamp(parameterIndex, (Timestamp) object);
		}
		else if (object instanceof String)
		{
			command.setString(parameterIndex, (String) object);
		}
		else if (object instanceof Long)
		{
			command.setLong(parameterIndex, (Long) object);
		}
		else if (object instanceof Integer)
		{
			command.setInt(parameterIndex, (Integer) object);
		}
		else if (object instanceof Boolean)
		{
			command.setBoolean(parameterIndex, (Boolean) object);
		}
		else if (object instanceof Float)
		{
			command.setFloat(parameterIndex, (Float) object);
		}
		else
		{
			throw new IllegalArgumentException(
					"type needs to be inserted in Set parameter method of DAOProvider class");
		}

	}

}
