package Shared.UnitTesting;

import Shared.Communicator.DatabaseCommunicator;

public class Manager_Test {

	public static void main(String[] args)
	{
		Manager_Test e = new Manager_Test();
	}
	
	public Manager_Test()
	{
		DatabaseCommunicator DBC = new DatabaseCommunicator();
		DBC.connect("admin", "gradMay17");
		DBC.tell("use MAINDB");
		
		System.out.println("MANAGER TESTING START");
		System.out.println();
		
		EmployeeHandler_GetEmployees_Test t1 = new EmployeeHandler_GetEmployees_Test(DBC);
		System.out.println("");
		
		EmployeeHandler_HireFire_Test t2 = new EmployeeHandler_HireFire_Test(DBC);
		System.out.println();
		
		FinancialHandler_GetItems_Test t3 = new FinancialHandler_GetItems_Test(DBC);
		System.out.println("MANGER TESTING --> SUCCESS");
		System.out.println("");
		
	}
	
}
