/**
 * 
 */
package es.pages;

import java.io.File;
import java.sql.Date;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.upload.services.UploadedFile;

import util.SpringUtils;
import es.model.movie.Movie;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * @author Javier
 *
 */
public class EditMovie {
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@Persist
	private Movie movieToEdit;
	
	@InjectPage
	private Index index;
	
	@InjectPage
	private ErrorPage errorPage;
	
	@Property
	@Persist
	private String movieTitle;
	
	@Property
	@Persist
	private String description;
	
	@Property 
	private java.util.Date debutDate;
	
	@Property
	private UploadedFile file;
	
	public EditMovie()
	{

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
	
	Object onSuccess()
	{
		if(movieToEdit == null) return null;
		
		movieToEdit.setName(movieTitle);
		movieToEdit.setSynopsys(description);

		if(debutDate != null)
		{
			movieToEdit.setPremiereDate(new Date(debutDate.getTime()));
		}
		
		if(file != null)
		{
			File copied = new File(file.getFileName());
			file.write(copied);
			movieToEdit.setImage(file.getFileName());
		}
		
		SpringUtils.getMovieService().updateMovie(movieToEdit);
		
		errorPage.setErrorMsg("Movie: " + movieTitle + " was edited successfully.");
		return errorPage;
	}
	
	public void setMovieToEdit(String movieName)
	{
		try {
			movieToEdit = SpringUtils.getMovieService().findMovieByName(movieName);
			
			if(movieToEdit != null)
			{
				movieTitle = movieToEdit.getName();
				description = movieToEdit.getSynopsys();
			}
			
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}