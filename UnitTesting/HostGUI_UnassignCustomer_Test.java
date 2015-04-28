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
	static ArrayList<String> expectedOutcome;
	static ArrayList<String> finalOutcome;
	
	/*
	 * This test should do the following:
	 * 
	 * 1) View the current status of all the tables (Customer(s) assigned to table or Customer(s) unassigned to table?)
	 * 2) Select a single table, and successfully unassign a customer from it (Change the table customer status to "Unoccupied")
	 * 3) Print out the new table status after the change has been made.
	 * 
	 */
	
	public static void main(String[] args){
		performTest2();
	}
	
	public synchronized static void performTest2() {
		// Declarations
		testGUI = new HostGUI();
		testComm = new DatabaseCommunicator();
		log = new ArrayList<String>();
		expectedOutcome = new ArrayList<String>();
		finalOutcome = new ArrayList<String>();
		
		// Satisfying preconditions
		precondition2();
		
		// Initial Table Conditions
		initialTableConditions2();
		
		// Expected Outcome
		expectedOutcomeCustomerUnassign2();
		
		// Changing status of tables
		ChangeTableStatus2();
		
		// New Customer Statuses
		newUnassignCustomerStatuses2();
		
		// Comparing values
		comparisonTest2();
		
		// Disconnecting
		disconnectingTest2();
		
		// Printing to text file
		printLog2();
		
		// Closing open windows
		closingWindows2();
		
	}
	
	public static void precondition2(){
		log.add("Performing Unit Test: updateTableUnoccupiedByCustomer");
		log.add("");
		log.add("Connecting to database... ");
		testComm.connect("admin","gradMay17");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		log.add("Meeting preconditions (assign customers to tables)");
		
		// Initial table Statuses
		initialStatusesOfHostforUnassignTest2();
		log.add("Success!");
		log.add("");
	}
	
	public static void ChangeTableStatus2(){
		
		// Changing customer statuses (test)
		changeCustomerStatusUnassigned(1,0);
		changeCustomerStatusUnassigned(2,1);
		changeCustomerStatusUnassigned(3,2);
		changeCustomerStatusUnassigned(4,3);
		changeCustomerStatusUnassigned(5,4);
		changeCustomerStatusUnassigned(6,5);
		changeCustomerStatusUnassigned(7,6);
		changeCustomerStatusUnassigned(8,7);
		changeCustomerStatusUnassigned(9,8);
		changeCustomerStatusUnassigned(10,9);
		
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
	
	private static void printLog2() {
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
	
	public static void changeCustomerStatusUnassigned(int TableNumber,int index){
		log.add("");
		log.add("Changing Table "+TableNumber+" customer status: ");
		log.add("");
		testGUI.h.updateTableUnoccupiedByCustomer(TableNumber);
		log.add("The database has been updated with the change to Table "+TableNumber+"!");
		log.add("Checking if change to database has been made successfully...");
		try {
			if(TableStatuses("C_Status").get(index).equals("Unoccupied")){
				log.add("SUBTEST "+TableNumber+"  = PASS");
				log.add("The changes to the database have been made successfully!"
						+ " Table "+TableNumber+" customer status has been set to unoccupied!");
				log.add("");
			}
			if(TableStatuses("C_Status").get(index).equals("Occupied")){
				log.add("SUBTEST "+TableNumber+" = FAIL");
				log.add("The changes to the database have been made unsuccessfully!"
						+ " Table "+TableNumber+" customer status has been not set to unoccupied!");
				log.add("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void initialStatusesOfHostforUnassignTest2(){
		for (int num = 1; num < 11; num++){
			testComm.update("UPDATE MAINDB.Table_Statuses SET C_Status = 'Occupied' WHERE TABLE_ID = "+num+";");
		}
	}
	
	public static void comparisonTest2(){
		log.add("");
		log.add("Comparing results of test against expected outcome.... ");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		int counter = 0;
			for (int y = 0; y < 10; y++){
				try {
					if(TableStatuses("C_Status").get(y).toString().equals("Unoccupied")){
						counter++;
					}
				} catch (SQLException e) {	
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (counter == 10){
				log.add("");
			log.add("EXPECTED RESULT MATCHES FINAL RESULT");
			log.add("");
			log.add("TEST = PASS");
				}
			else {
			log.add("");
			log.add("EXPECTED RESULT DOES NOT MATCH FINAL RESULT");
			log.add("");
			log.add("TEST = FAIL");
				}
			
			
	}
	public static void expectedOutcomeCustomerUnassign2(){
		log.add("");
		log.add("EXPECTED OUTCOME:");
		for (int x = 0; x < 10 ; x++){
			expectedOutcome.add(x,"Unoccupied");
			log.add("");
			log.add("Table " +(x+1)+": "+expectedOutcome.get(x).toString());
		}
	}
	public static void newUnassignCustomerStatuses2(){
		log.add("FINAL RESULT: Table customer statuses after change has been made: ");
		try {
			for(int i = 0; i < TableStatuses("C_Status").size();i++){
				log.add("\n");
				log.add("Table " +(i+1)+": " + TableStatuses("C_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void closingWindows2(){
		java.awt.Window win[] = java.awt.Window.getWindows(); 
		for(int j=0;j<win.length;j++){ 
			win[j].dispose();
		}
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/HostGUI_UnassignCustomer_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void disconnectingTest2(){
		testComm.disconnect();
		testGUI.h.disconnect();
		testGUI.notification.close();
		testGUI.dispose();
	}
	public static void initialTableConditions2(){
		log.add("Beginning to unaassign customers from table... "); 
		log.add("");
		log.add("Initial Table Conditions: ");
		try {
			for(int i = 0; i < TableStatuses("C_Status").size();i++){
				log.add("");
				log.add("Table " +(i+1)+": "+TableStatuses("C_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}