/**
 * 
 */
package es.pages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import util.SpringUtils;
import util.Utils;
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
	@Property
	private String tag;
	@Property
	private Movie recoMovie;
	@Property
	private Movie momMovie;
	
	//Account Creation
	@Property
	private String newUsername;
	@Property
	private String newPassword;
	@Property
	private String newEmail;
	
	@InjectPage
	private ErrorPage errorPage;
	
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
	
	Object onSuccess()
	{
		if(userService.addUser(new User(newUsername, newPassword, newEmail)) == null)
		{
			errorPage.setErrorMsg("Username is already used.");
		}
		else
		{
			errorPage.setErrorMsg("Account created. Now you can log in with your username and password.");
		}
		
		return errorPage;
	}
	
	public List<Movie> getLastMovies()
	{
		List<Movie> movieList = movieService.findLastMoviesAdded(10);
		
		return movieList;
	}
	
	public String getLastMovieAddedYear()
	{
		if(movieName != null && movieName.getPremiereDate() != null)
		{
			return Utils.getYear(movieName.getPremiereDate());
		}
		
		return "Unknown";
	}
	
	public int getMovieScore()
	{
		return movieService.findCalificationAverage(movieName.getName());
	}
	
	public List<Movie> getDebutMovies()
	{
		List<Movie> movieList = SpringUtils.getMovieService().findLastMoviesByDebut(10);
		return movieList;
	}
	
	public boolean getIsLoggedIn()
	{
		return username != null;
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
	
	public List<String> getTags()
	{
		List<String> listToRet = new ArrayList<String>();
		
		//THIS IS JUST A TEST!!!
		for(int i = 1; i < 7; i++)
		{
			try {
				listToRet.add(movieService.findGenreByID(i).getName());
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listToRet;
	}
	
	
	Object onActionFromViewProfile(String movieName)
	{		
		//movieService.executeMoviesSimilarityAlgorithm();
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromViewProfileRecoList(String movieName)
	{		
		//movieService.executeMoviesSimilarityAlgorithm();
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromViewProfileLastAdded(String movieName)
	{		
		//movieService.executeMoviesSimilarityAlgorithm();
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromViewSearch(String movieName)
	{
		return null;
	}
	
	Object onActionFromviewMovieOfTheMonth()
	{
		return null;
	}
	
	public List<Movie> getRecommendedMovies()
	{
		List<Movie> listToRet = null;
		
		if(username != null)
		{
			listToRet = SpringUtils.getUserService().findRecommendationsFor(username, 3.4f);
		}
		
		return listToRet.subList(0, Math.min(6, listToRet.size()));
	}
	
	public String getRecoMovieImage()
	{
		return "images/" + recoMovie.getImage();
	}
	
	public String getImageMovieScore()
	{
		Integer currAvgScore = movieService.findCalificationAverage(recoMovie.getName());
		
		return "images/" + currAvgScore + "_star.png";
	}
	
	public String getMomName()
	{
		momMovie = SpringUtils.getMovieService().findLastMoviesAdded(1).get(0);
		return momMovie.getName();
	}
	
	public String getMomScore()
	{
		Integer currAvgScore = movieService.findCalificationAverage(momMovie.getName());
		
		return "images/" + currAvgScore + "_star.png";
	}
	
	public String getMomImage()
	{
		return "images/" + momMovie.getImage();
	}
	
	public String getMomDescription()
	{
		return momMovie.getSynopsys();
	}
	
	public String getRecoMovieYear()
	{
		if(recoMovie != null && recoMovie.getPremiereDate() != null)
		{
			return Utils.getYear(recoMovie.getPremiereDate());
		}
		
		return "Unknown";
	}
}