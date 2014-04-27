package es.udc.fi.asi.model.department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import es.udc.fi.asi.model.util.exceptions.InstanceNotFoundException;

public class JdbcDepartmentDAO implements DepartmentDAO {
	
	private DataSource dataSource;
	
	public Department insert(Department department) {
		
		if (department == null)
			return null;
		
		PreparedStatement statement;
		try {
			
			Connection connection = dataSource.getConnection();
			
			statement = connection.prepareStatement(
					"INSERT INTO department (name, location) VALUES ?, ?", 
					Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, department.getName());
			statement.setString(2, department.getLocation());
			
			statement.executeUpdate();
			
			ResultSet resultSet = statement.getGeneratedKeys();
			
			if (resultSet != null && resultSet.next())
				department.setId(resultSet.getInt(1));
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return department;
	}
	
	public Department find(Integer departmentId) 
		throws InstanceNotFoundException {
		
		Department department = null;
		
		try {
			
			Connection connection = dataSource.getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
					"SELECT name, location FROM department WHERE id = ?");
			statement.setInt(1,  departmentId);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next())
				department = new Department(departmentId, resultSet.getString(1),
						resultSet.getString(2));
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return department;
	}
	
	public void update(Department department) {
		
		try {
			
			Connection connection = dataSource.getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE department SET name = ?, location = ? WHERE id = ?");
			
			statement.setString(1, department.getName());
			statement.setString(2, department.getLocation());
			statement.setInt(3, department.getId());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void remove(Integer departmentId) {
		
		try {
			
			Connection connection = dataSource.getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM department WHERE id = ?");
			
			statement.setInt(1, departmentId);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
}
