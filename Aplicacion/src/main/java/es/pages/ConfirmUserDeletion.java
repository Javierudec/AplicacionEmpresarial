package es.pages;

import es.model.user.User;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import util.SpringUtils;

public class ConfirmUserDeletion
{
	@Property
	@Persist
	private User user;

	@InjectPage
	private ErrorPage errorPage;
	
	public void setUserToDelete(User selectedUser) 
	{
		user = selectedUser;
	}
	
	Object onActionFromDeleteUser()
	{
		SpringUtils.getUserService().deleteUser(user);
		errorPage.setErrorMsg("Usuario fue eliminado.");
		
		return errorPage;
	}
	
	Object onActionFromCancelAction()
	{
		errorPage.setErrorMsg("Usuario no fue eliminado.");
	
		return errorPage;
	}
}