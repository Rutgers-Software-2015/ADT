package Shared;

public class Main {

	public static void main(String[] args) {
		
		//Define new communicator
		DatabaseCommunicator d = new DatabaseCommunicator();

		//d.getConnection(username, password)
		d.getConnection("admin","gradMay17");
		
		//d.tell(SQL Statement)
		d.tell("USE TEST;");
		d.tell("select * from EMPLOYEES;");
	}

}
