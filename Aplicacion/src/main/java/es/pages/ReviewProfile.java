/**
 * 
 */
package es.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import util.SpringUtils;
import es.model.article.Article;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * @author Javier
 *
 */
public class ReviewProfile {
	@Property
	@Persist
	private Article review;
	
	public void setReview(int id)
	{
		try {
			review = SpringUtils.getArticleService().findArticleByID(id);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}