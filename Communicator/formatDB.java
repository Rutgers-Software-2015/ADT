package Shared.Communicator;

/**
 * This class allows a user, function or class to interact
 * with a MySQL database using SQL statements
 * 
 * @author Samuel Baysting
 * @tester Samuel Baysting
 * @debugger Samuel Baysting
 * 
 */

public class formatDB{

	public static void main(String[] args)
	{
		DatabaseCommunicator DBC = new DatabaseCommunicator();
		String[] firstnames = {"Harsh", "Sam", "Ryan", "Rob", "Rahul", "David"};
		DBC.connect("admin", "gradMay17");
		DBC.tell("use MAINDB");
		String[] addr = {"1 Olong Way, East Brunswick, NJ 08816", "2 Olong Way, East Brunswick, NJ 08816", "3 Olong Way, East Brunswick, NJ 08816", "4 Olong Way, East Brunswick, NJ 08816", "5 Olong Way, East Brunswick, NJ 08816", "6 Olong Way, East Brunswick, NJ 08816"};
		
		for(int i = 0; i < 6; i++)
		{
			String sqlcomm = "UPDATE EmployeeList SET address = " + "'" + DBC.encrypt(addr[i]) + "'" + " WHERE firstname = " + "'"  + firstnames[i] + "';";
			DBC.update(sqlcomm);
		}
		
		DBC.disconnect();
	}
	
}
