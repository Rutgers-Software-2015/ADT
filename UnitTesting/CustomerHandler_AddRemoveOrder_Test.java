package Shared.UnitTesting;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Shared.Communicator.DatabaseCommunicator;
import Customer.CustomerGUI;

public class CustomerHandler_AddRemoveOrder_Test {
	
	static CustomerGUI testGUI;
	static DatabaseCommunicator testNet;
	static int total_additions = 25;
	static ArrayList<String> log;
	static Thread w;
	static boolean doneAdd = false;
	/*
	 * This test should do the following:
	 * 1) Add orders based on button choices (up to 50 orders)
	 * 2) Remove orders based on recorded random rows
	 * 3) Check that the orders are all proper in the table
	 */
	public static void main(String[] args) {
		performTest();
	}
	public static void performTest() {
		testGUI = new CustomerGUI();
		testNet = new DatabaseCommunicator();
		log = new ArrayList<String>();
		w = new Thread();
		w.start();
		precondition();
		addOrders();
		if(doneAdd) {
			removeOrders();
			placeOrder();
			verify();
			testGUI.closeWindow();
			printLog();
		}
	}
	private static void placeOrder() {
		
	}
	private static void precondition() {
		log.add("Performing Unit Test: AddRemoveOrder");
		log.add("Connecting to database...");
		testNet.connect("admin", "gradMay17");
		testNet.tell("use MAINDB;");
		log.add("Meeting preconditions (clearing table for test)");
		testNet.update("delete from TABLE_ORDER;");
	}
	private synchronized static void removeOrders() {
		log.add("Beginning to remove random amount of orders: ");
		int total_removes = (int)(Math.random() * total_additions);
		int sizeTotal = testGUI.patron.TOTAL_QUANTITY;
		for(int i = 0; i < total_removes; i++) {
			int randomItem = (int)(testGUI.patron.TOTAL_ORDERS.size() - 1);
			boolean happens = testGUI.removeOrder(randomItem);
		}
		if(sizeTotal - total_removes == testGUI.patron.TOTAL_QUANTITY) {
			log.add("Successful performed removal of " + total_removes + " items.");
		}
	}
	private synchronized static void addOrders() {
		log.add("Beginning to add random amount of orders: ");
		int randomItem = 0;
		for(int i = 0; i < total_additions; i++) {
			try {
				w.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String statement = "";
			randomItem = (int)(Math.random() * testGUI.menuButtons.size());
			String a1 = testGUI.menuButtons.get(randomItem).getText();
			a1 = a1.replaceAll("<html>", "");
			a1 = a1.replaceAll("</html>", "");
			a1 = a1.replaceAll("<br>", "");
			a1 = a1.substring(0, a1.length()-1);
			boolean happens = testGUI.addOrder(a1, "");
		}
		if(total_additions == testGUI.patron.TOTAL_QUANTITY) {
			log.add("Successfully performed addition of " + total_additions + " items.");
		}
		doneAdd = true;
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
		for(int i = 0; i < a.size(); i++) {
			if(!a.equals(b)) {
				log.add("The database does not match...");
				testNet.disconnect();
				return;
			}
		}
		log.add("The orders were sent in the database successfully!");
		testNet.disconnect();
	}
	private static void printLog() {
		for(String a : log) {
			System.out.println(a);
		}
	}
}
