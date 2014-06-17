/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.corelib.components.Zone;

import util.SpringUtils;
import es.model.article.Article;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * @author Javier
 *
 */
public class ReviewProfile {
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@InjectComponent
	private Zone userScoreZone;

	@Property
	@Persist
	private Article review;
	
	public void setReview(int id)
	{
		try {
			review = SpringUtils.getArticleService().findArticleByID(id);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAverageScore()
	{
		return "images/" + 1 + "_star.png";
		/*
		Integer currAvgScore = 0;
		
		//System.out.println("Movie name: " + movieName);
		
		if(movieName != null)
		{
			currAvgScore = movieService.findCalificationAverage(movieName);
		}
		
		return "images/" + currAvgScore + "_star.png";
		*/
	}
	
	public String getUserScore()
	{
		/*
		Integer currUserScore = 0;
		
		if(movieName != null && username != null)
		{
			try {
				currUserScore = movieService.findCalification(movieName, username);
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				return "images/0_star.png";
			}
		}
		
		return "images/" + currUserScore + "_star.png";
		*/
		return "images/" + 1 + "_star.png";
	}
	
	public int getNumUsersAvgScore()
	{
		return 0;
	}
	
	public Object onValueChanged(String score)
	{
		/*
		System.out.println("Zone score: " + score);
		
		if(movieName != null && username != null)
		{
			SpringUtils.getMovieService().setMovieCalificationForUser(username, movieName, Integer.parseInt(score));
		}
		*/
		return userScoreZone.getBody();
	}
}