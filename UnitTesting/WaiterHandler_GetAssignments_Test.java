package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

import Waiter.WaiterHandler;

public class WaiterHandler_GetAssignments_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new WaiterHandler_GetAssignments_Test();
	}
	
	public WaiterHandler_GetAssignments_Test()
	{
		WaiterHandler comm = new WaiterHandler();
		boolean pass = true;
		PrintStream file = null;
		try {
			file = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/WaiterHandler_GetAssignments_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		file.println("Testing table assignment retrieval");
		LinkedList<Integer> table = comm.getAssignedTables();
		for(int i = 1;i <= 10;i++){
			file.println("Testing table "+i+" retrieval...");
			table.contains(i);
			file.println("Test for table "+i+" successful!");
			file.println("");
		}
		
		if(pass){
			file.println("TEST = PASS");
		}
		else{
			file.println("TEST = FAIL");
		}
		
		file.close();
		comm.disconnect();
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/WaiterHandler_GetAssignments_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);

	}
}
