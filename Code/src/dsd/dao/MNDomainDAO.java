package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import dsd.model.MNDomain;

public class MNDomainDAO
{
	private static String tableName = "m_n_domain";
	private static String[] tableFields = new String[]{"N", "M"};

	public static MNDomain GetMNDomain()
	{
		MNDomain mnDomain = new MNDomain();
		try
		{
			Connection con = DAOProvider.getDataSource().getConnection();
			try
			{
				ResultSet results = DAOProvider.SelectTableSecure(tableName, " * ", "", "", con, null);
				while (results.next())
				{
					mnDomain.getN().add(results.getFloat(tableFields[0]));
					mnDomain.getM().add(results.getFloat(tableFields[1]));
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
		return mnDomain;
	}
}
