package dsd.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
		List<RawData> fetchedRawDataList = GetAllForPeriod(startDate, endDate);
		HashMap<Long, Long> dict = new HashMap<Long, Long>();
		for (RawData dataTuple : fetchedRawDataList)
		{
			dict.put(dataTuple.getTimestamp(), dataTuple.getRawDataID());
		}

		int counterUpdated = 0;
		int counterInserted = 0;
		List<RawData> listOfRawDataForInsertion = new ArrayList<RawData>();
		for (RawData dataTuple : rawDataList)
		{
			if (dict.containsKey(dataTuple.getTimestamp()))
			{
				dataTuple.setRawDataID(dict.get(dataTuple.getTimestamp()));
				RawDataDAO.UpdateRawData(Arrays.asList(dataTuple), fileType);
				counterUpdated++;
			}
			else
			{
				listOfRawDataForInsertion.add(dataTuple);
				counterInserted++;
			}
		}
		RawDataDAO.InsertRawData(listOfRawDataForInsertion);

		System.out.println("Tuples updated: " + counterUpdated + " touples inserted: " + counterInserted);
	}
}
