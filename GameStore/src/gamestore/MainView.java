/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestore;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Marco Flores
 */
public class MainView extends JFrame
{
       // Componentes principales
    
    private JTextField jTextCadena = new JTextField();
    private JTextArea jTextCifrado = new JTextArea();
    private JButton jButtonCatalogo = new JButton(new ImageIcon("imagenes/catalogo.png"));
    private JButton jButtonClientes  = new JButton(new ImageIcon("imagenes/clientes.png"));
    
    Properties prop;
        
    public MainView(Properties prop)
    {
        this.prop = prop;
        this.setTitle("Sistema de Información");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setBounds(50,50,680,480);
        initComponents();
        this.setVisible(true);
    }
    public void initComponents()
    {
        // Diseña menú
        JMenuBar barraMenus = new JMenuBar();
	JMenu archivo 	  = new JMenu("Archivo");
	JMenuItem salir   = new JMenuItem("Salir");
        this.setJMenuBar(barraMenus);
        barraMenus.add(archivo);
        archivo.add(salir);
        
        // Componentes auxiliares

        
        // Posiciones de los componentes
        this.setLayout(null); // El programador indica coordenadas y tamaño
        jButtonCatalogo.setBounds(20,50,300,300);
        jButtonClientes.setBounds(340,50,300,300);

         // Características de componentes

        
        // Coloca componentes

        this.add(jButtonCatalogo);
        this.add(jButtonClientes);
        
        // GESTION DE EVENTOS
        
        // Lo que se debe hacer cuando el usuario elija la opción "Salir" 
        
        salir.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        salir(evt);}});
    
        // Lo que se debe hacer cuando el usuario elija el botón "Cifrar"
        jButtonCatalogo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        procesarCatalogo(evt);}});
        
        // Lo que se debe hacer cuando el usuario elija la opción "Limpiar"
        jButtonClientes.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        procesarClientes(evt);}});
        
        
        // Lo que se debe hacer cuando e usurio elija la "X" de salir
        class MyWindowAdapter extends WindowAdapter
	{
            public void windowClosing(WindowEvent e)
            {
		exit();
            }
	}
        addWindowListener(new MyWindowAdapter()); 
    }
    
    public void salir(java.awt.event.ActionEvent evt)
    {
        exit();
    }

    public void exit()
    {
        int respuesta = JOptionPane.showConfirmDialog(this, "Desea salir?","Aviso",JOptionPane.YES_NO_OPTION);
        if(respuesta==JOptionPane.YES_OPTION) System.exit(0);
    }
    
    public void procesarCatalogo(java.awt.event.ActionEvent evt)
    {
        new VistaJuegos(this,prop).setVisible(true);
    }
    
    public void procesarClientes(java.awt.event.ActionEvent evt)
    {
       new VistaConsolas(this,prop).setVisible(true);
    }
    
    
}

