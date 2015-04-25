package Shared.UnitTesting;

import java.sql.ResultSet;
import java.sql.SQLException;

import Manager.*;
import Shared.Communicator.*;

public class EmployeeHandler_HireFire_Test {

	public EmployeeHandler_HireFire_Test(DatabaseCommunicator DBC)
	{
		System.out.println("TEST: EmployeeHandler --> HIRE EMPLOYEE");
		System.out.println("");
		
		System.out.println("Attempting to Hire: Monty Burns, 1 Springfield NJ 08845, Chef, $11 ");
		
		
		String sqlHire = "INSERT INTO EmployeeList (firstname, lastname, id, address, dob, school, gpa, crimes, qone, qtwo, qthree, qfour, position, salary, visibility) values ";
		String hirep = "('Monty', 'Burns' , 13, '1 Springfield NJ 08845', '1/1/09', 'Rutgers', 4, 0, 1,1,1,1, 'Chef', 11, 1);";
		
		DBC.update(sqlHire + hirep);
		
		System.out.println("Monty Burns Has Been Hired!");
		
		System.out.println("");
		
		
		
		System.out.println("TEST: EmployeeHandler --> FIRE Employee");
		
		String sqlfire = "DELETE FROM EmployeeList WHERE firstname = 'Monty';";
		
		DBC.update(sqlfire);
		
		System.out.println("Monty Burns Has Been Fired!");
		
		System.out.println("Hire/Fire TEST --> SUCCESS");
	
	}
}
