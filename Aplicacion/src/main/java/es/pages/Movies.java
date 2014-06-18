package es.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import util.FilterByActor;
import util.FilterByGenre;
import util.FilterByLetter;
import util.FilterByTitle;
import util.MovieList;
import util.SpringUtils;
import util.Utils;
import encoders.ActorEncoder;
import encoders.GenreEncoder;
import es.model.actor.Actor;
import es.model.genre.Genre;
import es.model.movie.Movie;

public class Movies 
{
	@Property
	String byTitle;	
	@Property
	String currentLetter;
	@Property
	Movie currentMovie;
	@Property
	private Actor selectedActor;
	@Property
	private Genre selectedGenre;
	@Property
	private Genre currentGenre;
	@Property
	private Actor currentActor;
	@Property
	private SelectModel actorSelectModel;
	@Property
	private SelectModel genreSelectModel;
	@Property
	private ActorEncoder actorEncoder;
	@Property
	private GenreEncoder genreEncoder;
	
	@InjectPage
	private MovieProfile movieProfile;
	@InjectPage
	private Movies movies;
	
	@InjectComponent
	private Zone movieListZone;
	
	@Inject
	SelectModelFactory selectModelFactory;
	
	public String[] getABC()
	{
		return new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "Z", "All" };
	}
	
	Movies()
	{
		actorEncoder = new ActorEncoder();
		genreEncoder = new GenreEncoder();
	}
	
	public boolean getMovieListNotEmpty()
	{
		return MovieList.getList().size() != 0;
	}
	
	public boolean getNextPage()
	{
		return MovieList.existNextPage();
	}
	
	public boolean getPrevPage()
	{
		return MovieList.existPrevPage();
	}
	
	public List<Movie> getMovieList()
	{
		return MovieList.getList();
	}
	
	public List<Genre> getGenreList()
	{
		return SpringUtils.getMovieService().findGenreForMovie(currentMovie.getName());
	}
	
	public List<Actor> getActorList()
	{
		return SpringUtils.getMovieService().findActorForMovie(currentMovie.getName());
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
	
	public String getCurrentMovieYear()
	{
		return Utils.getYear(currentMovie.getPremiereDate());
	}
	
	void onActivate()
	{
		if(!MovieList.isCompleteListSetted())
		{
			MovieList.setCompleteList(SpringUtils.getMovieService().findMoviesOrderByRank());
			MovieList.setList(MovieList.getCompleteList());
		}
		
		MovieList.setPage(0);
		
		List<Actor> actorList = SpringUtils.getMovieService().getAllActors();
		actorList.add(0, new Actor(-1, "All"));
		actorSelectModel = selectModelFactory.create(actorList, "name");
		
		List<Genre> genreList = SpringUtils.getMovieService().getAllGenres();
		genreList.add(0, new Genre(-1, "All"));
		genreSelectModel = selectModelFactory.create(genreList, "name");
		
		MovieList.UpdateListData();
	}
	
	Object onSuccess()
	{
		MovieList.setList(MovieList.getCompleteList());
		
		if(byTitle != null && !byTitle.isEmpty())
		{
			MovieList.setList(FilterByTitle.FilterList(byTitle, MovieList.getList()));
		}
		
		if(selectedActor != null && selectedActor.getName().compareTo("All") != 0)
		{
			MovieList.setList(FilterByActor.FilterList(selectedActor.getName(), MovieList.getCurrentList()));
		}
		
		if(selectedGenre != null && selectedGenre.getName().compareTo("All") != 0)
		{
			MovieList.setList(FilterByGenre.FilterList(selectedGenre.getName(), MovieList.getCurrentList()));
		}
		
		return movieListZone.getBody();
	}
	
	Object onActionFromLetter(String letter)
	{
		if(letter.compareTo("All") == 0) MovieList.setList(MovieList.getCompleteList());
		else
		{
			MovieList.setList(FilterByLetter.FilterList(letter, MovieList.getCompleteList()));
		}
		
		return movieListZone.getBody();
	}
	
	Object onActionFromViewByGenre(String genreName)
	{
		MovieList.setList(FilterByGenre.FilterList(genreName, MovieList.getCompleteList()));
		
		return movies;
	}
	
	Object onActionFromViewByActor(String actorName)
	{
		MovieList.setList(FilterByActor.FilterList(actorName, MovieList.getCompleteList()));
		
		return movies;
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
}