package Shared.UnitTesting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.PrintStream;
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
	
	public EmployeeHandler_GetEmployees_Test(DatabaseCommunicator DBC, PrintStream filewrite)
	{
		
		ResultSet rs = DBC.tell("Select * from EmployeeList;");
		
		filewrite.println("TEST: Employee Handler --> Get Employees");
		filewrite.println("Currently Fetching Employees....");
		
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
				
				
				filewrite.println(fn + " " + ln + "\t \t | \t" + fn + " " + ln);
				
			}
			filewrite.println("ALL EMPLOYEES HAVE BEEN VIEWED");
			filewrite.println("GET EMPLOYEES TEST --> SUCCESS");
			
		}catch(SQLException e)
		{
			filewrite.println("Unable to Connect!");
		}
		
	}
	
	
}
