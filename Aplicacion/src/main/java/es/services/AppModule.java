package es.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;

public class AppModule {
	
	  public static void contributeApplicationDefaults(MappedConfiguration<String,String> configuration)
	  {
		  configuration.add(SymbolConstants.APPLICATION_FOLDER , "web");
	  }
}
