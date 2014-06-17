package es.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import encoders.UserEncoder;
import es.model.user.User;
import util.SpringUtils;

public class RecommenderAlgorithm 
{
	@InjectComponent
	private Zone currentStateZone;
	
	@Property
	private SelectModel userSelectModel;
	@Property
	private User selectedUser;
	@Property
	UserEncoder userEncoder;
	@Property
	private String currentState;
	
	@Inject
	SelectModelFactory selectModelFactory;
	
	boolean bInProgress;
	
	RecommenderAlgorithm()
	{
		userEncoder = new UserEncoder();
	}
	
	public boolean getInProgress()
	{
		return bInProgress;
	}
	
	void executeAlgorithm()
	{
		SpringUtils.getMovieService().executeMoviesSimilarityAlgorithm();
	}
	
	void onActivate()
	{
		currentState = "Ready to start algorithm.";
		List<User> userList = SpringUtils.getUserService().getAllUsers();
		userSelectModel = selectModelFactory.create(userList, "name");
	}
	
	Object onSuccess()
	{
		SpringUtils.getUserService().calculatePredictionRanks(selectedUser.getID());
		currentState = "Rank predictions up to date for user with username: " + selectedUser.getName();
		
		return currentStateZone.getBody();
	}
	
	Object onActionFromInitAlgorithm()
	{
		executeAlgorithm();
		currentState = "Recommendation System is up to date.";
		
		return currentStateZone.getBody();
	}
}