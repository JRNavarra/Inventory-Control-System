package models;
import java.sql.*;
import interfaces.MenuBase;

public class FinishedGoodsMenu extends MenuBase
{
	
	@Override
	public int printMenu() 
	{
		System.out.println("------------------Finished Goods Menu------------------");
		System.out.println("To log finished goods into the system, enter the product");
		System.out.println("number and then the quantity of cases. You must then select");
		System.out.println("a location to store the product.");
		
		System.out.println("Enter the product number and press enter: ");
		productNum = scanner.nextLine();
		System.out.println(productNum); // input validation
		
		do
		{
			System.out.println("Enter the quantity of cases and press enter: ");
			if(scanner.hasNextInt())
			{
				qty = scanner.nextInt();
				System.out.println(qty); // input validation
				isNumber = true;
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				isNumber = false;
				scanner.next();
			}
		}while(!isNumber);
		
		do
		{
			System.out.println("Enter a location to store the finished goods: ");
			if(scanner.hasNextInt())
			{
				toLoc = scanner.nextInt();
				System.out.println(toLoc);
				isNumber = true;
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				isNumber = false;
				scanner.next();
			}
		}while(!isNumber);
		
		do
		{
			System.out.println(qty + " cases of product number " + productNum + " will be transferred");
			System.out.println("to storage location " + toLoc + ".");
			System.out.println("Is this correct?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			
			if(scanner.hasNext())
			{
				select = scanner.nextInt();
				isNumber = true;
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
			}
		}while(!isNumber || select < 1 || select > 2);
		
		if(select == 1)
		{
			boolean valid = false;
			// Add the product to the database once the db is populated
			try
			{
				sqlQuery = con.createStatement();
				
				String allProd = "SELECT * FROM " + c.getDB() + "product;";
				rows = sqlQuery.executeQuery(allProd);
				while(rows.next())
				{
					if(rows.getString(3).equals(productNum))
					{
						productID = rows.getInt(1);
						productName = rows.getString(2);
						valid = true;
					}
				}
				if(!valid)
				{
					System.out.println("Invalid product number!");
				}
				else
				{
					Statement sqlQuery2 = con.createStatement();
					//sqlState = con.createStatement();
					String allLoc = "SELECT * FROM " + c.getDB() + "location;";
					rows = sqlQuery2.executeQuery(allLoc);
					while(rows.next())
					{
						if(rows.getInt(1) == toLoc)
						{
							sqlState = con.createStatement();
							sqlState.executeUpdate("UPDATE " + c.getDB() + "location "
									+ "SET Product_ID = " + productID + ", Quantity = " + qty
									+ " WHERE LocationID = " + toLoc + ";");
							System.out.println(qty + " cases of " + productName + " have been moved to " + toLoc + ".");
						}
					}
				}
				
				
			}catch(SQLException e)
			{
				System.out.println("Warning! No database connection available!");
				e.printStackTrace();
			}catch(NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Please try again.");
		}
		
		return 0;
	}
	
}
