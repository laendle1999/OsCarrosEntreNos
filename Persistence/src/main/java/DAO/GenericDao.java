package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class GenericDao {
	private static final String DbURL = "jdbc:mysql://localhost/auto_manager";
	private static final String user = "root";
	private static final String password = "root";
	
	protected static Connection conn;
	
	public Connection getConnection() {
				
		if(GenericDao.conn == null) {
			try {
				GenericDao.conn = DriverManager.getConnection(GenericDao.DbURL, GenericDao.user, GenericDao.password);
			}catch(Exception e) {
				System.out.println("ERRO: "+e.getMessage());
			}
		}
		
		return GenericDao.conn;
	}
	
	public void closeConnection() {
		if(GenericDao.conn != null) {
			GenericDao.conn = null;
		}
	}
}
