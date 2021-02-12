package co.edu.brian.init;

import java.sql.*;
public class testConexionAccess {
	public static void main(String[] args) {
		
		Connection c = ConexionBDAccess.getConexion();
		
		if (c!=null){
		
			System.out.println("Si conecto");
			
		}
		
		else{
		
			System.out.println("No conecto");
		
		}
		
		ConexionBDAccess.desconectar();	
	}
}
