package Shared.UnitTesting;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.ResultSet;

import Shared.Communicator.DatabaseCommunicator;

public class DatabaseCommunicator_AESEncryptDecrypt_Test {
	
	/**
	 * AES Encrypt/Decrypt Unit Test
	 * 
	 * 1) Encrypt String
	 * 2) Create test table in DB
	 * 3) Send to test table
	 * 4) Retrieve from test table
	 * 5) Decrypt String
	 * 6) Test if initial and final string are equal
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	public static void main(String[] args) {
		new DatabaseCommunicator_AESEncryptDecrypt_Test();
	}
	
	
	DatabaseCommunicator_AESEncryptDecrypt_Test()
	{
		DatabaseCommunicator comm = new DatabaseCommunicator();
		SecureRandom random = new SecureRandom();
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/DatabaseCommunicator_AESEncryptDecrypt_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean pass = true;
		
		for(int i = 0;i < 5;i++){
		comm.connect("admin","gradMay17");
		String test = new BigInteger(64, random).toString(32);
		String enc = comm.encrypt(test);
		filewrite.println("STRING TO ENCRYPT: "+test);
		filewrite.println("HEX OUTPUT: "+enc);
		filewrite.println("Creating test table in DB...");
		comm.update("CREATE TABLE MAINDB.ENCRYPTIONTEST (test VARCHAR(64));");
		filewrite.println("Created! Storing hex data...");
		comm.update("INSERT INTO MAINDB.ENCRYPTIONTEST (test) values (\""+enc+"\");");
		filewrite.println("Stored! Retrieving hex data...");
		ResultSet rs = comm.tell("SELECT * FROM MAINDB.ENCRYPTIONTEST;");
		String data = null;
		try{
			rs.first();
			data = rs.getString(1);
			filewrite.println("HEX RETRIEVED: "+data);
			filewrite.println("Hex data retrieved! Decrypting data...");
		}catch(Exception e){
			filewrite.println("Hex data not retrieved!");
			e.printStackTrace(System.out);
		}
		filewrite.println("Deleting test table...");
		comm.update("DROP TABLE MAINDB.ENCRYPTIONTEST;");
		filewrite.println("Test table deleted!");
		String testdec = comm.decrypt(data);
		filewrite.println("DECRYPTED STRING: "+testdec);
		filewrite.println("IS EQUAL? "+test.equals(testdec));
		filewrite.println("");
		if(!test.equals(testdec)){
			pass = false;
		}
		}
		
		comm.disconnect();
		
		if(pass = true){
			filewrite.println("TEST = PASS");
		}else{
			filewrite.println("TEST = FAIL");
		}
	}
	
}
