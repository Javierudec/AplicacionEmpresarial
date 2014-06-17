/**
 * 
 */
package es.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import encoders.TagEncoder;
import es.model.article.Article;
import es.model.tag.Tag;
import util.FilterByTag;
import util.FilterByTitle;
import util.ReviewList;
import util.SpringUtils;

/**
 * @author Javier
 *
 */
public class Reviews 
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@Property
	private Article currentReview;
	
	@Property
	private String title;
	
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
	
	@InjectComponent
	private Zone reviewListZone;
	
	public boolean getReviewListNotEmpty()
	{
		return ReviewList.getList().size() > 0;
	}
	
	public List<Article> getReviewList()
	{
		return ReviewList.getList();
	}
	
	public Reviews()
	{
		ReviewList.setList(ReviewList.getCompleteList());
		
		tagEncoder = new TagEncoder();
		List<Tag> tagList = SpringUtils.getArticleService().getAllTags();
		tagSelectModel = selectModelFactory.create(tagList, "name");
	}
	
	Object onActionFromViewReview(int id)
	{
		return null;
	}
	
	Object onSuccessFromFilterArticle()
	{
		ReviewList.setList(ReviewList.getCompleteList());
		
		if(title != null && title.length() > 0)
		{
			ReviewList.setList(FilterByTitle.FilterArticleList(title, ReviewList.getCurrentList()));
		}
		
		//System.out.println("Tag1: " + selectedTag1.getName());
		
		if(selectedTag1 != null && selectedTag1.getName().compareTo("None") != 0)
		{
			//System.out.println("FilterByTag1");
			
			ReviewList.setList(FilterByTag.FilterArticleList(selectedTag1.getName(), ReviewList.getCurrentList()));
		}
		
		if(selectedTag2 != null && selectedTag2.getName().compareTo("None") != 0)
		{
			ReviewList.setList(FilterByTag.FilterArticleList(selectedTag2.getName(), ReviewList.getCurrentList()));
		}
		
		if(selectedTag3 != null && selectedTag3.getName().compareTo("None") != 0)
		{
			ReviewList.setList(FilterByTag.FilterArticleList(selectedTag3.getName(), ReviewList.getCurrentList()));
		}
		
		System.out.println("SIZE: " + ReviewList.getCurrentList().size());
		
		return reviewListZone.getBody();
	}
}