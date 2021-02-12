package co.edu.brian.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.healthmarketscience.jackcess.impl.RowIdImpl.Type;

import co.edu.brian.businesslogic.Libros;

public class ManageDB {
	
	
	private CallableStatement cs;
	private ResultSet result;
	private Libros libro;
	
	
	public ManageDB() {
	
		cs = null;
		
		libro = null;
		
	
	}
	
	/**
	 * Este metodo se encarga de leer los datos de la tabla libros
	 * de la base de datos y traer los datos a travez del procedimiento
	 * almacenado sp_listar_libros() el cual trae todos los registros en 
	 * dicha tabla.<br><br>
	 * para ello primero realiza una conexion a la base de datos  
	 * posteriormente realiza la consulta a travez del metodo execute() de la 
	 * clase CallableStatement.<br><br>
	 * 
	 * el objeto callablestatement es el encargado de invocar al 
	 * procedimiento almacenado alojado en mi base datos  la cual se encuentra 
	 * en mariadb es decir con esta instruccion le estoy diciendo que se 
	 * conecte a la base de datos con la instruccion Connection.getMariaDbConn()
	 * una vez se ha conectado este metodo le retorna un objeto de tipo connection
	 * dentro de dicho objeto hay un metodo llamado prepareCall que es el encargado
	 * de retornarme un objeto de tipo CallableStatement el cual es el encargado
	 * de ir al procedimiento almacenado el cual se le esta indicando por parametro
	 * en la variable String storedProcedure al ejecutarse dicho metodo
	 * este me retorna un objeto callablestatement en el cual puedo especificar 
	 * los parametros del procedimiento almacenado y almacenar los valores respectivos
	 * en los parametros del procedimiento pero para este caso no 
	 * tenemos parametros de envio ya que el metodo solo se encarga de 
	 * listar todos libros en la tabla libros y solo hay que verificar que la consulta 
	 * si me devuelva un objeto de tipo ResultSet.<br> 
	 * (El cual traduce conjunto de resultados)
	 *  este objeto es el que contiene todos los resultados y estan almacenados en memoria
	 *  en una cuadricula(grid) virtual<br><br>
	 *  
	 *  para acceder a cada una de las <b color = "red">columnas</b> del objeto debo hacer uso de los respectivos 
	 *  metodos getInt("nombre_campo_en_la_base_de_datos") el cual por comididad yo uso un 
	 * nombre del campo de la base de datos pero tambien se puede llamar por posicion fija
	 * ejm si yo tengo 
	 * <ul>
	 * <li>id</li>
	 * <li>NombreLibro</li>
	 * <li>editorial</li>
	 * <li>autor</li>
	 * <li>precio</li>
	 * </ul>
	 * en mi objeto Resultset:<br> la posicion 1 es para el campo id
	 * <br> la posicion 2 es para el campo NombreLibro
	 * <br> la posicion 3 es para el campo editorial
	 * <br> la posicion 4 es para el campo autor
	 * <br> la posicion 5 es para el campo precio
	 * de igual manera los demas objetos son de tipo letra entonces para los otros metodos 
	 * continuo con el metodo getString("nombre_campo"); y finalizo con el precio el cual
	 * es un double por lo cual uso el metodo getDouble("nombre_campo_db") <br><br>
	 * finalmente estos valores son almacenados en mi objeto Libros el cual se encarga de 
	 * recoger la informacion la tabla virtual resulset en cada uno de los campos de dicha clase
	 * al terminar el siclo while solo hay que agregar el objeto a la colleccion arraylist arrLibros
	 * esta coleccion me recibe objetos ilimitados de la clase Libros
	 * <br> y finalmente se cierran todas las conexiones
	 * @author brian alexander ramirez galeano
	 * @return ArrayList con la lista de todos los objetos que corresponden a todos los registros en el <br>
	 * objeto ResultSet
	 *  @throws SQLException
	 * 
	 */
	
