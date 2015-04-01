package Shared.ADT;

public class Table 
{
	public int TABLE_ID;
	public Employee Employee;
	public boolean Table_Status;
	
	public Table(int ID,Employee E,boolean status)
	{
		TABLE_ID=ID;
		Employee=E;
		Table_Status=status;
	}
	
	
}
