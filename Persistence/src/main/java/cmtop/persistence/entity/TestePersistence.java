package cmtop.persistence.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestePersistence {
	
	public static void main(String[] args) {
		ResultSet rs = null;
		Banco banco = new Banco();
		
		try {
			rs = banco.selectAllCars();
			
			while(rs.next()) {
				System.out.println("Modelo: "+rs.getString("modelo"));
			}
			
		}catch(SQLException e) {
			System.out.println("ERRO: "+e.getMessage());
		}	
	}
}
