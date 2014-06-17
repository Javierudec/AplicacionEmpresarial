package es.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import util.FilterByActor;
import util.FilterByGenre;
import util.MovieList;
import util.SpringUtils;
import util.Utils;
import encoders.ActorEncoder;
import encoders.GenreEncoder;
import es.model.actor.Actor;
import es.model.genre.Genre;
import es.model.movie.Movie;
import es.model.relation.Relation;
import es.model.util.exceptions.InstanceNotFoundException;

public class MovieProfile
{
	@SessionAttribute("loggedInUserName")
	@Property
	private String username; 
	
	@Persist
	@Property
	private String movieName;
	@Property
	@Persist
	private String scoreSelected;
	@Persist
	@Property
	private Movie movieData;
	@Property
	private Genre selectedGenre;
	@Property
	private Actor selectedActor;
	@Property
	private SelectModel genreSelectModel;
	@Property
	private SelectModel actorSelectModel;
	@Property
	GenreEncoder genreEncoder;
	@Property
	ActorEncoder actorEncoder;
	@Property
	private Genre currGenre;
	@Property 
	private Actor currActor;
	@Property
	private Relation relation;

	@InjectPage
	private Index index;
	@InjectPage
	private EditMovie editMovie;
	@InjectPage
	private ErrorPage errorPage;
	@InjectPage
	private MovieProfile movieProfile;	
	@InjectPage
	private Movies moviesPage;
	@InjectPage
	private AddRelation addRelation;
	
	@Inject
	SelectModelFactory selectModelFactory;
	
	@InjectComponent
	private Zone userScoreZone;
	@InjectComponent
	private Zone genreListZone;
	@InjectComponent
	private Zone actorListZone;
	
	public MovieProfile()
	{		
		genreEncoder = new GenreEncoder();
		actorEncoder = new ActorEncoder();
	}
	
	public boolean getIsAdmin()
	{
		return Utils.getIsAdmin(username);
	}
	
	public void setMovieByName(String movieName)
	{
		this.movieName = movieName;
		
		try 
		{
			movieData = SpringUtils.getMovieService().findMovieByName(movieName);
			scoreSelected = "0";
			
			if(username != null)
			{
				scoreSelected = SpringUtils.getMovieService().findCalification(movieName, username).toString();
			}
			
		} 
		catch(InstanceNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public List<Genre> getMovieGenres()
	{
		return SpringUtils.getMovieService().findGenreForMovie(movieName);
	}
	
	public List<Actor> getMovieActors()
	{
		return SpringUtils.getMovieService().findActorForMovie(movieName);
	}
	
	public List<Relation> getRelations()
	{
		return SpringUtils.getMovieService().findRelationsForMovie(movieName, 5);
	}
	
	public String getMovieImage()
	{
		if(movieData == null) return "NoImage";
		
		return "images/" + movieData.getImage();
	}
	
	public String getMovieDescription()
	{
		if(movieData == null) return "No description found.";
		
		return movieData.getSynopsys();
	}
	
	public String getAverageScore()
	{
		Integer currAvgScore = 0;

		if(movieName != null)
		{
			currAvgScore = SpringUtils.getMovieService().findCalificationAverage(movieName);
		}
		
		return "images/" + currAvgScore + "_star.png";
	}
	
	public String getUserScore()
	{
		Integer currUserScore = 0;
		
		if(movieName != null && username != null)
		{
			try 
			{
				currUserScore = SpringUtils.getMovieService().findCalification(movieName, username);
			}
			catch(InstanceNotFoundException e) 
			{
				return "images/0_star.png";
			}
		}
		
		return "images/" + currUserScore + "_star.png";
	}
	
	public String getDestinyMovieImage()
	{
		try 
		{
			return "images/" + SpringUtils.getMovieService().findMovieByName(relation.getDestinyMovie()).getImage();
		} 
		catch(InstanceNotFoundException e) 
		{
			return "";
		}
	}
	
	public String getAverageScoreRelation()
	{
		Integer currAvgScore = 0;

		if(relation.getDestinyMovie() != null)
		{
			currAvgScore = SpringUtils.getMovieService().findCalificationAverage(relation.getDestinyMovie());
		}
		
		return "images/" + currAvgScore + "_star.png";
	}
	
	public boolean getVideoExists()
	{		
		return movieData != null && movieData.getVideoURL().compareTo("NOVIDEO") != 0;
	}
	
	public int getNumUsersAvgScore()
	{
		int numUsers = 0;
		
		if(movieName != null)
		{
			numUsers = SpringUtils.getMovieService().findNumCalifications(movieName);
		}
		
		return numUsers;
	}
	
	void onActivate()
	{
		List<Genre> genreList = SpringUtils.getMovieService().getAllGenres();
		genreSelectModel = selectModelFactory.create(genreList, "name");
		
		List<Actor> actorList = SpringUtils.getMovieService().getAllActors();
		actorSelectModel = selectModelFactory.create(actorList, "name");		
	}
	
	public Object onValueChanged(String score)
	{
		if(movieName != null 
		   && username != null)
		{
			SpringUtils.getMovieService().setMovieCalificationForUser(username, movieName, Integer.parseInt(score));
		}
		
		return userScoreZone.getBody();
	}
	
	Object onActionFromSearchByGenre(String genre)
	{
		MovieList.setList(FilterByGenre.FilterList(genre, MovieList.getCompleteList()));
		
		return moviesPage;
	}
	
	Object onActionFromSearchByActor(String actor)
	{
		MovieList.setList(FilterByActor.FilterList(actor, MovieList.getCompleteList()));
		
		return moviesPage;
	}
	
	Object onActionFromAddRelation()
	{
		addRelation.setMovie(movieName);
		
		return addRelation;
	}
	
	Object onActionFromViewProfileRelation(String movieName)
	{		
		movieProfile.setMovieByName(movieName);
		
		return movieProfile;
	}
	
	Object onActionFromEditMovie()
	{
		editMovie.setMovieToEdit(movieName);
		
		return editMovie;
	}
	
	Object onActionFromDeleteMovie()
	{
		try
		{
			SpringUtils.getMovieService().deleteMovie(movieName);
		} 
		catch(InstanceNotFoundException e)
		{
			e.printStackTrace();
		}
		
		errorPage.setErrorMsg("Movie: " + movieName + " was removed successfully.");
		
		return errorPage;
	}
	
	Object onSuccessFromAddGenreForm()
	{
		SpringUtils.getMovieService().addGenreToMovie(movieName, selectedGenre.getName());
		
		return genreListZone.getBody();
	}
	
	Object onSuccessFromAddActorForm()
	{
		SpringUtils.getMovieService().addActorToMovie(movieName, selectedActor.getName());
		
		return actorListZone.getBody();
	}
}