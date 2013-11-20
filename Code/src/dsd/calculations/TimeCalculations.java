package dsd.calculations;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeCalculations
{
	public static long LabViewTimestampGregToMiliSeconds(long lvTimestamp)
	{
		return LabViewTimestampsToGregCalendar(lvTimestamp).getTime().getTime();
	}

	public static GregorianCalendar LabViewTimestampsToGregCalendar(long lvTimestamp)
	{

		GregorianCalendar returnDate = new GregorianCalendar(1904, 00, 01, 00, 00, 00);
		if (lvTimestamp < Integer.MAX_VALUE)
		{
			returnDate.add(Calendar.SECOND, (int) lvTimestamp);
		}
		else
		{
			returnDate.add(Calendar.SECOND, Integer.MAX_VALUE);
			returnDate = LabViewTimestampsToGregCalendarRecusiv(lvTimestamp - Integer.MAX_VALUE, returnDate);
		}

		return returnDate;
	}

	public static GregorianCalendar LabViewTimestampsToGregCalendarRecusiv(long lvTimestamp,
			GregorianCalendar returnDate)
	{

		if (lvTimestamp < Integer.MAX_VALUE)
		{
			returnDate.add(Calendar.SECOND, (int) lvTimestamp);
		}
		else
		{
			returnDate.add(Calendar.SECOND, Integer.MAX_VALUE);
			returnDate = LabViewTimestampsToGregCalendarRecusiv(lvTimestamp - Integer.MAX_VALUE, returnDate);
		}

		return returnDate;

	}

	public static GregorianCalendar PictureTimeToGregCalendar(String timestamp)
	{

		try
		{
			int year = Integer.parseInt(timestamp.substring(0, 2));
			int month = Integer.parseInt(timestamp.substring(2, 4));
			int day = Integer.parseInt(timestamp.substring(4, 6));
			int hour = Integer.parseInt(timestamp.substring(6, 8));
			int minute = Integer.parseInt(timestamp.substring(8, 10));
			int secound = Integer.parseInt(timestamp.substring(10, 12));

			GregorianCalendar date = new GregorianCalendar(year, month, day, hour, minute, secound);
			return date;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;

	}

	public static long PictureTimestampToGregToMiliSeconds(String timestamp)
	{
		return PictureTimeToGregCalendar(timestamp).getTime().getTime();
	}

}
