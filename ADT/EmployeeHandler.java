package ADT;

import java.sql.*;

/*
	This class will interface with the database and obtain employee infromation
*/

// NEED TO ADD
// JDBC imports

public class EmployeeHandler
{
	
	public static Employee[] AllEmployees = 
	{
		//Name,Address,Position,Salary,ID
		new Employee("Rob Schultz","1 Way Steet","Kitchen Staff","15","1"),
		new Employee("David Arakelyan","2 Way Steet","Kitchen Staff","15","2"),
		new Employee("Rahul Tandon","3 Way Steet","Waiter","15","3"),
		new Employee("Ryan Sanichar","4 Way Steet","Waiter","15","4"),
		new Employee("Harsh Shah","5 Way Steet","Manager","15","5"),
		new Employee("Sam Baysting","6 Way Steet","Waiter","15","6"),
		new Employee("Randy Orton","7 Way Steet","Busboy","15","7"),
		new Employee("AJ Lee","8 Way Steet","Host","15","8"),
	};
	
	public static int[][] WaiterAssignments = 
	{
		{0,0,0,0,0,0},
		{0,0,0,0,0,0},
		{0,0,0,0,0,0}		
	};
	
	/* 
		This function will return an employee object 
		After gathering info from the database
	*/
	public static Employee[] getAllEmployees()
	{
				return AllEmployees;
	}
	
	/*
		This function will add an employee to the database
	*/
	public static void addEmployee(String Name, String Address, String Position, String Salary, String id)
	{	
		Employee[] temp = new Employee[AllEmployees.length + 1];
		
		for(int i = 0; i < temp.length; i++)
		{
			temp[i] = AllEmployees[i];
		}
		
		temp[temp.length - 1] = new Employee(Name, Address, Position, Salary, id);
		
		AllEmployees = temp;
	}



	/*
		This function will remove an employee from the database
	*/
	public static void removeEmployee(String Name)
	{
		Employee[] temp = new Employee[AllEmployees.length - 1];
		
		for(int i = 0; i < temp.length; i++)
		{
			if(AllEmployees[i].Name == Name)
			{
				i++;
				continue;
			}
			temp[i] = AllEmployees[i];
		}
		
	}

	
	/*
		This function will allow the changing of an attribute of an employee
	*/	
	public static void editEmployee(String Name, String Address, String Position, String Salary)
	{
		
	}
	
	public static void AssignWaiterToTable(int waiter, int table)
	{
		int twaiter = waiter - 1;
		int ttable = table - 1;
		
		WaiterAssignments[twaiter][ttable] = 1;
	}
	
	public static void RemoveWaiterFromTable(int waiter, int table)
	{
		int twaiter = waiter - 1;
		int ttable = table - 1;
		
		WaiterAssignments[twaiter][ttable] = 0;
	}
	
	public static boolean IsWaiterAssigned(int waiter, int table)
	{
		int twaiter = waiter - 1;
		int ttable = table - 1;
		
		int status = WaiterAssignments[twaiter][ttable];
		
		if(status == 1)
		{
			return true;
		}else
		{
			return false;
		}
	}


}

