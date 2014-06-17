/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import es.model.util.exceptions.InstanceNotFoundException;
import util.SpringUtils;

/**
 * @author Javier
 *
 */
public class AddArticle {
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@InjectPage
	private ErrorPage errorPage;
	
	@Property
	private String title;
	
	@Property
	private String content;
	
	Object onSuccessFromAddArticleForm()
	{
		try 
		{
			SpringUtils.getArticleService().addArticle(title, content, username, null);
			
			errorPage.setErrorMsg("Article was added successfully.");
		} 
		catch (InstanceNotFoundException e) 
		{
			errorPage.setErrorMsg("Article was not added successfully.");
		}
		
		System.out.println("HOLA");
		
		return errorPage;
	}
}