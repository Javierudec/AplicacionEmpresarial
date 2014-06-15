package es.model.user;

import org.apache.tapestry5.beaneditor.Validate;

public class User {
	private String name;
	
	private String password;
	
	private String email;
	
	private int id;
	
	private boolean isAdmin;
	
	public User()
	{
		name = "Guest";
		password = "";
		email = "nomail@nomail.com";
		isAdmin = false;
	}
	
	public User( String _name, String _password, String _email ){
		name = _name;
		password = _password;
		email = _email;
	}
	
	public String getName(){ return name; }
	public String getPassword(){ return password; }
	public String getEmail(){ return email; }
	public int getID() { return id; }
	
	public void setName( String _name ){ name = _name; }
	public void setPassword( String _password ){ password = _password; }
	public void setEmail( String _email ){ email = _email; }
	public void setID(int _id) { id = _id; }
	
	public String toString(){ return "[ "+name+" - "+email+" ] ";}

	public boolean getIsAdmin() 
	{
		return isAdmin;
	}
	
	public void setIsAdmin(boolean b)
	{
		isAdmin = b;
	}
}
