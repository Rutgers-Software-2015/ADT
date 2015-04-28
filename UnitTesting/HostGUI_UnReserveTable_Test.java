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
	static ArrayList<String> expectedOutcome;
	static ArrayList<String> finalOutcome;
	
	/*
	 * This test should do the following:
	 * 
	 * 1) View the current status of all the tables (Customer(s) assigned to table or Customer(s) unassigned to table?)
	 * 2) Select a single table, and successfully assign a customer to it (Change the table customer status to "Occupied")
	 * 3) Print out the new table status after the change has been made.
	 * 
	 */
	
	public static void main(String[] args){
		performTest();
	}
	
	public synchronized static void performTest() {
		// Declarations
		testGUI = new HostGUI();
		testComm = new DatabaseCommunicator();
		log = new ArrayList<String>();
		expectedOutcome = new ArrayList<String>();
		finalOutcome = new ArrayList<String>();
		
		// Satisfying preconditions
		precondition4();
		
		// Initial Table Conditions
		initialTableConditions4();
		
		// Expected Outcome
		expectedOutcomeReservation4();
		
		// Changing status of tables
		ChangeTableStatus4();
		
		// New Customer Statuses
		newReservationStatuses4();
		
		// Comparing values
		comparisonTest4();
		
		// Disconnecting
		disconnectingTest4();
		
		// Printing to text file
		printLog4();
		
		// Closing open windows
		closingWindows4();
		
	}
	
	public static void precondition4(){
		log.add("Performing Unit Test: setBorderBlack_Not_Reserved();");
		log.add("");
		log.add("Connecting to database... ");
		testComm.connect("admin","gradMay17");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		log.add("Meeting preconditions (Reserve tables)");
		
		// Initial table Statuses
		initialStatusesOfHostforUnReserveTest();
		log.add("Success!");
		log.add("");
	}
	
	public static void ChangeTableStatus4(){
		// Changing customer statuses (test)
		changeToReserved(1,0,testGUI.W_1);
		changeToReserved(2,1,testGUI.W_2);
		changeToReserved(3,2,testGUI.W_3);
		changeToReserved(4,3,testGUI.W_4);
		changeToReserved(5,4,testGUI.W_5);
		changeToReserved(6,5,testGUI.W_6);
		changeToReserved(7,6,testGUI.W_7);
		changeToReserved(8,7,testGUI.W_8);
		changeToReserved(9,8,testGUI.W_9);
		changeToReserved(10,9,testGUI.W_10);
		
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
	
	private static void printLog4() {
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
	
	public static void changeToReserved(int TableNumber,int index,JButton W_X){
		log.add("");
		log.add("Changing Table "+TableNumber+" reservation status: ");
		log.add("");
		testGUI.h.setBorderBlack_Not_Reserved(W_X,TableNumber);
		log.add("The database has been updated with the change to Table "+TableNumber+"!");
		log.add("Checking if change to database has been made successfully...");
		try {
			if(TableStatuses("R_Status").get(index).equals("Not Reserved")){
				log.add("SUBTEST "+TableNumber+"  = PASS");
				log.add("The changes to the database have been made successfully!"
						+ " Table "+TableNumber+" Reservation status has been set to Not Reserved!");
				log.add("");
			}
			if(TableStatuses("R_Status").get(index).equals("Reserved")){
				log.add("SUBTEST "+TableNumber+" = FAIL");
				log.add("The changes to the database have been made unsuccessfully!"
						+ " Table "+TableNumber+" Reservation status has been not set to Not Reserved!");
				log.add("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void initialStatusesOfHostforUnReserveTest(){
		for (int num = 1; num < 11; num++){
			testComm.update("UPDATE MAINDB.Table_Statuses SET R_Status = 'Reserved' WHERE TABLE_ID = "+num+";");
		}
	}
	
	public static void comparisonTest4(){
		log.add("");
		log.add("Comparing results of test against expected outcome.... ");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		int counter = 0;
			for (int y = 0; y < 10; y++){
				try {
					if(TableStatuses("R_Status").get(y).toString().equals("Not Reserved")){
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
	public static void expectedOutcomeReservation4(){
		log.add("");
		log.add("EXPECTED OUTCOME:");
		for (int x = 0; x < 10 ; x++){
			expectedOutcome.add(x,"Not Reserved");
			log.add("");
			log.add("Table " +(x+1)+": "+expectedOutcome.get(x).toString());
		}
	}
	public static void newReservationStatuses4(){
		log.add("FINAL RESULT: Table customer statuses after change has been made: ");
		try {
			for(int i = 0; i < TableStatuses("R_Status").size();i++){
				log.add("\n");
				log.add("Table " +(i+1)+": " + TableStatuses("R_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void closingWindows4(){
		java.awt.Window win[] = java.awt.Window.getWindows(); 
		for(int j=0;j<win.length;j++){ 
			win[j].dispose();
		}
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/HostGUI_UnReserveTable_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void disconnectingTest4(){
		testComm.disconnect();
		testGUI.h.disconnect();
		testGUI.notification.close();
		testGUI.dispose();
	}
	
	public static void initialTableConditions4(){
		log.add("Beginning to unreserve tables... "); 
		log.add("");
		log.add("Initial Table Conditions: ");
		try {
			for(int i = 0; i < TableStatuses("R_Status").size();i++){
				log.add("");
				log.add("Table " +(i+1)+": "+TableStatuses("R_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	