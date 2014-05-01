package es.model.service;

import org.springframework.transaction.annotation.Transactional;

import es.model.actor.Actor;
import es.model.actor.ActorDAO;
import es.model.genre.Genre;
import es.model.genre.GenreDAO;
import es.model.movie.Movie;
import es.model.movie.MovieDAO;
import es.model.relation.Relation;
import es.model.relation.RelationDAO;

import java.sql.Date;
import java.util.ArrayList;

public class MovieService implements MovieServiceInterface {
	private ActorDAO actorDAO;
	private MovieDAO movieDAO;
	private GenreDAO genreDAO;
	private RelationDAO relationDAO;
	
	@Transactional
	public Genre addGenre(String genreName) {
		Genre genre = genreDAO.insert(genreName);
		
		return genre;
	}
	
	@Transactional
	public Actor addActor(String actorName) {
		Actor actor = actorDAO.insert(actorName);
		
		return actor;
	}
	
	public Movie addMovie(String name, String synopsis, Date premierDate, ArrayList<Integer> actorsID, ArrayList<Integer> genresID) {
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

	public void addRelationForMovie(String sourceName, String destinyMovie,
			String username, String comment) {
		// TODO Auto-generated method stub
		
	}

	public void setMovieCalificationForUser(String username, String movieID,
			Integer calification) {
		// TODO Auto-generated method stub
		
	}

	public Genre findGenreByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Actor findActorByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Movie findMovieByName(String movieName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Movie> findMoviesForGenre(Integer genreID) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Movie> findMoviesByActor(Integer actorID) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Genre> findGenreForMovie(String movieName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Actor> findActorForMovie(String movieName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer findCalification(String movieName, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer findCalificationAverage(String movieName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Relation> findRelationsForMovie(String movieName,
			Integer amount) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean userHasApprovedMovieRelation(String sourceName,
			String destinyName, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setApprovedStatus(String sourceName, String destinyName,
			String username, Boolean approvalStatus) {
		// TODO Auto-generated method stub
		
	}
	
	
}
