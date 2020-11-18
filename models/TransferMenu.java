package models;
import java.sql.*;
import interfaces.MenuBase;

public class TransferMenu extends MenuBase 
{
		
	@Override
	public int printMenu() 
	{
		System.out.println("------------------Transfer Menu------------------");
		System.out.println("This menu allows the user to transfer materials from");
		System.out.println("the warehouse into the production area and back when");
		System.out.println("the run is complete.");
		
		System.out.println("Please select the nature of the material transfer: ");
		System.out.println("1. From warehouse A to production area (WHA -> PROD)");
		System.out.println("2. From production area to warehouse A (PROD -> WHA)");
		
		if(scanner.hasNextInt())
		{
			select = scanner.nextInt();
			if(select == 1)
			{
				fromDept = "WHA";
				toDept = "PROD";
			}
			else if(select == 2)
			{
				fromDept = "PROD";
				toDept = "WHA";
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				return 1;
			}
		}
		else
		{
			System.out.println("Your selection was invalid. Please try again.");
			return 1;
		}
		
		if(fromDept.equals("WHA"))
		{
			whaInv();
			System.out.println("Enter the product ID of the material to transfer: ");
			
			if(scanner.hasNextInt())
			{
				productID = scanner.nextInt();
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				return 1;
			}
			
			System.out.println("Enter the location ID of the material you wish to transfer: ");
			
			if(scanner.hasNextInt())
			{
				fromLoc = scanner.nextInt();
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				return 1;
			}
			
			prodEmpty();
			System.out.println("Enter the location ID of an empty location: ");
			
			if(scanner.hasNextInt())
			{
				toLoc = scanner.nextInt();
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				return 1;
			}
			
		}
		else if(fromDept.equals("PROD"))
		{
			prodInv();
			System.out.println("Enter the product ID of the material to transfer: ");
			
			if(scanner.hasNextInt())
			{
				productID = scanner.nextInt();
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				return 1;
			}
			
			System.out.println("Enter the location ID of the material you wish to transfer: ");
			
			if(scanner.hasNextInt())
			{
				fromLoc = scanner.nextInt();
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				return 1;
			}
			
			whaEmpty();
			System.out.println("Enter the location ID of an empty location: ");
			
			if(scanner.hasNextInt())
			{
				toLoc = scanner.nextInt();
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				return 1;
			}
			
		}
		
		select = transfer(fromLoc, productID, toLoc);
		prodInv();
		whaInv();
		
		return select;
	}
	
	public static void prodInv ()
	{
		try
		{
			sqlState = con.createStatement();
			//String selectProd = "SELECT * FROM " + c.getDB() + "location WHERE Department = 'PROD';";
			String selectProd = "Select * from " + c.getDB() + "prodinventory;";
			rows = sqlState.executeQuery(selectProd);
			System.out.println("LocationID\tProduct_ID\tName\t\t\tQuantity\tUnit of Measure");
			
			while(rows.next())
			{
				System.out.println(rows.getInt(1) + "\t\t" + rows.getInt(2) + "\t\t" + rows.getString(3) + "\t\t" + rows.getInt(4) + "\t\t" + rows.getString(5));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void whaInv ()
	{
		try
		{
			sqlState = con.createStatement();
			//String selectProd = "SELECT * FROM " + c.getDB() + "location WHERE Department = 'WHA';";
			String selectProd = "Select * from " + c.getDB() + "whainventory;";
			rows = sqlState.executeQuery(selectProd);
			System.out.println("LocationID\tProduct_ID\tName\t\t\tQuantity\tUnit of Measure");
			
			while(rows.next())
			{
				System.out.println(rows.getInt(1) + "\t\t" + rows.getInt(2) + "\t\t" + rows.getString(3) + "\t\t" + rows.getInt(4) + "\t\t" + rows.getString(5));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void prodEmpty()
	{
		try
		{
			sqlState = con.createStatement();
			String selectProd = "SELECT * FROM " + c.getDB() + "location WHERE Department = 'PROD';";
			rows = sqlState.executeQuery(selectProd);
			System.out.println("LocationID\tDepartment\tProduct_ID\tQuantity");
			
			while(rows.next())
			{
				System.out.println(rows.getInt(1) + "\t\t" + rows.getString(2) + "\t\t" + rows.getInt(3) + "\t\t" + rows.getInt(4));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void whaEmpty()
	{
		try
		{
			sqlState = con.createStatement();
			String selectProd = "SELECT * FROM " + c.getDB() + "location WHERE Department = 'WHA';";
			rows = sqlState.executeQuery(selectProd);
			System.out.println("LocationID\tDepartment\tProduct_ID\tQuantity");
			
			while(rows.next())
			{
				System.out.println(rows.getInt(1) + "\t\t" + rows.getString(2) + "\t\t" + rows.getInt(3) + "\t\t" + rows.getInt(4));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static int transfer(int loc, int prod, int toLoc)
	{
		try 
		{
			boolean validLoc = false;
			sqlState = con.createStatement();
			rows = sqlState.executeQuery("select * from " + c.getDB() + "location;");
			
			while(rows.next())
			{
				if(rows.getInt(1) == loc)
				{
					validLoc = true;
					
					if(rows.getInt(3) == prod)
					{
						int quan = rows.getInt(4);
						
						String moveFrom = "update " + c.getDB() + "location "
								+ "set Product_ID = null, Quantity = null "
								+ "where LocationID = " + loc + ";";
						String moveTo = "update " + c.getDB() + "location "
								+ "set Product_ID = " + prod + ", Quantity = " + quan + " "
								+ "where LocationID = " + toLoc + ";";
						
						Statement transferFrom = con.createStatement();
						transferFrom.executeUpdate(moveFrom);
						Statement transferTo = con.createStatement();
						transferTo.executeUpdate(moveTo);
						
						System.out.println("Transfer complete.");
						return 0;
					}
					else
					{
						System.out.println("Invalid product at this location.");
					}
				}
			}
			if(!validLoc)
			{
				System.out.println("Invalid location.");
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return 2;
	}
}
