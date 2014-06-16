/**
 * 
 */
package es.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;

import util.FilterByTitle;
import util.MovieList;
import util.SpringUtils;
import es.model.service.*;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;
import es.pages.ErrorPage;
import es.pages.Index;
import es.pages.Movies;

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
	
	@Inject 
	private ComponentResources res; 
	
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
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;
    
    @InjectPage
    private Index index;
    
    @InjectPage
    private ErrorPage errorHandler;
    
    @InjectPage
    private Movies moviesPage;
    
    @Property
    private String searchByTitlePattern;
    
    public Layout()
    {
    	searchByTitlePattern = "Search by title...";
    }
    
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
    
    public String getClassForPageName()
    {
      return resources.getPageName().equalsIgnoreCase(pageName)
             ? "current_page_item"
             : null;
    }

    public String[] getPageNames()
    {
      return new String[] { "Home", "Movies", "Reviews", "About Us" };
    }
    
    public String getPageURL()
    {
    	if(pageName == "Home") return "Index";
    	if(pageName == "Movies") return "Movies";
    	if(pageName == "Reviews") return "Reviews";
    	if(pageName == "About Us") return "AboutUs";
    	
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
    
    
	Object onActionFromLogout()
	{
		username = null;
		
		return index;
	}
	
	Object onActionFromViewUserProfile()
	{	
		return null;
	}
	
	Object onSuccessFromLoginForm()
	{		
		try {
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
			
		} catch (InstanceNotFoundException e) {
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