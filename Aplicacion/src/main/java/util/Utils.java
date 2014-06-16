package util;

import java.util.Calendar;

import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public class Utils {
	public static String getYear(java.sql.Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) + "";
	}
	
	public static boolean getIsAdmin(String username)
	{
		if(username == null) return false;
		
		User user = null;
		
		try {
			user = SpringUtils.getUserService().findUserByName(username);
			return user.getIsAdmin();
		} catch (InstanceNotFoundException e) {
			return false;
		}
	}
}
