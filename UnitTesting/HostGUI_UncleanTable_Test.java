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

public class HostGUI_UncleanTable_Test {
	static HostGUI testGUI;
	static DatabaseCommunicator testComm;
	static ArrayList<String> log;
	static ArrayList<String> expectedOutcome;
	static ArrayList<String> finalOutcome;
	
	/**
	 * This test should do the following:
	 * 
	 * 1) View the current status of all the tables (Customer(s) assigned to table or Customer(s) unassigned to table?)
	 * 2) Select a single table, and successfully assign a customer to it (Change the table customer status to "Occupied")
	 * 3) Print out the new table status after the change has been made.
	 * 
	 * @author David Arakelyan
	 * @debugger David Arakelyan
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
		precondition5();
		
		// Initial Table Conditions
		initialTableConditions5();
		
		// Expected Outcome
		expectedOutcomeReservation5();
		
		// Changing status of tables
		ChangeTableStatus5();
		
		// New Customer Statuses
		newTableStatuses5();
		
		// Comparing values
		comparisonTest5();
		
		// Disconnecting
		disconnectingTest5();
		
		// Printing to text file
		printLog5();
		
		// Closing open windows
		closingWindows5();
		
	}
	
	public static void precondition5(){
		log.add("Performing Unit Test: markTableAsUnClean();");
		log.add("");
		log.add("Connecting to database... ");
		testComm.connect("admin","gradMay17");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		log.add("Meeting preconditions (Clean Tables)");
		
		// Initial table Statuses
		initialStatusesOfHostforUncleanTablesTest();
		log.add("Success!");
		log.add("");
	}
	
	public static void ChangeTableStatus5(){
		// Changing customer statuses (test)
		markTableAsUnClean(1,0,testGUI.Table1);
		markTableAsUnClean(2,1,testGUI.Table2);
		markTableAsUnClean(3,2,testGUI.Table3);
		markTableAsUnClean(4,3,testGUI.Table4);
		markTableAsUnClean(5,4,testGUI.Table5);
		markTableAsUnClean(6,5,testGUI.Table6);
		markTableAsUnClean(7,6,testGUI.Table7);
		markTableAsUnClean(8,7,testGUI.Table8);
		markTableAsUnClean(9,8,testGUI.Table9);
		markTableAsUnClean(10,9,testGUI.Table10);
		
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
	
	private static void printLog5() {
		String[] temp = new String[log.size()];
		for(int i = 0; i < log.size(); i++) {
			temp[i] = log.get(i);
		}
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/HostGUI_UnCleanTable_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String a : log) {
			filewrite.println(a);
		}
	}
	
	public static void markTableAsUnClean(int TableNumber,int index,JButton TableID){
		log.add("");
		log.add("Changing Table "+TableNumber+" status (clean or unclean): ");
		log.add("");
		testGUI.h.markTableAsUnClean(TableNumber);
		testGUI.TableStatusIndicatorFinal(TableID);
		log.add("The database has been updated with the change to Table "+TableNumber+"!");
		log.add("Checking if change to database has been made successfully...");
		try {
			if(TableStatuses("T_Status").get(index).equals("Unclean")){
				log.add("SUBTEST "+TableNumber+"  = PASS");
				log.add("The changes to the database have been made successfully!"
						+ " Table "+TableNumber+" Table status has been set to Unclean!");
				log.add("");
			}
			if(TableStatuses("T_Status").get(index).equals("Clean")){
				log.add("SUBTEST "+TableNumber+" = FAIL");
				log.add("The changes to the database have been made unsuccessfully!"
						+ " Table "+TableNumber+" Table status has been not set to Unclean!");
				log.add("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void initialStatusesOfHostforUncleanTablesTest(){
		for (int num = 1; num < 11; num++){
			testComm.update("UPDATE MAINDB.Table_Statuses SET T_Status = 'Clean' WHERE TABLE_ID = "+num+";");
		}
	}
	
	public static void comparisonTest5(){
		log.add("");
		log.add("Comparing results of test against expected outcome.... ");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		int counter = 0;
			for (int y = 0; y < 10; y++){
				try {
					if(TableStatuses("T_Status").get(y).toString().equals("Unclean")){
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
	public static void expectedOutcomeReservation5(){
		log.add("");
		log.add("EXPECTED OUTCOME:");
		for (int x = 0; x < 10 ; x++){
			expectedOutcome.add(x,"Unclean");
			log.add("");
			log.add("Table " +(x+1)+": "+expectedOutcome.get(x).toString());
		}
	}
	public static void newTableStatuses5(){
		log.add("FINAL RESULT: Table statuses after change has been made: ");
		try {
			for(int i = 0; i < TableStatuses("T_Status").size();i++){
				log.add("\n");
				log.add("Table " +(i+1)+": " + TableStatuses("T_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void closingWindows5(){
		java.awt.Window win[] = java.awt.Window.getWindows(); 
		for(int j=0;j<win.length;j++){ 
			win[j].dispose();
		}
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/HostGUI_UnCleanTable_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void disconnectingTest5(){
		testComm.disconnect();
		testGUI.h.disconnect();
		testGUI.notification.close();
		testGUI.dispose();
		System.exit(0);
	}
	
	public static void initialTableConditions5(){
		log.add("Beginning to mark tables as clean... "); 
		log.add("");
		log.add("Initial Table Conditions: ");
		try {
			for(int i = 0; i < TableStatuses("T_Status").size();i++){
				log.add("");
				log.add("Table " +(i+1)+": "+TableStatuses("T_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	