/**
 * 
 */
package es.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import encoders.TagEncoder;
import es.model.article.Article;
import es.model.tag.Tag;
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
	
	@Property
	private Tag selectedTag1;
	@Property
	private Tag selectedTag2;
	@Property
	private Tag selectedTag3;
	
	@Property
	private SelectModel tagSelectModel;
	
	@Property
	private TagEncoder tagEncoder;
	
	@Inject
	SelectModelFactory selectModelFactory;
	
	public AddArticle()
	{
		tagEncoder = new TagEncoder();
		List<Tag> tagList = SpringUtils.getArticleService().getAllTags();
		tagList.add(0, new Tag(-1, "None"));
		tagSelectModel = selectModelFactory.create(tagList, "name");
	}
	
	Object onSuccessFromAddArticleForm()
	{
		try 
		{
			Article articleCreated = SpringUtils.getArticleService().addArticle(title, content, username, null);
			
			System.out.println("Article: " + articleCreated.getID() + " - " + articleCreated.getTitle());
			
			if(selectedTag1 != null && selectedTag1.getName().compareTo("None") != 0)
			{
				SpringUtils.getArticleService().addTagToArticle(articleCreated, selectedTag1);
			}
			
			if(selectedTag2 != null && selectedTag2.getName().compareTo("None") != 0)
			{
				SpringUtils.getArticleService().addTagToArticle(articleCreated, selectedTag1);
			}
			
			if(selectedTag2 != null && selectedTag2.getName().compareTo("None") != 0)
			{
				SpringUtils.getArticleService().addTagToArticle(articleCreated, selectedTag1);
			}
			
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