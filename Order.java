package Utils;

public class Order {
	
	public int Order_ID;
	public MenuItem item;
	public Employee waiter;
	private int Quantity;
	private String Spc_Req;
	private static int ORDER_ID_LIST = 0;
	public Order(int a, int b, String c,Employee d) {
		item = new MenuItem(a);
		Quantity = b;
		Spc_Req = c;
		ORDER_ID_LIST++;
		Order_ID = ORDER_ID_LIST;
		waiter=d;
	}
	
	public void print()
	{
		System.out.println(String.format("%i %s %i %s.",Order_ID,item,Quantity,Spc_Req));
	}
}
