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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import dsd.model.Picture;

public class PicturesDAO
{

	private static String tableName = "pictures";
	private static String[] tableFields = new String[]{"ID", "path", "timestamp", "camera"};

	/***
	 * Returns all Pictures between given start- and end-Date from DB
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static ArrayList<Picture> getPictures(long startDate, long endDate)
	{
		ArrayList<Picture> returnList = new ArrayList<Picture>();

		try
		{
			Object[] parameters = new Object[2];
			parameters[0] = new Timestamp(startDate);
			parameters[1] = new Timestamp(endDate);
			
			Connection con = DAOProvider.getDataSource().getConnection();
			ResultSet result = DAOProvider.SelectTableSecure(tableName, "*", "timestamp > ? and timestamp < ? ", "", con, parameters);

			while (result.next())
			{
				Picture pic = new Picture();
				pic.setID(result.getInt(tableFields[0]));
				pic.setPath(result.getString(tableFields[1]));
				pic.setTimestamp(result.getTimestamp(tableFields[2]).getTime());
				pic.setCamera(result.getInt(tableFields[3]));
				returnList.add(pic);
			}

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/***
	 * Write all pictures from the given list into DB
	 * 
	 * @param pictureList
	 */
	public static void setPictures(ArrayList<Picture> pictureList)
	{

		Iterator<Picture> pictureIterator = pictureList.iterator();
		try
		{
			Connection con = dsd.dao.DAOProvider.getDataSource().getConnection();
			while (pictureIterator.hasNext())
			{
				Picture pic = pictureIterator.next();

				// Use not a secure function in the moment!!!
				Object[] input = {pic.getPath(), new Timestamp(pic.getTimestamp()), pic.getCamera()};
				DAOProvider.InsertRowSecure(tableName, tableFields[1] + ", " + tableFields[2] + ", "
						+ tableFields[3], con, input);

			}

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
