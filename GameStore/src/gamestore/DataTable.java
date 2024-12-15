/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestore;

import javax.swing.JTable;
/**
 *
 * @author Marco Flores
 */
public class DataTable extends JTable
{
    	public  DataTable()
	{
		super();
	}
	
        @Override
	public boolean isCellEditable(int row, int column) 
	{
		return false;
	}
}

