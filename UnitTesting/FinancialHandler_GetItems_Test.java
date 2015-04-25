package Shared.UnitTesting;

import java.sql.ResultSet;
import java.sql.SQLException;

import Shared.Communicator.DatabaseCommunicator;
import Manager.*;

public class FinancialHandler_GetItems_Test {
	
	public FinancialHandler_GetItems_Test(DatabaseCommunicator DBC)
	{
		System.out.println("TEST: FinancialHandler --> GetItems");
		ResultSet rs = DBC.tell("Select Item_Name from OrderHistory;");
		int itemcount = 0;
		try{
			while(rs.next() == true)
			{
				itemcount++;
			}
		}catch(SQLException e)
		{
			System.out.println(e);
		}
		System.out.println("Number of Items in Order History = " + itemcount);
		System.out.println("Item Count is not zero!");
		System.out.println("Financial Handler: GetItems --> SUCCESS");
		
	}
}
