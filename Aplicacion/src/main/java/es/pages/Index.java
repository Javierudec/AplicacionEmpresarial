package es.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import util.FilterByGenre;
import util.MovieList;
import util.SpringUtils;
import util.Utils;
import es.model.genre.Genre;
import es.model.movie.Movie;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public class Index
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.

	@Property
	private Movie movieName;
	@Property
	private String tag;
	@Property
	private Movie recoMovie;
	@Property
	private Movie momMovie;
	@Property
	private String newUsername;
	@Property
	private String newPassword;
	@Property
	private String newEmail;
	
	@InjectPage
	private MovieProfile movieProfile;
	@InjectPage
	private Index index;
	@InjectPage
	private ErrorPage errorPage;
	@InjectPage
	private Movies movies;
	
	public List<Movie> getLastMovies()
	{
		List<Movie> movieList = SpringUtils.getMovieService().findLastMoviesAdded(10);
		
		return movieList;
	}
	
	public List<Movie> getDebutMovies()
	{
		List<Movie> movieList = SpringUtils.getMovieService().findLastMoviesByDebut(10);
		
		return movieList;
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
	
	public List<String> getTags()
	{
		List<Genre> genreList = SpringUtils.getMovieService().getAllGenres();
		
		int n = genreList.size() - 6;
		int rnd = (int)(Math.random() * n);
		
		List<String> listToRet = new ArrayList<String>();
		
		for(int i = 0; i < 6; i++)
		{
			listToRet.add(genreList.get(i + rnd).getName());
		}
		
		return listToRet;
	}
	
	public String getLastMovieAddedYear()
	{
		if(movieName != null 
		   && movieName.getPremiereDate() != null)
		{
			return Utils.getYear(movieName.getPremiereDate());
		}
		
		return "Unknown";
	}
	
	public String getRecoMovieImage()
	{
		return "images/" + recoMovie.getImage();
	}
	
	public String getImageMovieScore()
	{
		Integer currAvgScore = SpringUtils.getMovieService().findCalificationAverage(recoMovie.getName());
		
		return "images/" + currAvgScore + "_star.png";
	}
	
	public String getMomName()
	{
		momMovie = SpringUtils.getMovieService().findLastMoviesAdded(1).get(0);
		
		return momMovie.getName();
	}
	
	public String getMomScore()
	{
		Integer currAvgScore = SpringUtils.getMovieService().findCalificationAverage(momMovie.getName());
		
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
		if(recoMovie != null 
		   && recoMovie.getPremiereDate() != null)
		{
			return Utils.getYear(recoMovie.getPremiereDate());
		}
		
		return "Unknown";
	}
	
	public int getMovieScore()
	{
		return SpringUtils.getMovieService().findCalificationAverage(movieName.getName());
	}
	
	public boolean getIsLoggedIn()
	{
		return username != null;
	}
	
	public boolean getIsAdmin()
	{
		try 
		{
			User user = SpringUtils.getUserService().findUserByName(username);
			
			return user.getIsAdmin();
		}
		catch(InstanceNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}		
	
	Object onActionFromViewProfile(String movieName)
	{		
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromViewProfileRecoList(String movieName)
	{		
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromViewProfileLastAdded(String movieName)
	{		
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromViewSearch(String genreName)
	{
		MovieList.setList(FilterByGenre.FilterList(genreName, MovieList.getCompleteList()));
		
		return movies;
	}
	
	Object onActionFromviewMovieOfTheMonth(String movieName)
	{
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onSuccess()
	{
		if(SpringUtils.getUserService().addUser(new User(newUsername, newPassword, newEmail)) == null)
		{
			errorPage.setErrorMsg("Username is already used.");
		}
		else
		{
			errorPage.setErrorMsg("Account created. Now you can log in with your username and password.");
		}
		
		return errorPage;
	}
}