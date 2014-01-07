/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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

	public static Calendar PictureTimeToGregCalendar(String timestamp)
	{
		try
		{
			int year = Integer.parseInt(timestamp.substring(0, 2));
			int month = Integer.parseInt(timestamp.substring(2, 4)) - 1;
			int day = Integer.parseInt(timestamp.substring(4, 6));
			int hour = Integer.parseInt(timestamp.substring(6, 8));
			int minute = Integer.parseInt(timestamp.substring(8, 10));
			int secound = Integer.parseInt(timestamp.substring(10, 12));

			Calendar date = Calendar.getInstance();
			date.set(year + 2000, month, day, hour, minute, secound);
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
		return PictureTimeToGregCalendar(timestamp).getTimeInMillis();
	}

}