	public ArrayList<Libros> listarDatos() throws SQLException {
		
		String storedProcedure = "{CALL SP_LISTAR_LIBROS()}";//procedimiento a llamar
		
		boolean confirm;//lo uso para confirmar si  me trajo un objeto resultset
		
		ArrayList<Libros> arrLibro = new ArrayList<Libros>();
		//un arraylist es una lista de arreglos es decir es un objeto de tipo array
		
			
			cs = ConexionDB.getMariaDbConn()
						   .prepareCall(storedProcedure);
			
			/*
			 * 
			 * el objeto callablestatement es el encargado de invocar al 
			 * procedimiento almacenado alojado en mi base datos  la cual se encuentra 
			 * en mariadb es decir con esta instruccion le estoy diciendo que se 
			 * conecte a la base de datos con la instruccion Connection.getMariaDbConn()
			 * una vez se ha conectado este metodo le retorna un objeto de tipo connection
			 * dentro de dicho objeto hay un metodo llamado prepareCall que es el encargado
			 * de retornarme un objeto de tipo CallableStatement el cual es el encargado
			 * de ir al procedimiento almacenado el cual se le esta indicando por parametro
			 * en la variable String storedProcedure al ejecutarse dicho metodo
			 * este me retorna un objeto callablestatement en el cual puedo especificar 
			 * los parametros del procedimiento almacenado y almacenar los valores respectivos
			 * en los parametros del procedimiento pero para este caso no 
			 * tenemos parametros de envio ya que el metodo solo se encarga de 
			 * listar todos libros en la tabla libros y solo hay que verificar que la consulta 
			 * si me devuelva un objeto de tipo ResultSet.
			 * 
			 * 
			 * */
			
			
			confirm = cs.execute(); //se ejecuta la consulta
			
			if(confirm == false) {
				
				throw new SQLException("NO SE LOGRO REALIZAR LA "
										+ "CONSULTA INTENTELO DE NUEVO");
				
			}
					
			/*
			 * sí llegó hasta aqui quiere decir que realizo la consulta exitosamente
			 * y que me ha traido un objeto ResultSet con los datos de la consulta
			 * 
			 */ 
			
			result = cs.getResultSet();
			
			while(result.next()) {
			
				libro = new Libros();
				
				libro.setId_libro(result.getInt("ID"));
				libro.setNombreLibro(result.getString("NombreLibro"));
				libro.setEditorial(result.getString("editorial"));
				libro.setAutor(result.getString("autor"));
				libro.setPrecio(result.getDouble("precio"));
			    
				/*
				 * se va llenando el arraylist con cada uno de los 
				 * registros encontrados en la tabla libros
				 * 
				 */
				
				arrLibro.add(libro);
		   }
			
		cs.close();
		result.close();
		ConexionDB.desconectar();
		
		return arrLibro;
		
				
	}
	
	
	/**
	 * Este metodo funciona exactamente igual a listarDatos() pero con la 
	 * unica diferencia de que recibe un parametro y regresa una tabla virtual 
	 * filtrada con base en los criterios dispuestos en el objeto String dato<br>
	 * 
	 * @param dato, objeto String encargado de filtrar la tabla con base a 
	 * los criterios dipuesto es en este<br>
	 * @return ArrayList con los datos filtrados de la tabla libros 
	 * @author Brian Alexander Ramirez Galeano
	 * 
	 * */
	public ArrayList<Libros> filtrarDatos(String dato) throws SQLException {
		
		String query = "{CALL SP_FILTRAR_LIBROS(?)}";
		
		boolean confirm;
		
		ArrayList<Libros> arrLibro = new ArrayList<Libros>();
		
			
			cs = ConexionDB.getMariaDbConn()
						   .prepareCall(query);
			
			cs.setString(1, dato);
			
			confirm = cs.execute();
			
			if(confirm == false) {
				
				throw new SQLException("NO SE LOGRO REALIZAR "
										+"LA CONSULTA INTENTELO DE NUEVO");
				
			}
					
			
			result = cs.getResultSet();
			
			while(result.next()) {
			
				libro = new Libros();
				
				libro.setId_libro(result.getInt("ID"));
				libro.setNombreLibro(result.getString("NombreLibro"));
				libro.setEditorial(result.getString("editorial"));
				libro.setAutor(result.getString("autor"));
				libro.setPrecio(result.getDouble("precio"));
			    
				arrLibro.add(libro);
		   }
			
		cs.close();
		result.close();
		ConexionDB.desconectar();
		
		return arrLibro;
		
				
	}
	
	
	
	public int elimanarRegistro(int id) throws SQLException{
		
		String storedProcedure = "{call SP_BORRAR_LIBROS_ID(?)}";
		int affectedRows = 0;
		cs = ConexionDB.getMariaDbConn()
			 .prepareCall(storedProcedure);
		
		cs.setInt(1, id); 
		
		affectedRows = cs.executeUpdate();
		
		
		if(affectedRows<=0) {
			
			cs.close();
			ConexionDB.desconectar();
			return 0;
			
		}
		
		else {
			
			cs.close();
			ConexionDB.desconectar();
			return 1;
		}
			
	}
	
	
	public int actualizarRegistro(Libros book) throws SQLException{
			
			String storedProcedure = "{CALL SP_ACTUALIZAR_LIBROS(?, ?, ?, ?, ?)}";
		
			int affectedRows = 0;
			cs = ConexionDB.getMariaDbConn()
				 .prepareCall(storedProcedure);
			
			cs.setInt(1, book.getId_libro()); 
			cs.setString(2, book.getNombreLibro()); 
			cs.setString(3, book.getEditorial()); 
			cs.setString(4, book.getAutor()); 
			cs.setDouble(5, book.getPrecio()); 
			
			
			affectedRows = cs.executeUpdate();
			
			if (affectedRows <= 0) {
				cs.close();
				ConexionDB.desconectar();
				return 0;
			}
			else {
				cs.close();
				ConexionDB.desconectar();
				return 1;
			}
					
	}

	public int insertarRegistro(Libros book) throws SQLException{
		
		String storedProcedure = "{CALL SP_INSERTAR_LIBROS(?, ?, ?, ?)}";
		
		int affectedRows = 0;
		cs = ConexionDB.getMariaDbConn()
			 .prepareCall(storedProcedure);
				 
		cs.setString(1, book.getNombreLibro()); 
		cs.setString(2, book.getEditorial()); 
		cs.setString(3, book.getAutor()); 
		cs.setDouble(4, book.getPrecio()); 
				
		affectedRows = cs.executeUpdate();
		
		
		
		if(affectedRows <=0) {
			 
			 cs.close();
			 ConexionDB.desconectar();
			 return 0;
		}
	
		else {
			
			cs.close();
			ConexionDB.desconectar();
			return 1;
			
		}
				
	}
	
	
	
	
}
