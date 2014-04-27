package es.udc.fi.asi.model.department;

import es.udc.fi.asi.model.util.exceptions.InstanceNotFoundException;

/**
 * Data access operations for Departments.
 */
public interface DepartmentDAO {
	
	/**
	 * Finds a department from its ID.
	 * @throws InstanceNotFoundException If the department is not found.
	 */
	public abstract Department find(Integer departmentId)
			throws InstanceNotFoundException;

	/**
	 * Inserts a new department.
	 * @return The department, with its identifier.
	 */
	public abstract Department insert(	Department department);

	/**
	 * Updates the data of a given department.
	 */
	public abstract void update(Department department);

	/**
	 * Removes a department from its ID.
	 */
	public abstract void remove(Integer departmentId);

}