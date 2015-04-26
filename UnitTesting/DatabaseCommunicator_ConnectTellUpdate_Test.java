package Shared.UnitTesting;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.ResultSet;

import Shared.Communicator.DatabaseCommunicator;

public class DatabaseCommunicator_ConnectTellUpdate_Test {
	
	/**
	 * Connect, Tell and Update Unit Test
	 * 
	 * 1) Connect to DB
	 * 2) Create test table in DB
	 * 3) Send random string to test table
	 * 4) Retrieve string from test table
	 * 5) Delete string from test table
	 * 6) Verify if string was deleted
	 * 7) Compare original and retrieved data
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	public static void main(String[] args) {
		new DatabaseCommunicator_ConnectTellUpdate_Test();
	}
	
	
	DatabaseCommunicator_ConnectTellUpdate_Test()
	{
		DatabaseCommunicator comm = new DatabaseCommunicator();
		SecureRandom random = new SecureRandom();
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/DatabaseCommunicator_ConnectTellUpdate_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean pass = true;
		comm.connect("admin","gradMay17");
		
		for(int i = 0;i < 5;i++){
	
		String test = new BigInteger(64, random).toString(32);
		filewrite.println("GENERATED STRING: "+test);
		filewrite.println("Creating test table in DB...");
		comm.update("CREATE TABLE MAINDB.TELLUPDATETEST (test VARCHAR(64));");
		filewrite.println("Created! Storing data...");
		comm.update("INSERT INTO MAINDB.TELLUPDATETEST (test) values (\""+test+"\");");
		filewrite.println("Stored! Retrieving data...");
		ResultSet rs = comm.tell("SELECT * FROM MAINDB.TELLUPDATETEST;");
		String data = null;
		try{
			rs.first();
			data = rs.getString(1);
			filewrite.println("STRING RETRIEVED: "+data);
			filewrite.println("Data retrieved!");
		}catch(Exception e){
			filewrite.println("Data not retrieved!");
			pass = false;
			e.printStackTrace(System.out);
		}
		filewrite.println("Removing data from table...");
		comm.update("DELETE FROM MAINDB.TELLUPDATETEST WHERE test=\""+data+"\"");
		filewrite.println("Data removed! Verifying...");
		rs = comm.tell("SELECT * FROM MAINDB.TELLUDPATETEST");
		if(rs != null){
			pass = false;
			filewrite.println("Data not successfully removed.");
		}
		else{
			filewrite.println("Data successfully removed!");
		}
		filewrite.println("Deleting test table...");
		comm.update("DROP TABLE MAINDB.TELLUPDATETEST;");
		filewrite.println("Test table deleted!");
		filewrite.println("IS ORIGINAL AND RETRIEVED DATA EQUAL? "+test.equals(data));
		filewrite.println("");
		
		if(!data.equals(test)){
			pass = false;
		}
		}
		
		comm.disconnect();
		
		if(pass == true){
			filewrite.println("TEST = PASS");
		}else{
			filewrite.println("TEST = FAIL");
		}
	}
	
}