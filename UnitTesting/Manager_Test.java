package Shared.UnitTesting;

import Shared.Communicator.DatabaseCommunicator;

import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Manager_Test {

	public static void main(String[] args)
	{
		Manager_Test e = new Manager_Test();
	}
	
	public Manager_Test()
	{
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/Manager_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DatabaseCommunicator DBC = new DatabaseCommunicator();
		DBC.connect("admin", "gradMay17");
		DBC.tell("use MAINDB");
		
		filewrite.println("MANAGER TESTING START");
		filewrite.println();
		
		EmployeeHandler_GetEmployees_Test t1 = new EmployeeHandler_GetEmployees_Test(DBC, filewrite);
		filewrite.println("");
		
		EmployeeHandler_HireFire_Test t2 = new EmployeeHandler_HireFire_Test(DBC, filewrite);
		filewrite.println();
		
		FinancialHandler_GetItems_Test t3 = new FinancialHandler_GetItems_Test(DBC, filewrite);
		filewrite.println("MANGER TESTING --> SUCCESS");
		
		filewrite.println("TEST = PASS");
	}
	
}
