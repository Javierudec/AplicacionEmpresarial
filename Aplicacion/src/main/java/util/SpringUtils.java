package util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.model.service.MovieService;
import es.model.service.UserService;

public class SpringUtils {
	private static SpringUtils springUtils;
	private static ApplicationContext context;
	
	private SpringUtils()
	{
		context = new ClassPathXmlApplicationContext("spring-module.xml");
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
}
