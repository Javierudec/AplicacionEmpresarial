package es.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import util.SpringUtils;
import encoders.UserEncoder;
import es.model.user.User;

public class DeleteUser 
{
	@Property
	private SelectModel userSelectModel;
	@Property
	private User selectedUser;
	@Property
	UserEncoder userEncoder;
	
	@InjectPage
	private ConfirmUserDeletion confirmUserDeletion;
	
	@Inject
	SelectModelFactory selectModelFactory;
	
	DeleteUser()
	{
		userEncoder = new UserEncoder();
	}
	
	void onActivate()
	{
		List<User> userList = SpringUtils.getUserService().getAllUsers();
		userSelectModel = selectModelFactory.create(userList, "name");
	}
	
	Object onSuccessFromDeleteUserForm()
	{
		confirmUserDeletion.setUserToDelete(selectedUser);		
		
		return confirmUserDeletion;
	}
}