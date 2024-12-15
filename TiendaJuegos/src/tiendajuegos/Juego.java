/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiendajuegos;

/**
 *
 * @author Marco Flores
 */
import java.sql.*;
import java.util.*;

public class Juego
{
	private String upc;
	private String descripcion;
	private String desarrollador;
	private String plataforma;
	private String clasificacion;
	private String generos;
	private String imagen;
	private double precio;

	public Juego()
	{}

	public Juego(String upc, String descripcion, String desarrollador, String plataforma, String clasificacion, String generos, String imagen, double precio)
	{
		this.upc = upc;
		this.descripcion = descripcion;
		this.desarrollador = desarrollador;
		this.plataforma = plataforma;
		this.clasificacion = clasificacion;
		this.generos = generos;
		this.imagen = imagen;
		this.precio = precio;


	}

	// Metodos get

	public String getUpc()
	{
		return upc;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public String getDesarrollador()
	{
		return desarrollador;
	}

	public String getPlataforma()
	{
		return plataforma;
	}

	public String getClasificacion()
	{
		return clasificacion;
	}

	public String getGeneros()
	{
		return generos;
	}

	public String getImagen()
	{
		return "imagenes/" + imagen;
	}

	public  double getPrecio()
	{
		return precio;
	}

	// Métodos set

	public void setUpc(String upc)
	{
		this.upc = upc;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setDesarrollador(String desarrollador)
	{
		this.desarrollador = desarrollador;
	}

	public void setPlataforma(String plataforma)
	{
		this.plataforma = plataforma;
	}

	public void setClasificacion(String clasificacion)
	{
		this.clasificacion = clasificacion;
	}

	public void setGeneros(String generos)
	{
		this.generos = generos;
	}

	public void setImagen(String imagen)
	{
		this.imagen = imagen;
	}

	public void setPrecio(double precio)
	{
		this.precio = precio;
	}


	public void muestraDatos()
	{
		System.out.println("UPC  :" + upc);
		System.out.println("Descripcion:" + descripcion);
		System.out.println("Desarrollador :" + desarrollador);
		System.out.println("Plataforma :" + plataforma);
		System.out.println("Clasificacion :" + clasificacion);
		System.out.println("Generos :" + generos);
		System.out.println("Precio:" + precio);
		
	}


	public static Juego getJuegoFromDB(String upcConsulta, Properties prop)
	{
            Juego juego = new Juego(); // Nuevo juego en blanco

            try
	    {

                String driver = prop.getProperty("dbdriver");
                String host   = prop.getProperty("dbhost");
                String user   = prop.getProperty("dbuser");
                String password = prop.getProperty("dbpassword");
                String name     = prop.getProperty("dbname");
                String url = host + name  + "?user=" + user + "&password=" + "buckgresql";
                System.out.println("Conexion a la BD: " + url);


                Class.forName(driver);     // Carga el driver


                Connection con = DriverManager.getConnection(url); // Crea una conexion a la BD

                PreparedStatement ps = con.prepareStatement("SELECT * FROM JUEGOS WHERE UPC = ?");
                ps.setString(1,upcConsulta);
                System.out.println(ps.toString());
                ps.executeQuery();
                ResultSet rs = ps.getResultSet();

                if(rs!=null && rs.next())
                {
                    String upc   = rs.getString("upc");
                    String descripcion = rs.getString("descripcion");
                    String desarrollador  = rs.getString("desarrollador");
                    String plataforma  = rs.getString("plataforma");
                    String clasificacion  = rs.getString("clasificacion");
                    String generos  = rs.getString("generos");
                    String imagen = rs.getString("imagen");
                    double precio = rs.getDouble("precio");

                    juego.setUpc(upc);
                    juego.setDescripcion(descripcion);
                    juego.setDesarrollador(desarrollador);
                    juego.setPlataforma(plataforma);
                    juego.setClasificacion(clasificacion);
                    juego.setGeneros(generos);
                    juego.setImagen(imagen);
                    juego.setPrecio(precio);
                    con.close();
                    return juego;
                }

	    }
            catch (ClassNotFoundException | SQLException ex)
	    {
	    	ex.printStackTrace(System.out);
	    }     
	    
            return null;
	}
        
        public boolean cambiar(Properties prop)
	{
            boolean exito = false;
            
            try
	    {

                String driver = prop.getProperty("dbdriver");
                String host   = prop.getProperty("dbhost");
                String user   = prop.getProperty("dbuser");
                String password = prop.getProperty("dbpassword");
                String name     = prop.getProperty("dbname");
                String url = host + name  + "?user=" + user + "&password=" + password;
                System.out.println("Conexion a la BD: " + url);


                Class.forName(driver);     // Carga el driver


                Connection con = DriverManager.getConnection(url); // Crea una conexion a la BD

                PreparedStatement ps = con.prepareStatement("UPDATE JUEGOS SET DESCRIPCION = ? WHERE UPC = ?");
                
                ps.setString(1, this.descripcion); // El descripcion que llega de la Vista
                ps.setString(2, this.upc);
                System.out.println(ps.toString());
                exito = ps.executeUpdate() > 0;
                con.close();
    
	    }
            
            catch (ClassNotFoundException | SQLException ex)
	    {
	    	ex.printStackTrace(System.out);
	    }            
	    return exito;
	}

        public boolean borrar(Properties prop)
	{
            boolean exito = false;
            
            try
	    {

                String driver = prop.getProperty("dbdriver");
                String host   = prop.getProperty("dbhost");
                String user   = prop.getProperty("dbuser");
                String password = prop.getProperty("dbpassword");
                String name     = prop.getProperty("dbname");
                String url = host + name  + "?user=" + user + "&password=" + password;
                System.out.println("Conexion a la BD: " + url);


                Class.forName(driver);     // Carga el driver


                Connection con = DriverManager.getConnection(url); // Crea una conexion a la BD

                PreparedStatement ps = con.prepareStatement("DELETE FROM JUEGOS WHERE UPC = ?");
                ps.setString(1, this.upc);
                System.out.println(ps.toString());
                exito = ps.executeUpdate() > 0;
                con.close();
               
	    }
            catch (ClassNotFoundException | SQLException ex)
	    {
	    	ex.printStackTrace(System.out);
	    }     
	    return exito;
	}

        public boolean alta(Properties prop)
	{
            boolean exito = false;
            
            try
	    {

                String driver = prop.getProperty("dbdriver");
                String host   = prop.getProperty("dbhost");
                String user   = prop.getProperty("dbuser");
                String password = prop.getProperty("dbpassword");
                String name     = prop.getProperty("dbname");
                String url = host + name  + "?user=" + user + "&password=" + password;
                System.out.println("Conexion a la BD: " + url);


                Class.forName(driver);     // Carga el driver


                Connection con = DriverManager.getConnection(url); // Crea una conexion a la BD

                PreparedStatement ps = con.prepareStatement("INSERT INTO JUEGOS (UPC, DESCRIPCION) VALUES (?,?)");
                ps.setString(1, this.upc); 
                ps.setString(2, this.descripcion); 
                System.out.println(ps.toString());
                exito = ps.executeUpdate() > 0;
                con.close();
               
	    }
            catch (ClassNotFoundException | SQLException ex)
	    {
	    	ex.printStackTrace(System.out);
	    }     
	    return exito;
	}

        
}