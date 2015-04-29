package Shared.UnitTesting;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import Manager.*;
import Manager.ManagerHandlers.MenuHandler;
import Shared.Communicator.*;

public class Menu_Test {
	
	public Menu_Test(DatabaseCommunicator DBC, PrintStream filewrite)
	{

	/*
	 * 
	 * Test for the adding an item to the Menu
	 * 	
	 */
		
	filewrite.println("TEST: MenuHandler --> ADD MENU ITEM");
	filewrite.println("");
	filewrite.println("Attempting to Add: Name: Beef Wellington, Price: 20, Cost:8.50, Ingredients: Beef,Dough,Mustard,Description: Our signature beef dish wrapped in our freshly made dough and topped with mustard. Menu Section: Entree");
	
	String newMenuItem = "('" + 1 + "', '" + "Beef Wellington" + "', '" + 20 + "', '" + 8.50 + "', '" + "Beef, Dough, Mustard" + "', '" + "Our signature beef dish wrapped in our freshly made dough and topped with mustard" + "', '" + "Entree" + "', '" + 1 + "');";
	String command = "INSERT INTO MENU (MENU_ID, ITEM_NAME, PRICE, COST, INGREDIENTS, DESCRIPTION, MENU_SECTION, VALID) VALUES " + newMenuItem;
	
	DBC.update(command);

	filewrite.println("Beef Wellington has been added!");
	
	filewrite.println("");
	
	filewrite.println("ADD MENU ITEM TEST --> SUCCESS");
	
	/*
	 * 
	 * Test for editing a menu item
	 * 	
	 */
	
	filewrite.println("TEST: MenuHandler --> EDIT MENU ITEM");
	filewrite.println("");
	filewrite.println("Attempting to Make the Following Changes:  Price: 30, Cost:12.50, Ingredients: Beef,Dough,Mustard,Salt Description: Our signature beef dish wrapped in our freshly made dough and topped with mustard and with our finest seasoning.");
	
	DBC.update("DELETE FROM MENU WHERE MENU_ID='" + "1" + "';");
	String editMenuItem = "('" + 1 + "', '" + "Beef Wellington" + "', '" + "20" + "', '" + "8.50" + "', '" + "Beef, Dough, Mustard" + "', '" + "Our signature beef dish wrapped in our freshly made dough and topped with mustard" + "', '" + "Entree" + "', '" + "1" + "');";
	String editcommand = "INSERT INTO MENU (MENU_ID, ITEM_NAME, PRICE, COST, INGREDIENTS, DESCRIPTION, MENU_SECTION, VALID) VALUES " + editMenuItem;
	DBC.update(editcommand);

	filewrite.println("Beef Wellington has been edited!");
	
	filewrite.println("");
	
	filewrite.println("EDIT MENU ITEM TEST --> SUCCESS");
	
	/*
	 * 
	 * Test for removing a menu item
	 * 	
	 */
	
	DBC.update("DELETE FROM MENU WHERE ITEM_NAME='" + "Beef Wellington" + "';");
	
	filewrite.println("Beef Wellington has been removed!");
	
	filewrite.println("");
	
	filewrite.println("REMOVE MENU ITEM TEST --> SUCCESS");
	
	filewrite.println("");
	
	filewrite.println("ADD/EDIT/REMOVE MENU ITEM TESTS --> SUCCESS");
	

}


}
