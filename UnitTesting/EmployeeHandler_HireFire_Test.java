package Shared.UnitTesting;

import java.io.PrintStream; 
import java.sql.ResultSet;
import java.sql.SQLException;

import Manager.*;
import Shared.Communicator.*;

public class EmployeeHandler_HireFire_Test {

	public EmployeeHandler_HireFire_Test(DatabaseCommunicator DBC, PrintStream filewrite)
	{
		filewrite.println("TEST: EmployeeHandler --> HIRE EMPLOYEE");
		filewrite.println("");
		
		filewrite.println("Attempting to Hire: Monty Burns, 1 Springfield NJ 08845, Chef, $11 ");
		
		
		String sqlHire = "INSERT INTO EmployeeList (firstname, lastname, id, address, dob, school, gpa, crimes, qone, qtwo, qthree, qfour, position, salary, visibility) values ";
		String hirep = "('Monty', 'Burns' , 13, '1 Springfield NJ 08845', '1/1/09', 'Rutgers', 4, 0, 1,1,1,1, 'Chef', 11, 1);";
		
		DBC.update(sqlHire + hirep);
		
		filewrite.println("Monty Burns Has Been Hired!");
		
		filewrite.println("");
		
		
		
		filewrite.println("TEST: EmployeeHandler --> FIRE Employee");
		
		String sqlfire = "DELETE FROM EmployeeList WHERE firstname = 'Monty';";
		
		DBC.update(sqlfire);
		
		filewrite.println("Monty Burns Has Been Fired!");
		
		
		
		filewrite.println("Hire/Fire TEST --> SUCCESS");
	
	}
}
