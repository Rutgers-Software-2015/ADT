package Shared.UnitTesting;
import java.sql.ResultSet;
import java.sql.SQLException;

import Manager.*;
import Shared.Communicator.*;

public class EmployeeHandler_GetEmployees_Test {

	/*
	 * This test should do the following:
	 * 1) Retrieve the Employees from the Database
	 * 2) Filter Them to only output named employees
	 * 3) Only Harsh, Sam, Rahul, David, Rob, and Ryan should be printed
	 * 4) No Encrypted output should be present
	 */
	
	public EmployeeHandler_GetEmployees_Test()
	{
		DatabaseCommunicator DBC = new DatabaseCommunicator();
		DBC.connect("admin", "gradMay17");
		DBC.tell("use MAINDB");
		ResultSet rs = DBC.tell("Select * from EmployeeList;");
		
		System.out.println("TEST: Employee Handler --> Get Employees");
		System.out.println("Currently Fetching Employees....");
		
		try
		{
			System.out.println("Current Employees:");
			while(rs.next() == true)
			{
				if(rs.getString("firstname").equals("waiter"))
				{
					continue;
				}
				if(rs.getString("firstname").equals("host"))
				{
					continue;
				}
				if(rs.getString("firstname").equals("busboy"))
				{
					continue;
				}
				if(rs.getString("firstname").equals("customer"))
				{
					continue;
				}
				if(rs.getString("firstname").equals("kitchen"))
				{
					continue;
				}
				if(rs.getString("firstname").equals("manager"))
				{
					continue;
				}
				if(rs.getString("firstname").equals("debug"))
				{
					continue;
				}
				
				String fn = rs.getString("firstname");
				String ln = rs.getString("lastname");
				
				
				System.out.println(fn + " " + ln + "\t \t | \t" + fn + " " + ln);
				
			}
			System.out.println("ALL EMPLOYEES HAVE BEEN VIEWED");
			System.out.println("GET EMPLOYEES TEST --> SUCCESS");
			DBC.disconnect();
		}catch(SQLException e)
		{
			System.out.println("Unable to Connect!");
		}
		
	}
	
	
}
