package es.model.service;

import java.sql.Date;
import java.util.ArrayList;

import es.model.actor.Actor;
import es.model.genre.Genre;
import es.model.movie.Movie;
import es.model.relation.Relation;

public interface MovieServiceInterface {
	public abstract Genre addGenre(String genreName);
	
	public abstract Actor addActor(String actorName);
	
	public abstract Movie addMovie(String name, String synopsis, Date premierDate, ArrayList<Integer> actorsID, ArrayList<Integer> genresID);
	
	public abstract void addRelationForMovie(String sourceName, String destinyMovie, String username, String comment);
	
	public abstract void setMovieCalificationForUser(String username, String movieID, Integer calification);
	
	public abstract Genre findGenreByID(Integer id);
	
	public abstract Actor findActorByID(Integer id);
	
	public abstract Movie findMovieByName(String movieName);
	
	public abstract ArrayList<Movie> findMoviesForGenre(Integer genreID);
	
	public abstract ArrayList<Movie> findMoviesByActor(Integer actorID);
	
	public abstract ArrayList<Genre> findGenreForMovie(String movieName);
	
	public abstract ArrayList<Actor> findActorForMovie(String movieName);
	
	public abstract Integer findCalification(String movieName, String username);
	
	public abstract Integer findCalificationAverage(String movieName);
	
	public abstract ArrayList<Relation> findRelationsForMovie(String movieName, Integer amount);
	
	public abstract Boolean userHasApprovedMovieRelation(String sourceName, String destinyName, String username);
	
	public abstract void setApprovedStatus(String sourceName, String destinyName, String username, Boolean approvalStatus);
}
