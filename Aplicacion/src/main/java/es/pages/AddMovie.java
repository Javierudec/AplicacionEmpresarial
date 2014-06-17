package es.pages;

import java.io.File;
import java.sql.Date;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.upload.services.UploadedFile;

import util.SpringUtils;
import es.model.movie.Movie;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public class AddMovie 
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; //Information about identified user. PERSISTENT to all page sites.
	
	@Property
	private String movieTitle;
	@Property
	private String description;
	@Property 
	private java.util.Date debutDate;
	@Property
	private UploadedFile file;
	@Property
	private String videourl;
	
	@InjectPage
	private Index index;
	
	void onActivate()
	{
		videourl = "NOVIDEO";
	}

	public boolean getIsAdmin()
	{
		if(username == null) return false;
		
		User user = null;
		
		try 
		{
			user = SpringUtils.getUserService().findUserByName(username);
			
			return user.getIsAdmin();
		}
		catch(InstanceNotFoundException e) 
		{
			return false;
		}
	}
	
	public Object onSuccess()
	{		
		Movie movie = new Movie(movieTitle, description, new Date(debutDate.getTime()));
		File copied = new File(file.getFileName());
		
		file.write(copied);
		movie.setImage(file.getFileName());
		movie.setVideoURL(videourl);
		SpringUtils.getMovieService().addMovie(movie);
		
		return index;
	}
}