package models;
import java.sql.SQLException;

import interfaces.MenuBase;

public class ScrapMenu extends MenuBase
{

	@Override
	public int printMenu() 
	{
		System.out.println("------------------Scrap Menu------------------");
		System.out.println("To log scrap, follow the prompts to remove ");
		System.out.println("materials from the database as they are discarded.");
		
		System.out.println("In which department is the scrap located?");
		System.out.println("1. PROD");
		System.out.println("2. WHA");
		System.out.println("3. WHB");
		
		if(scanner.hasNextInt())
		{
			select = scanner.nextInt();
		}
		else
		{
			System.out.println("Your selection was invalid. Please try again.");
			return 0;
		}
		
		if(select == 1)
		{
			allProd();

		}
		else if(select == 2)
		{
			allWha();
		}
		else if(select == 3)
		{
			allWhb();
		}
		else
		{
			System.out.println("Your selection was invalid. Please try again.");
			return 0;
		}
		
		System.out.println("Enter the location ID of the material that needs to be scrapped: ");
		
		if(scanner.hasNextInt())
		{
			fromLoc = scanner.nextInt();
		}
		else
		{
			System.out.println("Your selection was invalid. Please try again.");
			return 0;
		}
		
		try 
		{
			sqlQuery = con.createStatement();
			rows = sqlQuery.executeQuery("select * from " + c.getDB() + "location;");
			while(rows.next())
			{
				if(rows.getInt(1) == fromLoc)
				{
					fromDept = rows.getString(2);
					productID = rows.getInt(3);
					qty = rows.getInt(4);
				}
				
			}
		} catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
		
		try
		{
			sqlState = con.createStatement();
			String update = "update " + c.getDB() + "location "
					+ "set Product_ID = null, Quantity = null where LocationID = " + fromLoc + ";";
			sqlState.executeUpdate(update);
					
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			sqlState = con.createStatement();
			String insert = "insert into " + c.getDB() + "scrap(ProductID,quantity,Department) "
					+ "values(" + productID + "," + qty + ",'" + fromDept + "');";
			sqlState.executeUpdate(insert);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static void allProd()
	{
		try 
		{
			sqlState = con.createStatement();
			rows = sqlState.executeQuery("select * from " + c.getDB() + "allprod;");
			System.out.println("LocationID\tProduct_ID\tName\t\t\tQuantity\tUnit of Measure");
			
			while(rows.next())
			{
				System.out.println(rows.getInt(1) + "\t\t" + rows.getInt(2) + "\t\t" + rows.getString(3) + "\t\t" + rows.getInt(4) + "\t\t" + rows.getString(5));
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void allWha()
	{
		try
		{
			sqlState = con.createStatement();
			rows = sqlState.executeQuery("select * from " + c.getDB() + "allwha;");
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
	
	public static void allWhb()
	{
		try
		{
			sqlState = con.createStatement();
			rows = sqlState.executeQuery("select * from " + c.getDB() + "allwhb;");
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
}
