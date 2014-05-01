package es.model.user;

public class User {
	private String name;
	private String password;
	private String email;
	
	public User( String _name, String _password, String _email ){
		name = _name;
		password = _password;
		email = _email;
	}
	
	public String getName(){ return name; }
	public String getPassword(){ return password; }
	public String getEmail(){ return email; }
	
	public void setName( String _name ){ name = _name; }
	public void setPassword( String _password ){ password = _password; }
	public void setEmail( String _email ){ email = _email; }
	
	public String toString(){ return "[ "+name+" - "+email+" ] ";}
}
