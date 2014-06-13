/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import util.SpringUtils;
import entities.MovieEvaluation;
import es.model.movie.Movie;
import es.model.service.MovieService;
import es.model.service.UserService;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * @author Javier
 *
 */
public class MovieProfile {
	@Persist
	@Property
	private String movieName;
	
	@Persist
	@Property
	private Movie movieData;
	
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@InjectPage
	private Index index;
	
	private MovieService movieService;
	
	@Property
	private MovieEvaluation movieEvaluation;
	
	public void setMovieByName(String movieName)
	{
		this.movieName = movieName;
		try {
			movieData = SpringUtils.getMovieService().findMovieByName(movieName);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MovieProfile()
	{
		movieService = SpringUtils.getMovieService();
	}
	
	//This method is executed when the form inside AccountCreation Page is submitted.
	Object onSuccess()
	{		
		movieService.setMovieCalificationForUser(username, movieName, movieEvaluation.toInt());
		
		return index; //Redirect to Index page.
	}
	
	public String getMovieImage()
	{
		if(movieData == null) return "NoImage";
		
		return "images/" + movieData.getImage();
	}
}