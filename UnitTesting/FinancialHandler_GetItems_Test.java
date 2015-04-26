package Shared.UnitTesting;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import Shared.Communicator.DatabaseCommunicator;
import Manager.*;

public class FinancialHandler_GetItems_Test {
	
	public FinancialHandler_GetItems_Test(DatabaseCommunicator DBC, PrintStream filewrite)
	{
		filewrite.println("TEST: FinancialHandler --> GetItems");
		ResultSet rs = DBC.tell("Select Item_Name from OrderHistory;");
		ResultSet rs2 = DBC.tell("Select ITEM_NAME from MENU;");
		int rs2size = 1;
		int itemcount = 1;
		try
		{
			rs.beforeFirst();
			while(rs2.next() == true)
			{
				rs2size++;
			}
			filewrite.println("The Number of Menu Items is: " + rs2size);
			rs2.beforeFirst();
			String[] items = new String[rs2size];
			int q = 0;
			filewrite.println("Reading menu items...");
			while(rs2.next() == true)
			{
				items[q] = rs2.getString("Item_Name");
				filewrite.println(items[q]);
			}
			int[] amounts = new int[rs2size];
			for(int i = 0; i < amounts.length; i++)
			{
				amounts[i] = 0;
			}
			while(rs.next() == true)
			{
				int index = 0;
				String n = rs.getString("Item_Name");
				for(int i = 0; i < items.length; i++)
				{
					
					String g = items[i];
					if(n.equals(items[i]))
					{
						index = i;
						amounts[index] = amounts[index]  + 1;
					}
				}
				
				itemcount++;
			}
			/*for(int i = 0; i < items.length; i++)
			{
				System.out.println(items[i] + "\t | " + amounts[i]);
			}*/
			
		}catch(SQLException e)
		{
			System.out.println(e);
		}
		
		
		
		filewrite.println("Item Count is not zero!");
		filewrite.println("Number of Items in Order History = " + itemcount);
		filewrite.println("Financial Handler: GetItems --> SUCCESS");
		
	}
	
	
}
