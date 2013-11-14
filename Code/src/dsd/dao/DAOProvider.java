package dsd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAOProvider {

	private static DataSource dataSource;

	private DAOProvider() {

	}

	private static DataSource getDataSource() {
		try {
			if (dataSource == null) {
				Context ctx = new InitialContext();
				dataSource = (DataSource) ctx
						.lookup("java:comp/env/jdbc/RTBMconnection");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	public static int Query(String query) throws SQLException {
		
		Connection con = getDataSource().getConnection();
		try {
			PreparedStatement command = con.prepareStatement(query);
			return command.executeUpdate();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			con.close();
		}
		return -1;
	}
	
	public static ResultSet SelectTable(String table, String select, String where, String order) throws SQLException
	{
		ResultSet resultSet = null;
		Connection con = getDataSource().getConnection();
		try {
			PreparedStatement command = con.prepareStatement(
					String.format("select %s from %s %s %s", 
							select, table,
							(where.trim().equals("") ? "" : "where " + where),
							(order.trim().equals("") ? "" : "order by " + order)));
			resultSet = command.executeQuery();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			con.close();
		}
		return resultSet;
	}
	
	
	public static int DeleteRow(String table, String where) throws SQLException
	{
		Connection con = getDataSource().getConnection();
		try {
			PreparedStatement command = con.prepareStatement(String.format("delete from %s %s", 
					table,
					(where.trim().equals("") ? "" : "where " + where)));
			return command.executeUpdate();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			con.close();
		}
		return -1;
	}
	
	public static int InsertRow(String table, String fields, String values) throws SQLException
	{
		Connection con = getDataSource().getConnection();
		try {
			PreparedStatement command = con.prepareStatement(
					String.format("insert into %s (%s) values %s", 
					table, fields, values));
			command.executeUpdate();
			
			command = con.prepareStatement(
					String.format("select Max(ID) from %s", table)
					);
			ResultSet rs = command.executeQuery();
			rs.next();
			
			return rs.getInt(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			con.close();
		}
		return -1;
	}
	
	public static int UpdateRow(String table, String set, String where) throws SQLException
	{
		Connection con = getDataSource().getConnection();
		try {
			PreparedStatement command = con.prepareStatement(
					String.format("update %s set %s %s", 
					table, set,
					(where.trim().equals("") ? "" : "where " + where))
					);
			return command.executeUpdate();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			con.close();
		}
		return -1;
	}
	
}
