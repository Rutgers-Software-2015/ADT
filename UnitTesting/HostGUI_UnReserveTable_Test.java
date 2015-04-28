package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Shared.Communicator.DatabaseCommunicator;
import Busboy.BusboyGUI;
import Customer.CustomerGUI;
import Host.HostGUI;
@SuppressWarnings("unused")
public class HostGUI_UnReserveTable_Test {
	static HostGUI testGUI;
	static DatabaseCommunicator testComm;
	static ArrayList<String> log;
	
	
	/*
	 * This test should do the following:
	 * 
	 * 1) View the current status of all the tables (Table reserved for customers or not?)
	 * 2) Select a single table, and successfully reserve it for a customer.
	 * 3) Print out the new table status after the change has been made.
	 * 
	 */
	
	public static void man(String[] args){
		performTest();
	}
	
	public synchronized static void performTest() {
		testGUI = new HostGUI();
		testComm = new DatabaseCommunicator();
		log = new ArrayList<String>();
		precondition();
		ChangeTableStatusNotReserved();
		log.add("Closing open windows...");
		java.awt.Window win[] = java.awt.Window.getWindows(); 
		for(int j=0;j<win.length;j++){ 
			win[j].dispose();
		}
		printLog();
	}
	
	public static void precondition(){
		log.add("Performing Unit Test: updateTableUnoccupiedByCustomer");
		log.add("Connecting to databse... ");
		testComm.connect("admin","gradMay17");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_TableStatuses Order by Table_ID;");
		log.add("Meeting preconditions (unassign customer from table)");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 1;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 2;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 3;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 4;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 5;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 6;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 7;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 8;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 9;");
		testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = 10;");
		log.add("Success!");
		log.add("");
	}
	
	public static void ChangeTableStatusNotReserved(){
		log.add("Beginning to UnReserve table for customers: ");
		log.add("Initial Table Conditions: ");
		try {
			for(int i = 0; i < TableStatuses("R_Status").size();i++){
				log.add("\n");
				log.add(TableStatuses("R_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Table 1
		log.add("Changing Table 1 reservation status: ");
		testGUI.h.setBorderBlack_Not_Reserved(testGUI.W_1,1);
		log.add("The database has been updated with the change to Table 1!");
		log.add("Checking if change to database has been made successfully...");
		try {
			if(TableStatuses("R_Status").get(0).equals("Not Reserved")){
				log.add("TEST = PASS");
				log.add("The changes to the database have been made successfully!"
						+ "Table 1 customer status has been set to Not Reserved!");
				log.add("");
			}
			if(TableStatuses("R_Status").get(0).equals("Reserved")){
				log.add("TEST = FAIL");
				log.add("The changes to the database have been made unsuccessfully!"
						+ "Table 1 customer status has not been set to Not Reserved!");
				log.add("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Changing the rest of the table statuses
		changeStatusesNotReserved(2, 1,testGUI.W_2);
		changeStatusesNotReserved(3, 2,testGUI.W_3);
		changeStatusesNotReserved(4, 3,testGUI.W_4);
		changeStatusesNotReserved(5, 4,testGUI.W_5);
		changeStatusesNotReserved(6, 5,testGUI.W_6);
		changeStatusesNotReserved(7, 6,testGUI.W_7);
		changeStatusesNotReserved(8, 7,testGUI.W_8);
		changeStatusesNotReserved(9, 8,testGUI.W_9);
		changeStatusesNotReserved(10, 9,testGUI.W_10);
		
		log.add("Table Reservation statuses after change has been made: ");
		try {
			for(int i = 0; i < TableStatuses("R_Status").size();i++){
				log.add("\n");
				log.add(TableStatuses("R_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testComm.disconnect();

	}
	
	public static ArrayList<String> TableStatuses(String NameOfListFromTable_Statuses) throws SQLException{
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
			ResultSet rs = testComm.tell("Select * from Table_Statuses Order by Table_ID;");
			ArrayList<String> LIST = new ArrayList<String>();
			rs.beforeFirst();
			while(rs.next() == true){
				String temp = rs.getString(NameOfListFromTable_Statuses);
				LIST.add(temp);
			}
			System.out.println(NameOfListFromTable_Statuses + " ... done calculating");
			return LIST;
		}
	
	private static void printLog() {
		String[] temp = new String[log.size()];
		for(int i = 0; i < log.size(); i++) {
			temp[i] = log.get(i);
		}
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/HostGUI_UnReserveTable_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String a : log) {
			filewrite.println(a);
		}
	
	}
	
	public static void changeStatusesNotReserved(int tablenumber, int index,JButton W_){
		log.add("Changing Table "+tablenumber+" reservation status: ");
		testGUI.h.setBorderBlack_Not_Reserved(W_,tablenumber);
		log.add("The database has been updated with the change to Table "+tablenumber+"!");
		log.add("Checking if change to database has been made successfully...");
		try {
			if(TableStatuses("R_Status").get(index).equals("Not Reserved")){
				log.add("TEST = PASS");
				log.add("The changes to the database have been made successfully!"
						+ "Table "+tablenumber+" customer status has been set to Not Reserved!");
				log.add("");
			}
			if(TableStatuses("R_Status").get(index).equals("Reserved")){
				log.add("TEST = FAIL");
				log.add("The changes to the database have been made unsuccessfully!"
						+ "Table "+tablenumber+" customer status has not been set to Reserved!");
				log.add("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}