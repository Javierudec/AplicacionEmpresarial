package es;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;

public class AppModule {
	  public static void contributeApplicationDefaults(MappedConfiguration<String,String> configuration)
	  {
	    configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,fr,de");
	    configuration.add(SymbolConstants.FILE_CHECK_INTERVAL, "10 m");
	    configuration.add(SymbolConstants.DEFAULT_STYLESHEET, "context:layout/layout.css");
	  }
}
