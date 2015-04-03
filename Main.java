package Shared;

import java.sql.ResultSet;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		
		//Define new communicator
		DatabaseCommunicator d = new DatabaseCommunicator();

		//d.getConnection(username, password)
		d.connect("admin","gradMay17");
		
		//d.tell(SQL Statement)
		d.tell("USE TEST;");
		d.consolePrintTable(d.tell("select * from EMPLOYEES;"));

		
	}

}
