package es.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import util.Utils;

public class CPanel
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username;
	
	public boolean getIsAdmin()
	{
		return Utils.getIsAdmin(username);
	}
}