package co.edu.brian.init;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
public class BasesDeDatosAccess extends JFrame implements ActionListener {
	JTextArea txtSalida;
	JLabel lNombres,lTelefono,lEliminar,lTituloInsertar,lTituloBorrar,lTituloActualizar,lActualizarNombres, lActualizarTelefono,lIdActualizar;
	JTextField tfNombres, tfTelefono,tfIdEliminar,tfActualizarNombres, tfActualizarTelefono,tfIdActualizar;
	JButton btGuardar,btEliminar,btActualizar;
	JPanel panel;
	//constructor
	BasesDeDatosAccess(){
	
	panel = new JPanel();
	panel.setLayout(null);
	txtSalida=new JTextArea();
	txtSalida.setEditable(false);
    JScrollPane scroll = new JScrollPane(txtSalida);
    scroll.setBounds(10, 10, 860, 350);
	panel.add(scroll);
	
	lTituloInsertar=new JLabel("INSERTAR DATO");
	lTituloInsertar.setBounds(10,380,100,20);
	panel.add(lTituloInsertar);

	lTituloBorrar=new JLabel("BORRAR DATO");
	lTituloBorrar.setBounds(310,380,100,20);
	panel.add(lTituloBorrar);

	lTituloActualizar=new JLabel("ACTUALIZAR DATO");
	lTituloActualizar.setBounds(500,380,200,20);
	panel.add(lTituloActualizar);

	lNombres=new JLabel("Nombre: ");
	lNombres.setBounds(10,410,100,20);
	panel.add(lNombres);
	tfNombres=new JTextField();
	tfNombres.setBounds(110,410,100,20);
	panel.add(tfNombres);
	lTelefono=new JLabel("Telefono: ");
	lTelefono.setBounds(10,440,100,20);
	panel.add(lTelefono);
	tfTelefono=new JTextField();
	tfTelefono.setBounds(110,440,100,20);
	panel.add(tfTelefono);
	btGuardar = new JButton("Guardar");
	btGuardar.setBounds(10,470,100,20);
	btGuardar.addActionListener(this);
	panel.add(btGuardar);
	lEliminar=new JLabel("ID a eliminar");
	lEliminar.setBounds(310,400,100,20);
	panel.add(lEliminar);
	tfIdEliminar=new JTextField();
	tfIdEliminar.setBounds(310,420,100,20);
	panel.add(tfIdEliminar);
	
	btEliminar = new JButton("Eliminar");
	btEliminar.setBounds(310,440,100,20);
	btEliminar.addActionListener(this);
	panel.add(btEliminar);
	
	
	lIdActualizar=new JLabel("ID a actualizar: ");
	lIdActualizar.setBounds(500,410,100,20);
	panel.add(lIdActualizar);
	tfIdActualizar=new JTextField();
	tfIdActualizar.setBounds(610,410,100,20);
	panel.add(tfIdActualizar);
	lActualizarNombres=new JLabel("Nuevo Nombre: ");
	lActualizarNombres.setBounds(500,440,100,20);
	panel.add(lActualizarNombres);
	tfActualizarNombres=new JTextField();
	tfActualizarNombres.setBounds(610,440,100,20);
	panel.add(tfActualizarNombres);
	lActualizarTelefono=new JLabel("Nuevo Telefono: ");
	lActualizarTelefono.setBounds(500,470,100,20);
	panel.add(lActualizarTelefono);
	tfActualizarTelefono=new JTextField();
	tfActualizarTelefono.setBounds(610,470,100,20);
	panel.add(tfActualizarTelefono);
	btActualizar=new JButton("Actualizar");
	btActualizar.addActionListener(this);
	btActualizar.setBounds(500,500,100,20);
	panel.add(btActualizar);

	
	add (panel);
	setSize(900,650);
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    //centramos la ventana en la mitad de la pantalla.
	setLocationRelativeTo(null);
	mostrarDatos();
	}
	
	public void mostrarDatos(){
		txtSalida.setText("");//vaciamos el texto de salida 
		Connection c=ConexionBDAccess.getConexion();
		Statement comando;
		try {
			comando = c.createStatement();
			ResultSet registro = comando.executeQuery("select id,nombres,telefono from usuarios ");
			txtSalida.setText("SALIDA DE DATOS \n");
			while (registro.next()) {
				int id;
				String nombres,telefono;
				id=registro.getInt("id");
				nombres=registro.getString("nombres");
				telefono=registro.getString("telefono");
				txtSalida.append((id+" | "+nombres+ " | "+ telefono+"\n"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConexionBDAccess.desconectar();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btGuardar){
			Connection c=ConexionBDAccess.getConexion();
			String sql = "INSERT INTO usuarios (nombres, telefono) "
		            + "VALUES (?, ?)";
			try {
				PreparedStatement st = c.prepareStatement(sql);
				st.setString(1, tfNombres.getText());
				st.setString(2, tfTelefono.getText());
				st.executeUpdate();
				//vaciamos campos de entrada
				tfNombres.setText("");
				tfTelefono.setText("");
				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			mostrarDatos();
			ConexionBDAccess.desconectar();
		}
		if (e.getSource()==btEliminar){
			Connection c=ConexionBDAccess.getConexion();
			String sql = "DELETE FROM usuarios WHERE id = ?";
			try {
				PreparedStatement st = c.prepareStatement(sql);
				st.setString(1, tfIdEliminar.getText());
				st.executeUpdate();
				//vaciamos campos de entrada
				tfIdEliminar.setText("");
				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			mostrarDatos();
			ConexionBDAccess.desconectar();
		}
		if (e.getSource()==btActualizar){
			Connection c=ConexionBDAccess.getConexion();
			String sql = "UPDATE usuarios SET nombres = ?, telefono=? WHERE id = ?";
			try {
				PreparedStatement st = c.prepareStatement(sql);
				st.setString(1, tfActualizarNombres.getText());
				st.setString(2, tfActualizarTelefono.getText());
				st.setString(3, tfIdActualizar.getText());

				st.executeUpdate();
				//vaciamos campos de entrada
				tfActualizarNombres.setText("");
				tfActualizarTelefono.setText("");
				tfIdActualizar.setText("");

				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			mostrarDatos();
			ConexionBDAccess.desconectar();
		}
		
	}
	public static void main(String[] args) {
		new BasesDeDatosAccess();
	}	
}
