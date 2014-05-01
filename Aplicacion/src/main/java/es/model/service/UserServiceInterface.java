package es.model.service;

import java.util.ArrayList;

import es.model.article.Article;
import es.model.user.User;
import es.model.util.exceptions.InstanceNotFoundException;

public interface UserServiceInterface {

	public abstract ArrayList<Article> findArticlesOfUser( String authorName );
	
	public abstract User addUser( User user );
	
	public User findUserByName(String userName) 
			throws InstanceNotFoundException;
	
}
