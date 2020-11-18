import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
import java.util.Scanner;
import java.util.logging.*;

import interfaces.*;
import models.*;

public class Main {
	//Create a logger for the Main class
	static Logger logger1 = Logger.getLogger(Main.class.getName());	//Class logger 1
	static Logger logger2 = Logger.getLogger(Main.class.getName() + ".crit"); //Class logger 2
	
	// db objects
	public static Connector c = new Connector();
	public static Connection con = c.conDB();
	//public static Statement sqlState = null;
	public static ResultSet rows = null;
	
	// Variable declarations for menus and their selection values.
	public static Scanner scanner = new Scanner(System.in);
	public static int sel = 0;
	public static MainMenu mainMenu = new MainMenu();
	public static AdminMenu adminMenu = new AdminMenu();
	public static TransferMenu transferMenu = new TransferMenu();
	public static FinishedGoodsMenu fgMenu = new FinishedGoodsMenu();
	public static ScrapMenu scrapMenu = new ScrapMenu();
	public static CheckInvMenu invMenu = new CheckInvMenu();

	public static void main(String[] args) 
	{
		//Logger components
		//Two loggers to log to separate files critical issues and mundane issues.
		logger1.addHandler(new ConsoleHandler());		//Handler (Console)
		logger1.setLevel(Level.ALL);
		logger2.addHandler(new ConsoleHandler());
		logger2.setLevel(Level.WARNING);
		LogManager lm = LogManager.getLogManager();		//Log Manager
		FileHandler fh1;								//Handler (File) handles ALL logs (logger1)
		FileHandler fh2;								//Handler (File) handles WARNING (logger2)
		
		//The lowest level logs will be written when materials are moved from one place to another.
		try	{
			fh1 = new FileHandler("C:\\Users\\Jeremy\\Desktop\\School\\Bachelor's\\Advanced Java Programming\\Course Project\\Acme Inventory Control System\\Logs\\log%g.log", 10000, 15, true);
			fh1.setFormatter(new SimpleFormatter());	//Formatter
			fh1.setLevel(Level.ALL);
			logger1.addHandler(fh1);
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//The WARNING level logs will be written when scrap is created or a quantity of material is moved
		//that doesn't exist in the system.  This will signify an inventory discrepancy.
		try {
			fh2 = new FileHandler("C:\\Users\\Jeremy\\Desktop\\School\\Bachelor's\\Advanced Java Programming\\Course Project\\Acme Inventory Control System\\Logs\\critLog%g.log", 10000, 15, true);
			fh2.setFormatter(new SimpleFormatter());	//Formatter
			fh2.setLevel(Level.WARNING);
			logger2.addHandler(fh2);
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Print the login menu and save the username and password to variables. I did not make a separate 
		// class for this menu because I wanted to save the name and password in variables here in the 
		// main class. Eventually I want to store them in the database
		String user;
		String pword;
		boolean admin = false;
		System.out.println("------------------Login Menu-----------------");
		System.out.println("Please enter your username and press enter: ");
		System.out.println("---------------------------------------------");
		user = scanner.nextLine();
		System.out.println("Please enter the password for user " + user + " and press enter: ");
		pword = scanner.nextLine();
		
		// Check username and password for match in database
		try
		{
			boolean validUser = false;
			Statement sqlState = con.createStatement();
			String selectStuff = "Select * from " + c.getDB() + "user;";
			ResultSet rows = sqlState.executeQuery(selectStuff);
			while(rows.next())
			{
				if(rows.getString(1).equals(user))
				{
					if(rows.getString(3).equals(pword))
					{
						System.out.println("Welcome " + user + "!");
						validUser = true;
						if(rows.getInt(5) == 1)
						{
							admin = true;
						}
							
						break;
					}
					else
					{
						System.out.println("Incorrect password!");
						System.out.println("See the administrator!");
						sel = 1;
						validUser = true;
						break;
					}
				}
			}
			if(!validUser)
			{
				System.out.println("Invalid username!");
				sel = 1;
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Confirming the user input ***REMOVE AFTER DEBUGGING***
		// System.out.println("Test input: User = " + user + " Password = " + pword);
		
		// Once the user has been established, allow them to access the main menu
		while(sel == 0)
		{
			if(admin)
			{
				sel = adminMenu.printMenu();
			}
			else
			{
				sel = mainMenu.printMenu();
			}
			
			switch(sel)
			{
				case 1:
					sel = transferMenu.printMenu();
					if(sel == 0)
					{
						logger1.log(Level.INFO, "Materials have been transferred.");
					}
					else if(sel == 1)
					{
						logger1.log(Level.INFO, "Invalid user input!");
						sel = 0;
					}
					else if(sel == 2)
					{
						logger2.log(Level.WARNING, "Transfer problems! Try again.");
						sel = 0;
					}
					
					break;
					
				case 2:
					sel = fgMenu.printMenu();
					break;
					
				case 3:
					sel = scrapMenu.printMenu();
					break;
					
				case 4:
					sel = invMenu.printMenu();
					break;
					
				case 5:
					AdminMenu.addUser();
					sel = 0;
					break;
					
				case 6:
					AdminMenu.addProduct();
					sel = 0;
					break;
					
				case 9:
					System.out.println("Exiting...");
					System.exit(0);
					break;
				
				default:
					System.out.println("Your selection was invalid. Please try again.");
					sel = 0;
					break;
			}
		}
		System.out.println("Exiting...");
	}
}
