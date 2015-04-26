package Shared.UnitTesting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Shared.Communicator.DatabaseCommunicator;
import Customer.CustomerGUI;

public class CustomerHandler_AddRemoveOrder_Test {
	
	static CustomerGUI testGUI;
	static DatabaseCommunicator testNet;
	static int total_additions = 25;
	static ArrayList<String> log;
	static boolean[] flags;
	/*
	 * This test should do the following:
	 * 1) Add orders based on button choices (up to 50 orders)
	 * 2) Remove orders based on recorded random rows
	 * 3) Check that the orders are all proper in the table
	 */
	public static void main(String[] args) {
		performTest();
	}
	public synchronized static void performTest() {
		testGUI = new CustomerGUI();
		testNet = new DatabaseCommunicator();
		log = new ArrayList<String>();
		flags = new boolean[3];
		precondition();
		addOrders();
			removeOrders();
			verify();
			testGUI.closeWindow();
			printLog();
	}
	private static void precondition() {
		log.add("Performing Unit Test: AddRemoveOrder");
		log.add("Connecting to database...");
		testNet.connect("admin", "gradMay17");
		testNet.tell("use MAINDB;");
		log.add("Meeting preconditions (clearing table for test): ");
		testNet.update("delete from TABLE_ORDER;");
		log.add("Success!");
		log.add("");
	}
	private static void removeOrders() {
		log.add("Beginning to remove random amount of orders: ");
		int total_removes = (int)(Math.random() * total_additions);
		int sizeTotal = testGUI.patron.TOTAL_QUANTITY;
		for(int i = 0; i < total_removes; i++) {
			int randomItem = (int)(testGUI.patron.TOTAL_ORDERS.size() - 1);
			boolean happens = testGUI.removeOrder(randomItem);
			String result = happens ? "success!" : "failure.";
			log.add("Removing a randomItem from row " + randomItem + " and it was a... " + result);
		}
		if(sizeTotal - total_removes == testGUI.patron.TOTAL_QUANTITY) {
			log.add("Successful performed removal of " + total_removes + " items.");
			flags[1] = true;
		}
		log.add("");
	}
	private static void addOrders() {
		log.add("Beginning to add random amount of orders: ");
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
			log.add("Adding a random menu item with from button " + randomItem + " and it was a... " + result);
		}
		if(total_additions == testGUI.patron.TOTAL_QUANTITY) {
			log.add("Successfully performed addition of " + total_additions + " items.");
			flags[0] = true;
		}
		log.add("");
	}
	private static void verify() {
		log.add("Pushing contents to database, and comparing contents..."); 
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
				log.add("These orders do not match...");
				flags[2] = false;
			}
			log.add("The orders at index " + i + " match!");
		}
		log.add("The orders were sent in the database successfully!");
		log.add("");
		for(boolean st : flags) {
			if(!st) {
				log.add("TEST = FAIL");
				return;
			}
		}
		log.add("TEST = PASS");
		testNet.disconnect();
	}
	private static void printLog() {
		String[] temp = new String[log.size()];
		for(int i = 0; i < log.size(); i++) {
			temp[i] = log.get(i);
		}
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/CustomerHandler_AddRemoveOrder_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String a : log) {
			filewrite.println(a);
		}
	}
}
