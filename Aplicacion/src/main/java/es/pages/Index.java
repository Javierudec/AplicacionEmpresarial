/**
 * 
 */
package es.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import util.SpringUtils;
import es.model.movie.Movie;
import es.model.service.MovieService;
import es.model.service.UserService;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public class Index
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	private MovieService movieService;
	private UserService userService;
	
	@InjectPage
	private MovieProfile movieProfile;
	@InjectPage
	private Index index;
	
	@Property
	private Movie movieName;
	
	public Index()
	{
		movieService = SpringUtils.getMovieService();
		userService = SpringUtils.getUserService();
	}
	
	@PageLoaded
	public void Init()
	{
		movieName = null;
	}
	
	public List<Movie> getLastMovies()
	{
		List<Movie> movieList = movieService.findLastMoviesAdded(10);
		
		return movieList;
	}
	
	public int getMovieScore()
	{
		return movieService.findCalificationAverage(movieName.getName());
	}
	
	public boolean getIsAdmin()
	{
		try {
			User user = userService.findUserByName(username);
			
			return user.getIsAdmin();
			
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	/*
	Object onActionFromViewProfile(String movieName)
	{				
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromLogout()
	{
		username = null;
		
		return index;
	}
	*/
}