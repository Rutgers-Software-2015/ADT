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
@SuppressWarnings("unused")
public class BusboyGUI_UncleanTable_Test {
	static BusboyGUI testGUI;
	static DatabaseCommunicator testComm;
	static ArrayList<String> log;
	
	/*
	 * This test should do the following:
	 * 
	 * 1) View the current status of all the tables (Unclean or Clean?)
	 * 2) Select a single table, and successfully change its status to unclean
	 * 3) Print out the new table statuses after the change has been made.
	 * 
	 */
	
	public static void man(String[] args){
		performTest();
	}
	
	public synchronized static void performTest() {
		testGUI = new BusboyGUI();
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
		log.add("Performing Unit Test: changeTableStatus");
		log.add("Connecting to databse... ");
		testComm.connect("admin","gradMay17");
		testComm.tell("use MAINDB;");
		testComm.tell("Select * from MAINDB.Table_Statuses Order by Table_ID;");
		log.add("Meeting preconditions (setting table as Clean");
		testComm.update("UPDATE MAINDB.Table_Statuses SET T_Status = 'Clean' WHERE TABLE_ID = 1;");
		log.add("Success!");
		log.add("");
	}
	
	public static void ChangeTableStatus(){
		log.add("Beginning to set table as Unclean: ");
		log.add("Initial Table Conditions: ");
		try {
			for(int i = 0; i < TableStatuses("T_Status").size();i++){
				log.add("\n");
				log.add(TableStatuses("T_Status").get(i).toString());
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.add("Changing Table 1 status to Unclean: ");
		testGUI.Table1.setEnabled(true);
		testGUI.bHandler.ChangeTableStatus(testGUI.createList(),1);
		log.add("The database has been updated with the change to Table 1!");
		log.add("Checking if change to database has been made successfully...");
		try {
			if(TableStatuses("T_Status").get(0).equals("Unclean")){
				log.add("TEST = PASS");
				log.add("The changes to the database have been made successfully!"
						+ "Table 1 has been set to Unclean!");
				log.add("");
			}
			if(TableStatuses("T_Status").get(0).equals("Clean")){
				log.add("TEST = FAIL");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.add("Table Statuses after change has been made: ");
		try {
			for(int i = 0; i < TableStatuses("T_Status").size();i++){
				log.add("\n");
				log.add(TableStatuses("T_Status").get(i).toString());
			
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
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/BusboyGUI_UncleanTable_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String a : log) {
			filewrite.println(a);
		}
	}
}
	
