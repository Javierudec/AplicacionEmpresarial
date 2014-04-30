package es.model.department;

public class Department {
	
	private Integer id;
	private String name;
	private String location;
	
	public Department(Integer id, String name, String location) {
		this.id = id;
		this.name = name;
		this.location = location;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", location="
				+ location + "]";
	}
	
}
