package Shared.Communicator;

import java.sql.ResultSet;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseCommunicator comm = new DatabaseCommunicator();
		comm.connect("admin","gradMay17");
		String test = "This is a test!";
		String enc = comm.encrypt(test);
		System.out.println("STRING TO ENCRYPT: "+test);
		System.out.println("HEX OUTPUT: "+enc);
		System.out.println("Creating test table in DB...");
		comm.update("CREATE TABLE MAINDB.ENCRYPTIONTEST (test VARCHAR(32));");
		System.out.println("Created! Storing hex data...");
		comm.update("INSERT INTO MAINDB.ENCRYPTIONTEST (test) values (\""+enc+"\");");
		System.out.println("Stored! Retrieving hex data...");
		ResultSet rs = comm.tell("SELECT * FROM MAINDB.ENCRYPTIONTEST;");
		String data = null;
		try{
			rs.first();
			data = rs.getString(1);
			System.out.println("HEX RETRIEVED: "+data);
			System.out.println("Hex data retrieved! Decrypting data...");
		}catch(Exception e){
			System.out.println("Hex data not retrieved!");
			e.printStackTrace(System.out);
			return;
		}
		System.out.println("Deleting table ENCRYPTIONTEST...");
		comm.update("DROP TABLE MAINDB.ENCRYPTIONTEST;");
		System.out.println("ENCRYPTIONTEST Deleted!");
		String testdec = comm.decrypt(data);
		System.out.println("DECRYPTED STRING: "+testdec);
		System.out.println("IS EQUAL? "+test.equals(testdec));
		comm.disconnect();
		
		
	}

}
