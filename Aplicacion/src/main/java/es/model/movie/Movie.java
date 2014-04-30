package es.model.movie;

import java.util.Date;

public class Movie {
	private String name;
	private String synopsys;
	private Date premiereDate;
	
	Movie(String _name, String _synopsys, Date _premiereDate){
		name = _name;
		synopsys = _synopsys;
		premiereDate = _premiereDate;
	}
	
	String getName(){ return name; }
	String getSynopsys(){ return synopsys; }
	Date getPremiereDate(){ return premiereDate; }
	
	void setName( String _name ){ name = _name; }
	void setSynopsys( String _synopsys ){ synopsys = _synopsys; }
	void setPremiereDate( Date _premiereDate ) { premiereDate = _premiereDate; }	
}
