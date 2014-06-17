package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import util.SpringUtils;
import util.Utils;

public class AddGenre 
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@Property
	private String genreName;
	
	@InjectPage
	private ErrorPage errorPage;
	
	public boolean getIsAdmin()
	{
		return Utils.getIsAdmin(username);
	}
	
	Object onSuccessFromAddGenre()
	{
		SpringUtils.getMovieService().addGenre(genreName);
		errorPage.setErrorMsg(genreName + " was successfully added.");
		
		return errorPage;
	}
}