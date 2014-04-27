package es.udc.fi.asi.model.employee;

import java.util.List;

import es.udc.fi.asi.model.util.exceptions.InstanceNotFoundException;

/**
 * Interface for data access operations for "Employee" entities.
 */
public interface EmployeeDAO {
	
	/**
	 * Finds an employee from his ID.
	 * @param employeeId The Id of the employee to be found.
	 * @throws InstanceNotFoundException If the employee is not found.
	 */
	public Employee find(Integer employeeId)
			throws InstanceNotFoundException;
	
	/**
	 * Finds the employees that work in the department with the given ID.
	 * @param departmentId Department identifier.
	 */
	public List<Employee> findEmployeesByDepartmentId(Integer departmentId);

	/**
	 * Inserts a new employee in the database.
	 * @return A EmployeeVO with the data of the inserted employee, including the Id.
	 */
	public Employee insert(Employee employee);

	/**
	 * Updates the data of an employee in the database.
	 * @param employee Contains the new data of the employee.
	 */
	public void update(Employee employee);

	/**
	 * Removes the employee with the given Id.
	 * @param employeeId Id of the employee to be removed.
	 */
	public void remove(Integer employeeId);

}