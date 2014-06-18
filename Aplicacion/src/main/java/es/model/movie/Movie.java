package es.model.movie;

import java.sql.Date;

import org.apache.tapestry5.annotations.Property;

public class Movie {
	private String name;
	private String synopsys;
	private java.sql.Date premiereDate;
	private String image;
	private Integer id;
	private String videoURL;
	private Integer avgRank;
	
	public Movie(String _name, String _synopsys, java.sql.Date _premiereDate){
		name = _name;
		synopsys = _synopsys;
		premiereDate = _premiereDate;
		image = "";
		avgRank = 0;
	}
	
	public Movie(String _name, String _synopsys, Date _premiereDate, String _image) {
		name = _name;
		synopsys = _synopsys;
		premiereDate = _premiereDate;
		image = _image;
		avgRank = 0;
	}

	public String getName(){ return name; }
	public String getSynopsys(){ return synopsys; }
	public java.sql.Date getPremiereDate(){ return premiereDate; }
	public String getImage() { return image; }
	public Integer getID() { return id; }
	public String getVideoURL() { return videoURL; }
	public Integer getAvgRank() { return avgRank; }
	
	public void setName( String _name ){ name = _name; }
	public void setSynopsys( String _synopsys ){ synopsys = _synopsys; }
	public void setPremiereDate( java.sql.Date _premiereDate ) { premiereDate = _premiereDate; }	
	public void setImage(String _image) { image = _image; }
	public void setID(Integer _id) { id = _id; }
	public void setVideoURL(String _videoURL) { videoURL = _videoURL; }
	public void setAvgRank(Integer _avgRank) { avgRank = _avgRank; }
	
	public String toString(){
		return "[ "+name+" - "+premiereDate+" ]\n[ "+premiereDate+" ]"; 
	}
}
