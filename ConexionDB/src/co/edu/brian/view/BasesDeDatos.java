package co.edu.brian.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import co.edu.brian.businesslogic.Libros;
import co.edu.brian.model.ConexionDB;
import co.edu.brian.model.ManageDB;

public class BasesDeDatos extends JFrame implements ActionListener {
	
	//private JTextArea txtSalida;
	
	private JLabel lblInsertarNombres,
				   lblInsertarEditorial,
				   lblPunto,
				   lblPunto2,
				   lblInsertarAutor,
				   lblInsertarPrecio,
				   lTituloInsertar,
				   lTituloBorrar,
				   lTituloActualizar,
				   lActualizarNombres, 
				   lActualizarEditorial,
				   lblActualizarAutor,
				   lblActualizarPrecio,
				   lIdActualizar,
				   lblFiltrar,
				   lblNombre1,
				   lblNombre2;
	
	private JTableHeader jth;
	
	
	
	private JTextField txtInsertarNombres,
					   txtInsertarEditorial,
					   txtInsertarAutor,
					   tfActualizarNombres, 
					   tfActualizarEditorial,
					   txtActualizarAutor,
					   txtFiltro,
					   tfIdActualizar;
	
	private JSpinner jspActualizarPrecio,
 					 jspInsertarPrecio,
 					 jspActualizarPrecio2,
 					 jspActualizarPrecio3,
 					 jspActualizarPrecio4,
 					 jspInsertarPrecio2,
 					 jspInsertarPrecio3,
					 
 					 jspInsertarPrecio4;
	
	private JButton btGuardar,
					btEliminar,
					btActualizar;
	
	private JPanel panel;
	
	private ManageDB mdb;
	
	private JTable jtdata;
	private DefaultTableModel dtm;
	private DefaultTableCellRenderer centerRenderer;
	
	//constructor
	BasesDeDatos(){
	
	mdb = new ManageDB();
		
	panel = new JPanel();
	
	panel.setLayout(null);
	
	txtFiltro = new JTextField();
	
	txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			warn();
			
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			warn();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			warn();
		}
		
