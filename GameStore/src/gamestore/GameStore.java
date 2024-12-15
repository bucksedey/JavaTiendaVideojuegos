/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package gamestore;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Marco Flores
 */
public class GameStore {

  /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
        
        // Testing Database
        String homeDir = System.getProperty("user.dir");
        System.out.println("\nATENCION: Verifique el archivo 'config.properties' que se encuentra en la carpeta\n\n"+homeDir+"\n");
        boolean todoBien = true;
        Properties prop   = new Properties();  // Para guardar la conf de la base de datos
        
        // Carga las propiedades desde el archivo
	try
	{
            prop.load(new FileInputStream("config.properties"));
	}
	catch (Exception ex)
	{
            todoBien = false;
            JOptionPane.showMessageDialog(null, "Problema con el archivo de propiedades\n"+ homeDir + "'config.properties", "Aviso!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
	}
        
        if(todoBien)
        {
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
                con.close();
            }
            catch (ClassNotFoundException ex)
            {
                todoBien = false;
                JOptionPane.showConfirmDialog(null, "Problema al cargar el driver", "Aviso!!!" , JOptionPane.ERROR_MESSAGE);
            }
            catch (SQLException ex)
            {
                todoBien = false;
                JOptionPane.showConfirmDialog(null, "Problema al tratar de hacer la conexion", "Aviso!!!" , JOptionPane.ERROR_MESSAGE);
            }            
        }
        
        if(todoBien) // Solo si todo está bien con la BD
        {
            MainView ventana = new MainView(prop);
            ventana.setVisible(true);
        }
    }
    
}
 