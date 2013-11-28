package dsd.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import dsd.dao.RawDataDAO;
import dsd.model.RawData;
import dsd.model.enums.eFileType;

public class RawDataController
{
	public static ArrayList<RawData> GetAllForPeriod(Calendar startDate, Calendar endDate)
	{
		return RawDataDAO.GetAllForPeriod(startDate, endDate);
	}

	public static void InsertOrUpdateRawData(List<RawData> rawDataList, eFileType fileType)
	{
		Collections.sort(rawDataList, new Comparator<RawData>()
		{
			@Override
			public int compare(RawData o1, RawData o2)
			{
				return (int) (o1.getTimestamp() - o2.getTimestamp());
			}
		});

		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.setTimeInMillis(rawDataList.get(0).getTimestamp());
		endDate.setTimeInMillis(rawDataList.get(rawDataList.size() - 1).getTimestamp());
		List<RawData> existingRawDataList = GetAllForPeriod(startDate, endDate);
		Hashtable<Long, RawData> dictExisting = new Hashtable<Long, RawData>();
		for (RawData dataTuple : existingRawDataList)
		{
			dictExisting.put(dataTuple.getTimestamp(), dataTuple);
		}

		int counterUpdated = 0;
		int counterInserted = 0;
		List<RawData> listOfRawDataForInsertion = new ArrayList<RawData>();
		List<RawData> listOfRawDataForUpdate = new ArrayList<RawData>();
		for (RawData dataTuple : rawDataList)
		{
			if (dictExisting.containsKey(dataTuple.getTimestamp()))
			{
				RawData existingDataTuple = dictExisting.get(dataTuple.getTimestamp());
				dataTuple.setRawDataID(existingDataTuple.getRawDataID());
				if (fileType.equals(eFileType.Analog))
				{
					dataTuple.setSonar(existingDataTuple.getSonar());
					dataTuple.setSonarType(existingDataTuple.getSonarType());
				}
				else if (fileType.equals(eFileType.Sonar))
				{
					dataTuple.setWindSpeed(existingDataTuple.getWindSpeed());
					dataTuple.setWindDirection(existingDataTuple.getWindDirection());
					dataTuple.setHydrometer(existingDataTuple.getHydrometer());
				}
				else
					throw new IllegalArgumentException("File type not recognized");
				listOfRawDataForUpdate.add(dataTuple);
				counterUpdated++;
			}
			else
			{
				listOfRawDataForInsertion.add(dataTuple);
				counterInserted++;
			}
		}
		if (listOfRawDataForUpdate.size() > 0)
			RawDataDAO.UpdateRawData(listOfRawDataForUpdate, fileType);
		if (listOfRawDataForInsertion.size() > 0)
			RawDataDAO.InsertRawData(listOfRawDataForInsertion);

		System.out.println("Tuples updated: " + counterUpdated + " touples inserted: " + counterInserted);
	}
}
