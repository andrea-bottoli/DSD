package dsd.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class FilesDAO
{

	// Just for the moment as dirty solution!!
	private static String anaFileDir = "/home/joti/Dokumente/Uni/MDH/DSD/Data Sources/2011.Borgoforte/04. Ane_Idro/";

	public static ArrayList<File> getNewAneFiles(GregorianCalendar date)
	{
		ArrayList<File> returnList = new ArrayList<File>();

		File file = new File(anaFileDir);
		File[] fileArray = file.listFiles();

		for (int i = 0; i < fileArray.length; i++)
		{
			String fileName = fileArray[i].getName();
			if (fileName.substring(0, 1).equals("a"))
			{
				String timestamp = fileName.substring(6, fileName.length() - 4);
				if (date.before(dsd.calculations.TimeCalculations.LabViewTimestampsToGregCalendar(Long
						.parseLong(timestamp))))
				{
					returnList.add(fileArray[i]);
				}
			}
		}

		return returnList;

	}
	public static ArrayList<File> getNewSonoFiles(GregorianCalendar date)
	{
		return null;

	}

}
