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

public class MovieService implements MovieServiceInterface {
	private ActorDAO actorDAO;
	private MovieDAO movieDAO;
	private GenreDAO genreDAO;
	private RelationDAO relationDAO;
	
	@SuppressWarnings("unused")
	private PlatformTransactionManager transactionManager;
	
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
	
	@Transactional
	public Genre addGenre(String genreName) 
	{
		Genre genre = genreDAO.insert(genreName);
		
		return genre;
	}
	
	@Transactional
	public Actor addActor(String actorName)
	{
		Actor actor = actorDAO.insert(actorName);
		
		return actor;
	}
	
	@Transactional
	public Movie addMovie(String name, String synopsis, Date premierDate, ArrayList<Integer> actorsID, ArrayList<Integer> genresID) 
	{
		Movie movie = movieDAO.insert(new Movie(name, synopsis, premierDate));
		
		while(actorsID.iterator().hasNext()) 
		{
			int currID = actorsID.iterator().next();
			movieDAO.addActorToMovie(name, currID);
		}
		
		while(genresID.iterator().hasNext())
		{
			int currID = genresID.iterator().next();
			movieDAO.addGenreToMovie(name, currID);
		}
		
		return movie;
	}

	@Transactional
	public void addRelationForMovie(String sourceName, String destinyMovie, String username, String comment) 
	{
		relationDAO.insert(new Relation(sourceName, destinyMovie, username, comment));		
	}

	@Transactional
	public void setMovieCalificationForUser(String username, String movieID, Integer calification) 
	{
		movieDAO.addCalificationToMovie(movieID, username, calification);		
	}

	public Genre findGenreByID(Integer id) {
		Genre genre = null;
		try {
			genre = genreDAO.find(id);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return genre;
	}

	public Actor findActorByID(Integer id) {
		Actor actor = null;
		try {
			actor = actorDAO.find(id);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actor;
	}

	public Movie findMovieByName(String movieName) {
		Movie movie = null;
		try {
			movie = movieDAO.find(movieName);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}

	public ArrayList<Movie> findMoviesForGenre(Integer genreID) 
	{
	
		return null;
	}

	public ArrayList<Movie> findMoviesByActor(Integer actorID) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Genre> findGenreForMovie(String movieName) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Actor> findActorForMovie(String movieName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Integer findCalification(String movieName, String username) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Integer findCalificationAverage(String movieName) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Relation> findRelationsForMovie(String movieName,
			Integer amount) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean userHasApprovedMovieRelation(String sourceName,
			String destinyName, String username)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setApprovedStatus(String sourceName, String destinyName,
			String username, Boolean approvalStatus)
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
