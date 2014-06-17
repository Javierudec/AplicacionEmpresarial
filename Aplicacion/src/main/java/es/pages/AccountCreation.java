package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import util.SpringUtils;
import es.model.user.User;

public class AccountCreation
{
	@Property
	private String newUsername;
	@Property
	private String newPassword;
	@Property
	private String newEmail;
	
	@InjectPage
	private ErrorPage errorPage;
	
	Object onSuccess()
	{
		if(SpringUtils.getUserService().addUser(new User(newUsername, newPassword, newEmail)) == null)
		{
			errorPage.setErrorMsg("Username is already used.");
		}
		else
		{
			errorPage.setErrorMsg("Account created. Now you can log in with your username and password.");
		}
		
		return errorPage;
	}
}
