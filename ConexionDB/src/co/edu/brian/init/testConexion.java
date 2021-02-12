package co.edu.brian.init;

import java.sql.*;

import co.edu.brian.model.ConexionDB;
public class testConexion {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection c = ConexionDB.getMariaDbConn();
		
		if (c != null){
				
			System.out.println("Si conecto");
			
		}
		
		else{
		
			System.out.println("No conecto");
			
		}
		
		ConexionDB.desconectar();	
	}
}
