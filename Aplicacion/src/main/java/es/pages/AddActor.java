package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import util.SpringUtils;
import util.Utils;

public class AddActor 
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@Property
	private String actorName;
	
	@InjectPage
	private ErrorPage errorPage;

	public boolean getIsAdmin()
	{
		return Utils.getIsAdmin(username);
	}
	
	Object onSuccessFromAddActor()
	{
		SpringUtils.getMovieService().addActor(actorName);
		
		errorPage.setErrorMsg(actorName + " was successfully added.");
		return errorPage;
	}
}