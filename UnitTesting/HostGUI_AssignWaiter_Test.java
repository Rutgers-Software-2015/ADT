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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Shared.Communicator.DatabaseCommunicator;
import Busboy.BusboyGUI;
import Customer.CustomerGUI;
import Host.HostGUI;
@SuppressWarnings("unused")
public class HostGUI_AssignWaiter_Test {
	
	static HostGUI testGUI;
	static DatabaseCommunicator testComm;
	static ArrayList<String> log;
	static ArrayList<String> expectedOutcome1;
	static ArrayList<String> expectedOutcome2;
	static ArrayList<String> expectedOutcome3;
	static ArrayList<String> expectedOutcome4;
	static ArrayList<String> expectedOutcome5;
	static ArrayList<String> expectedOutcome6;
	static ArrayList<String> expectedOutcome7;
	static ArrayList<String> expectedOutcome8;
	static ArrayList<String> expectedOutcome9;
	static ArrayList<String> expectedOutcome10;
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
		performTestA();
	}
	
	public synchronized static void performTestA() {
		// Declarations
		testGUI = new HostGUI();
		testComm = new DatabaseCommunicator();
		log = new ArrayList<String>();
		expectedOutcome1 = new ArrayList<String>();
		expectedOutcome2 = new ArrayList<String>();
		expectedOutcome3 = new ArrayList<String>();
		expectedOutcome4 = new ArrayList<String>();
		expectedOutcome5 = new ArrayList<String>();
		expectedOutcome6 = new ArrayList<String>();
		expectedOutcome7 = new ArrayList<String>();
		expectedOutcome8 = new ArrayList<String>();
		expectedOutcome9 = new ArrayList<String>();
		expectedOutcome10 = new ArrayList<String>();
		
		finalOutcome = new ArrayList<String>();
		
		// Satisfying preconditions
		preconditionA();
		
		// Initial Table Conditions
		initialTableConditionsA(testGUI.Waiters_for_Table_1);
		initialTableConditionsA(testGUI.Waiters_for_Table_2);
		initialTableConditionsA(testGUI.Waiters_for_Table_3);
		initialTableConditionsA(testGUI.Waiters_for_Table_4);
		initialTableConditionsA(testGUI.Waiters_for_Table_5);
		initialTableConditionsA(testGUI.Waiters_for_Table_6);
		initialTableConditionsA(testGUI.Waiters_for_Table_7);
		initialTableConditionsA(testGUI.Waiters_for_Table_8);
		initialTableConditionsA(testGUI.Waiters_for_Table_9);
		initialTableConditionsA(testGUI.Waiters_for_Table_10);
		
		// Expected Outcome
		expectedOutcomeAssigningWaiters();
		
		// Changing status of tables
		ChangeTableStatusA();
		
		// New Customer Statuses
		newWaiterStatusesA(testGUI.Waiters_for_Table_1);
		newWaiterStatusesA(testGUI.Waiters_for_Table_2);
		newWaiterStatusesA(testGUI.Waiters_for_Table_3);
		newWaiterStatusesA(testGUI.Waiters_for_Table_4);
		newWaiterStatusesA(testGUI.Waiters_for_Table_5);
		newWaiterStatusesA(testGUI.Waiters_for_Table_6);
		newWaiterStatusesA(testGUI.Waiters_for_Table_7);
		newWaiterStatusesA(testGUI.Waiters_for_Table_8);
		newWaiterStatusesA(testGUI.Waiters_for_Table_9);
		newWaiterStatusesA(testGUI.Waiters_for_Table_10);
		
		// Comparing values
		//comparisonTestA();
		
		// Disconnecting
		disconnectingTestA();
		
		// Printing to text file
		printLogA();
		
		// Closing open windows
		closingWindowsA();
		
	}
	
	public static void preconditionA(){
		log.add("Performing Unit Test: removeDublicatesAndAdd() and CommunicateWaiterStatusChange();");
		log.add("");
		log.add("Connecting to database... ");
		testComm.connect("admin","gradMay17");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		log.add("Meeting preconditions (UnReserve tables)");
		log.add("Success!");
		log.add("");
	}
	
	public static void ChangeTableStatusA(){
		// Changing Waiter statuses (test)
		
		testGUI.removeDuplicatesAndAdd(testGUI.Waiters_for_Table_1,testGUI.WaiterList);
		testGUI.Waiters_for_Table_1.setSelectedItem(testGUI.WaiterList.getItemAt(0));
		testGUI.h.CommunicateWaiterStatusChange(testGUI.Waiters_for_Table_1,1);
		
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
	
	private static void printLogA() {
		String[] temp = new String[log.size()];
		for(int i = 0; i < log.size(); i++) {
			temp[i] = log.get(i);
		}
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/HostGUI_ReserveTable_Test_Result.txt");
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
		testGUI.h.setBorderBlue_Reserved(W_X,TableNumber);
		log.add("The database has been updated with the change to Table "+TableNumber+"!");
		log.add("Checking if change to database has been made successfully...");
		try {
			if(TableStatuses("R_Status").get(index).equals("Reserved")){
				log.add("SUBTEST "+TableNumber+"  = PASS");
				log.add("The changes to the database have been made successfully!"
						+ " Table "+TableNumber+" Reservation status has been set to Reserved!");
				log.add("");
			}
			if(TableStatuses("R_Status").get(index).equals("Not Reserved")){
				log.add("SUBTEST "+TableNumber+" = FAIL");
				log.add("The changes to the database have been made unsuccessfully!"
						+ " Table "+TableNumber+" Reservation status has been not set to Reserved!");
				log.add("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void initialStatusesOfHostforAssignWaiterTest(){
		for (int num = 1; num < 11; num++){
			testComm.update("UPDATE MAINDB.Table_Statuses SET EMP_ID_1 = '-' WHERE TABLE_ID = "+num+";");
			testComm.update("UPDATE MAINDB.Table_Statuses SET EMP_ID_2 = '-' WHERE TABLE_ID = "+num+";");
			testComm.update("UPDATE MAINDB.Table_Statuses SET EMP_ID_3 = '-' WHERE TABLE_ID = "+num+";");
			testComm.update("UPDATE MAINDB.Table_Statuses SET EMP_ID_4 = '-' WHERE TABLE_ID = "+num+";");
			testComm.update("UPDATE MAINDB.Table_Statuses SET EMP_ID_5 = '-' WHERE TABLE_ID = "+num+";");
		}
	}
	
	public static void comparisonTestA(){
		log.add("");
		log.add("Comparing results of test against expected outcome.... ");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		int counter = 0;
			for (int y = 0; y < 10; y++){
				try {
					if(TableStatuses("R_Status").get(y).toString().equals("Reserved")){
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
	public static void expectedOutcomeAssigningWaiters(){
		log.add("");
		log.add("EXPECTED OUTCOME:");
		for (int x = 0; x < 10 ; x++){
			log.add("Table "+(x+1)+" Assigned Waiters: ");
			log.add(testGUI.WaiterList.getItemAt(0).toString());
			log.add(testGUI.WaiterList.getItemAt(1).toString());
			log.add(testGUI.WaiterList.getItemAt(2).toString());
			log.add(testGUI.WaiterList.getItemAt(3).toString());
			log.add(testGUI.WaiterList.getItemAt(4).toString());
			log.add(testGUI.WaiterList.getItemAt(5).toString());

		}
	}
	public static void newWaiterStatusesA(JComboBox Waiters_at_tables){
		log.add("FINAL RESULT: Table customer statuses after change has been made: ");
		for (int x = 0; x < 11 ; x++){
			log.add("Table "+x+": ");
			log.add(Waiters_at_tables.getItemAt(0).toString());
			log.add(Waiters_at_tables.getItemAt(1).toString());
			log.add(Waiters_at_tables.getItemAt(2).toString());
			log.add(Waiters_at_tables.getItemAt(3).toString());
			log.add(Waiters_at_tables.getItemAt(4).toString());
			log.add(Waiters_at_tables.getItemAt(5).toString());
		}
	}
	public static void closingWindowsA(){
		java.awt.Window win[] = java.awt.Window.getWindows(); 
		for(int j=0;j<win.length;j++){ 
			win[j].dispose();
		}
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/HostGUI_ReserveTable_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void disconnectingTestA(){
		testComm.disconnect();
		testGUI.h.disconnect();
		testGUI.notification.close();
		testGUI.dispose();
	}
	
	public static void initialTableConditionsA(JComboBox Waiters_at_tables){
		
		// Initial table Statuses
		initialStatusesOfHostforAssignWaiterTest();
		
		log.add("Beginning to unoccupy all waiters at tables... "); 
		log.add("");
		log.add("Initial Table Conditions: ");
			for (int x = 0; x < 11 ; x++){
				log.add("Table "+x+": ");
				log.add(Waiters_at_tables.getItemAt(0).toString());
				log.add(Waiters_at_tables.getItemAt(1).toString());
				log.add(Waiters_at_tables.getItemAt(2).toString());
				log.add(Waiters_at_tables.getItemAt(3).toString());
				log.add(Waiters_at_tables.getItemAt(4).toString());
				log.add(Waiters_at_tables.getItemAt(5).toString());
			}
		
	}
}
	




