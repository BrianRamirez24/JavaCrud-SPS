package co.edu.brian.init;

import java.sql.*;
public class ConexionBDAccess{
static String ubicacion = "D:\\proyectos-java\\ConexionDB\\src\\co\\edu\\brian\\init\\cursosenabasesdedatos.mdb";
static String driver = "jdbc:ucanaccess://"+ubicacion;
static Connection conexion=null;
	ConexionBDAccess(){
		    }
	public static Connection getConexion(){
		  try{
	    	  conexion=DriverManager.getConnection(driver);
	          System.out.println("Conexion Exitosa a base de datos");
	        }
	        catch ( Exception e )
	        {
	            System.out.println("No se pudo conectar a base de datos");
	        }
		return conexion;
	   }
	   public static void desconectar(){
		   try {
			if (conexion!=null){  
			conexion.close();}
			System.out.println("Conexion Cerrada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
   	   }
}