package Shared.Communicator;

import java.sql.ResultSet;

public class Test extends DatabaseCommunicator{

	public static void main(String[] args) {
		
		new Test();
		
	}
	
	public Test()
	{
		super();
		System.out.println(SHA_256_Hash("I am a string"));
		decrypt(encrypt("I am a string"));
		connect("admin","gradMay17");
		ResultSet r = tell("SELECT * FROM login.users");
		update("INSERT INTO login.users (username,password,employee_ID) "
				+ "VALUES (\"Samuel\",\""+SHA_256_Hash("Password")+"\", 12);");
		r = tell("SELECT * FROM login.users");
		consolePrintTable(r);
	}

}
