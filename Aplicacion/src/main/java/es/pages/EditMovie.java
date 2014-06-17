package es.pages;

import java.io.File;
import java.sql.Date;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.upload.services.UploadedFile;

import util.SpringUtils;
import util.Utils;
import es.model.movie.Movie;
import es.model.util.exceptions.InstanceNotFoundException;

public class EditMovie 
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; 
	
	@Persist
	private Movie movieToEdit;
	@Property
	@Persist
	private String movieTitle;
	@Property
	@Persist
	private String description;
	@Property 
	@Persist
	private String videourl;
	@Property 
	private java.util.Date debutDate;
	@Property
	private UploadedFile file;
	
	@InjectPage
	private Index index;
	@InjectPage
	private ErrorPage errorPage;
	
	public boolean getIsAdmin()
	{
		return Utils.getIsAdmin(username);
	}
	
	public void setMovieToEdit(String movieName)
	{
		try
		{
			movieToEdit = SpringUtils.getMovieService().findMovieByName(movieName);
			
			if(movieToEdit != null)
			{
				movieTitle  = movieToEdit.getName();
				description = movieToEdit.getSynopsys();
				videourl    = movieToEdit.getVideoURL();
			}
		}
		catch (InstanceNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	Object onSuccess()
	{
		if(movieToEdit == null) return null;
		
		movieToEdit.setName(movieTitle);
		movieToEdit.setSynopsys(description);
		movieToEdit.setVideoURL(videourl);

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
	

}