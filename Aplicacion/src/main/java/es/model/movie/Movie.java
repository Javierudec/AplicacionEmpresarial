package es.model.movie;

import org.apache.tapestry5.annotations.Property;
import org.hibernate.annotations.Entity;

@Entity
public class Movie {
	private String name;
	private String synopsys;
	private java.sql.Date premiereDate;
	
	public Movie(String _name, String _synopsys, java.sql.Date _premiereDate){
		name = _name;
		synopsys = _synopsys;
		premiereDate = _premiereDate;
	}
	
	public String getName(){ return name; }
	public String getSynopsys(){ return synopsys; }
	public java.sql.Date getPremiereDate(){ return premiereDate; }
	
	public void setName( String _name ){ name = _name; }
	public void setSynopsys( String _synopsys ){ synopsys = _synopsys; }
	public void setPremiereDate( java.sql.Date _premiereDate ) { premiereDate = _premiereDate; }	
	
	public String toString(){
		return "[ "+name+" - "+premiereDate+" ]\n[ "+premiereDate+" ]"; 
	}
}
