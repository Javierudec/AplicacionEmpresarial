/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
	private Index index;
	
	private UserService userService;
	
	public AccountCreation()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");
    	
    	userService = (UserService)context.getBean("userServiceBean");
	}
	
	//This method is executed when the form inside AccountCreation Page is submitted.
	Object onSuccess()
	{
		userService.addUser(new User(userInformation.name, userInformation.password, userInformation.email));
		
		return index;
	}
}
