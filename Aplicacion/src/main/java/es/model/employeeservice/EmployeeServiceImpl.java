package es.model.employeeservice;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import es.model.department.DepartmentDAO;
import es.model.employee.Employee;
import es.model.employee.EmployeeDAO;
import es.model.util.exceptions.InstanceNotFoundException;

public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeDAO employeeDAO;
	private DepartmentDAO departmentDAO;
	private PlatformTransactionManager transactionManager;
	
	/**
	 * Finds an employee by its identifier.
	 */
	public Employee findEmployeeById(Integer id) throws InstanceNotFoundException {
		
		TransactionStatus transactionStatus =
				transactionManager.getTransaction(null);
		Employee employee = null;
		
		try {
			employee = employeeDAO.find(id); 
		
			transactionManager.commit(transactionStatus);	// COMMIT
			
		} catch (RuntimeException e) {
			transactionManager.rollback(transactionStatus);	// ROLLBACK
			
			/* TODO Aquí habría que hacer una gestión de excepciones mejor... */
			throw e;
		}
		
		return employee;
	}
	
	
	/* --------------------- Setter methods --------------------- */
	
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
}
