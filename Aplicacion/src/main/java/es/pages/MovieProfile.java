/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Javier
 *
 */
public class MovieProfile {
	@Persist
	@Property
	private String movieName;
	
	public void setMovieByName(String movieName)
	{
		this.movieName = movieName;
	}
}