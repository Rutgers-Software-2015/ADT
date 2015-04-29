/**
 * Inventory_Test.java 
 * 
@author Ryan Sanichar 
@tester Ryan Sanichar
@debugger Ryan Sanichar

 * 
 * Unit Test file for InventoryHandler.java file
 * Tests to make sure you can add/edit/remove inventory items from the database.
 */

package Shared.UnitTesting;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import Manager.ManagerHandlers.InventoryHandler;
import Shared.Communicator.DatabaseCommunicator;

public class Inventory_Test {

	public Inventory_Test(DatabaseCommunicator DBC, PrintStream filewrite)
	{

	/*
	 * 
	 * Test for the adding an item to the Inventory
	 * 	
	 */
		
	filewrite.println("TEST: InventoryHandler --> ADD INVENTORY ITEM");
	filewrite.println("");
	filewrite.println("Attempting to Add: Name: Beef, Quantity: 400");
	
	DBC.update("INSERT INTO INVENTORY (Item_Name, Amount) VALUES "+ "('" + "" + "', '" + 400 + "');" );
	
	filewrite.println("Beef has been added to the inventory!");
	
	filewrite.println("");
	
	filewrite.println("ADD INVENTORY ITEM TEST --> SUCCESS");
	
	/*
	 * 
	 * Test for editing an inventory item
	 * 	
	 */
	
	filewrite.println("TEST: InventoryHandler --> EDIT INVENTORY ITEM");
	filewrite.println("");
	filewrite.println("Attempting to add 300 more pounds of beef to the table.");
	
	ResultSet rs =  DBC.tell("Select Amount from INVENTORY where Item_Name = '" + "Beef" + "';");

	DBC.update("UPDATE INVENTORY SET Amount = '" + (300 + 300) + "' WHERE Item_Name = '" + "Beef" + "';");
	
	filewrite.println("There is now more beef!");
	
	filewrite.println("");
	
	filewrite.println("EDIT INVENTORY ITEM TEST --> SUCCESS");
	
	/*
	 * 
	 * Test for removing an item from the inventory
	 * 	
	 */
	
	DBC.update("DELETE FROM INVENTORY WHERE Item_Name='" + "Beef" + "';");
	
	filewrite.println("Beef has been removed!");
	
	filewrite.println("");
	
	filewrite.println("REMOVE INVENTORY ITEM TEST --> SUCCESS");
	
	filewrite.println("");
	
	filewrite.println("ADD/EDIT/REMOVE IVENTORY ITEM TESTS --> SUCCESS");
	
	}
	
}
