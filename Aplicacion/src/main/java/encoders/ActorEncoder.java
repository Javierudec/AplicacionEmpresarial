package encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;
import es.model.actor.Actor;

public class ActorEncoder implements ValueEncoder<Actor>, ValueEncoderFactory<Actor> 
{
    @Override
    public String toClient(Actor value)
    {
        // return the given object's ID
        return value.getName();
    }
 
    @Override
    public Actor toValue(String name) 
    { 
        return new Actor(-1, name); 
    }
 
    // let this ValueEncoder also serve as a ValueEncoderFactory
    @Override
    public ValueEncoder<Actor> create(Class<Actor> type) 
    {
        return this; 
    }
} 
