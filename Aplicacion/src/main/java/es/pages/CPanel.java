/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;
import util.SpringUtils;

/**
 * @author Javier
 *
 */
public class CPanel {
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	public boolean getIsAdmin()
	{
		if(username == null) return false;
		
		User user = null;
		
		try {
			user = SpringUtils.getUserService().findUserByName(username);
			return user.getIsAdmin();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}