package encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;

import util.SpringUtils;
import es.model.tag.Tag;


public class TagEncoder implements ValueEncoder<Tag>, ValueEncoderFactory<Tag> 
{
    @Override
    public String toClient(Tag value) 
    {
        // return the given object's ID
        return value.getName();
    }
 
    @Override
    public Tag toValue(String name) 
    { 
        return SpringUtils.getArticleService().findTagByName(name); 
    }
 
    // let this ValueEncoder also serve as a ValueEncoderFactory
    @Override
    public ValueEncoder<Tag> create(Class<Tag> type) 
    {
        return this; 
    }
} 
