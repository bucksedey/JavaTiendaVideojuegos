/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiendajuegos;

/**
 *
 * @author Marco Flores
 */

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Vista extends JFrame
{
	private JTextField tUpc  = new JTextField();
	private JTextField tDescripcion = new JTextField();
	private JTextField tDesarrollador = new JTextField();
	private JTextField tPlataforma = new JTextField();
	private JTextField tClasificacion = new JTextField();
	private JTextField tGeneros = new JTextField();
	private JButton    btBuscar  = new JButton("Buscar");
        private JButton    btInsertar  = new JButton("Agregar");
        private JButton    btBorrar  = new JButton("Eliminar");
        private JButton    btCambiar  = new JButton("Cambiar");
        private JButton    btLimpiar = new JButton("Limpiar");
        
        
	private JLabel  imagen    = new JLabel();

	private Properties prop;
        
        private String mensajeError = "";


	public Vista(Properties prop)
	{
                this.prop = prop;
		initComponents();
		this.setTitle("Tienda Videojuegos (CRUD del Catálogo)");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setBounds(10,10,700,300);
	
                // Carga las propiedades desde el archivo
		try
		{
			prop.load(new FileInputStream("config.properties"));
		}
		catch (IOException ex)
		{
			ex.printStackTrace(System.out);
		}
	}

	public final void initComponents()
	{
                       
            JLabel et1        = new JLabel("UPC:");
            JLabel et2        = new JLabel("Descripcion:");
            JLabel et3        = new JLabel("Desarrollador:");
            JLabel et4        = new JLabel("Plataforma:");
            JLabel et5        = new JLabel("Generos:");
            JLabel et6        = new JLabel("Clasificacion:");
            
            // Diseña el menu

            JMenuBar barraMenus = new JMenuBar();
            JMenu menuArchivo 	  = new JMenu("Archivo");
	    JMenuItem opSalir   = new JMenuItem("Salir");
            this.setJMenuBar(barraMenus);

            barraMenus.add(menuArchivo);
            menuArchivo.add(opSalir);

		// Componentes

	    et1.setBounds(10,30,100,25);
	    et2.setBounds(10,70,100,25);
            et3.setBounds(10,110,100,25);
            et4.setBounds(10,150,100,25);
            et5.setBounds(10,190,100,25);
            et6.setBounds(10,230,100,25);
	    imagen.setBounds(400,30,125,170);
	    tUpc.setBounds(80,30,150,25);
	    tDescripcion.setBounds(80,70,150,25);
            tDesarrollador.setBounds(80,110,150,25);
            tPlataforma.setBounds(80,150,150,25);
            tClasificacion.setBounds(80,190,150,25);
            tGeneros.setBounds(80,230,150,25);
            
            // Botones
	    btBuscar.setBounds(440,30,80,25);
            btInsertar.setBounds(500,110,80,25);
            btBorrar.setBounds(420,110,80,25);
            btCambiar.setBounds(340,110,85,25);
            btLimpiar.setBounds(260,110,80,25);
            
            
	    add(et1);
	    add(et2);
	    add(et3);
	    add(et4);
	    add(et5);
            add(et6);
	    add(tUpc);
	    add(tDescripcion);
	    add(tDesarrollador);
	    add(tPlataforma);
	    add(tClasificacion);
	    add(tGeneros);
	    add(btBuscar);
            add(btInsertar);
            add(btBorrar);
            add(btCambiar);
            add(btLimpiar);
	    add(imagen);
            
            opSalir.addActionListener(evt -> gestionaSalir(evt));
            btBuscar.addActionListener(evt -> gestionaBuscar(evt));
            btInsertar.addActionListener(evt -> gestionaInsertar(evt));
            btBorrar.addActionListener(evt -> gestionaBorrar(evt));
            btCambiar.addActionListener(evt -> gestionaCambiar(evt));
            btLimpiar.addActionListener(evt -> gestionaLimpiar(evt));
            
            
            class MyWindowAdapter extends WindowAdapter
            {
                @Override
		public void windowClosing(WindowEvent e)
		{
			exit();
		}
            }
            addWindowListener(new MyWindowAdapter());   

	}

        // Gestión de eventos
        
        public void gestionaSalir(java.awt.event.ActionEvent evt)
        {
            exit();
        }
    
        public void exit()
        {
            int respuesta = JOptionPane.showConfirmDialog(rootPane, "Desea salir?","Aviso",JOptionPane.YES_NO_OPTION);
            if(respuesta==JOptionPane.YES_OPTION) System.exit(0);
        }
        
        public void gestionaBuscar(java.awt.event.ActionEvent evt)
        {
            if(tUpc.getText().isBlank())
            {
		JOptionPane.showMessageDialog(this, "Para localizar un juego se requiere el UPC", "Aviso!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Juego newGame = Juego.getJuegoFromDB(tUpc.getText(),prop); // Método estático para obtener un juego desde la BD 
		if(newGame != null) // Si hubo éxito
		{
                    tDescripcion.setText(newGame.getDescripcion());
                    String nombreArchivoImagen = newGame.getImagen();
                    imagen.setIcon(new ImageIcon(nombreArchivoImagen));
		}
                else JOptionPane.showMessageDialog(this, "El juego con el UPC indicado no fue localizado", "Aviso!", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
        public void gestionaCambiar(java.awt.event.ActionEvent evt)
        {
            if(tUpc.getText().isBlank())
            {
                JOptionPane.showMessageDialog(this, "Para localizar el juego que se va \na actualizar se requiere el UPC", "Aviso!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                
                if(!invalido()) // Se intenta realizar el UPDATE solo si no hay error de captura
                {
                    Juego newGame = Juego.getJuegoFromDB(tUpc.getText(),prop); // Método estático para obtener un juego desde la BD 
                    if(newGame != null)
                    {
                        newGame.setDescripcion(tDescripcion.getText()); // Actualiza el descripcion del objeto juego
                        
                        if(newGame.cambiar(prop)) // Si hubo éxito
                            JOptionPane.showMessageDialog(this, "Registro actualizado: " + tUpc.getText(), "Aviso!",JOptionPane.INFORMATION_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(this, "Acción no realizada!!","Aviso!",JOptionPane.ERROR_MESSAGE);
                    }
                    else JOptionPane.showMessageDialog(this, "El juego con el UPC indicado no fue localizado", "Aviso!", JOptionPane.ERROR_MESSAGE);                    
                } 
                else JOptionPane.showMessageDialog(this, mensajeError, "Aviso!", JOptionPane.ERROR_MESSAGE);                
	    }
                                       
        }
        
        public void gestionaInsertar(java.awt.event.ActionEvent evt)
        {
            if(!invalido()) // Se intenta realizar el INSERT solo si no hay error de captura
            {               
               // Primero investigamos si no hay otro registro con el mismo UPC
                Juego newGame = Juego.getJuegoFromDB(tUpc.getText(),prop);
               
                if(newGame == null) // Solo si el UPC no está registrado 
                {                        
                    // Adquirimos los datos de la vista              
                    newGame = new Juego();
                    newGame.setUpc(tUpc.getText());
                    newGame.setDescripcion(tDescripcion.getText());
                    newGame.setDesarrollador(tDesarrollador.getText());
                    newGame.setPlataforma(tPlataforma.getText());
                    newGame.setGeneros(tGeneros.getText());
                
                    // Tratamos de ejecutar el alta
                                
                    if(newGame.alta(prop)) // Si la alta fue exitosa
                        JOptionPane.showMessageDialog(this, "Registro agregado: " + tUpc.getText(), "Aviso!",JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(this, "Acción no realizada!!","Aviso!",JOptionPane.ERROR_MESSAGE);                           
                }
                else JOptionPane.showMessageDialog(this, "El UPC ya está registrado", "Aviso!", JOptionPane.ERROR_MESSAGE); 
            } 
            else JOptionPane.showMessageDialog(this, mensajeError, "Aviso!", JOptionPane.ERROR_MESSAGE);          
        }
        
        public void gestionaBorrar(java.awt.event.ActionEvent evt)
        {
            if(tUpc.getText().isBlank())
            {
                JOptionPane.showMessageDialog(this, "Para localizar el juego que se va \na eliminar se requiere el UPC", "Aviso!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // Solicitamos confirmación     
                int respuesta = JOptionPane.showConfirmDialog(this, "Desea borrar este registro?", "Atención!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                if(respuesta==JOptionPane.YES_OPTION) // Si el usuario confirma
                {

                    Juego newGame = Juego.getJuegoFromDB(tUpc.getText(),prop); // Trata de recuperar el juego de la BD
                    
                    if(newGame != null) // Si lo encuentra
                    {
                        // Intenta eliminar el registro
                        if(newGame.borrar(prop)) // Si hubo éxito
                        {   
                            JOptionPane.showMessageDialog(this, "Registro eliminado: " + tUpc.getText(), "Aviso!",JOptionPane.WARNING_MESSAGE);
                            limpiarCampos();
                        }    
                        else JOptionPane.showMessageDialog(this, "Acción no realizada!!","Aviso!",JOptionPane.ERROR_MESSAGE);
                    }
                    else JOptionPane.showMessageDialog(this, "El juego con el UPC indicado no fue localizado", "Aviso!", JOptionPane.ERROR_MESSAGE);            
		}
            }
            
        }
        
        public void gestionaLimpiar(java.awt.event.ActionEvent evt)
        {
            limpiarCampos();
        }
        
        private void limpiarCampos()
        {
                    tUpc.setText("");
                    tDescripcion.setText("");
                    tDesarrollador.setText("");
                    tPlataforma.setText("");
                    tClasificacion.setText("");
                    tGeneros.setText("");
                    imagen.setIcon(null);
        }
        
        // Validación de datos
        private boolean invalido()
        {
            boolean hayError = false;
            mensajeError = "";
            
            if(tUpc.getText().isBlank())
            {
                hayError = true;
                mensajeError = mensajeError.concat("No debe dejar el UPC en blanco\n");
            }
            
            if(tDescripcion.getText().isBlank())
            {
                        hayError = true;
                        mensajeError = mensajeError.concat("No debe dejar la descripcion en blanco\n");
            }
            
            return hayError;
        }
}