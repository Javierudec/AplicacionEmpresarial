package es.model.movie;

import java.util.ArrayList;

import es.model.util.exceptions.InstanceNotFoundException;

public interface MovieDAO {

	public abstract Movie find(String movieName)
			throws InstanceNotFoundException;
	
	public abstract ArrayList<Movie> findByPremiereDate(java.sql.Date date, int amount); 
	
	public abstract Movie insert(Movie movie);

	public abstract void addActorToMovie(String movieTitle, int actorID);
	
	public abstract void addGenreToMovie(String movieTitle, int genreID);
	
	public abstract void update(Movie movie)
			throws InstanceNotFoundException;

	
	public abstract void delete(String movieName)
			throws InstanceNotFoundException;
	
	
}
