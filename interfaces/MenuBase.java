package interfaces;

import java.sql.*;
import java.util.Scanner;

import models.Connector;

public abstract class MenuBase 
{
	public String productName, productNum, fromDept, toDept;
	public int select, productID, fromLoc, toLoc, qty;
	public boolean isNumber;
	public Scanner scanner = new Scanner(System.in);
	
	//db objects
	public static Connector c = new Connector();
	public static Connection con = c.conDB();
	public static Statement sqlState = null;
	public static Statement sqlQuery = null;
	public static ResultSet rows;
	
	abstract public int printMenu();
}
