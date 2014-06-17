package es.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class ErrorPage 
{
	@Persist
	@Property
	private String errorMessage;
	
	public void setErrorMsg(String message)
	{
		errorMessage = message;
	}
}