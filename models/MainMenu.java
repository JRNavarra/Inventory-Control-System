package models;
import interfaces.MenuBase;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainMenu extends MenuBase 
{
	@Override
	public int printMenu()
	{
		// The main menu will allow the user to choose from a set of options that will
		// allow them to access the programs functionality.  Will include "Transfer Materials,"
		// "Log Finished Goods," "Document Scrap," "Check Inventory Levels" and "Exit Program."
		System.out.println("------------------Main Menu------------------");
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
			System.out.println("5. Exit Program");
			System.out.println("Please input your selection and press enter: ");
			
			if(scanner.hasNextInt())
			{
				select = scanner.nextInt();
				isNumber = true;
			}
			else
			{
				System.out.println("Your selection was invalid. Please try again.");
				isNumber = false;
				scanner.next();
			}
		}while(!(isNumber));
		
		// Return select for the switch
		if(select >= 1 && select <= 4)
		{
			return select;
		}
		else if(select == 5)
		{
			return 9;
		}
		else
		{
			//System.out.println(select + " is not a valid choice, try again.");
			return 0;
		}
	}
}
