/**
 * 
 */
package co.edu.brian.model;

/**
 * @author brian-pc
 *
 */
import java.sql.*;

public class ConexionDB {
	
	private static final String MARIADB_DRIVER = "org.mariadb.jdbc.Driver"; 
		
	private static final String DATABASE = "nombre base de datos";
		
    private static final String USER = "nombre usuario mysql/mariadb(en este caso)";
    
    private static final String PASSWORD = "su contrasena en la base de datos";
    
    
    private static final int PORT = 3307;
    
   
    private static final String URL = ("jdbc:mariadb://localhost:" + PORT
    									+"/" + DATABASE 
    									+"?user=" + USER
    									+"&password=" + PASSWORD);
    
    private static Connection conexion = null;

	public ConexionDB(){
	
	}
   
   public static Connection getMariaDbConn(){
	
	   if (conexion == null){
	    try {
	    	
	    	Class.forName(MARIADB_DRIVER);
	    	
			conexion = DriverManager.getConnection(URL);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
   
	   }
	   
	   return conexion;
   }
 
   public static void desconectar(){
	   try {
			if (conexion != null){   
					conexion.close();
					conexion = null;
			
				}
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
	   
   }
}