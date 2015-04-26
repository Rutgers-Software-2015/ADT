package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import Shared.Communicator.DatabaseCommunicator;
import Waiter.WaiterHandler;
import Customer.CustomerGUI;

public class WaiterHandler_RetrieveOrders_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new WaiterHandler_RetrieveOrders_Test();
	}
	
	public WaiterHandler_RetrieveOrders_Test()
	{	
		CustomerGUI cust = new CustomerGUI();
		WaiterHandler comm = new WaiterHandler();
		DatabaseCommunicator c = new DatabaseCommunicator();
		boolean pass = true;
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/WaiterHandler_RetrieveOrders_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		filewrite.println("Verifying connection to DB...");
		c.connect("admin","gradMay17");
		filewrite.println("Connection verified!");
		filewrite.println("Removing all orders from the DB...");
		c.update("DELETE FROM MAINDB.TABLE_ORDER");
		filewrite.println("Orders removed!");
		filewrite.println("");
		filewrite.println("Adding random amount of orders: ");
		int randomItem = 0;
		int total_additions = 25;
		for(int i = 0; i < total_additions; i++) {
			String statement = "";
			randomItem = (int)(Math.random() * cust.menuButtons.size());
			String a1 = cust.menuButtons.get(randomItem).getText();
			a1 = a1.replaceAll("<html>", "");
			a1 = a1.replaceAll("</html>", "");
			a1 = a1.replaceAll("<br>", "");
			a1 = a1.substring(0, a1.length()-1);
			boolean happens = cust.addOrder(a1, "");
			String result = happens? "success!" : "failure.";
			filewrite.println("Adding a random menu item with from button " + randomItem + " and it was a... " + result);
		}
		cust.placeOrder();
		if(total_additions == cust.patron.TOTAL_QUANTITY) {
			filewrite.println("Successfully performed addition of " + total_additions + " items.");
		}
		filewrite.println("");
		filewrite.println("Retrieving table ID used for random additions...");
		int ID = cust.patron.TABLE_ID;
		filewrite.println("TABLE ID USED: "+ID);
		cust.dispose();
		filewrite.println("Retrieving generated orders...");
		comm.getInfoForTable(ID);
		filewrite.println("Orders retrieved!");
		filewrite.println("Printing orders...");
		for(int i = 0;i < comm.items.size();i++){
			filewrite.println(comm.items.get(i));
		}
		filewrite.println("Checking quantities...");
		int q = 0;
		for(int i = 0;i < comm.quantities.size();i++){
			q += comm.quantities.get(i);
		}
		filewrite.println("SUM OF QUANTITIES = "+q);
		if(q!=25){
			filewrite.println("Failed to retrieve all generated orders!");
			pass = false;
		}
		else{
			filewrite.println("Successfully retrieved all orders!");
		}
	
		filewrite.println("Closing connections...");
		c.disconnect();
		comm.disconnect();
		filewrite.println("");
		if(pass){
			filewrite.println("TEST = PASS");
		}
		else{
			filewrite.println("TEST = FAIL");
		}
		
		filewrite.close();
		comm.disconnect();
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/WaiterHandler_RetrieveOrders_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
		
		
	}

}
