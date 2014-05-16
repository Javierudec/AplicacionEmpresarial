package es;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.model.actor.Actor;
import es.model.genre.Genre;
import es.model.movie.Movie;
import es.model.service.ArticleService;
import es.model.service.MovieService;
import es.model.service.UserService;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public class RunExamples {
	
	
    public static void main(String[] args) {
    	
    	ApplicationContext context = 
    			new ClassPathXmlApplicationContext("spring-module.xml");
    	
    	MovieService movieService = (MovieService) context.getBean("movieServiceBean");
    	UserService userService = ( UserService ) context.getBean("userServiceBean");
    	ArticleService articleService = ( ArticleService ) context.getBean("articleServiceBean");
    	
    	
    	List<Movie> list = movieService.findLastMoviesAdded(10);
		System.out.println("asdasd");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i).getName());
		}
    	
    	/*
    	Actor actor001 = null;
		try {
			actor001 = movieService.findActorByID(9999);
			System.out.println(actor001);
		} catch (InstanceNotFoundException e) {
			System.out.println("Actor no encontrado");
		}*/
    	
    	/*
    	Genre genre001 = movieService.addGenre("Terror");
    	System.out.println(genre001);
    	*/
    	
    	/*
    	ArrayList<Integer> actorsID = new ArrayList<Integer>();
    	actorsID.add(1);
    	ArrayList<Integer> genresID = new ArrayList<Integer>();
    	genresID.add(1);
    	Movie movie = movieService.addMovie("My name is Khan", "Khan es un ...", new Date(10122013), actorsID , genresID); 
    	System.out.println(movie);
    	*/
    	
    	//movieService.addRelationForMovie("My name is Khan", "My name is Khan", "lcjury", "Esta pelicula es...");
    	
    	//movieService.setMovieCalificationForUser("lcjury", "My name is Khan", 5);
    	/*
    	try {
			Genre genre = movieService.findGenreByID(0);
			System.out.println(genre);
		} catch (InstanceNotFoundException e) {
			System.out.println("El genero no existe!");
		}*/
    	/*
    	try {
			Actor actor = movieService.findActorByID(1);
			System.out.println(actor);
		} catch (InstanceNotFoundException e) {
			System.out.println("El actor no existe!");
		}*/
    	/*
    	try {
			Movie movie = movieService.findMovieByName("My name is Khan2");
			System.out.println(movie);
		} catch (InstanceNotFoundException e) {
			System.out.println("la pelicula no existe!");
		}*/
   
   		/*ArrayList<Movie> movies = movieService.findMoviesForGenre(1);
		System.out.println(movies);*/
		
    	/*ArrayList<Movie> movies = movieService.findMoviesByActor(1);
		System.out.println(movies);*/
    	
    	/*ArrayList<Genre> genres = movieService.findGenreForMovie("My name is Khan");
    	System.out.println(genres);*/
    	
    	/*ArrayList<Actor> actors = movieService.findActorForMovie("My name is Khan");
    	System.out.println(actors);*/
    	
    	/*
    	try {
			System.out.println(movieService.findCalification("My name is Khan", "lcjury"));
		} catch (InstanceNotFoundException e) {
			System.out.println(" no hay calificacion ");
		}
		*/
    	/*
    	System.out.println(movieService.findCalificationAverage("My name is Khan"));
    	System.out.println(movieService.findCalificationAverage("asfasdf"));
    	*/
    	
    	//System.out.println(movieService.findRelationsForMovie("My name is Khan", 10));
    	
    	//System.out.println(movieService.userHasApprovedMovieRelation("My name is Khan", "Thor", "lcjury", "lcjury"));
    	//movieService.setApprovedStatus("My name is Khan", "Thor", "lcjury", "lcjury", true);
    	
    	//userService.addUser(new User("Jury", "jurypass", "jurymail@mail.jury"));
    	/* try {
			System.out.println(userService.findUserByName("lcjury"));
		} catch (InstanceNotFoundException e) {
			System.out.println("usuario no encontrado");} */
    	
    	//System.out.println(userService.findArticlesOfUser("lcjury"));
    	
    	//System.out.println(articleService.addTag("Año nuevo!"));
    	//System.out.println(articleService.tagExist("Año nuevo!"));
    	
    	/*try {
			System.out.println(articleService.findArticleByID(1));
		} catch (InstanceNotFoundException e) {
			System.out.println("El articulo no existe!");
		}*/
    	/*
    	try {
			System.out.println(articleService.findCalification("lcjury", 1));
		} catch (InstanceNotFoundException e) {
			System.out.println("no se encuentra la calificacion");
		}*/
    	/*
    	System.out.println(articleService.findCalificationAverage(1));
    	*/
    	
    	//System.out.println(articleService.findTagsByArticleID(1));
    	
    	//System.out.println(articleService.findLastArticles(10));
    	
    	((ClassPathXmlApplicationContext) context).close();
    }
}
