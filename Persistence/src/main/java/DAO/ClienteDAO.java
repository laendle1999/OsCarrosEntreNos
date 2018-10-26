package DAO;

import java.sql.Connection;

public class ClienteDAO extends GenericDao {
	
	public void verifyConnection() {
		Connection con = this.getConnection();
		
		if(con != null) {
			System.out.println("Connection Work!!!\n");
		}else {
			System.out.println("Connection Failed!!!\n");
		}
	}
}
