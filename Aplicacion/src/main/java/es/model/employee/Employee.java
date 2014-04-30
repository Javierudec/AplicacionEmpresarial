package es.model.employee;

public class Employee {
	
	private Integer id;
	private String name;
	private String job;
	private Double salary;
	private Integer departmentId;
	
	public Employee(Integer id, String name, String job, Double salary,
			Integer departmentId) {
		this.id = id;
		this.name = name;
		this.job = job;
		this.salary = salary;
		this.departmentId = departmentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", job=" + job
				+ ", salary=" + salary + ", departmentId=" + departmentId + "]";
	}

}
