package ADT;
/**This is the Abstract Data Type of Order. It contains the constructor and variables associated with it.
 * 
 * @author Everyone
 *
 */
public class Order {
	
	public int Order_ID; //Order ID
	public int seatNumber; //Assigned seat number at the table
	public MenuItem item; //Menu Item
	public Employee employee = null; //Employee associated with the order
	public int Quantity; //Quantity of item
	public String Spc_Req = null; //Special Order
	private static int ORDER_ID_LIST = 0; //Number of items in the list
	
	/*
	 * Constructor for
	 */
	public Order(int MENU_ID, int Quantity, String SPC_REQ, int seatNumber) {
		this.seatNumber = seatNumber;
		item = new MenuItem(MENU_ID);
		this.Quantity = Quantity;
		Spc_Req = SPC_REQ;
		ORDER_ID_LIST++;
		Order_ID = ORDER_ID_LIST;
	}
	public Order(int MENU_ID, float payment,int paymentType) {
		this.seatNumber = 0;
		item = new MenuItem(MENU_ID,payment);
		this.Quantity = paymentType;
		Spc_Req = null;
		ORDER_ID_LIST++;
		Order_ID = ORDER_ID_LIST;
	}
	
	public void print()
	{
		System.out.println(String.format("%i %s %i %s.",Order_ID,item,Quantity,Spc_Req));
	}
}
