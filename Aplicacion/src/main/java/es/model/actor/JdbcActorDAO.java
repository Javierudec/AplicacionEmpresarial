package es.model.actor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import util.SpringUtils;
import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcActorDAO implements ActorDAO {

	private DataSource dataSource;
	
	// Tested!
	public Actor find(int actorID) throws InstanceNotFoundException {
		Actor actor = null;
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id, name FROM actor WHERE id = ?");
			statement.setInt(1, actorID);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newActorID = resultSet.getInt(1);
				String newActorName = resultSet.getString(2);
				
				actor = new Actor( newActorID , newActorName );
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return actor;

	}
	/*
	 * Tested!
	 * TODO: add amount!
	 */
	public ArrayList<Actor> findActorsForMovie(String movieName)
	{
		ArrayList<Actor> actors = new ArrayList<Actor>();
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT actor.* FROM actor, movie_has_actor WHERE movie_has_actor.movietitle = ? AND movie_has_actor.idactor = actor.id");
			
			statement.setString( 1, movieName );
			//statement.setInt( 2, amount );
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() )
				actors.add( new Actor(resultSet.getInt(1),resultSet.getString(2) ) );
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		
		return actors;
	}
	
	// Tested!
	public Actor insert(String actorName) {
		Actor actor = null;
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO actor (name) VALUES (?) RETURNING id;");		
			
			statement.setString(1, actorName );
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newActorID = resultSet.getInt(1);
				String newActorName = actorName;
				actor = new Actor(newActorID, newActorName);
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return actor;
	}

	public Actor update(Actor actor) throws InstanceNotFoundException {
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE actor SET name = '?' WHERE ID = '?';");
			
			statement.setString(1, actor.getName());
			statement.setInt(2, actor.getID());
		
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return actor;
	}

	public void delete(int actorID) throws InstanceNotFoundException {
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM genre WHERE ID = '?';");
			
			statement.setInt(1, actorID);
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}	
	}
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}
	
	@Override
	public Actor find(String name) throws InstanceNotFoundException {
		Actor actor = null;
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id, name FROM actor WHERE name = ?");
			statement.setString(1, name);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newActorID = resultSet.getInt(1);
				String newActorName = resultSet.getString(2);
				
				actor = new Actor( newActorID , newActorName );
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return actor;
	}
	
	@Override
	public List<Actor> getAll() 
	{
		List<Actor> actorList = new ArrayList<Actor>();
		
		try
		{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM actor ORDER BY name");
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				int newActorID = resultSet.getInt(1);
				String newActorName = resultSet.getString(2);
				
				actorList.add(new Actor(newActorID, newActorName));
			}
			
		} 
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		
		return actorList;
	}
	
}
