/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;

import util.SpringUtils;
import entities.MovieInformation;
import es.model.service.MovieService;

/**
 * @author Javier
 *
 */
public class AddMovie {
	@Property
	private MovieInformation movieInformation;
	
	private MovieService movieService;
	
	@InjectPage
	private Index index;
	
	public AddMovie()
	{
		movieService = SpringUtils.getMovieService();
	}
	
	public Object onSuccess()
	{
		movieService.addMovie(movieInformation.title, "", null, null, null);
		
		return index;
	}
}