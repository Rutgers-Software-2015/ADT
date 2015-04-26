package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.ResultSet;

import Shared.Communicator.DatabaseCommunicator;

public class DatabaseCommunicator_SHAHashAndMatch_Test {
	
	/**
	 * SHA 256 Hash Unit Test
	 * 
	 * 1) Hash a randomly generated string
	 * 2) Create test table in DB
	 * 3) Send to test table
	 * 4) Retrieve from test table
	 * 5) Test if original hash equals retrieved hash
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	public static void main(String[] args) {
		new DatabaseCommunicator_SHAHashAndMatch_Test();
	}
	
	
	DatabaseCommunicator_SHAHashAndMatch_Test()
	{
		DatabaseCommunicator comm = new DatabaseCommunicator();
		SecureRandom random = new SecureRandom();
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/DatabaseCommunicator_SHAHashAndMatch_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean pass = true;
		
		for(int i = 0;i < 5;i++){
		comm.connect("admin","gradMay17");
		String test = new BigInteger(64, random).toString(32);
		String enc = comm.SHA_256_Hash(test);
		filewrite.println("STRING TO HASH: "+test);
		filewrite.println("HASH GENERATION: "+enc);
		filewrite.println("Creating test table in DB...");
		comm.update("CREATE TABLE MAINDB.ENCRYPTIONTEST (test VARCHAR(64));");
		filewrite.println("Created! Storing hash data...");
		comm.update("INSERT INTO MAINDB.ENCRYPTIONTEST (test) values (\""+enc+"\");");
		filewrite.println("Stored! Retrieving hash data...");
		ResultSet rs = comm.tell("SELECT * FROM MAINDB.ENCRYPTIONTEST;");
		String data = null;
		try{
			rs.first();
			data = rs.getString(1);
			filewrite.println("HASH RETRIEVED: "+data);
			filewrite.println("Hash data retrieved!");
		}catch(Exception e){
			filewrite.println("Hash data not retrieved!");
			e.printStackTrace(System.out);
		}
		filewrite.println("Deleting test table...");
		comm.update("DROP TABLE MAINDB.ENCRYPTIONTEST;");
		filewrite.println("Test table deleted!");
		filewrite.println("IS EQUAL? "+enc.equals(data));
		filewrite.println("");
		if(!enc.equals(data)){
			pass = false;
		}
		}
		
		comm.disconnect();
		
		if(pass == true){
			filewrite.println("TEST = PASS");
		}else{
			filewrite.println("TEST = FAIL");
		}
		
		filewrite.close();
		
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/DatabaseCommunicator_SHAHashAndMatch_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}