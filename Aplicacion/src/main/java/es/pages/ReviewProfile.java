package es.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.corelib.components.Zone;

import util.SpringUtils;
import es.model.article.Article;
import es.model.util.exceptions.InstanceNotFoundException;

public class ReviewProfile 
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@Property
	@Persist
	private Article review;
	@Property
	private String scoreSelected;
	
	@InjectComponent
	private Zone userScoreZone;
	
	public void setReview(int id)
	{
		try 
		{
			review = SpringUtils.getArticleService().findArticleByID(id);
		}
		catch(InstanceNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getAverageScore()
	{
		Integer currAvgScore = 0;
		
		if(review != null)
		{
			currAvgScore = SpringUtils.getArticleService().findCalificationAverage(review.getID());
		}
		
		return "images/" + currAvgScore + "_star.png";
	}
	
	public String getReviewScore()
	{
		Integer currUserScore = 0;
		
		if(review != null 
		   && username != null)
		{
			try 
			{
				currUserScore = SpringUtils.getArticleService().findCalification(username, review.getID());
			}
			catch(InstanceNotFoundException e) 
			{
				return "images/0_star.png";
			}
		}
		
		return "images/" + currUserScore + "_star.png";
	}
	
	public int getNumUsersAvgScore()
	{
		return SpringUtils.getArticleService().findNumUsersCalification(review.getID());
	}
	
	public Object onValueChanged(String score)
	{
		if(review != null
		   && username != null)
		{			
			try 
			{
				SpringUtils.getArticleService().addArticleCalificationForUser(Integer.parseInt(score), username, review.getID());
			}
			catch(NumberFormatException e)
			{
				e.printStackTrace();
			} 
			catch(InstanceNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	
		return userScoreZone.getBody();
	}
}