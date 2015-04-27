package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Shared.Communicator.DatabaseCommunicator;
import Customer.CustomerGUI;

public class Customer_KitchenStaff_IntegrationTest {
	
	 CustomerGUI testGUI;
	 DatabaseCommunicator testNet;
	 int total_additions = 25;
	 PrintStream filewrite;
	 boolean[] flags;
	 KitchenStaff_GetOrders_Test2 ks;
	/*
	 * This test should do the following:
	 * 1) Add orders based on button choices (up to 50 orders)
	 * 2) Remove orders based on recorded random rows
	 * 3) Check that the orders are all proper in the table
	 */
	public static void main(String[] args) {
		Customer_KitchenStaff_IntegrationTest b = new Customer_KitchenStaff_IntegrationTest();
		b.performTest();
	}
	public  void performTest() {
		testGUI = new CustomerGUI();
		testNet = new DatabaseCommunicator();
		flags = new boolean[3];
		ks = new KitchenStaff_GetOrders_Test2();
		filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/Customer_KitchenStaff_Integration_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		precondition();
		ks.getOrdersTest();
		addOrders();
		removeOrders();
		verify();
		testGUI.closeWindow();
		ks.getOrdersTest();
		printLog();
	}
	private  void precondition() {
		filewrite.println("Performing Unit Test: AddRemoveOrder");
		filewrite.println("Connecting to database...");
		testNet.connect("admin", "gradMay17");
		testNet.tell("use MAINDB;");
		filewrite.println("Meeting preconditions (clearing table for test): ");
		testNet.update("delete from TABLE_ORDER;");
		filewrite.println("Success!");
		filewrite.println("");
	}
	private  void removeOrders() {
		filewrite.println("Beginning to remove random amount of orders: ");
		int total_removes = (int)(Math.random() * total_additions);
		int sizeTotal = testGUI.patron.TOTAL_QUANTITY;
		for(int i = 0; i < total_removes; i++) {
			int randomItem = (int)(testGUI.patron.TOTAL_ORDERS.size() - 1);
			boolean happens = testGUI.removeOrder(randomItem);
			String result = happens ? "success!" : "failure.";
			filewrite.println("Removing a randomItem from row " + randomItem + " and it was a... " + result);
		}
		if(sizeTotal - total_removes == testGUI.patron.TOTAL_QUANTITY) {
			filewrite.println("Successful performed removal of " + total_removes + " items.");
			flags[1] = true;
		}
		filewrite.println("");
	}
	private  void addOrders() {
		filewrite.println("Beginning to add random amount of orders: ");
		int randomItem = 0;
		for(int i = 0; i < total_additions; i++) {
			String statement = "";
			randomItem = (int)(Math.random() * testGUI.menuButtons.size());
			String a1 = testGUI.menuButtons.get(randomItem).getText();
			a1 = a1.replaceAll("<html>", "");
			a1 = a1.replaceAll("</html>", "");
			a1 = a1.replaceAll("<br>", "");
			a1 = a1.substring(0, a1.length()-1);
			boolean happens = testGUI.addOrder(a1, "");
			String result = happens? "success!" : "failure.";
			filewrite.println("Adding a random menu item with from button " + randomItem + " and it was a... " + result);
		}
		if(total_additions == testGUI.patron.TOTAL_QUANTITY) {
			filewrite.println("Successfully performed addition of " + total_additions + " items.");
			flags[0] = true;
		}
		filewrite.println("");
	}
	private  void verify() {
		filewrite.println("Pushing contents to database, and comparing contents..."); 
		DefaultTableModel dft = (DefaultTableModel) testGUI.tableOfOrders.getModel();
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		for(int j = 0; j < testGUI.patron.TOTAL_ORDERS.size(); j++) {
			String c = "" + dft.getValueAt(j, 0) + " " + dft.getValueAt(j, 3);
			a.add(c);
		}
		testGUI.placeOrder();
		ResultSet rs = testNet.tell("select * from TABLE_ORDER");
		
		try {
			rs.beforeFirst();
			while(rs.next() == true) {
				b.add(rs.getString("ITEM_NAME") + " " + rs.getInt("QUANTITY"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		flags[2] = true;
		for(int i = 0; i < a.size(); i++) {
			if(!a.equals(b)) {
				filewrite.println("These orders do not match...");
				flags[2] = false;
			}
			filewrite.println("The orders at index " + i + " match!");
		}
		filewrite.println("The orders were sent in the database successfully!");
		filewrite.println("");
		for(boolean st : flags) {
			if(!st) {
				filewrite.println("TEST = FAIL");
				return;
			}
		}
		filewrite.println("TEST = PASS");
		testNet.disconnect();
	}
	private  void printLog() {
		filewrite.close();
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/Customer_KitchenStaff_Integration_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class KitchenStaff_GetOrders_Test2
	{
		
		public void getOrdersTest()
		{
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
			
			
			
		
		}
	}

}
