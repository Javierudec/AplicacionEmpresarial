/**
 * 
 */
package es.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import util.SpringUtils;
import es.model.movie.Movie;
import es.model.service.MovieService;

public class Index
{
	private MovieService movieService;
	
	@InjectPage
	private MovieProfile movieProfile;
	
	@Property
	private Movie movieName;
	
	public Index()
	{
		movieService = SpringUtils.getMovieService();
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
	
	Object onActionFromViewProfile(String movieName)
	{		
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
}