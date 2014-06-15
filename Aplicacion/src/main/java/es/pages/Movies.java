/**
 * 
 */
package es.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

import util.FilterByLetter;
import util.MovieList;
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
	
	@InjectPage
	private MovieProfile movieProfile;
	
	@InjectComponent
	private Zone movieListZone;
	
	public String[] getABC()
	{
		return new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "Z" };
	}
	
	Movies()
	{
		MovieList.setCompleteList(SpringUtils.getMovieService().findMoviesOrderByRank());
		//System.out.println(MovieList.getCompleteList().size());
		MovieList.setList(MovieList.getCompleteList());
		MovieList.setPage(0);
	}
	
	Object onActionFromLetter(String letter)
	{
		MovieList.setList(FilterByLetter.FilterList(letter, MovieList.getCompleteList()));
		
		return movieListZone.getBody();
	}
	
	Object onActionFromViewProfile(String movieName)
	{
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromNextPage()
	{
		MovieList.setPage(MovieList.getPage() + 1);
		
		return movieListZone.getBody();
	}
	
	Object onActionFromPrevPage()
	{
		MovieList.setPage(MovieList.getPage() - 1);
		
		return movieListZone.getBody();
	}
	
	public boolean getMovieListNotEmpty()
	{
		return MovieList.getList().size() != 0;
	}
	
	public List<Movie> getMovieList()
	{
		return MovieList.getList();
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
	
	public boolean getNextPage()
	{
		return MovieList.existNextPage();
	}
	
	public boolean getPrevPage()
	{
		return MovieList.existPrevPage();
	}
}