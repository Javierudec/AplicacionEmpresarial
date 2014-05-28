/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.ioc.annotations.Inject;

import util.SpringUtils;
import entities.LoginInformation;
import es.model.service.UserService;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * @author Javier
 *
 */
public class Login {
	@Property
	private LoginInformation loginInformation;
	
	@InjectPage
	private Index index;
	
	private UserService userService;
	
	@SessionAttribute("loggedInUserName")
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	public Login()
	{
		userService = (UserService) SpringUtils.getContext().getBean("userServiceBean");
	}
	
	//This method is executed when the form inside AccountCreation Page is submitted.
	Object onSuccess()
	{		
		//Check is data is valid (User in database and Password match)
		try {
			User user = userService.findUserByName(loginInformation.username);
			
			if(user != null && user.getPassword().equals(loginInformation.password))
			{
				username = user.getName();
				System.out.println(username);
			}

		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return index; //Redirect to Index page.
	}
}