package Utils;
import java.util.Queue;

public class TableOrder
{
	public Queue<Order> FullTableOrder;
	public Employee Employee;
	
	public TableOrder(Queue<Order> fulltableorder,Employee employee)
	{
		this.FullTableOrder=fulltableorder;
		this.Employee=employee;
		
	}
}
