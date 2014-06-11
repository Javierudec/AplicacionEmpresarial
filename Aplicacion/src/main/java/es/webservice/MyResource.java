package es.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import util.SpringUtils;
import es.model.movie.Movie;
import es.model.service.MovieService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	String movieList = "";
    	MovieService movieService = SpringUtils.getMovieService();
    	
    	List<Movie> movies = movieService.findLastMoviesAdded(10);
    	
    	for(int i = 0; i < movies.size(); i++)
    	{
    		movieList = movieList + movies.get(i).getName() + "\n";
    	}
    	
        return movieList;
    }
}
