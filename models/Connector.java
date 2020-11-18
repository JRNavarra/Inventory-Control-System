package models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector
{
	// Initialize database objects
	private String dburl = "jdbc:mysql://localhost:3306";
	Connection con = null;
	String dbName = "acme_inventory_control_system.";
	
	// Constructor
	public Connector()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dburl, "root", "Qwerty123");
			System.out.println("Connected...");
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Warning! No database connection available!");
		}
	}
	
	public Connection conDB()
	{
		return con;
	}
	
	public String getDB()
	{
		return dbName;
	}
	
}