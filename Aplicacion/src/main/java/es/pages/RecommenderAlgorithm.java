/**
 * 
 */
package es.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import encoders.MovieEncoder;
import encoders.UserEncoder;
import es.model.movie.Movie;
import es.model.user.User;
import util.SpringUtils;

/**
 * @author Javier
 *
 */
public class RecommenderAlgorithm {
	@InjectComponent
	private Zone currentStateZone;
	
	@Property
	private SelectModel userSelectModel;
	
	@Property
	private User selectedUser;
	
	@Inject
	SelectModelFactory selectModelFactory;
	
	@Property
	UserEncoder userEncoder;
	
	@Property
	private String currentState;
	
	boolean bInProgress;
	
	RecommenderAlgorithm()
	{
		currentState = "Ready to start algorithm.";
		
		userEncoder = new UserEncoder();
		
		List<User> userList = SpringUtils.getUserService().getAllUsers();
		userSelectModel = selectModelFactory.create(userList, "name");
		
	}
	
	Object onSuccess()
	{
		SpringUtils.getUserService().calculatePredictionRanks(selectedUser.getID());
		
		currentState = "Rank predictions up to date for user with username: " + selectedUser.getName();
		
		return currentStateZone.getBody();
	}
	
	public boolean getInProgress()
	{
		return bInProgress;
	}
	
	
	Object onActionFromInitAlgorithm()
	{
		executeAlgorithm();
		
		currentState = "Recommendation System is up to date.";
		
		return currentStateZone.getBody();
	}
	
	void executeAlgorithm()
	{
		SpringUtils.getMovieService().executeMoviesSimilarityAlgorithm();
	}
}