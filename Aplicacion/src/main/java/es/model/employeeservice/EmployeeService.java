package es.model.employeeservice;

import es.model.employee.Employee;
import es.model.util.exceptions.InstanceNotFoundException;

/**
 * Employee service.
 */
public interface EmployeeService {
	
	/**
	 * Finds the data of an employee from the employee's id.
	 */
	public Employee findEmployeeById(Integer id) throws InstanceNotFoundException;
	
}
