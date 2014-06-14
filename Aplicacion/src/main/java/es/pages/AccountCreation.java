/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.SpringUtils;
import entities.UserInformation;
import es.model.service.UserService;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * @author Javier
 *
 */
public class AccountCreation {
	@Property
	private UserInformation userInformation;
	//private AccountInformation accountInformation;

	@InjectPage
	private ErrorPage errorPage;
	
	@Property
	private String newUsername;
	
	@Property
	private String newPassword;
	
	@Property
	private String newEmail;
	
	private UserService userService;
	
	public AccountCreation()
	{
		userService = SpringUtils.getUserService();
	}
	
	//This method is executed when the form inside AccountCreation Page is submitted.
	Object onSuccess()
	{
		if(userService.addUser(new User(newUsername, newPassword, newEmail)) == null)
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
