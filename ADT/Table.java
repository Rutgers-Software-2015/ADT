package Shared.ADT;

/*This is the Abstract Data Type of Table. It contains the constructor and variables associated with it.
 * 
 * @author Everyone
 * @tester Everyone
 * @debugger Everyone
 *
 */

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
