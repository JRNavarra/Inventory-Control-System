package models;
import java.sql.*;
import java.util.Scanner;

import interfaces.MenuBase;

public class AdminMenu extends MenuBase
{
	//class objects
	public static String username, email, password, productName, productNum, desc, units;
	public static int sel, admin, finishedGoods;
	public static Scanner scanner = new Scanner(System.in);
	public static boolean ok, valid;
	
	@Override
	public int printMenu() 
	{
		System.out.println("------------------Admin Menu------------------");
		System.out.println("Welcome to the Acme Inventory Control System");
		System.out.println("---------------------------------------------");
		
		// This do/while loop checks for valid integer input won't proceed until an int is 
		// given by the user.
		do
		{
			System.out.println("1. Transfer Materials");
			System.out.println("2. Log Finished Goods");
			System.out.println("3. Document Scrap");
			System.out.println("4. Check Inventory Levels");
			System.out.println("5. Add New User");
			System.out.println("6. Add New Product");
			System.out.println("7. Exit Program");
			System.out.println("Please input your selection and press enter: ");
			
			if(scanner.hasNextInt())
			{
				select = scanner.nextInt();
				isNumber = true;
				scanner.nextLine();
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				isNumber = false;
				scanner.next();
			}
		}while(!(isNumber));
		
		// Return select for the switch
		if(select >= 1 && select <= 6)
		{
			return select;
		}
		else if(select == 7)
		{
			return 9;
		}
		else
		{
			//System.out.println(select + " is not a valid choice, try again.");
			return 0;
		}
	}
	
	public static void addUser()
	{
		System.out.println("Enter username: ");
		username = scanner.nextLine();
		
		System.out.println("Enter email: ");
		email = scanner.nextLine();
		
		do
		{
			System.out.println("Enter password (Password must be at least 8 characters and\n"
					+ "contain at least one uppercase letter, one lowercase letter\n"
					+ "and one number): ");
			password = scanner.nextLine();

			//Validate password with regex
			valid = true;
			if(password.length() < 8)
			{
				System.out.println("Password must be at least 8 characters");
				valid = false;
			}
			
			String upper = "(.*[A-Z].*)";
			if(!password.matches(upper))
			{
				System.out.println("Password must contain at least one uppercase letter");
				valid = false;
			}
			
			String lower = "(.*[a-z].*)";
			if(!password.matches(lower))
			{
				System.out.println("Password must contain at least one lowercase letter");
				valid = false;
			}
			
			String num = "(.*[0-9].*)";
			if(!password.matches(num))
			{
				System.out.println("Password must contain at least one number");
				valid = false;
			}
			
			if(valid)
			{
				System.out.println("Password is valid");
				//valid = true;
			}
			
		}while(!valid);
		
		do
		{
			System.out.println("Is the user an admin?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			
			if(scanner.hasNextInt())
			{
				sel = scanner.nextInt();
				if(sel == 1)
				{
					admin = 1;
					ok = true;
				}
				else if(sel == 2)
				{
					admin = 0;
					ok = true;
				}
				else
				{
					System.out.println("Your selection was invalid. Please try again.");
					ok = false;
				}
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				ok = false;
			}
		
		}while(!ok);
		
		try
		{
			sqlState = con.createStatement();
			System.out.println("INSERT INTO " + c.getDB() + "user (username,email,passwod,AdminTag) "
								+ "VALUES (" + username + "," + email + "," + password + "," + 
								admin + ");");
			sqlState.executeUpdate("INSERT INTO " + c.getDB() + "user (username,email,password,AdminTag) "
									+ "VALUES ('" + username + "','" + email + "','" + password + "'," + 
									admin + ");");
		}catch(SQLException e)
		{
			System.out.println("Warning! No database connection available!");
			e.printStackTrace();
		}
	}

	public static void addProduct()
	{
		System.out.println("Enter product name: ");
		productName = scanner.nextLine();
		
		System.out.println("Enter product number: ");
		productNum = scanner.nextLine();
		
		System.out.println("Enter product description (do not use \', \" or \\): ");
		desc = scanner.nextLine();
		
		do
		{
			System.out.println("Is the product finished goods or raw material? ");
			System.out.println("1. Finished goods ");
			System.out.println("2. Raw material ");
			
			if(scanner.hasNextInt())
			{
				sel = scanner.nextInt();
				if(sel == 1)
				{
					finishedGoods = 1;
					ok = true;
				}
				else if(sel == 2)
				{
					finishedGoods = 0;
					ok = true;
				}
				else
				{
					System.out.println("Your selection was invalid. Please try again.");
					ok = false;
				}
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				ok = false;
			}
		}while(!ok);
		
		do
		{
			System.out.println("What is the unit of measure of the product?");
			System.out.println("1. Lbs");
			System.out.println("2. Cases");
			
			if(scanner.hasNextInt())
			{
				sel = scanner.nextInt();
				if(sel == 1)
				{
					units = "lbs";
					ok = true;
				}
				else if(sel == 2)
				{
					units = "cases";
					ok = true;
				}
				else
				{
					System.out.println("Your selection was invalid. Please try again.");
					ok = false;
				}
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				ok = false;
			}
		}while(!ok);
		
		try
		{
			sqlState = con.createStatement();
			System.out.println("INSERT INTO " + c.getDB() + "product (Name,ProductNumber,FinishedGoods,Description,UnitOfMeasure) "
								+ "VALUES (" + productName + "," + productNum + "," + finishedGoods + "," + 
								desc + "," + units + ");");
			sqlState.executeUpdate("INSERT INTO " + c.getDB() + "product (Name,ProductNumber,FinishedGoods,Description,UnitOfMeasure) "
									+ "VALUES ('" + productName + "','" + productNum + "'," + finishedGoods + ",'" + 
									desc + "','" + units + "');");
		}catch(SQLException e)
		{
			System.out.println("Warning! No database connection available!");
			e.printStackTrace();
		}	
	}
}
