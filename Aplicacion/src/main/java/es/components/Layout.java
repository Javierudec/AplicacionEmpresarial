/**
 * 
 */
package es.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import es.model.user.User;

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
    private String pageName;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;
    
    public Layout()
    {
    	//username = "Guest";
    }
    
    public String getClassForPageName()
    {
      return resources.getPageName().equalsIgnoreCase(pageName)
             ? "current_page_item"
             : null;
    }

    public String[] getPageNames()
    {
      return new String[] { "Index", "Movies", "Reviews", "AboutUs" };
    }
    
	Object onActionFromLogout()
	{
		username = null;
		
		return null;
	}
	
	Object onActionFromViewUserProfile()
	{	
		return null;
	}
}