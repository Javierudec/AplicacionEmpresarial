package es.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import util.SpringUtils;
import es.model.movie.Movie;
import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcUserDAO implements UserDAO {
	
	private DataSource dataSource;

	
	public User find(String userName) throws InstanceNotFoundException {
		User user = null;
		
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT name, password, mail, rights, id FROM site_user WHERE name = ?");
			statement.setString(1, userName);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				String newUserName = resultSet.getString(1);
				String newUserPassword = resultSet.getString(2);
				String newUserMail = resultSet.getString(3);
				int id = resultSet.getInt(5);
				int rights = resultSet.getInt(4);
				
				user = new User(newUserName, newUserPassword, newUserMail);
				user.setID(id);
				if(rights == 1)
				{
					user.setIsAdmin(true);
				}
				else
				{
					user.setIsAdmin(false);
				}
				
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return user;
	}

	// TODO: check if user already exist!
	public User insert(User user) {		
		PreparedStatement statement;
		try{
			Connection connection = SpringUtils.getConnection();
			
			statement = connection.prepareStatement("SELECT name, password, mail, rights FROM site_user WHERE name = ?");
			statement.setString(1, user.getName());
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next())
			{
				return null;
			}
			else
			{
				statement = connection.prepareStatement(
						"INSERT INTO site_user(name,password,mail,rights) VALUES (?,?,?,0);");
				statement.setString(1, user.getName());
				statement.setString(2, user.getPassword());
				statement.setString(3, user.getEmail());
				
				statement.executeUpdate();
			}
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return user;
	}

	public User update(User user) throws InstanceNotFoundException {
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE site_user SET password = '?', mail = '?'  WHERE name = '?';");
			
			statement.setString(1, user.getPassword());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getName());
			
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return user;
	}

	public void delete(String userName) throws InstanceNotFoundException {
		try{
			Connection connection = SpringUtils.getConnection();
			
			User u = find(userName);
			
			PreparedStatement statement = connection.prepareStatement("DELETE FROM rank_movie WHERE username = ?");
			statement.setInt(1, u.getID());
			statement.executeUpdate();
			
			statement = connection.prepareStatement("DELETE FROM predicted_rank WHERE user_id = ?");
			statement.setInt(1, u.getID());
			statement.executeUpdate();
			
			statement = connection.prepareStatement("DELETE FROM site_user WHERE name = ?");
			statement.setString(1, userName);
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}		
	}
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}

	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id, name, password, mail, rights FROM site_user");
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() ){
				String newUserName = resultSet.getString(2);
				String newUserPassword = resultSet.getString(3);
				String newUserMail = resultSet.getString(4);
				int id = resultSet.getInt(1);
				int rights = resultSet.getInt(5);
				
				User user = new User(newUserName, newUserPassword, newUserMail);
				user.setID(id);
				if(rights == 1)
				{
					user.setIsAdmin(true);
				}
				else
				{
					user.setIsAdmin(false);
				}
				
				userList.add(user);
			}
				
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		
		return userList;
	}

	@Override
	public void insertPredictedRank(int userID, int movieID, double pUJ) {
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement("DELETE FROM predicted_rank WHERE user_id = ? AND movie_id = ?");
			statement.setInt(1, userID);
			statement.setInt(2, movieID);
			statement.executeUpdate();
			
			statement = connection.prepareStatement("INSERT INTO predicted_rank VALUES (?,?,?)");
			statement.setInt(1, userID);
			statement.setInt(2, movieID);
			statement.setDouble(3, pUJ);
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}	
	}

	@Override
	public void resetPredictionsFor(int userID) {
		Connection connection = SpringUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM predicted_rank WHERE user_id = ?");
			statement.setInt(1,  userID);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
