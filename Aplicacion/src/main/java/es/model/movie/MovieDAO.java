package es.model.movie;

import java.util.ArrayList;
import java.util.List;

import es.model.util.exceptions.InstanceNotFoundException;

public interface MovieDAO {

	public abstract Movie find(String movieName)
			throws InstanceNotFoundException;
	
	public abstract ArrayList<Movie> findByPremiereDate(java.sql.Date date, int amount); 
	
	public abstract ArrayList<Movie> findMoviesForGenre(int genreID, int amount);
	
	public abstract ArrayList<Movie> findMoviesByActor(int actorID); 

	public abstract int findCalification(String movieName, String userName)
			throws InstanceNotFoundException; 

	public abstract int findCalificationAverage(String movieName);
	
	public abstract Movie insert(Movie movie);

	public abstract void addActorToMovie(String movieTitle, int actorID);
	
	public abstract void addGenreToMovie(String movieTitle, int genreID);
	
	public abstract void addCalificationToMovie(String movieTitle, String username, int calification);
	
	public abstract void update(Movie movie)
			throws InstanceNotFoundException;

	public abstract void delete(String movieName)
			throws InstanceNotFoundException;

	public abstract List<Movie> findLastMoviesAdded(int i);

	public abstract void executeMoviesSimilarityAlgorithm();
}
