/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestore;

import gamestore.DataTable;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Marco Flores
 */
public class VistaConsolas extends JDialog
{
        private final DataTable      dataTable  = new DataTable();
        private final JScrollPane tablePanel = new JScrollPane(dataTable);
      
	private final JLabel  imagen    = new JLabel();

	private Properties prop;
        
	public VistaConsolas(JFrame parent, Properties prop)
	{
                super(parent, true);
                this.prop = prop;
                cargarDatos();
		initComponents();
		this.setTitle("Accesorios y Consolas");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setBounds(50,50,1050,370);	
	}

	public void initComponents()
	{                      
            JLabel et1        = new JLabel("Catálogo de Consolas y Accesorios");
            
            // Diseña el menu

            JMenuBar barraMenus = new JMenuBar();
            JMenu menuArchivo 	  = new JMenu("Archivo");
	    JMenuItem opSalir   = new JMenuItem("Salir");
            this.setJMenuBar(barraMenus);

            barraMenus.add(menuArchivo);
            menuArchivo.add(opSalir);
           
            // Componentes

	    et1.setBounds(10,10,500,25);
            tablePanel.setBounds(10,40,800,200);
	    imagen.setBounds(820,40,200,255);
            
            imagen.setBorder( new TitledBorder( "Portada" ) );
            tablePanel.setBorder(new TitledBorder( "Registros" ));
            dataTable.setFont(new Font("Arial Narrow",Font.PLAIN, 14));
            
            // Botones
           
	    add(et1);
            add(tablePanel);
            add(imagen);
            
            opSalir.addActionListener(evt -> gestionaSalir(evt));
            
            
            // Eventos de teclado sobre la tabla           
            dataTable.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(KeyEvent e) 
                {
                    muestraImagen();
                }      
            });
            
            // Eventos de ratón sobre la tabla
            dataTable.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e) 
                {
                    muestraImagen();
                }   
                
                public void mouseReleased(MouseEvent e) 
                {
                    muestraImagen();
                }   
                
            });
            
            class MyWindowAdapter extends WindowAdapter
            {
		public void windowClosing(WindowEvent e)
		{
			exit();
		}
            }
            addWindowListener(new MyWindowAdapter());
            muestraImagen();
            
	}

        // Gestión de eventos
        
        public void gestionaSalir(java.awt.event.ActionEvent evt)
        {
            exit();
        }
    
        public void exit()
        {
            int respuesta = JOptionPane.showConfirmDialog(rootPane, "Seguro que desea cerrar?","Aviso",JOptionPane.YES_NO_OPTION);
            if(respuesta==JOptionPane.YES_OPTION) this.setVisible(false);
        }
       
        public void gestionaClick()
        {
            muestraImagen();
        }
        
        public void cargarDatos()
        {
                // Recupera propiedades
            
                String driver = prop.getProperty("dbdriver");
                String host   = prop.getProperty("dbhost");
                String user   = prop.getProperty("dbuser");
                String password = prop.getProperty("dbpassword");
                String name     = prop.getProperty("dbname");
                String url = host + name  + "?user=" + user + "&password=" + password;
                System.out.println("Conexion a la BD: " + url);
                
                try
                {

                    Class.forName(driver);     // Carga el driver
                    Connection con = DriverManager.getConnection(url); // Crea una conexion a la BD
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM consolas ORDER BY ID",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ps.executeQuery();
                    ResultSet rs = ps.getResultSet();
                    if(rs != null)
                    {
                        // Investiga el numero de registros recuperados por el QUERY
	        
                        rs.last();
                        int renglones = rs.getRow();
                        
                        
                        String[] header = {"ID","NOMBRE","MODELO","PRECIO","IMAGEN"};
                        String[][] records = new String[renglones][5];
                                                
                        rs.beforeFirst();
                        int i = 0;
                        while(rs.next())
                        {   
                            records[i][0] = rs.getString("ID");
                            records[i][1] = rs.getString("NOMBRE");
                            records[i][2] = rs.getString("MODELO");
                            records[i][3] = String.format("$%.2f",rs.getDouble("PRECIO"));
                            records[i][4] = rs.getString("IMAGEN");    
                            i++;
                        }
                        
                        // Pone los datos en la Tabla
                        
                        DefaultTableModel tableModel = new DefaultTableModel(records, header);
                        dataTable.setModel(tableModel);
                        (dataTable).tableChanged(new TableModelEvent(tableModel));     
                        dataTable.setRowSelectionInterval(0, 0);
                        
                        //personaliza ancho de las columnas
                        
                        dataTable.getColumnModel().getColumn(0).setPreferredWidth(60);
                        dataTable.getColumnModel().getColumn(1).setPreferredWidth(300);
                        dataTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                        dataTable.getColumnModel().getColumn(3).setPreferredWidth(100);
                        dataTable.getColumnModel().getColumn(4).setPreferredWidth(30);
                                                
                    }
                    
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
        }
        
        public void muestraImagen()
        {
            int fila = dataTable.getSelectedRow();
            String nombreArchivoImagen = (String) dataTable.getValueAt(fila, 4);
            imagen.setIcon(new ImageIcon("imagenes/"+nombreArchivoImagen));
        }
}
