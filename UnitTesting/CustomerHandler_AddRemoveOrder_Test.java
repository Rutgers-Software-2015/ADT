package Shared.UnitTesting;

import javax.swing.JOptionPane;

import Customer.CustomerGUI;

public class CustomerHandler_AddRemoveOrder_Test {
	
	static CustomerGUI testGUI;
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
		addOrders();
		
	}
	private static void addOrders() {
		int total_additions = (int)(Math.random() * 50);
		int randomItem = 0;
		for(int i = 0; i < total_additions; i++) {
			randomItem = (int)(Math.random() * testGUI.menuButtons.size());
			String a1 = testGUI.menuButtons.get(randomItem).getText();
			a1 = a1.replaceAll("<html>", "");
			a1 = a1.replaceAll("</html>", "");
			a1 = a1.replaceAll("<br>", "");
			a1 = a1.substring(0, a1.length()-1);
			testGUI.addOrder(a1, "");
			testGUI.tableOfOrders.repaint();
		}
	}
}
