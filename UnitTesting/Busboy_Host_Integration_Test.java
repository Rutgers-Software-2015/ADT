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
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import Shared.Communicator.DatabaseCommunicator;
import Busboy.BusboyGUI;
import Customer.CustomerGUI;
import Host.HostGUI;
@SuppressWarnings("unused")

public class Busboy_Host_Integration_Test {
	static HostGUI testGUI;
	static BusboyGUI testBGUI;
	static DatabaseCommunicator testComm;
	static ArrayList<String> log;
	static ArrayList<String> expectedOutcome;
	static ArrayList<String> finalOutcome;
	
	/**
	 * This test should do the following:
	 * 
	 * 1) The busboy will select all tables and set them to clean
	 * 2) The host will view the changes and if they are consistent with the database changes made by the busboy the test will pass
	 * 
	 * @author David Arakelyan
	 * @debugger David Arakelyan
	 * 
	 */
	
	public static void main(String[] args){
		performTestBusboy();
		performTestHost();
		
	}
	
	public synchronized static void performTestHost(){
		// Comparison Test
		comparisonTestBH();
		
		// Printing to text file
		 printLogBH();
		   
		// Closing open windows
		closingWindowsBH();
		
		 // Disconnecting
		disconnectingTestBH();
		
		
	}
	
	public synchronized static void performTestBusboy() {
		// Declarations
		//testGUI = new HostGUI();
		testBGUI = new BusboyGUI();
		testComm = new DatabaseCommunicator();
		log = new ArrayList<String>();
		expectedOutcome = new ArrayList<String>();
		finalOutcome = new ArrayList<String>();
		
		// Satisfying preconditions --
		preconditionBH();
		
		// Initial Table Conditions --
		initialTableConditionsBH();
		
		// Busboy set tables to dirty: --
		setSelectedTrueBH();
		
		// Expected Outcome --
		expectedOutcomeReservationBH();
		
		// Changing status of tables (busboy) --
		ChangeTableStatusBH();
		
		// New Customer Statuses --
		newTableStatusesBH();
		
		
	}
	
	public static void preconditionBH(){
		log.add("Performing Integration Test: Busboy sets tables to unclean, Host interface accepts changes;");
		log.add("");
		log.add("Connecting to database... ");
		testComm.connect("admin","gradMay17");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		log.add("Meeting preconditions (Connecting to database)");

		log.add("Success!");
		log.add("");
	}
	
	public static void ChangeTableStatusBH(){
		changingTableStatuses(1, 0,testBGUI.Table1);
		changingTableStatuses(2, 1,testBGUI.Table2);
		changingTableStatuses(3, 2,testBGUI.Table3);
		changingTableStatuses(4, 3,testBGUI.Table4);
		changingTableStatuses(5, 4,testBGUI.Table5);
		changingTableStatuses(6, 5,testBGUI.Table6);
		changingTableStatuses(7, 6,testBGUI.Table7);
		changingTableStatuses(8, 7,testBGUI.Table8);
		changingTableStatuses(9, 8,testBGUI.Table9);
		changingTableStatuses(10, 9,testBGUI.Table10);
		
	}
	public static void changingTableStatuses(int TableID, int index,JToggleButton Table){
		log.add("");
		log.add("Changing Table "+TableID+" status to unclean: ");//"+TableID+"

		Table.setSelected(false);
		
		testBGUI.bHandler.ChangeTableStatus(testBGUI.createList(),TableID);
		log.add("The database has been updated with the change to Table "+TableID+"!");
		log.add("Checking if change to database has been made successfully...");
		
		try {
			if(TableStatuses("T_Status").get(index).equals("Unclean")){
				log.add("SUBTEST "+TableID+" = PASS");
				log.add("The changes to the database have been made successfully!"
						+ " Table "+TableID+" has been set to Unclean!");
				log.add("");
			}
			if(TableStatuses("T_Status").get(index).equals("Clean")){
				log.add("SUBTEST "+TableID+" = FAIL");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("changingTableStatuses done");
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
	
	private static void printLogBH() {
		String[] temp = new String[log.size()];
		for(int i = 0; i < log.size(); i++) {
			temp[i] = log.get(i);
		}
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/Busboy_Host_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String a : log) {
			filewrite.println(a);
		}
	}
	
	
	public static void comparisonTestBH(){
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
			log.add("EXPECTED RESULT MATCHES FINAL RESULT: HOST INTERFACE CHANGED WITH BUSBOY UPDATES");
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
	public static void expectedOutcomeReservationBH(){
		log.add("");
		log.add("EXPECTED OUTCOME IN HOST:");
		for (int x = 0; x < 10 ; x++){
			expectedOutcome.add(x,"Unclean");
			log.add("");
			log.add("Table " +(x+1)+": "+expectedOutcome.get(x).toString());
		}
	}
	public static void newTableStatusesBH(){
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
	public static void closingWindowsBH(){
		java.awt.Window win[] = java.awt.Window.getWindows(); 
		for(int j=0;j<win.length;j++){ 
			win[j].dispose();
		}
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/Busboy_Host_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void disconnectingTestBH(){
		testComm.disconnect();
		testBGUI.b.disconnect();
		testBGUI.notification.close();
		testBGUI.dispose();
		System.exit(0);
	}
	
	public static void initialTableConditionsBH(){
		log.add("Beginning to mark tables as unclean in busboy... "); 
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
	
	public static void setSelectedTrueBH(){
		testBGUI.Table1.setSelected(true);
		testBGUI.Table2.setSelected(true);
		testBGUI.Table3.setSelected(true);
		testBGUI.Table4.setSelected(true);
		testBGUI.Table6.setSelected(true);
		testBGUI.Table7.setSelected(true);
		testBGUI.Table8.setSelected(true);
		testBGUI.Table9.setSelected(true);
		testBGUI.Table10.setSelected(true);
	}
}
