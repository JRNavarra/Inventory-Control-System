package models;
import java.sql.SQLException;

import interfaces.MenuBase;

public class CheckInvMenu extends MenuBase
{

	@Override
	public int printMenu() 
	{
		System.out.println("------------------Check Inventory Menu------------------");
		System.out.println("Check the inventory levels in the production area, ");
		System.out.println("warehouse A or warehouse B or check the scrap log.");
		System.out.println("1. Production");
		System.out.println("2. Warehouse A");
		System.out.println("3. Warehouse B");
		System.out.println("4. Scrap log");
		
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
			try
			{
				sqlState = con.createStatement();
				rows = sqlState.executeQuery("select * from " + c.getDB() + "allProd;");
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
		else if(select == 2)
		{
			try
			{
				sqlState = con.createStatement();
				rows = sqlState.executeQuery("select * from " + c.getDB() + "allWha;");
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
		else if(select == 3)
		{
			try
			{
				sqlState = con.createStatement();
				rows = sqlState.executeQuery("select * from " + c.getDB() + "allWhb;");
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
		else if(select == 4)
		{
			try
			{
				sqlState = con.createStatement();
				rows = sqlState.executeQuery("select * from " + c.getDB() + "allScrap;");
				System.out.println("ScrapID\t\tDepartment\tProduct_ID\tName\t\t\tQuantity\tUnit of Measure\t\tTime");
				
				while(rows.next())
				{
					System.out.println(rows.getInt(1) + "\t\t" + rows.getString(2) + "\t\t" + rows.getInt(3) + "\t\t" + rows.getString(4) + "\t\t" + rows.getInt(5) + "\t\t" + rows.getString(6) + "\t\t\t" + rows.getTimestamp(7));
				}
				
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Your selection was invalid. Please try again.");
			return 0;
		}
		
		return 0;
	}
	
}
