package dsd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			Connection con = DAOProvider.getDataSource().getConnection();
			ResultSet result = DAOProvider.SelectTable(tableName, "*", "timestamp > " + startDate
					+ " and timestamp <" + endDate, "", con);

			while (result.next())
			{
				Picture pic = new Picture();
				pic.setID(result.getInt(tableFields[0]));
				pic.setPath(result.getString(tableFields[1]));
				pic.setTimestamp(result.getLong(tableFields[2]));
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
	public void setPictures(ArrayList<Picture> pictureList)
	{

		Iterator<Picture> pictureIterator = pictureList.iterator();
		try
		{
			Connection con = dsd.dao.DAOProvider.getDataSource().getConnection();
			while (pictureIterator.hasNext())
			{
				Picture pic = pictureIterator.next();

				// Use not a secure function in the moment!!!
				Object[] input = {pic.getPath(), pic.getTimestamp(), pic.getCamera()};
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
