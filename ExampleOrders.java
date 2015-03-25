package ADT;

public class ExampleOrders {
	
	public static TableOrder table1;
	public static TableOrder table2;
	public static TableOrder table3;
	public static TableOrder table4;
	public static TableOrder table5;
	
	public ExampleOrders()
	{
		Employee emp = new Employee();
		//TableOrder(Employee employee,int TABLE_ID)
		table1 = new TableOrder(emp,1);
		table2 = new TableOrder(emp,2);
		table4 = new TableOrder(emp,4);
		table3 = new TableOrder(emp,3);
		table5 = new TableOrder(emp,5);
		
		//table1.add(new Order(int MENU_ID, int Quantity, String SPC_REQ, int seatNumber));
		table1.add(new Order(1, 1, null,1));
		table1.add(new Order(2, 3, null,1));
		table1.add(new Order(4, 1, null,2));
		table2.add(new Order(2, 2, null,1));
		table2.add(new Order(5, 2, "NO FRIES", 3));
		table2.add(new Order(6, 1, null, 2));
		table3.add(new Order(3, 2, null, 4));
		table3.add(new Order(1, 1, null, 3));
		table3.add(new Order(1, 1, null, 1));
		table3.add(new Order(2, 3, null, 2));
		table4.add(new Order(6, 1, null, 1));
		table4.add(new Order(4, 1, null, 1));
		table5.add(new Order(5, 4, null, 1));
		table5.add(new Order(2, 2, null, 2));
	}
}
