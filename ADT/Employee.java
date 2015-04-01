package ADT;

public class Employee
{
	public String Name;
	public String Address;
	public String Position;
	public String Salary;
	public String ID;
	private String Username;
	private String Password;
	

	public Employee()
	{
		Name = null;
		Address = null;
		Position = null;
		Salary = null;
		ID = null;
	}
	
	public Employee(String name, String address, String position, String salary, String ident)
	{
		Name = name;
		Address = address;
		Position = position;
		Salary = salary;
		ID = ident;
	}
	
	public boolean setEmployeeUsername(String newUser)
	{
		//Must be logged in as this employee to use or be a Manager
		return true;
	}
	
	public String getEmployeeUsername()
	{
		//Can only be accessed by Manager or Login GUI
		return null;
	}
	
	public String getEmployeePassword()
	{
		//Can only be accessed by the Login GUI (not even the Manager)
		return null;
	}
	

}
