/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
