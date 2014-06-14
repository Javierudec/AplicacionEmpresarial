/**
 * 
 */
package es.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Property;

import util.SpringUtils;
import es.model.movie.Movie;

/**
 * @author Javier
 *
 */
public class Movies {
	@Property
	String currentLetter;
	
	@Property
	Movie currentMovie;
	
	List<Movie> completeList;
	List<Movie> movieList;
	
	public String[] getABC()
	{
		return new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "Z" };
	}
	
	@PageLoaded
	void InitPage()
	{
		completeList = SpringUtils.getMovieService().findMoviesOrderByRank();
		
		movieList = completeList.subList(0, Math.min(10, completeList.size()));
		//movieList = new ArrayList<Movie>();
		//movieList.add(new Movie("HOLA", "HOLA", null));
	}
	
	Object onActionFromLetter(String letter)
	{
		
		
		return null;
	}
	
	Object onActionFromViewProfile(Movie movie)
	{
		
		return null;
	}
	
	public List<Movie> getMovieList()
	{
		return movieList;
	}
	
	public String getAverageScore()
	{
		Integer currAvgScore = 0;
		
		if(currentMovie != null)
		{
			currAvgScore = SpringUtils.getMovieService().findCalificationAverage(currentMovie.getName());
		}
		
		return "images/" + currAvgScore + "_star.png";
	}
	
	public String getMovieImage()
	{
		if(currentMovie == null) return "NoImage";
		
		return "images/" + currentMovie.getImage();
	}
	
	public String getMovieDescription()
	{
		if(currentMovie == null) return "No description found.";
		
		return currentMovie.getSynopsys();
	}
}