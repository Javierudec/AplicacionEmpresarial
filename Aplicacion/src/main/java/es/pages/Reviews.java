/**
 * 
 */
package es.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import es.model.article.Article;
import util.ReviewList;

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
	}
	
	Object onActionFromViewReview(int id)
	{
		return null;
	}
}