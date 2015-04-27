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
import Busboy.BusboyGUI;
import Customer.CustomerGUI;
import Host.HostGUI;
@SuppressWarnings("unused")
public class HostGUI_UnassignCustomer_Test {
	static HostGUI testGUI;
	static DatabaseCommunicator testComm;
	static ArrayList<String> log;
	
	
	/*
	 * This test should do the following:
	 * 
	 * 1) View the current status of all the tables (Customer(s) assigned to table or Customer(s) unassigned to table?)
	 * 2) Select a single table, and successfully unassign a customer from it (Change the table customer status to "Unoccupied")
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
		ChangeTableStatus();
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
		testComm.update("UPDATE MAINDB.Table_Statuses SET C_Status = 'Occupied' WHERE TABLE_ID = 1;");
		log.add("Success!");
		log.add("");
	}
	
	public static void ChangeTableStatus(){
		log.add("Beginning to assign customers to table: ");
		log.add("Initial Table Conditions: ");
		try {
			for(int i = 0; i < TableStatuses("C_Status").size();i++){
				log.add("\n");
				log.add(TableStatuses("C_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.add("Changing Table 1 customer status: ");
		testGUI.h.updateTableUnoccupiedByCustomer(1);
		log.add("The database has been updated with the change to Table 1!");
		log.add("Checking if change to database has been made successfully...");
		try {
			if(TableStatuses("C_Status").get(0).equals("Unoccupied")){
				log.add("TEST = PASS");
				log.add("The changes to the database have been made successfully!"
						+ "Table 1 customer status has been set to unoccupied!");
				log.add("");
			}
			if(TableStatuses("C_Status").get(0).equals("Occupied")){
				log.add("TEST = FAIL");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.add("Table customer statuses after change has been made: ");
		try {
			for(int i = 0; i < TableStatuses("C_Status").size();i++){
				log.add("\n");
				log.add(TableStatuses("C_Status").get(i).toString());
			
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
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/HostGUI_UnassignCustomer_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String a : log) {
			filewrite.println(a);
		}
	}
}
	



