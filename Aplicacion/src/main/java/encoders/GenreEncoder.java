package encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;

import es.model.genre.Genre;


public class GenreEncoder implements ValueEncoder<Genre>, ValueEncoderFactory<Genre> 
{
    @Override
    public String toClient(Genre value) 
    {
        // return the given object's ID
        return value.getName();
    }
 
    @Override
    public Genre toValue(String name) 
    { 
        return new Genre(-1, name); 
    }
 
    // let this ValueEncoder also serve as a ValueEncoderFactory
    @Override
    public ValueEncoder<Genre> create(Class<Genre> type) 
    {
        return this; 
    }
} 
