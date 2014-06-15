package encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;

import util.SpringUtils;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public class UserEncoder implements ValueEncoder<User>, ValueEncoderFactory<User> {
    @Override
    public String toClient(User value) {
        // return the given object's ID
        return value.getName();
    }
 
    @Override
    public User toValue(String name) { 
        // find the color object of the given ID in the database
        try {
			return SpringUtils.getUserService().findUserByName(name);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return null;
    }
 
    // let this ValueEncoder also serve as a ValueEncoderFactory
    @Override
    public ValueEncoder<User> create(Class<User> type) {
        return this; 
    }
} 
