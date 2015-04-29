package Shared.ADT;
import java.util.LinkedList;
import java.util.Queue;

/*This is  ADT for the TableOrder..
 * 
 * @author Everyone
 * @tester Everyone
 * @debugger Everyone
 *
 */
public class TableOrder
{
	public Queue<Order> FullTableOrder; //Entire order queue for the table
	public Employee Employee; //Employee assigned to table
	public int TABLE_ID; //ID associated with the table
	
	public TableOrder(Queue<Order> fulltableorder,Employee employee,int TABLE_ID)
	{

		this.FullTableOrder=fulltableorder;
		this.Employee=employee;
		this.TABLE_ID = TABLE_ID;
		
	}
	
	public TableOrder(Employee employee,int TABLE_ID)
	{

		FullTableOrder = new LinkedList<Order>();
		this.Employee=employee;
		this.TABLE_ID = TABLE_ID;
		
	}
	
	public void add(Order order)
	{
		order.employee = Employee;
		FullTableOrder.add(order);
	}
}
