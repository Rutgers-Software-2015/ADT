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
import Customer.CustomerGUI;

public class CustomerHandler_GetMenu_Test {
	
	static CustomerGUI testGUI;
	static DatabaseCommunicator testNet;
	static int total_additions = 25;
	static ArrayList<String> online;
	static ArrayList<String> offline;
	static ArrayList<String> log;
	static boolean[] flags;
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
		flags = new boolean[3];
		precondition();
		readTableMenu();
		readMenuButtons();
		verify();
		printLog();
	}
	private static void printLog() {
		
	}
	private static void verify() {
		
	}
	private static void readMenuButtons() {
		
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
					log.add("" + temp + " is an invalid menu item.");
				}
			}
			log.add("All menu items successfully parsed!");
			log.add("");
			testNet.disconnect();
		} catch(Exception e) {
			log.add("Something went wrong: ");
			log.add(e.toString());
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