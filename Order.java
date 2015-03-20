package ADT;

public class Order {
	
	public int Order_ID; //Order ID
	public int seatNumber; //Assigned seat number at the table
	public MenuItem item; //Menu Item
	private int Quantity; //Quantity of item
	private String Spc_Req = null; //Special Order
	private static int ORDER_ID_LIST = 0; //Number of items in the list
	
	public Order(int MENU_ID, int Quantity, String SPC_REQ,Employee employee) {
		item = new MenuItem(MENU_ID);
		this.Quantity = Quantity;
		Spc_Req = SPC_REQ;
		ORDER_ID_LIST++;
		Order_ID = ORDER_ID_LIST;
	}
	
	public void print()
	{
		System.out.println(String.format("%i %s %i %s.",Order_ID,item,Quantity,Spc_Req));
	}
}
