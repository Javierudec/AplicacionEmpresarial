package entities;

import org.apache.tapestry5.beaneditor.Validate;

public class UserInformation {
	@Validate("required")
	public String name;
	
	@Validate("required,minLength=6,maxLength=20")
	public String password;
	
	@Validate("required,regexp=^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$")
	public String email;
}
