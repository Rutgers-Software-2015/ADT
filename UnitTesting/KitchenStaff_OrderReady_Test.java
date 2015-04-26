package Shared.UnitTesting;

import javax.swing.JFrame;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import KitchenStaff.*;

/*
 * @author Rahul Tandon
 * @tester Rahul Tandon
 * @debugger Rahul Tandon
 */

/*
 * Preconditions : There must exist some NOT_READY rows in the TABLE_ORDERS Database table. Function will exit if not enough rows,
 * 
 */


@SuppressWarnings("serial")
public class KitchenStaff_OrderReady_Test extends JFrame
{
	
	private KitchenStaff_OrderReady_Test parent = this;
	public static void main(String[] args)  
	{
		new KitchenStaff_OrderReady_Test();
	}
	KitchenStaff_OrderReady_Test() 
	{
		super();
		parent = this;
		try 
		{
			OrderReady();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void OrderReady() throws SQLException
	{
		KitchenStaffGUI testGUI=new KitchenStaffGUI();		
		PrintStream filewrite=null;
		
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff_OrderReady_Test_Result.txt");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try
		{
			
			try 
			{
				testGUI.FillWaitingOrders();
			}
			catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			int rowcount=testGUI.CurrentOrder.getRowCount();
			int randomrow=0;
			filewrite.println("The current row count is:"+rowcount);	
			if(rowcount==0)
			{
				filewrite.println("Preconditions arent met. Not enough rows.");
				filewrite.println("TEST=FAIL");
				testGUI.dispose();
				return;
			}
		
			//Pick a Random row
			Random r = new Random();
			filewrite.println("Selecting a random row.");
			try
			{
				randomrow = r.nextInt(rowcount) +0;
				filewrite.println("Now we will Ready the order at row="+randomrow);
			}
			catch( IllegalArgumentException a)
			{
			
			}
			String qs="";
			try
			{
				qs=(String) testGUI.CurrentOrder.getValueAt(randomrow, 2);
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				filewrite.println("No Orders Available");
				filewrite.println("TEST=FAIL");
				testGUI.end();
				filewrite.close();
				try {
					Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff_OrderReady_Test_Result.txt"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
	
			int q=Integer.parseInt(qs);
			KitchenStaffCommunicator c=new KitchenStaffCommunicator();
		
			// Readying the Order
		
		
			String[] temp=c.getTableOrders();
			int idloc= 7*(randomrow)+5;           //Gets location of MENUID
			int MenuID=Integer.parseInt(temp[idloc]); // Gets the MENUID value
			int rowid=Integer.parseInt(temp[7*(randomrow)+6]); //gets row id of order
		
			// Before readying the order
			ResultSet beforeready=c.tell("Select CURRENT_STATUS from TABLE_ORDER where rowid="+rowid);
			String beforeStatus=beforeready.getString("CURRENT_STATUS");
			filewrite.println("The status of the order before readying it is:"+beforeStatus);
	
			filewrite.println("Order Ready function executed.");
			
			//Get the ingredients and update inventory accordingly and ready the order.
			c.getMenuItemIngredientsandUpdate(MenuID,rowid,q);
		
			ResultSet afterready=c.tell("Select CURRENT_STATUS from TABLE_ORDER where rowid="+rowid);
			String afterStatus=afterready.getString("CURRENT_STATUS");
			filewrite.println("The status of the order after we executed the Order Ready function is:"+afterStatus);
			
			if(afterStatus.equals("READY"))
			{
				filewrite.println("The order's status is now READY so the:");
				filewrite.println("TEST=PASS");
			}
			else
			{
				filewrite.println("The order's status is not READY so the:");
				filewrite.println("TEST=FAIL");
			}
		
	
			testGUI.end();
			
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	     
		filewrite.close();
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff_OrderReady_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

