package Shared.UnitTesting.KitchenStaff;

/*
 * This test gets the contents of the Inventory available in the restaurant. It would display 
 *  each items name and the stock available for that item.
 *  @author Rahul Tandon
 *  @debugger Rahul Tandon
 *  @tester Rahul Tandon
 */
/*
 * Preconditions : There must exist some stock available in the restaurant.
 */
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import Shared.Communicator.DatabaseCommunicator;

public class KitchenStaff_GetInventory_Test 
{
	
	public static void main(String[] args) 
	{
	
		getInventoryTest();
	}
		public static void getInventoryTest()
		{
			PrintStream filewrite=null;
			try
			{
				try {
					filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff/KitchenStaff_getInventory_Test_Result.txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DatabaseCommunicator DBC = new DatabaseCommunicator();
				DBC.connect("admin", "gradMay17");
				DBC.tell("use MAINDB");
				ResultSet Inventory = DBC.tell("Select * from INVENTORY;");
				
				Inventory.beforeFirst();
			
				filewrite.println("Showing Inventory Available in the restaurant.");
				filewrite.println(" ");
			
				while(Inventory.next())
				{
						filewrite.println("Item_Name:"+ Inventory.getString("Item_Name"));
		
						filewrite.println("Amount:"+Inventory.getString("Amount"));

						filewrite.println(" ");
				}
				
				filewrite.println("TEST=PASS");
				DBC.disconnect();
			}
			catch(SQLException e)
			{
				filewrite.println("TEST=FAIL");
			}
			filewrite.close();
			
			try {
				Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff/KitchenStaff_getInventory_Test_Result.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}

}
