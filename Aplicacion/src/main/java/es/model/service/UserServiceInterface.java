package es.model.service;

import java.util.ArrayList;

import es.model.article.Article;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public interface UserServiceInterface {

	public abstract ArrayList<Article> findArticlesOfUser( String authorName );
	
	public abstract User addUser( User user );
	
	public abstract User findUserByName(String userName) 
			throws InstanceNotFoundException;
	
	public abstract void calculatePredictionRanks();
	
	public abstract void calculatePredictionRanks(int user_id);
	
}
