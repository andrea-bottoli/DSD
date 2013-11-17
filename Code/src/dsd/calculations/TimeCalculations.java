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

}
