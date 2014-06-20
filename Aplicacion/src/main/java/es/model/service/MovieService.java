package es.model.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.model.actor.Actor;
import es.model.actor.ActorDAO;
import es.model.genre.Genre;
import es.model.genre.GenreDAO;
import es.model.movie.Movie;
import es.model.movie.MovieDAO;
import es.model.relation.Relation;
import es.model.relation.RelationDAO;
import es.model.util.exceptions.InstanceNotFoundException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class MovieService implements MovieServiceInterface {
	private ActorDAO actorDAO;
	private MovieDAO movieDAO;
	private GenreDAO genreDAO;
	private RelationDAO relationDAO;
	
	@SuppressWarnings("unused")
	private PlatformTransactionManager transactionManager;
	
	
	public Genre addGenre(String genreName) 
	{
		Genre genre = genreDAO.insert(genreName);
		
		return genre;
	}
	
	public Actor addActor(String actorName)
	{
		Actor actor = actorDAO.insert(actorName);
		
		return actor;
	}
	
	public Movie addMovie(String name, String synopsis, Date premierDate, ArrayList<Integer> actorsID, ArrayList<Integer> genresID) 
	{
		Movie movie = movieDAO.insert(new Movie(name, synopsis, premierDate));
		
		if(actorsID != null)
		{
			for( int i = 0; i < actorsID.size(); ++i ) 
				movieDAO.addActorToMovie(name, actorsID.get(i));
		}
		
		if(genresID != null)
		{
			for( int i = 0; i < genresID.size(); ++i ) 
				movieDAO.addGenreToMovie(name, genresID.get(i));
		}
		
		return movie;
	}
	
	// Tested!
	@Transactional
	public void addRelationForMovie(String sourceName, String destinyMovie, String username, String comment) 
	{
		relationDAO.insert( new Relation(sourceName, destinyMovie, username, comment) );		
	}

	/*
	 * Tested!
	 * TODO: añadir la restriccion, solo una calificacion por usuario
	 */
	@Transactional
	public void setMovieCalificationForUser(String userName, String movieName, Integer calification) 
	{
		movieDAO.addCalificationToMovie( userName, movieName, calification);		
	}
	// Tested!
	public Genre findGenreByID(Integer id) throws InstanceNotFoundException {
		Genre genre = null;
		try {
			genre = genreDAO.find(id);
		} catch (InstanceNotFoundException e) {
			throw e;
		}
		return genre;
	}

	//Tested!
	public Actor findActorByID(Integer id) throws InstanceNotFoundException {
		Actor actor = null;
		try {
			actor = actorDAO.find(id);
		} catch (InstanceNotFoundException e) {
			throw e;
		}
		return actor;
	}

	//Tested!
	public Movie findMovieByName(String movieName) throws InstanceNotFoundException {
		Movie movie = null;
		try {
			movie = movieDAO.find(movieName);
		} catch (InstanceNotFoundException e) {
			throw e;
		}
		return movie;
	}
	
	public List<Movie> findLastMoviesAdded(int i) {
		return movieDAO.findLastMoviesAdded(i);
	}
	
	// Tested!
	public ArrayList<Movie> findMoviesForGenre(Integer genreID) 
	{
		return movieDAO.findMoviesForGenre( genreID, 10 );
	}
	// Tested!
	public ArrayList<Movie> findMoviesByActor(Integer actorID) 
	{
		return movieDAO.findMoviesByActor( actorID );
	}
	// Tested!
	public ArrayList<Genre> findGenreForMovie(String movieName) 
	{
		return genreDAO.findGenresForMovie( movieName );
	}
	// Tested!
	public ArrayList<Actor> findActorForMovie(String movieName)
	{
		return actorDAO.findActorsForMovie( movieName );
	}
	// Tested!
	public Integer findCalification(String movieName, String userName) throws InstanceNotFoundException 
	{
		return movieDAO.findCalification( movieName, userName );
	}
	// Tested!
	public Integer findCalificationAverage(String movieName) 
	{
		return movieDAO.findCalificationAverage( movieName );
	}
	// Tested!
	public ArrayList<Relation> findRelationsForMovie(String movieName, Integer amount) 
	{
		return relationDAO.findRelationForMovie( movieName, amount );
	}
	// Tested
	// No tiene sentido que authorName == userName
	public Boolean userHasApprovedMovieRelation(String sourceName, String destinyName, String authorName, String userName)
	{
		return relationDAO.userHasApprovedMovieRelation(sourceName, destinyName, authorName, userName);
		
	}
	// Tested
	public void setApprovedStatus(String sourceName, String destinyName,
			String authorName, String userName, Boolean approvalStatus) {
		try {
			if( approvalStatus )
				relationDAO.insertApproval( sourceName, destinyName, authorName, userName );
			else
				relationDAO.deleteApproval( sourceName, destinyName, authorName, userName );
		} catch (InstanceNotFoundException e) {
			// Si el estado no corresponde en verdad no se quiere hacer nada mas.
		}
	}
	
	
	public void setActorDAO(ActorDAO actorDAO) 
	{
		this.actorDAO = actorDAO;
	}
	
	public void setMovieDAO(MovieDAO movieDAO) 
	{
		this.movieDAO = movieDAO;
	}
	
	public void setGenreDAO(GenreDAO genreDAO)
	{
		this.genreDAO = genreDAO;
	}
	
	public void setRelationDAO(RelationDAO relationDAO)
	{
		this.relationDAO = relationDAO;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) 
	{
		this.transactionManager = transactionManager;
	}

	@Override
	public void executeMoviesSimilarityAlgorithm() {
		movieDAO.executeMoviesSimilarityAlgorithm();
	}

	public List<Movie> findLastMoviesByDebut(int i) {
		return movieDAO.findLastMoviesByDebut(i);
	}

	public int findNumCalifications(String movieName) {
		return movieDAO.findNumCalifications(movieName);
	}

	public List<Movie> findMoviesOrderByRank() {
		return movieDAO.findMoviesOrderByRank();
	}

	public void executeMoviesSimilarityAlgorithm(String name) {
		movieDAO.executeMoviesSimilarityAlgorithm(name);
		
	}

	public void addMovie(Movie movie) {
		movieDAO.insert(movie);
		
	}

	public void updateMovie(Movie movieToEdit) {
		movieDAO.updateMovie(movieToEdit);
	}

	public void deleteMovie(String movieName) throws InstanceNotFoundException{
		movieDAO.delete(movieName);
	}

	public List<Genre> getAllGenres() {
		return genreDAO.getAll();
	}

	public void addGenreToMovie(String movieName, String name) {
		try {
			movieDAO.addGenreToMovie(movieName, genreDAO.find(name).getID());
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addActorToMovie(String movieName, String name) {
		try {
			movieDAO.addActorToMovie(movieName, actorDAO.find(name).getID());
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Actor> getAllActors() {
		return actorDAO.getAll();
	}

	public List<Movie> getAllMovies() {
		return movieDAO.getAll();
	}

	public Movie findMovieByID(int id) 
	{
		try 
		{
			return movieDAO.findByID(id);
		} 
		catch(InstanceNotFoundException e)
		{
			return null;
		}
	}
}
