package es.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcUserDAO implements UserDAO {
	
	private DataSource dataSource;

	
	public User find(String userName) throws InstanceNotFoundException {
		User user = null;
		
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT name, password, mail, rights FROM site_user WHERE name = ?");
			statement.setString(1, userName);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				String newUserName = resultSet.getString(1);
				String newUserPassword = resultSet.getString(2);
				String newUserMail = resultSet.getString(3);
				int rights = resultSet.getInt(4);
				
				user = new User(newUserName, newUserPassword, newUserMail);
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
			Connection connection = dataSource.getConnection();
			statement = connection.prepareStatement(
					"INSERT INTO site_user(name,password,mail,rights) VALUES (?,?,?,0);");
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return user;
	}

	public User update(User user) throws InstanceNotFoundException {
		try{
			Connection connection = dataSource.getConnection();
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
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM site_user WHERE name = '?';");
			
			statement.setString(1, userName);
			
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}		
	}
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}

}
