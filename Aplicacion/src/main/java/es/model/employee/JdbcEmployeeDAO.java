package es.model.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import es.model.util.exceptions.InstanceNotFoundException;

/**
 * Implements the data access operations for the "Employee" entity using JDBC.
 */
public class JdbcEmployeeDAO implements EmployeeDAO {
	
	private DataSource dataSource;
	
	public Employee find(Integer employeeId) 
			throws InstanceNotFoundException {

		Employee employee = null;

		try {
			Connection connection = dataSource.getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
					"SELECT name, job, salary, departmentId FROM employee WHERE id = ?");
			statement.setInt(1, employeeId);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String name = resultSet.getString(1);
				String job =  resultSet.getString(2);
				Double salary = resultSet.getDouble(3);
				Integer departmentId = resultSet.getInt(4);

				employee = new Employee(employeeId, name, job, salary, departmentId);
			} else {
				throw new InstanceNotFoundException();
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return employee;
	}
	
	public List<Employee> findEmployeesByDepartmentId(Integer departmentId) {
		
		List<Employee> list = new ArrayList<Employee>(); 
		
		try {
			
			Connection connection = dataSource.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"SELECT id, name, job, salary, departmentId " + 
					"FROM employee WHERE departmentId = ?");
			
			statement.setInt(1, departmentId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				
				list.add(new Employee(
						resultSet.getInt(1),	// id
						resultSet.getString(2), // name
						resultSet.getString(3), // job
						resultSet.getDouble(4),	// salary
						resultSet.getInt(5))	// departmentId
				);

			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
		
		return list;
	}

	public Employee insert(Employee employee) {
		
		if (employee == null)
			return null;
		
		try {
			
			Connection connection = dataSource.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO employee (name, job, salary, departmentId) " + 
					"VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, employee.getName());
			statement.setString(2, employee.getJob());
			statement.setDouble(3, employee.getSalary());
			statement.setInt(4, employee.getDepartmentId());
			
			statement.executeUpdate();
			
			ResultSet resultSet = statement.getGeneratedKeys();
			
			if (resultSet != null && resultSet.next())
				employee.setId(resultSet.getInt(1));
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return employee;
	}
	
	public void update(Employee employee) {
		
		try {
			
			Connection connection = dataSource.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"UPDATE employee SET name = ?, salary = ?, departmentId = ? " + 
					"WHERE id = ?");
			
			statement.setString(1, employee.getName());
			statement.setDouble(2, employee.getSalary());
			statement.setInt(3, employee.getDepartmentId());
			statement.setInt(4, employee.getId());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void remove(Integer employeeId) {
		try {
			
			Connection connection = dataSource.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM employee WHERE id = ?");
			statement.setInt(1, employeeId);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
}
