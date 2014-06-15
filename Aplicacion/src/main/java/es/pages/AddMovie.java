/**
 * 
 */
package es.pages;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.upload.services.UploadedFile;

import util.SpringUtils;
import entities.MovieInformation;
import es.model.movie.Movie;
import es.model.service.MovieService;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * @author Javier
 *
 */
public class AddMovie {
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	private MovieService movieService;
	
	@InjectPage
	private Index index;
	
	@Property
	private String movieTitle;
	
	@Property
	private String description;
	
	@Property 
	private java.util.Date debutDate;
	
	@Property
	private UploadedFile file;
	
	public AddMovie()
	{
		movieService = SpringUtils.getMovieService();
	}

	public boolean getIsAdmin()
	{
		if(username == null) return false;
		
		User user = null;
		
		try {
			user = SpringUtils.getUserService().findUserByName(username);
			return user.getIsAdmin();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public Object onSuccess()
	{		
		Movie movie = new Movie(movieTitle, description, new Date(debutDate.getTime()));
		
		File copied = new File(file.getFileName());
		
		file.write(copied);
		
		movie.setImage(file.getFileName());
		
		movieService.addMovie(movie);
		
		return index;
	}
}