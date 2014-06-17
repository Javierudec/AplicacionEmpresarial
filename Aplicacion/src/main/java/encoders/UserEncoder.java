package encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;

import util.SpringUtils;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public class UserEncoder implements ValueEncoder<User>, ValueEncoderFactory<User>
{
    @Override
    public String toClient(User value) 
    {
        return value.getName();
    }
 
    @Override
    public User toValue(String name) 
    { 
        try 
        {
			return SpringUtils.getUserService().findUserByName(name);
		} 
        catch(InstanceNotFoundException e) 
        {
			e.printStackTrace();
		} 
        
        return null;
    }
  
    @Override
    public ValueEncoder<User> create(Class<User> type) 
    {
        return this; 
    }
} 