		  public void warn() {
			     if (txtFiltro.getText().length() > 0){
			    	 
			    	 limpiarTabla();
			    	 filtrarDatos(txtFiltro.getText().trim().toLowerCase());
			     }
			     else {
			    	 limpiarTabla();
			    	 mostrarDatos();
			     }
			     
			     
			     }
	});
	
	txtFiltro.setBounds(150, 10, 600, 30);
	
	panel.add(txtFiltro);
	
	lblFiltrar = new JLabel("Filtrar datos");
	lblFiltrar.setFont(new Font("Arial",
								 Font.BOLD,
								 16));
		
	lblFiltrar.setBounds(50, 10, 150, 30);
		
	panel.add(lblFiltrar);
	
	lblNombre1 = new JLabel("Brian Alexander");
	lblNombre1.setFont(new Font("Arial",
								 Font.BOLD,
								 16));
		
	lblNombre1.setBounds(720, 550, 150, 30);
		
	panel.add(lblNombre1);
	
	lblNombre2 = new JLabel("Ramirez Galeano");
	lblNombre2.setFont(new Font("Arial",
								 Font.BOLD,
								 16));
		
	lblNombre2.setBounds(720, 570, 150, 30);
		
	panel.add(lblNombre2);
	
	
	
	
	
	jtdata = new JTable();
	    
	String columnas [] = {
						  "\nID\n",
						  "\nNombre Libro\n",
						  "\nEditorial\n",
						  "\nAutor\n",
					 	  "\nPrecio\n"
						 };
	//como hacer que las columnas no sean editables
	
	dtm = new DefaultTableModel(){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };//todos los false son las columnas de la tabla que no seran editables

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
         
        };
	
	
	
	
	dtm.setColumnIdentifiers(columnas);

	jtdata.setModel(dtm);
	jth = jtdata.getTableHeader();
	
	jtdata.setBackground(Color.white);
	jtdata.setForeground(Color.black);
	jtdata.setFont(new Font("Tahoma",
							 Font.PLAIN,
							 16));
	jtdata.setSelectionBackground(new Color(35,
											185,
											231)
								  );
	
	jth.setFont(new Font("Arial",
						 Font.BOLD,
						 16)
				);
	
	jtdata.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
		 
			if(jtdata.getSelectedRow()!=-1) {
			
					gestionFila();
				
		 }		
				
		}
	});
	
	jth.setBackground(new Color(204,
								255,
								255));
	
	centerRenderer = new DefaultTableCellRenderer(); 
	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	
	//centrar todas las celdas del jtable
		
	jtdata.setGridColor(Color.black);
	jtdata.setSelectionForeground(Color.white);
	
	//como centrar el contenido de cada celda en la tabla
	
	  jtdata.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
      jtdata.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
      jtdata.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
      jtdata.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
      jtdata.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	
    
   	 jtdata.getColumnModel().getColumn(1).setMinWidth(150);

      
	
	//hacer invisible una columna 
    
	 jtdata.getColumnModel().getColumn(0).setMinWidth(0);
	 jtdata.getColumnModel().getColumn(0).setMaxWidth(0);
	 jtdata.getColumnModel().getColumn(0).setWidth(0);
	
	JScrollPane scroll = new JScrollPane(jtdata);
		
	scroll.setBackground(Color.red);
	scroll.setForeground(Color.green);
	
	
	scroll.setBounds(10,50,860,300);
	this.getContentPane().add(scroll);
	
	lTituloInsertar=new JLabel("INSERTAR DATO");
	lTituloInsertar.setBounds(10,380,100,20);
	panel.add(lTituloInsertar);

	lTituloBorrar=new JLabel("BORRAR LIBRO");
	lTituloBorrar.setBounds(310,380,100,20);
	panel.add(lTituloBorrar);

	lTituloActualizar=new JLabel("ACTUALIZAR LIBRO");
	lTituloActualizar.setBounds(500,380,200,20);
	panel.add(lTituloActualizar);

	lblInsertarNombres = new JLabel("Nombre Libro: ");
	lblInsertarNombres.setBounds(10,410,100,20);
	panel.add(lblInsertarNombres);
	
	txtInsertarNombres = new JTextField();
	txtInsertarNombres.setBounds(110,410,150,20);
	txtInsertarNombres.setHorizontalAlignment(JTextField.HORIZONTAL);
	panel.add(txtInsertarNombres);
	
	
	lblInsertarEditorial = new JLabel("Editorial: ");
	lblInsertarEditorial.setBounds(10,440,100,20);
	panel.add(lblInsertarEditorial);
	
	txtInsertarEditorial = new JTextField();
	txtInsertarEditorial.setBounds(110,440,150,20);
	txtInsertarEditorial.setHorizontalAlignment(JTextField.HORIZONTAL);
	panel.add(txtInsertarEditorial);
	
	
	lblInsertarAutor = new JLabel("Autor: ");
	lblInsertarAutor.setBounds(10,470,100,20);
	panel.add(lblInsertarAutor);
	
	txtInsertarAutor = new JTextField();
	txtInsertarAutor.setBounds(110,470,150,20);
	txtInsertarAutor.setHorizontalAlignment(JTextField.HORIZONTAL);
	panel.add(txtInsertarAutor);
	
	
	lblInsertarPrecio = new JLabel("Precio: ");
	lblInsertarPrecio.setBounds(10,500,100,20);
	panel.add(lblInsertarPrecio);
	
	jspInsertarPrecio = new JSpinner(new SpinnerNumberModel(0,
 														    0,
														    1900,
														    100));
	
	//no permite que nadie pueda escribir en el JSpinner
	jspInsertarPrecio.setEditor(new JSpinner.DefaultEditor(jspInsertarPrecio));
	jspInsertarPrecio.setBounds(80, 500, 50, 20);
	add(jspInsertarPrecio);

	jspInsertarPrecio2 = new JSpinner(new SpinnerNumberModel(0,
			    											 0,
			    											 90,
			    											 10));

	//no permite que nadie pueda escribir en el JSpinner
	jspInsertarPrecio2.setEditor(new JSpinner.DefaultEditor(jspInsertarPrecio2));
	jspInsertarPrecio2.setBounds(135, 500, 35, 20);
	add(jspInsertarPrecio2);
	
	jspInsertarPrecio3 = new JSpinner(new SpinnerNumberModel(0,
															 0,
															 9,
															 1));

	//no permite que nadie pueda escribir en el JSpinner
	jspInsertarPrecio3.setEditor(new JSpinner.DefaultEditor(jspInsertarPrecio3));
	jspInsertarPrecio3.setBounds(175, 500, 35, 20);
	add(jspInsertarPrecio3);
	
	/*######################################################################################*/
	
	lblPunto = new JLabel(".");
	lblPunto.setBounds(215,500,10,20);
	lblPunto.setFont(new Font("Arial", Font.BOLD, 16));
	panel.add(lblPunto);
	
	
	jspInsertarPrecio4 = new JSpinner(new SpinnerNumberModel(0,
															 0,
															 99,
															 1));

	//no permite que nadie pueda escribir en el JSpinner
	jspInsertarPrecio4.setEditor(new JSpinner.DefaultEditor(jspInsertarPrecio4));
	jspInsertarPrecio4.setBounds(225, 500, 35, 20);
	add(jspInsertarPrecio4);

	
	/*######################################################################################*/	
	
	btGuardar = new JButton("Guardar");
	btGuardar.setBounds(80,540,100,20);
	btGuardar.addActionListener(this);
	panel.add(btGuardar);
	
		
	btEliminar = new JButton("Eliminar");
	btEliminar.setBounds(310,400,100,20);
	btEliminar.addActionListener(this);
	panel.add(btEliminar);
	
	
	
	
	lIdActualizar=new JLabel("ID a actualizar: ");
	lIdActualizar.setBounds(500,410,100,20);
	panel.add(lIdActualizar);
	
	tfIdActualizar=new JTextField();
	tfIdActualizar.setBounds(610,410,50,20);
	tfIdActualizar.setEnabled(false);
	tfIdActualizar.setHorizontalAlignment(JTextField.HORIZONTAL);
	panel.add(tfIdActualizar);
	
	lActualizarNombres=new JLabel("Nuevo Nombre: ");
	lActualizarNombres.setBounds(500,440,100,20);
	lActualizarNombres.setHorizontalAlignment(JTextField.HORIZONTAL);
	panel.add(lActualizarNombres);
	
	tfActualizarNombres=new JTextField();
	tfActualizarNombres.setBounds(610,440,200,20);
	tfActualizarNombres.setHorizontalAlignment(JTextField.HORIZONTAL);
	panel.add(tfActualizarNombres);
	
	lActualizarEditorial = new JLabel("Nueva Editorial: ");
	lActualizarEditorial.setBounds(500,470,100,20);
	panel.add(lActualizarEditorial);
	
	tfActualizarEditorial = new JTextField();
	tfActualizarEditorial.setBounds(610,470,200,20);
	tfActualizarEditorial.setHorizontalAlignment(JTextField.HORIZONTAL);
	panel.add(tfActualizarEditorial);
	
	lblActualizarAutor=new JLabel("Nuevo Autor: ");
	lblActualizarAutor.setBounds(500,500,100,20);
	panel.add(lblActualizarAutor);
	
	txtActualizarAutor = new JTextField();
	txtActualizarAutor.setBounds(610,500,200,20);
	txtActualizarAutor.setHorizontalAlignment(JTextField.HORIZONTAL);
	panel.add(txtActualizarAutor);
	
	
	
	lblActualizarPrecio = new JLabel("Nuevo Precio: ");
	lblActualizarPrecio.setBounds(500,530,100,20);
	panel.add(lblActualizarPrecio);
	
	jspActualizarPrecio = new JSpinner(new SpinnerNumberModel(0,
															  0,
															  2000,
															  10.00));

	//no permite que nadie pueda escribir en el JSpinner
	jspActualizarPrecio.setEditor(new JSpinner.DefaultEditor(jspActualizarPrecio));
	jspActualizarPrecio.setBounds(610, 530, 100, 20);
	add(jspActualizarPrecio);

	/*
	jspActualizarPrecio2 = new JSpinner(new SpinnerNumberModel(0,
			  0,
			  2000,
			  10));

	//no permite que nadie pueda escribir en el JSpinner
	jspActualizarPrecio2.setEditor(new JSpinner.DefaultEditor(jspActualizarPrecio2));
	jspActualizarPrecio2.setBounds(610, 530, 100, 20);
	add(jspActualizarPrecio2);
	
	jspActualizarPrecio3 = new JSpinner(new SpinnerNumberModel(0,
			  0,
			  2000,
			  10));

	//no permite que nadie pueda escribir en el JSpinner
	jspActualizarPrecio3.setEditor(new JSpinner.DefaultEditor(jspActualizarPrecio3));
	jspActualizarPrecio3.setBounds(610, 530, 100, 20);
	add(jspActualizarPrecio3);
	
	jspActualizarPrecio4 = new JSpinner(new SpinnerNumberModel(0,
			  0,
			  2000,
			  10));

	//no permite que nadie pueda escribir en el JSpinner
	jspActualizarPrecio4.setEditor(new JSpinner.DefaultEditor(jspActualizarPrecio4));
	jspActualizarPrecio4.setBounds(610, 530, 100, 20);
	add(jspActualizarPrecio4);
	*/
	
	
	
	btActualizar = new JButton("Actualizar");
	btActualizar.addActionListener(this);
	btActualizar.setBounds(500,560,100,20);
	panel.add(btActualizar);

	
	add (panel);
	setSize(900,650);
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    //centramos la ventana en la mitad de la pantalla.
	setLocationRelativeTo(null);
	mostrarDatos();
	
	}
	
	
	public void gestionFila() {

		tfIdActualizar.setText(
					  jtdata.getValueAt(
							  jtdata.getSelectedRow(), 
							  0).toString());

		tfActualizarNombres.setText(
							jtdata.getValueAt(
								   jtdata.getSelectedRow(), 
								   1).toString());
		
		tfActualizarEditorial.setText(
							  jtdata.getValueAt(
									  jtdata.getSelectedRow(), 
									  2).toString());
		
		txtActualizarAutor.setText(jtdata.getValueAt(jtdata.getSelectedRow(), 
		3).toString());
		String valorLibro = jtdata.getValueAt(jtdata.getSelectedRow(), 4).toString()
		.substring(1, 
			   jtdata.getValueAt(jtdata.getSelectedRow(), 
					   			 4).toString().length());
		
		jspActualizarPrecio.setValue(Double.parseDouble(valorLibro));
		

	}
	
	public void filtrarDatos(String dato) {
			
		    Object cellSpace[] = new Object[5];
		    
		try {
			
			ArrayList<Libros> datos = mdb.filtrarDatos(dato);
			
			//txtSalida.setText("SALIDA DE DATOS \n");
		
			
			for(Libros lib: datos) {
		 		 
				 cellSpace[0] = lib.getId_libro();
				 cellSpace[1] = lib.getNombreLibro();
				 cellSpace[2] = lib.getEditorial();
				 cellSpace[3] = lib.getAutor();								 
				 cellSpace[4] = "$" + lib.getPrecio();
				 dtm.addRow(cellSpace);
				
				/*
				txtSalida.append( " " + lib.getId_libro() +" |  " 
					      		+ lib.getNombreLibro() +  " |  "
					      		+ lib.getEditorial() + " |  "
					      		+ lib.getAutor() + " |  "
					      		+ " $"+lib.getPrecio() + " | "
					      		+ "\n ");*/
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	
	public void mostrarDatos(){
		
		
		
		//txtSalida.setText("");//vaciamos el texto de salida 
			Object cellSpace[] = new Object[5];	
		try {
			ArrayList<Libros> datos = mdb.listarDatos();
			
			//txtSalida.setText("SALIDA DE DATOS \n");
		
			
			for(Libros lib: datos) {
		 		 
				 cellSpace[0] = lib.getId_libro();
				 cellSpace[1] = lib.getNombreLibro();
				 cellSpace[2] = lib.getEditorial();
				 cellSpace[3] = lib.getAutor();								 
				 cellSpace[4] = "$" + lib.getPrecio();
				 dtm.addRow(cellSpace);
				 
				 
				/*
				txtSalida.append( " " + lib.getId_libro() +" |  " 
					      		+ lib.getNombreLibro() +  " |  "
					      		+ lib.getEditorial() + " |  "
					      		+ lib.getAutor() + " |  "
					      		+ " $"+lib.getPrecio() + " | "
					      		+ "\n ");*/
			}
			
			datos.clear();
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		/*Connection c = ConexionDB.getMariaDbConn();
		try {
			c.prepareCall("{CALL SP_LISTAR_USUARIOS()}");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Statement comando;
		try {
			comando = c.createStatement();
			ResultSet registro = comando.executeQuery("select ID,Nombres,telefono from usuario where 1 ORDER BY id desc");
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
		ConexionDB.desconectar();
		*/
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		acciones(e);
		
	}
	
	
	public void acciones(ActionEvent e) {
						
		if (e.getSource() == this.btEliminar) {
			eliminarDatos();
		}
		
		else if(e.getSource() == this.btActualizar) {
			actualizarDatos();
		}
		
		else if(e.getSource() == this.btGuardar) {
			registrarLibro();
		}
		
	}
	
	
	public void eliminarDatos() {
			
		int dato = 0;
		int response = 0;
		int responsedb = 0;
		String libro = null;
		String autor = null;
		
			
			/*
			 * codigo necesario para retornar valores 
			 * especificos de una celda puntual en un 
			 * jtable
			   dato = Integer.parseInt(jtdata.getValueAt(jtdata.getSelectedRow(), 
													  	 #de columna a seleccionar)
			.toString());
			
			 *
			 */
			
		if(jtdata.getSelectedRowCount() == 0 || jtdata.getSelectedRowCount()>=2) {
		
						
			JOptionPane.showMessageDialog(null,
										  "debe seleccionar una fila antes de eliminar un libro",
										  "error de seleccion",
										   JOptionPane.ERROR_MESSAGE);
			
			jtdata.getSelectionModel().clearSelection();
			
			
		}
	
		/*####################################################################################*/
			
		else if(jtdata.getSelectedRowCount() == 1){
			
			
			
			dato = Integer.parseInt(jtdata.getValueAt(jtdata.getSelectedRow(), 
					  0).toString());

			libro = jtdata.getValueAt(jtdata.getSelectedRow(), 
				  1).toString();
			
			autor = jtdata.getValueAt(jtdata.getSelectedRow(), 
				  3).toString();
			
			
			response = JOptionPane.showConfirmDialog(null, 
					   "desea eliminar el libro:  " + libro 
					   + "\n del autor:   " + autor, 
					   "eliminar libro", 
					   JOptionPane.YES_NO_OPTION,
					   JOptionPane.WARNING_MESSAGE);

			if(response == JOptionPane.YES_OPTION) {
				
				
				
				try {
					
					responsedb = mdb.elimanarRegistro(dato);
					
					if(responsedb == 0) {
						JOptionPane.showMessageDialog(null,
													  "no se logro eliminar el libro intente nuevamente",
													  "error de eliminacion",
													  JOptionPane.ERROR_MESSAGE);

						limpiarDatos(1);

						dtm.removeRow(jtdata.getSelectedRow());
						
					}
					
					else {
						
						
						
						
					//	dtm.removeRow(jtdata.getSelectedRow());
						
						dtm.removeRow(jtdata.getSelectedRow());
						
						if(jtdata.getRowCount() == 0 && txtFiltro.getText().trim().isEmpty() == false) {
						
							txtFiltro.requestFocus();
							txtFiltro.setText("");
							
						}
						
						limpiarDatos(1);
						
						JOptionPane.showMessageDialog(null,
								  "libro eliminado exitosamente",
								  "informacion",
								  JOptionPane.INFORMATION_MESSAGE);
						
						
						
					}
				} 
				catch (SQLException ex) {
				
					JOptionPane.showMessageDialog(null,
												 ex.getMessage(),
												 "error",
												 JOptionPane.ERROR_MESSAGE);
					
				}
				
				
			   					
				/*
				 * codigo para eliminar una fila 
				 * dtm.removeRow(jtdata.getSelectedRow());
				 * 
				 * dtm = default table model
				 * 
				 * */ 
			}
			
			else if(response == JOptionPane.NO_OPTION){
				

				jtdata.getSelectionModel().clearSelection();
				tfIdActualizar.setText("");
				tfActualizarNombres.setText("");
				tfActualizarEditorial.setText("");
				txtActualizarAutor.setText("");
				jspActualizarPrecio.setValue(0.00);
				
				
				
			}
		}
		
		
		/*####################################################################################*/
			
		
			
					
			
				
				/*
				JOptionPane.showMessageDialog(null,
											  "el id a eliminar es el: " + dato
											  ,"eliminacion de libro",
											  JOptionPane.WARNING_MESSAGE);
				*/
				
					
	}
	
	public void actualizarDatos() {
		
		
		int response = 0;
		boolean centinel;
		
		if(jtdata.getSelectedRowCount() >=2) {
			
			JOptionPane.showMessageDialog(null,
										  "solo debe tener una fila seleccionada"+
										  " para modificar un libro",
										  "error al modificar el libro",
										  JOptionPane.ERROR_MESSAGE);

			
			limpiarDatos(1);
    		jtdata.getSelectionModel().clearSelection();
    		
    	}

		else if(tfIdActualizar.getText().isEmpty()) {
			
			JOptionPane.showMessageDialog(null,
					  "debe seleccionar un libro antes de actualizarlo",
					  "error al actualizar libro",
					  JOptionPane.ERROR_MESSAGE);
				
		}
		
		else if(tfActualizarNombres.getText().isEmpty()) {
		
			JOptionPane.showMessageDialog(null,
										  "debe digitar un nombre antes de actualizar el libro",
										  "error al actualizar libro",
										  JOptionPane.ERROR_MESSAGE);
		    
			tfActualizarNombres.requestFocus();
		
		}
		
		else if(tfActualizarEditorial.getText().isEmpty()) {
		
			JOptionPane.showMessageDialog(null,
					  "debe ingresar una editorial antes de actualizar el libro",
					  "error al actualizar libro",
					  JOptionPane.ERROR_MESSAGE);
			
			tfActualizarEditorial.requestFocus();		
			
		}
		
		else if(txtActualizarAutor.getText().isEmpty()) {
		
			JOptionPane.showMessageDialog(null,
					  "debe ingresar una autor antes de actualizar el libro",
					  "error al actualizar libro",
					  JOptionPane.ERROR_MESSAGE);
			
			txtActualizarAutor.requestFocus();
		
		}
				
		else {
			
			Libros lib = new Libros();
			
			lib.setId_libro(Integer.parseInt(tfIdActualizar.getText().trim().toLowerCase()));
			lib.setNombreLibro(tfActualizarNombres.getText().trim().toLowerCase());
			lib.setEditorial(tfActualizarEditorial.getText().trim().toLowerCase());
			lib.setAutor(txtActualizarAutor.getText().trim().toLowerCase());
			lib.setPrecio((double) jspActualizarPrecio.getValue());
			
			try {
				
				response = mdb.actualizarRegistro(lib);
				
				lib = null;
				
				if(response == 0) {
					
				 JOptionPane.showMessageDialog(null,
						 						"error al modificar el libro o libro ya existente",
						 						"error de actualizacion",
						 						JOptionPane.ERROR_MESSAGE);
				 jtdata.getSelectionModel().clearSelection();
				
				 tfActualizarNombres.requestFocus();
				 
				 if(tfActualizarNombres.getText().trim().isEmpty()==false) {
					 tfActualizarNombres.selectAll();
				 }
				 
				}
				
				else {
										
											   
					jtdata.setValueAt(lib.getNombreLibro(), 
							 jtdata.getSelectedRow(), 
							 1);
			
					jtdata.setValueAt(lib.getEditorial(), 
							 		  jtdata.getSelectedRow(), 
							 		  2);
					
					jtdata.setValueAt(lib.getAutor(), 
									  jtdata.getSelectedRow(), 
									  3);
					
					jtdata.setValueAt("$" + lib.getPrecio(), 
									  jtdata.getSelectedRow(), 
									  4);
					
					limpiarDatos(1);
					
					jtdata.getSelectionModel().clearSelection();
					
					JOptionPane.showMessageDialog(null,
	 						"libro modificado exitosamente",
	 						"libro actualizado",
	 						JOptionPane.INFORMATION_MESSAGE);
					
					
				}
							
			} 
			catch (Exception ex) {
				
				System.out.println(ex.getMessage());
				
			}
			
		
			
		
		}
	}

	public void registrarLibro() {
	
		int response = 0;
		
		double precio = 0.0;
		
		if(txtFiltro.getText().trim().isEmpty() == false) {
			
			JOptionPane.showMessageDialog(null,
					  "para registrar un libro no"+
					  " debe tener datos filtrados",
					  "error al registrar el libro",
					  JOptionPane.ERROR_MESSAGE);
				
				txtFiltro.requestFocus();
				
				txtFiltro.selectAll();
		}
		
		else if(jtdata.getSelectedRowCount() >=1) {
			
			JOptionPane.showMessageDialog(null,
										  "para registrar un libro no"+
										  " debe tener filas seleccionadas",
										  "error al registrar el libro",
										  JOptionPane.ERROR_MESSAGE);

			txtInsertarNombres.requestFocus();
			limpiarDatos(1);
    		jtdata.getSelectionModel().clearSelection();
    		
    	}
    	
		else if(txtInsertarNombres.getText().isEmpty()) {
		
			JOptionPane.showMessageDialog(null,
										  "debe ingresar un nombre antes de registrar el libro",
										  "error al actualizar libro",
										  JOptionPane.ERROR_MESSAGE);
		    
			txtInsertarNombres.requestFocus();
		
		}
		
		else if(txtInsertarEditorial.getText().isEmpty()) {
		
			JOptionPane.showMessageDialog(null,
					  "debe ingresar una editorial antes de registrar el libro",
					  "error al actualizar libro",
					  JOptionPane.ERROR_MESSAGE);
			
			txtInsertarEditorial.requestFocus();		
			
		}
		
		else if(txtInsertarAutor.getText().isEmpty()) {
		
			JOptionPane.showMessageDialog(null,
					  "debe ingresar una autor antes de registrar el libro",
					  "error al actualizar libro",
					  JOptionPane.ERROR_MESSAGE);
			
			txtInsertarAutor.requestFocus();
		
		}
				
		else {
						
			
			try {
				
				precio = generarPrecio(jspInsertarPrecio.getValue(),
									   jspInsertarPrecio2.getValue(),
									   jspInsertarPrecio3.getValue(),
									   jspInsertarPrecio4.getValue());
			  
				Libros book = new Libros();
			    book.setNombreLibro(txtInsertarNombres.getText().trim().toLowerCase());
			    book.setEditorial(txtInsertarEditorial.getText().trim().toLowerCase());
			    book.setAutor(txtInsertarAutor.getText().trim().toLowerCase());
			    book.setPrecio(precio);
			    
			    
			    response = mdb.insertarRegistro(book);
			    
			    if(response == 0) {
			    	
					JOptionPane.showMessageDialog(null,
							  "error al registrar libro o libro ya existente "
							  +"\nintente nuevamente",
							  "error de registro libro",
							  JOptionPane.ERROR_MESSAGE);
					
					txtInsertarNombres.requestFocus();
			    
			    }
			    
			    else {
			    				    	
			    	limpiarTabla();
			    	mostrarDatos();
			    	limpiarDatos(2);
			    	
			    	
			    	JOptionPane.showMessageDialog(null,
							  "El libro ha sido registrado exitosamente",
							  "Libro registrado",
							  JOptionPane.INFORMATION_MESSAGE);
			    			    
			    	 
			    	
			    }
			    
			    
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
				
		}
		
	}
	
	public void limpiarDatos(int input) {
		
		if(input == 1) {
			
			
			tfIdActualizar.setText("");
			tfActualizarNombres.setText("");
			tfActualizarEditorial.setText("");
			txtActualizarAutor.setText("");
			jspActualizarPrecio.setValue(0.00);
			
			if(txtFiltro.getText().trim().isEmpty()==false) {
				txtFiltro.setText("");
			}
		
		}
		
		else if(input == 2) {
			
			txtInsertarNombres.setText("");
			txtInsertarEditorial.setText("");
			txtInsertarAutor.setText("");
			jspInsertarPrecio.setValue(0);
			jspInsertarPrecio2.setValue(0);
			jspInsertarPrecio3.setValue(0);
			jspInsertarPrecio4.setValue(0);
			
		}
		
		
		
	}

	/*
	public void actionPerformed(ActionEvent e) {
		 
		if (e.getSource()==btGuardar){
		
			Connection c=ConexionDB.getMariaDbConn();
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
			ConexionDB.desconectar();
		}
		
		if (e.getSource() == btEliminar){
			Connection c = ConexionDB.getMariaDbConn();
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
			ConexionDB.desconectar();
		}
		
		
		if (e.getSource()==btActualizar){
			Connection c=ConexionDB.getMariaDbConn();
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
			ConexionDB.desconectar();
		}
		
		
		
	}
	*/
	
	public void limpiarTabla() {
		
		for(int i = (dtm.getRowCount() -1); i > - 1; i--) {

			dtm.removeRow(i);
	
		}
	}
	
	
	public double generarPrecio(Object n1, 
								Object n2, 
								Object n3, 
								Object n4) {
		
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		double aux = 0;
		double precio = 0.0;
		
		num1 = (int) n1;
		num2 = (int) n2;
		num3 = (int) n3;
		aux =  (int) n4;
		precio = precio + (aux/100); 
		precio = precio + num1 + num2 + num3;
		
		return precio;
		
	}
	
	public static void main(String[] args) {
		new BasesDeDatos();
	}	
}
