package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import Shared.Communicator.DatabaseCommunicator;
import Busboy.BusboyGUI;
import Customer.CustomerGUI;
@SuppressWarnings("unused")
public class BusboyGUI_CleanTable_Test {
	static BusboyGUI testGUI;
	static DatabaseCommunicator testComm;
	static ArrayList<String> log;
	static ArrayList<String> initialTableStatuses;
	static ArrayList<String> expectedOutcome;
	
	
	
	/**
	 * This test should do the following:
	 * 
	 * 1) The tables will initially be set to Unclean
	 * 2) The busboy will set all tables to clean
	 * 
	 * @author David Arakelyan
	 * @debugger David Arakelyan
	 * 
	 */
	
	public static void main(String[] args){
		performTest();
	}
	
	public static void performTest() {
		expectedOutcome = new ArrayList<String>();
		testGUI = new BusboyGUI();
		testComm = new DatabaseCommunicator();
		log = new ArrayList<String>();
		
		// Preconditions: Connecting to the database and initial statuses
		precondition();
		
		// Making the initial table status changes
		initialTableStatus();
		
		// Creating more initial conditions
		setSelectedFalse();
		
		// Expected outcomes
		expectedOutcomeBusboy();
		
		// Changes to table Statuses
		TableStatusesBusboy_Changes();
		
		// New statuses
		newTableStatusesBusboy();
		
		// Comparison Test
		comparisonTest();
		
		// Print to log
		printLog();
		
		// Disconnect from the database
		disconnectingTestB();
		
		// Close all windows
		closingWindowsBusboy();
		
		// Close the system
		System.exit(0);
		
		
	}
	
	public static void precondition(){
		log.add("Performing Unit Test: markTableAsCLean");
		log.add("Connecting to database... ");
		testComm.connect("admin","gradMay17");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		log.add("Meeting preconditions (connecting to the database)");
		setTablesToUncleanInitial();
		log.add("Success!");
		log.add("");
		System.out.println("Precondition done");
	}
	
	public static void initialTableStatus(){
		log.add("Beginning to set tables as clean... ");
		log.add("");
		log.add("Initial Table Conditions: ");
		try {
			for(int i = 0; i < TableStatuses("T_Status").size();i++){
				log.add("");
				log.add("Table "+(i+1)+": "+TableStatuses("T_Status").get(i).toString());
				
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Initial Table Statuses done");

	}
	
	public static void setSelectedFalse(){
		testGUI.Table1.setSelected(false);
		testGUI.Table2.setSelected(false);
		testGUI.Table3.setSelected(false);
		testGUI.Table4.setSelected(false);
		testGUI.Table6.setSelected(false);
		testGUI.Table7.setSelected(false);
		testGUI.Table8.setSelected(false);
		testGUI.Table9.setSelected(false);
		testGUI.Table10.setSelected(false);
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
			System.out.println("ArrayList done");
			return LIST;
		}
	
	private static void printLog() {
		String[] temp = new String[log.size()];
		for(int i = 0; i < log.size(); i++) {
			temp[i] = log.get(i);
		}
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/BusboyGUI_CleanTable_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String a : log) {
			filewrite.println(a);
		}
		System.out.println("PrintLog done");
	}
	public static void setTablesToUncleanInitial(){
		for (int count = 1; count < 11; count++){
			testComm.update("UPDATE MAINDB.Table_Statuses SET T_Status = 'Unclean' WHERE TABLE_ID = +"+count+";");
		}
		System.out.println("SetTablesToUncleanInitial done");
	}
	
	public static void TableStatusesBusboy_Changes(){
		changingTableStatuses(1, 0,testGUI.Table1);
		changingTableStatuses(2, 1,testGUI.Table2);
		changingTableStatuses(3, 2,testGUI.Table3);
		changingTableStatuses(4, 3,testGUI.Table4);
		changingTableStatuses(5, 4,testGUI.Table5);
		changingTableStatuses(6, 5,testGUI.Table6);
		changingTableStatuses(7, 6,testGUI.Table7);
		changingTableStatuses(8, 7,testGUI.Table8);
		changingTableStatuses(9, 8,testGUI.Table9);
		changingTableStatuses(10, 9,testGUI.Table10);
		System.out.println("TableStausesBusboy_Changes done");
	}
	public static void changingTableStatuses(int TableID, int index,JToggleButton Table){
		log.add("");
		log.add("Changing Table "+TableID+" status to clean: ");//"+TableID+"

		Table.setSelected(true);
		
		testGUI.bHandler.ChangeTableStatus(testGUI.createList(),TableID);
		log.add("The database has been updated with the change to Table "+TableID+"!");
		log.add("Checking if change to database has been made successfully...");
		
		try {
			if(TableStatuses("T_Status").get(index).equals("Clean")){
				log.add("SUBTEST "+TableID+" = PASS");
				log.add("The changes to the database have been made successfully!"
						+ "Table "+TableID+" has been set to clean!");
				log.add("");
			}
			if(TableStatuses("T_Status").get(index).equals("Unclean")){
				log.add("SUBTEST "+TableID+" = FAIL");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("changingTableStatuses done");
	}
	
	
	public static void newTableStatusesBusboy(){
		log.add("Table Statuses after change has been made: ");
		try {
			for(int i = 0; i < TableStatuses("T_Status").size();i++){
				log.add("\n");
				log.add("Table "+(i+1)+": "+TableStatuses("T_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("newTableStatusesBusboy done");
	}
	
	public static void closingWindowsBusboy(){
		java.awt.Window win[] = java.awt.Window.getWindows(); 
		for(int j=0;j<win.length;j++){ 
			win[j].dispose();
		}
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/BusboyGUI_CleanTable_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("closingWindowsBusboy done");
	}
	
	public static void expectedOutcomeBusboy(){
		log.add("");
		log.add("EXPECTED OUTCOME:");
		for (int x = 0; x < 10 ; x++){
			expectedOutcome.add(x,"Clean");
			log.add("");
			log.add("Table " +(x+1)+": "+expectedOutcome.get(x).toString());
		}
		System.out.println("expectedOutcomeBusboy done");
	}
	public static void disconnectingTestB(){
		testComm.disconnect();
		testGUI.b.disconnect();
		testGUI.notification.close();
		testGUI.dispose();
		
	}
	
	public static void comparisonTest(){
		log.add("");
		log.add("Comparing results of test against expected outcome.... ");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		int counter = 0;
			for (int y = 0; y < 10; y++){
				try {
					if(TableStatuses("T_Status").get(y).toString().equals("Clean")){
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
}
