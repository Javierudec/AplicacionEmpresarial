package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.model.service.MovieService;
import es.model.service.UserService;

public class SpringUtils {
	private static SpringUtils springUtils;
	private static ApplicationContext context;
	private static Connection connection;
	
	private SpringUtils()
	{
		context = new ClassPathXmlApplicationContext("spring-module.xml");
		DataSource s = (DataSource)context.getBean("dataSourceBean");
		try {
			connection = s.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SpringUtils getSpring()
	{
		if(springUtils == null)
		{
			springUtils = new SpringUtils();
		}
		
		return springUtils;
	}
	
	public static ApplicationContext getContext()
	{
		if(getSpring() != null)
		{
			return context;
		}
		else
		{
			return null;
		}
	}
	
	public static UserService getUserService()
	{
		if(getSpring() == null)
		{
			return null;		
		}
		
		return (UserService)context.getBean("userServiceBean");
	}

	public static MovieService getMovieService() 
	{
		if(getSpring() == null)
		{
			return null;		
		}
		
		return (MovieService)context.getBean("movieServiceBean");
	}
	
	public static Connection getConnection() 
	{
		if(getSpring() == null)
		{
			return null;		
		}
		
		return connection;
	}
}
