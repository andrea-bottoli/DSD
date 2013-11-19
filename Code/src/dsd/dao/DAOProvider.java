package dsd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * 
	 * @param query
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int Query(String query, Connection con) throws SQLException
	{
		try
		{
			PreparedStatement command = con.prepareStatement(query);
			return command.executeUpdate();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param table
	 * @param select
	 * @param where
	 * @param order
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet SelectTable(String table, String select, String where, String order,
			Connection con) throws SQLException
	{
		ResultSet resultSet = null;
		try
		{
			PreparedStatement command = con.prepareStatement(String.format("select %s from %s %s %s", select,
					table, (where.trim().equals("") ? "" : "where " + where), (order.trim().equals("")
							? ""
							: "order by " + order)));
			resultSet = command.executeQuery();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return resultSet;
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
			Connection con, String[] parameters) throws SQLException
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
				command.setString(i, parameters[i]);
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
	 * 
	 * @param table
	 * @param where
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int DeleteRow(String table, String where, Connection con) throws SQLException
	{
		try
		{
			PreparedStatement command = con.prepareStatement(String.format("delete from %s %s", table, (where
					.trim().equals("") ? "" : "where " + where)));
			return command.executeUpdate();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param table
	 * @param fields
	 * @param values
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int InsertRow(String table, String fields, String values, Connection con)
			throws SQLException
	{
		try
		{
			PreparedStatement command = con.prepareStatement(String.format("insert into %s (%s) values %s",
					table, fields, values));
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

	/**
	 * 
	 * @param table
	 * @param set
	 * @param where
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int UpdateRow(String table, String set, String where, Connection con) throws SQLException
	{
		try
		{
			PreparedStatement command = con.prepareStatement(String.format("update %s set %s %s", table, set,
					(where.trim().equals("") ? "" : "where " + where)));
			return command.executeUpdate();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

}
