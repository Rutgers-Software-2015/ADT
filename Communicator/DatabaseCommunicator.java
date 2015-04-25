package Shared.Communicator;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class DatabaseCommunicator implements ActionListener {

	/**
	 * This class allows a user, function or class to interact
	 * with a MySQL database using SQL statements
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	public Connection c = null;
	private static Key key = null;
	private static File keyFile = new File("src/Shared/Communicator/AES.key");
	
	public int status = -1; // THIS IS THE MOST RECENT DATABASE CONNECTION STATUS
	
	private Timer databaseTimer;
	protected String sqlUser = null;
	protected String sqlPass = null;
	
	/**
	 * Creates a new instance of DatabaseCommunicator
	 * Loads the driver to connect to the database and loads AES key from file
	 * 
	 * @param none
	 * @return none
	 * 
	 */
	
	public DatabaseCommunicator()
	{
		
		 try{
		        System.out.println("Attempting to load com.mysql.jdbc.Driver...");
		        Class.forName("com.mysql.jdbc.Driver");
		    }
		    catch(ClassNotFoundException e){
		        throw new RuntimeException("Cannot find the driver in the classpath! Please install the driver.", e);
		    }
		 System.out.println("Driver loaded!");

		 try {
			loadKey();
		} catch (IOException e) {
			System.out.println("Failed to load key!");
			e.printStackTrace();
		}
		 
	}
	
	/**
	 * This function sets up a connection to the MySQL Database
	 * 
	 * @param sqlUser - Username to use for MySQL Database
	 * @param sqlPass - Password to use for MySQL Database
	 * @return 0 - Successful connection
	 * @return 1 = Connect attempt failed (Bad user/pass or 
	 * 			   no connection to database available)
	 */
	
	public int connect(String sqlUser, String sqlPass)
	{
		this.sqlUser = sqlUser;
		this.sqlPass = sqlPass;
		
		// Create a timer to update the connection status
		System.out.println("Enabling timer...");
		databaseTimer = new Timer(15000,this);
		databaseTimer.setRepeats(true);
		databaseTimer.setCoalesce(true);
		databaseTimer.setInitialDelay(0);
		databaseTimer.start();
		
		String sqlIP = "jdbc:mysql://172.16.60.193";
		 
		try{
			System.out.println("Attempting connection to "
					+ sqlIP + "...\nUsername: " + sqlUser
					+ "\nPassword: " + sqlPass);
			DriverManager.setLoginTimeout(3);
			c = DriverManager.getConnection(sqlIP, sqlUser, sqlPass);
		}
		catch(SQLException err){
			System.out.println("Connection failed!");
			System.out.println(err.getMessage());
			err.printStackTrace();
			status = 1;
			return 1;
		}
		
		System.out.println("Connection successful!");
		System.out.println("Verifying connection...");
		
		if (c != null) {
			
		    System.out.println("Connection confirmed! Ready to access database.");
		    status = 0;
		    return 0;
		    
		} else {
			
		    System.out.println("Failed to make connection!");
		    status = 1;
		    return 1;
		    
		}
	}
	
	/**
	 * This function returns the exit code for the connect() function
	 * 
	 * @return 0 - connection successful
	 * @return 1 - connection failed
	 * 
	 */
	
	public int getConnectionStatus()
	{
		try {
			if(c!=null){
				if(c.isValid(2)){
					status = 0;
					return 0;
				}
				else{ //If connection not valid
					c = null;
					System.out.println("ATTEMPTING DB RECONNECT...");
					if(connect(sqlUser,sqlPass) == 0){ //Attempt reconnect
						System.out.println("RECONNECT SUCCESSFUL!");
						status = 0;
						return 0;
					}
					else{
						System.out.println("RECONNECT FAILED!");
						status = 1;
						return 1;
					}
				}
			}
			else{
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			status = 1;
			return 1;
		}
	}
	
	/**
	 * This function disconnects the client from the SQL database
	 * 
	 * @param none
	 * @return none
	 * 
	 */
	
	public void disconnect()
	{
		System.out.println("Disconnecting from database...");
		
		if(c != null){
			try {
				c.close();
			} catch (SQLException e) {
				System.out.println("System did not close gracefully...");
				e.printStackTrace();
			}
			
			c = null;
		}
		
		System.out.println("Disabling database clock...");
		databaseTimer = null;
		return;
	}
	
	/**
	 * Sends a command to the SQL database
	 * 
	 * Accepts queries only, no database changing commands
	 * 
	 * @param sqlCommand - SQL statement to be sent to the database
	 * @return ResultSet - The information block returned by the database
	 * @return null - empty set, invalid connection or SQL exception
	 * 
	 */
	
	public ResultSet tell(String sqlCommand)
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			if(getConnectionStatus()==0){
				
				stmt = c.createStatement();
				rs = stmt.executeQuery(sqlCommand);
		    
				//Print SQL Warnings into console
				SQLWarning sqlWarning = rs.getWarnings();
				while(sqlWarning != null){
					System.out.println(sqlWarning.getMessage());
					sqlWarning = sqlWarning.getNextWarning();
				}
		    
				//If no output, return null
				try{
					rs.first();
				}
				catch(NullPointerException e){
					return null;
				}
			}
			else{
				return null;
			}
		    
		}
		catch (SQLException ex){
		    //Error handler
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		
		//Return ResultSet and SQL Messages
		return rs;
	}
	
	/**
	 * Sends a command to the SQL database
	 * 
	 * Accepts queries only, no database changing commands
	 * 
	 * @param sqlCommand - SQL statement to be sent to the database
	 * @return LinkedList - Index 0 - The information block returned by the database
	 * 						Index 1 - The strings returned by the database
	 * 
	 */
	
	public LinkedList tellGetStrings(String sqlCommand)
	{
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder s = new StringBuilder();
		LinkedList l = new LinkedList();
		
		try {
		    stmt = c.createStatement();
		    rs = stmt.executeQuery(sqlCommand);
		    
		    //Print SQL Warnings into console
		    SQLWarning sqlWarning = rs.getWarnings();
		    while(sqlWarning != null){
		    	System.out.println(sqlWarning.getMessage());
		    	s.append(sqlWarning.getMessage() + "\n");
		    	sqlWarning = sqlWarning.getNextWarning();
		    }
		    
		    //If no output, return null
		    try{
		    	rs.first();
		    }
		    catch(NullPointerException e){
		    	l.addLast(rs);
		    	l.addLast(s.toString());
		    	return l;
		    }
		    
		}
		catch (SQLException ex){
		    //Error handler
			s.append("SQLException: " + ex.getMessage() + "\n");
		    System.out.println("SQLException: " + ex.getMessage());
		    s.append("SQLState: " + ex.getSQLState() + "\n");
		    System.out.println("SQLState: " + ex.getSQLState());
		    s.append("VendorError: " + ex.getErrorCode() + "\n");
		    System.out.println("VendorError: " + ex.getErrorCode());
		    l.addLast(null);
		    l.addLast(s.toString());
		    return l;
		}
		
		//Return ResultSet and SQL Messages
		l.addLast(rs);
		l.addLast(s.toString());
		   return l;
	}
	
	/**
	 * Sends a command to the SQL database
	 * 
	 * Accepts database changing commands
	 * 
	 * @param sqlCommand - String SQL statement to be sent to the database
	 * @return row count integer (if applicable) or 0 (if nothing)
	 * @return -1 - update could not execute (No valid connection)
	 * @return -2 - update could not execute (SQL Exception - Possible invalid syntax or command)
	 * 
	 */
	
	public int update(String sqlCommand)
	{
		Statement stmt = null;
		
		try {
			if(getConnectionStatus()==0){
				stmt = c.createStatement();
				return(stmt.executeUpdate(sqlCommand));
			}
			else{
				return -1;
			}
		    
		}
		catch (SQLException ex){
		    //Error handler
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return -2;
		}
	}
	
	/**
	 * Sends a command to the SQL database
	 * 
	 * Accepts database changing commands
	 * 
	 * @param sqlCommand - String SQL statement to be sent to the database
	 * @return Linked List - Index 0 - row count integer (if applicable) or 0 (if nothing), or -1 for exception thrown
	 * 						 Index 1 - String with SQL Messages
	 * 
	 */
	
	public LinkedList updateGetStrings(String sqlCommand)
	{
		Statement stmt = null;
		StringBuilder s = new StringBuilder();
		LinkedList l = new LinkedList();
		
		try {
		    stmt = c.createStatement();
		    l.add(stmt.executeUpdate(sqlCommand));
		    l.add(s.toString());
		    return l;
		    
		}
		catch (SQLException ex){
		    //Error handler
		    System.out.println("SQLException: " + ex.getMessage());
		    s.append("SQLException: " + ex.getMessage() + "\n");
		    System.out.println("SQLState: " + ex.getSQLState());
		    s.append("SQLState: " + ex.getSQLState() + "\n");
		    System.out.println("VendorError: " + ex.getErrorCode());
		    s.append("VendorError: " + ex.getErrorCode() + "\n");
		    l.add(-1);
		    l.add(s.toString());
		    return l;
		}
	}
	
	/**
	 * Prints the table associated with the ResultSet to the
	 * system console
	 * 
	 * @param rs - ResultSet returned by tell() function
	 * @return none
	 * 
	 */
	
	public String consolePrintTable(ResultSet rs)
	{
		try{
			
		System.out.println("");
		
		ResultSetMetaData rsd = rs.getMetaData();
	    int colsize = rsd.getColumnCount();
	    
	    do{
	    	for(int i = 1; i <= colsize; i++){
	    		String r = rs.getString(i);
	    		System.out.print(rsd.getColumnName(i) + "  ");
	    		System.out.println(r + "  ");
	    	}
	    	System.out.println("");
	    }while(rs.next());
	    
		}
	    catch(SQLException sqlEx){
	    	 //Error Handler
		    System.out.println("SQLException: " + sqlEx.getMessage());
		    System.out.println("SQLState: " + sqlEx.getSQLState());
		    System.out.println("VendorError: " + sqlEx.getErrorCode());
	    }
		
		return null;
	}
	
	/**
	 * Converts any given string to hexadecimal irreversible SHA-256 hash
	 * Used for password storage
	 * 
	 * @param password - String to convert into SHA-256 hash
	 * @return SHA-256 hexadecimal hash string
	 * 
	 */
	
	public String SHA_256_Hash(String password)
	{
		MessageDigest md = null;
		StringReader fis = new StringReader(password);
        char[] dataChars = new char[1024];
		
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			//Shouldn't ever hit this catch() statement
			e1.printStackTrace();
		}

        try {
        	fis.mark(256);
			while ((fis.read(dataChars)) != -1) {
			  //md.update(dataBytes, 0, nread);
			}
			fis.reset();
		} catch (IOException e) {
			e.printStackTrace();
		};
		
		//Start character array to byte array conversion
		CharBuffer charBuffer = CharBuffer.wrap(dataChars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] dataBytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        //Finish byte array conversion
		
        md.update(dataBytes); //Update digest with byte information
        byte[] mdbytes = md.digest(); //Construct hash
 
        //Convert 256 byte array to 64 hex string
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        System.out.println("Hex hash generation: " + sb.toString()+"\n");
		
		return sb.toString();
	}
	
	/**
	 * Function uses an AES 128 encryption algorithm w/ key to encrypt string data
	 * Used for storage of sensitive data in the database
	 * 
	 * @precondition loadKey() must have been successful
	 * 
	 * @param String data
	 * @return byte array containing encrypted data
	 * 
	 */
	
	public String encrypt(String data)
	{
		
		System.out.println("******************ENCRYPT******************");
			System.out.println("\nString input: "+data);
			Cipher c = null;
			
			try {
				
				c = Cipher.getInstance("AES");
				c.init(Cipher.ENCRYPT_MODE, key);
				System.out.println("Byte input: "+data.getBytes());
				System.out.println("Byte input length: "+data.getBytes().length);
				byte[] encVal = c.doFinal(data.getBytes());
				System.out.println("Byte output: "+encVal+"\n");
				
				//Convert 256 byte array to 64 hex string
				String hexString = DatatypeConverter.printHexBinary(encVal);
		 
		        System.out.println("AES encryption: " + hexString +"\n");
				
				return hexString;
				
			} catch (IllegalBlockSizeException | BadPaddingException | 
					NoSuchAlgorithmException | NoSuchPaddingException | 
					InvalidKeyException e) {
				
				e.printStackTrace();
				return null;
			}
	}
	
	/**
	 * Function uses an AES 128 decryption algorithm w/ key to decrypt string data
	 * Used for decryption of encrypted data stored in the database
	 * 
	 * @precondition loadKey() must have been successful
	 * 
	 * @param byte array containing encrypted data
	 * @return decrypted String of data
	 * 
	 */
	
	public String decrypt(String dataHex)
	{
		    //Decode hex string
			byte[] data = DatatypeConverter.parseHexBinary(dataHex);
		    //Decrypt data
		    System.out.println("******************DECRYPT******************");
			System.out.println("\nByte input: "+data);
			Cipher c = null;
			
			try {
				
				c = Cipher.getInstance("AES");
				c.init(Cipher.DECRYPT_MODE, key);
				byte[] decValue = c.doFinal(data);
				System.out.println("Byte output: "+decValue);
				String decryptedValue = new String(decValue);
				System.out.println("String output: "+decryptedValue+"\n");
				
				return decryptedValue;
				
			} catch (IllegalBlockSizeException | BadPaddingException | 
					NoSuchAlgorithmException | NoSuchPaddingException | 
					InvalidKeyException e) {
				
				e.printStackTrace();
				return null;
			}
		
	}
	
	/**
	 * Loads the key from the specified file and stores it in static key object
	 * This function is not to be used by the user
	 * 
	 * @precondition AES.key must be present in the same directory as DatabaseHandler.java
	 * 
	 * @throws IOException
	 * @return none
	 * 
	 */

	public void loadKey() throws IOException
	{
		System.out.println("\nLoading key...");
	    String data = new String(readFileToByteArray(keyFile));
	    byte[] encoded;
	    try {
	        encoded = decodeHex(data.toCharArray());
	    } catch (DecoderException e) {
	        e.printStackTrace();
	        return;
	    }
	    key = new SecretKeySpec(encoded, "AES");
	    System.out.println("Input key: "+key);
	    System.out.println("Key loaded!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object a = e.getSource();
		
		if(a == databaseTimer)
		{
			System.out.println("DATABASE CLOCK TICK");
			status = getConnectionStatus();
			System.out.println("CONNECTION STATUS: " + status);
		}
		
	}
	
}