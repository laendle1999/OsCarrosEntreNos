package cmtop.persistence.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco {
	private static final String DbURL = "jdbc:mysql://localhost/auto_manager";
	private static final String user = "root";
	private static final String password = "root";
	
	protected static Connection conn;
	
	public Connection getConnection() {
				
		if(Banco.conn == null) {
			try {
				Banco.conn = DriverManager.getConnection(Banco.DbURL, Banco.user, Banco.password);
			}catch(Exception e) {
				System.out.println("ERRO: "+e.getMessage());
			}
		}
		
		return Banco.conn;
	}
	
	public void closeConnection() {
		if(Banco.conn != null) {
			Banco.conn = null;
		}
	}
	
	public boolean verifyConnection() {
		this.getConnection();
		
		if(this.conn != null) {
			System.out.println("Connection Work!!!\n");
			return true;
		}else {
			System.out.println("Connection Failed!!!\n");
			return false;
		}
	}
	
	public ResultSet selectAllCars() throws SQLException {
		Statement stm = null;
		ResultSet rs = null;
		
		if(this.verifyConnection()) {
			String sql = "Select * from carro Limit 3";
			stm = this.conn.createStatement();
			
			if(stm.execute(sql)) {
				rs = stm.getResultSet();
				
				
			}else {
				System.out.println("Query result is zero!");
			}
			
		}else {
			
		}
		
		return rs;
	}
}
