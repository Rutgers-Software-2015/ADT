package ADT;

public class ExampleOrders {
	
	public TableOrder table1;
	public TableOrder table2;
	public TableOrder table3;
	public TableOrder table4;
	
	public ExampleOrders()
	{
		Employee emp = new Employee(1);
		//TableOrder(Employee employee,int TABLE_ID)
		table1 = new TableOrder(emp,1);
		table2 = new TableOrder(emp,2);
		table4 = new TableOrder(emp,4);
		table3 = new TableOrder(emp,3);
		
		//table1.add(new Order(int MENU_ID, int Quantity, String SPC_REQ, int seatNumber));
		table1.add(new Order(1, 1, null,1));
		table1.add(new Order(2, 3, null,1));
		table1.add(new Order(4, 1, null,2));
		table2.add(new Order(2, 2, null,1));
		table2.add(new Order(5, 2, "NO FRIES", 3));
		table2.add(new Order(6, 1, null, 2));
	}
}
