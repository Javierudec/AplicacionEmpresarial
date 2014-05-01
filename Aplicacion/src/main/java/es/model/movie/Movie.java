package es.model.movie;

public class Movie {
	private String name;
	private String synopsys;
	private java.sql.Date premiereDate;
	
	Movie(String _name, String _synopsys, java.sql.Date _premiereDate){
		name = _name;
		synopsys = _synopsys;
		premiereDate = _premiereDate;
	}
	
	String getName(){ return name; }
	String getSynopsys(){ return synopsys; }
	java.sql.Date getPremiereDate(){ return premiereDate; }
	
	void setName( String _name ){ name = _name; }
	void setSynopsys( String _synopsys ){ synopsys = _synopsys; }
	void setPremiereDate( java.sql.Date _premiereDate ) { premiereDate = _premiereDate; }	
}
