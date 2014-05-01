package es.model.relation;

import javax.sql.DataSource;

public class JdbcRelationDAO implements RelationDAO {

	private DataSource dataSource;
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}
	
	public Relation find(String sourceMovie, String destinyMovie,
			String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insert(Relation relation) {
		// TODO Auto-generated method stub
		
	}

	public void delete(String sourceMovie, String destinyMovie, String username) {
		// TODO Auto-generated method stub
		
	}

	public void Update(Relation relation) {
		// TODO Auto-generated method stub
		
	}

}
