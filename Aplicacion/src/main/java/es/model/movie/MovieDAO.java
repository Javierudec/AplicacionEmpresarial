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

	public abstract List<Movie> findLastMoviesByDebut(int i);

	public abstract int findNumCalifications(String movieName);
	
	public abstract List<Movie> findMoviesOrderByRank();

	public abstract void executeMoviesSimilarityAlgorithm(String name);

	public abstract List<Movie> getAllMovies();

	public abstract List<Integer> findMoviesRankedBy(int userID);

	public abstract double findSimilarity(int movieID, int movieRankedID);

	public abstract int findCalification(int movieRankedID, int userID);

	public abstract List<Movie> findRecommendationsFor(int id, float f);
	
	public abstract void updateMovie(Movie movie);

	public abstract List<Movie> getAll();

	public abstract Movie findByID(int id) throws InstanceNotFoundException;
}
