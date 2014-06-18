/**
 * 
 */
package es.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;

import util.FilterByTitle;
import util.MovieList;
import util.SpringUtils;
import util.Utils;
import es.pages.ErrorPage;
import es.pages.Index;
import es.pages.Movies;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * Layout component for pages of application tutorial1.
 */
//@IncludeStylesheet("context:layout/layout.css")
//@Import(stylesheet="context:/layout/layout.css")
public class Layout
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
		
    /** The page title, for the <title> element and the <h1> element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;
    @Property
    private String loginUsername;
    @Property
    private String loginPassword;
    @Property
    private String pageName;
    @Property
    private String searchByTitlePattern;
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;
	@Inject 
	private ComponentResources res; 
	
    @InjectPage
    private Index index;
    @InjectPage
    private ErrorPage errorHandler;
    @InjectPage
    private Movies moviesPage;
    
    public Layout()
    {
    	searchByTitlePattern = "Search by title...";
    }
    
	public boolean getIsAdmin()
	{
		return Utils.getIsAdmin(username);
	}
    
    public String getClassForPageName()
    {
      return resources.getPageName().equalsIgnoreCase(pageName)
             ? "current_page_item"
             : null;
    }

    public String[] getPageNames()
    {
      return new String[] { "Home", "Movies", "Articles", "About Us" };
    }
    
    public String getPageURL()
    {   	
    	if(pageName.compareTo("Home") == 0) return "Index";
    	if(pageName.compareTo("Movies") == 0) return "Movies";
    	if(pageName.compareTo("Articles") == 0) return "Reviews";
    	if(pageName.compareTo("About Us") == 0) return "AboutUs";
    	
    	return "";
    }
    
    
    public String getClassPage()
    {
    	if(getPageURL().compareToIgnoreCase(res.getPageName()) == 0) 
    	{
    		return "active";
    	}
    	
    	return "";
    }
    
    public String getCurrentTime()
    {
    	DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
    	Date date = new Date();
    	return dateFormat.format(date);
    }
    
	Object onActionFromLogout()
	{
		username = null;
		
		return index;
	}
	
	/*
	Object onActionFromViewUserProfile()
	{	
		return null;
	}
	*/
	
	Object onSuccessFromLoginForm()
	{		
		try 
		{
			User user = SpringUtils.getUserService().findUserByName(loginUsername);
				
			if(user != null && user.getPassword().equals(loginPassword))
			{
				username = user.getName();
			}
			else
			{
				errorHandler.setErrorMsg("Usuario o contraseña no validos.");
				return errorHandler;
			}
			
		} 
		catch(InstanceNotFoundException e) 
		{
			errorHandler.setErrorMsg("Usuario o contraseña no validos.");
			
			return errorHandler;
		}
		
		return index;
	}
	
	Object onSuccessFromSearchByTitle()
	{
		MovieList.setCompleteList(SpringUtils.getMovieService().findMoviesOrderByRank());
		MovieList.setList(FilterByTitle.FilterList(searchByTitlePattern, MovieList.getCompleteList()));
		
		return moviesPage;
	}
}