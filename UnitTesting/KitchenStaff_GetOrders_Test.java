package Shared.UnitTesting.KitchenStaff;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Shared.Communicator.DatabaseCommunicator;

/*
 * This test gets the NOT READY orders in our database and prints out the TABLEID,
 *  ItemName, Quantity, Special Instructions and Current Status.
 *  @author Rahul Tandon
 *  @debugger Rahul Tandon
 *  @tester Rahul Tandon
 */

/*
 * Preconditions : There must exist some NOT_READY rows in the TABLE_ORDERS Database table. Function will exit if not enough rows,
 * 
 */
public class KitchenStaff_GetOrders_Test
{
	public static void main(String[] args) 
	{
	
		getOrdersTest();
	}
	public static void getOrdersTest()
	{
		PrintStream filewrite = null;
		try 
		{
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff/KitchenStaff_getOrders_Test_Result.txt");
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			
			DatabaseCommunicator DBC = new DatabaseCommunicator();
			DBC.connect("admin", "gradMay17");
			DBC.tell("use MAINDB");
			ResultSet Orders = DBC.tell("Select * from TABLE_ORDER;");
			
			Orders.beforeFirst();
		
			filewrite.println("Showing Table Orders that need to be completed:");
			filewrite.println(" ");
		
			while(Orders.next())
			{
				if(Orders.getString("CURRENT_STATUS") != null)
				{
					if(Orders.getString("CURRENT_STATUS").equals("NOT READY"))
					{
							filewrite.println("Table ID:"+ Orders.getString("TABLE_ID"));
	
							filewrite.println("MenuItem:"+Orders.getString("ITEM_NAME"));

							filewrite.println("Quantity:"+Orders.getString("QUANTITY"));

							filewrite.println("Special Instructions:"+Orders.getString("SPEC_INSTR"));
							filewrite.println("The Current Status:"+Orders.getString("CURRENT_STATUS"));
							filewrite.println(" ");
					}
				}
			}
			
			DBC.disconnect();
			filewrite.println("All the orders that haven't been completed should be displayed.");
			filewrite.println(" ");
			filewrite.println("TEST=PASS");
		}
		catch(SQLException e)
		{
			filewrite.println("TEST=FAIL");
		}
		filewrite.close();
		
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff/KitchenStaff_getOrders_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
}
