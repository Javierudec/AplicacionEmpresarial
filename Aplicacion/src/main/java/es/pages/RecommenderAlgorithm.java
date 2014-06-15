/**
 * 
 */
package es.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import encoders.MovieEncoder;
import es.model.movie.Movie;
import util.SpringUtils;

/**
 * @author Javier
 *
 */
public class RecommenderAlgorithm {
	@InjectComponent
	private Zone currentStateZone;
	
	@Property
	private SelectModel colorSelectModel;
	
	@Property
	private Movie selectedColor;
	
	@Inject
	SelectModelFactory selectModelFactory;
	
	@Property
	MovieEncoder movieEncoder;
	
	@Property
	private String currentState;
	
	boolean bInProgress;
	
	RecommenderAlgorithm()
	{
		currentState = "Ready to start algorithm.";
		
		movieEncoder = new MovieEncoder();
		
		List<Movie> movieList = SpringUtils.getMovieService().findMoviesOrderByRank();
		colorSelectModel = selectModelFactory.create(movieList, "name");
		
	}
	
	Object onSuccess()
	{
		currentState = "Similarity calculated for movie: ." + selectedColor.getName();
		
		SpringUtils.getMovieService().executeMoviesSimilarityAlgorithm(selectedColor.getName());
		
		return currentStateZone.getBody();
	}
	
	public boolean getInProgress()
	{
		return bInProgress;
	}
	
	/*
	Object onActionFromInitAlgorithm()
	{
		//executeAlgorithm();
		
		currentState = "Recommendation System is up to date.";
		
		return currentStateZone.getBody();
	}
	*/
	
	void executeAlgorithm()
	{
		SpringUtils.getMovieService().executeMoviesSimilarityAlgorithm();
	}
}