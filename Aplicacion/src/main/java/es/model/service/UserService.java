package es.model.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;

import es.model.article.Article;
import es.model.article.ArticleDAO;
import es.model.movie.Movie;
import es.model.movie.MovieDAO;
import es.model.user.User;
import es.model.user.UserDAO;
import es.model.util.exceptions.InstanceNotFoundException;


public class UserService implements UserServiceInterface {
	
	private UserDAO userDAO;
	private ArticleDAO articleDAO;
	private MovieDAO movieDAO;
	@SuppressWarnings("unused")
	private PlatformTransactionManager transactionManager;
	
	//Tested!
	public ArrayList<Article> findArticlesOfUser( String authorName ){	
		return articleDAO.findArticlesByAuthorName(authorName, 10);
	}

	// Tested!
	// TODO: throw if user already exist!
	public User addUser( User user ){
		return userDAO.insert(user);
	}

	// Tested
	public User findUserByName(String userName) throws InstanceNotFoundException {
		User user = null;
		user = userDAO.find( userName ); 
		return user;
	}		

	/* --------------------- Setter methods --------------------- */
		
	public void setuserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}
	public void setMovieDAO(MovieDAO movieDAO) 
	{
		this.movieDAO = movieDAO;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void calculatePredictionRanks() {
		List<User> userList = userDAO.getAllUsers();
		List<Movie> movieList = movieDAO.getAllMovies();
		
		for(int i = 0; i < userList.size(); i++)
		{
			System.out.println(i + "/" + (userList.size() - 1));
			
			List<Integer> moviesRankedBy = movieDAO.findMoviesRankedBy(userList.get(i).getID());
			int userID = userList.get(i).getID();
			
			for(int j = 0; j < movieList.size(); j++)
			{
				int movieID = movieList.get(j).getID();
				
				if(moviesRankedBy.contains(movieID)) continue;
				
				double PUJ = 0.0f;
				double enumerator = 0.0f;
				double denominator = 0.0f;
				
				for(int k = 0; k < moviesRankedBy.size(); k ++)
				{
					int movieRankedID = moviesRankedBy.get(k);
					double SJK = movieDAO.findSimilarity(movieID, movieRankedID);
					double RUK = movieDAO.findCalification(movieRankedID, userID);
					
					enumerator += SJK * RUK;
					denominator += Math.abs(SJK);
				}
				
				if(denominator > 0.0f) PUJ = enumerator / denominator;
				
				userDAO.insertPredictedRank(userID, movieID, PUJ);
			}
		}
	}	
	
	public void calculatePredictionRanks(int user_id) {
		List<Movie> movieList = movieDAO.getAllMovies();
		
		int userID = user_id;
		List<Integer> moviesRankedBy = movieDAO.findMoviesRankedBy(userID);
		
		System.out.println("moviesRankedBy: " + moviesRankedBy.size());
		
		for(int i = 0; i < moviesRankedBy.size(); i++)
		{
			System.out.println(moviesRankedBy.get(i));
		}
		
		userDAO.resetPredictionsFor(userID);
			
		for(int j = 0; j < movieList.size(); j++)
		{
			int movieID = movieList.get(j).getID();
			
			if(moviesRankedBy.contains(movieID)) 
			{
				System.out.println(movieID + " is already ranked.");
				
				continue;
			}
			
			double PUJ = 0.0f;
			double enumerator = 0.0f;
			double denominator = 0.0f;
			
			for(int k = 0; k < moviesRankedBy.size(); k ++)
			{
				int movieRankedID = moviesRankedBy.get(k);
				double SJK = movieDAO.findSimilarity(movieID, movieRankedID);
				double RUK = movieDAO.findCalification(movieRankedID, userID);
				
				enumerator += SJK * RUK;
				denominator += Math.abs(SJK);
			}
			
			if(denominator > 0.0f) PUJ = enumerator / denominator;
			
			userDAO.insertPredictedRank(userID, movieID, PUJ);
		}
	}

	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	public List<Movie> findRecommendationsFor(String username, float f) {
		User u = null;
		try {
			u = userDAO.find(username);
			return movieDAO.findRecommendationsFor(u.getID(), f);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}