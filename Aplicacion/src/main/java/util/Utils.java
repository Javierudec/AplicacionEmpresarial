package util;

import java.util.Calendar;

public class Utils {
	public static String getYear(java.sql.Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) + "";
	}
}
