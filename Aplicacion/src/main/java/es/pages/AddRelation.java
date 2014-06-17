package es.pages;

import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.services.SelectModelFactory;

import util.SpringUtils;
import encoders.MovieEncoder;
import es.model.movie.Movie;

public class AddRelation 
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.	@SessionAttribute("loggedInUserName")
	
	@Property
	@Persist
	private String movieName;
	
	@Property
	private String relationDescription;
	
	@Property
	private Movie selectedMovie;
	
	@Property
	private SelectModel movieSelectModel;
	
	@Inject
	SelectModelFactory selectModelFactory;
	
	@Property
	MovieEncoder movieEncoder;
	
	@InjectPage
	private ErrorPage errorPage;
	
	public AddRelation()
	{
		movieEncoder = new MovieEncoder();
		List<Movie> movieList = SpringUtils.getMovieService().getAllMovies();
		movieSelectModel = selectModelFactory.create(movieList, "name");
	}
	
	public void setMovie(String movieName) {
		this.movieName = movieName;
	}
	
	Object onSuccessFromAddRel()
	{
		if(selectedMovie != null
		   && movieName != null
		   && relationDescription != null)
		{
			SpringUtils.getMovieService().addRelationForMovie(movieName, selectedMovie.getName(), username, relationDescription);
			
			errorPage.setErrorMsg("Your comment was added successfully.");
		}
		else
		{
			errorPage.setErrorMsg("Your comment was not added successfully.");
		}
		
		return errorPage;
	}
}