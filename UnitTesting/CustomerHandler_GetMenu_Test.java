package Shared.UnitTesting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Shared.ADT.MenuItem;
import Shared.Communicator.DatabaseCommunicator;
import Shared.Gradients.GradientButton;
import Customer.CustomerGUI;

public class CustomerHandler_GetMenu_Test {
	
	static CustomerGUI testGUI;
	static DatabaseCommunicator testNet;
	static int total_additions = 25;
	static ArrayList<String> online;
	static ArrayList<String> offline;
	static ArrayList<String> log;
	static boolean[] flags = {true, true, true};
	/*
	 * This test should do the following:
	 * 1) Ping the database for the valid menu items
	 * 2) Check the menu buttons in the GUI
	 * 3) Check that only the menu items that are valid are present in the buttons for the GUI.
	 */
	public static void main(String[] args) {
		performTest();
	}
	public synchronized static void performTest() {
		testGUI = new CustomerGUI();
		testNet = new DatabaseCommunicator();
		log = new ArrayList<String>();
		online = new ArrayList<String>();
		offline = new ArrayList<String>();
		
		try {
			precondition();
		} catch (Exception e) {
			log.add("Something went wrong.");
			log.add(e.toString());
			flags[0] = false;
		}
		readTableMenu();
		readMenuButtons();
		verify();
		testGUI.closeWindow();
		printLog();
	}
	private static void printLog() {
		String[] temp = new String[log.size()];
		for(int i = 0; i < log.size(); i++) {
			temp[i] = log.get(i);
		}
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/CustomerHandler_GetMenu_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String a : log) {
			filewrite.println(a);
		}
	}
	private static void verify() {
		log.add("Checking every button is mapped to every valid bit and vice versa...");
		boolean discrepency = offline.size() != online.size();
		if(!discrepency) {
			for(int i = 0; i < offline.size(); i++) {
				boolean contained = false;
				for(int  j = 0; j < online.size(); j++) {
					if(offline.get(i).equals(online.get(j))) {
						contained = true;
						log.add("Element [" + online.get(j) + "] was found in both lists!");
					}
				}
				if(!contained) {
					log.add("Something went wrong.");
					log.add("An element didn't match");
					flags[2] = false;
				}
			}
		} else {
			log.add("Something went wrong.");
			log.add("Elements do not match!!");
			flags[2] = false;
		}
		log.add("Finished verifying...");
		for(boolean b : flags) {
			if(!b) {
				log.add("TEST = FAIL");
				return;
			}
		}
		log.add("TEST = PASS");
	}
	private static void readMenuButtons() {
		try {
			log.add("Reading buttons from GUI...");
			for(GradientButton a : testGUI.menuButtons) {
				String temp = a.getText();
				temp = temp.replaceAll("<html>", "");
				temp = temp.replaceAll("</html>", "");
				temp = temp.replaceAll("<br>", "");
				temp = temp.substring(0, temp.length()-1);
				offline.add(temp);
				log.add(temp + " is a menu item in the GUI");
			}
		} catch (Exception e) {
			log.add("Something went wrong: ");
			log.add(e.toString());
			flags[1] = false;
		}
		log.add("");
	}
	private static void readTableMenu() {
		try {
			log.add("Reading from the menu...");
			ResultSet rs = testNet.tell("select * from MENU");
			rs.beforeFirst();
			while(rs.next() == true) {
				String temp = rs.getString("ITEM_NAME");
				if(rs.getInt("VALID") != 0) {
					online.add(temp);
					log.add("" + temp + " is a valid menu item.");
				} else {
					log.add("" + temp + " is NOT a valid menu item.");
				}
			}
			log.add("All menu items successfully parsed!");
			log.add("");
			testNet.disconnect();
		} catch(Exception e) {
			log.add("Something went wrong: ");
			log.add(e.toString());
			flags[0] = false;
		}
	}
	private static void precondition() {
		log.add("Performing Unit Test: GetMenu");
		log.add("Connecting to database...");
		testNet.connect("admin", "gradMay17");
		testNet.tell("use MAINDB;");
		log.add("Success!");
		log.add("");
	}
}