package encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;

import util.SpringUtils;
import es.model.movie.Movie;
import es.model.util.exceptions.InstanceNotFoundException;

public class MovieEncoder implements ValueEncoder<Movie>, ValueEncoderFactory<Movie> 
{
    @Override
    public String toClient(Movie value) 
    {
        // return the given object's ID
        return value.getName();
    }
 
    @Override
    public Movie toValue(String name)
    { 
        // find the color object of the given ID in the database
        try 
        {
			return SpringUtils.getMovieService().findMovieByName(name);
		} 
        catch(InstanceNotFoundException e)
        {
			e.printStackTrace();
		} 
        
        return null;
    }
 
    // let this ValueEncoder also serve as a ValueEncoderFactory
    @Override
    public ValueEncoder<Movie> create(Class<Movie> type) 
    {
        return this; 
    }
} 