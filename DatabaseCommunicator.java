package Shared;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;

import com.jcraft.jsch.*;

public class DatabaseCommunicator {

	/**
	 * This class allows a user, function or class to interact
	 * with a MySQL database using SQL statements
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	public Connection c = null;
	public Session session = null;
	public JSch jsch = null;
	public Channel channel = null;
	public InputStream in;
	
	/**
	 * Creates a new instance of DatabaseCommunicator and
	 * loads the driver to connect to the database
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
	}
	
	/**
	 * This functions identifies whether you are connected to a rutgers
	 * server and executes the correct connection handler
	 * 
	 * CURRENTLY ONLY WORKS FOR SERVERS WITHIN RUTGERS
	 * 
	 * @param sqlUser - Username to access the SQL database
	 * @param sqlPass - Password to access the SQL database
	 * @return exit code (0 = successful)
	 * 
	 */
	
	public int getConnection(String sqlUser, String sqlPass)
	{
		return getConnectionDirect(sqlUser,sqlPass);
	}
	
	/**
	 * THIS FUNCTION DOES NOT WORK. DO NOT USE
	 * 
	 * @return
	 * @throws IOException
	 */
	
	private int getConnectionSSH() throws IOException
	{
		//Return 0 = Successful connection
		//Return 1 = Connection attempt failed
		//Return 2 = Connection verification failed
		
		String sshIP = "172.16.60.193";
		String sshUser = "root";
		String sshPass = "gradMay17";
		
		String sqlIP = "jdbc:mysql://172.16.60.193/var/lib/mysql/";
		String sqlPort = "3306";
		String sqlUser = "root";
		String sqlPass = "gradMay17";
		
		 jsch = new JSch();//Define new SSH connector
		 
		 try {
			System.out.println("Attempting to create an SSH session (" + sshUser + "@" + sshIP + ")...");
			session = jsch.getSession(sshUser, sshIP);
		} catch (JSchException e) {
			System.out.println("SSH session creation failed!");
			e.printStackTrace();
			return 1;
		}
		 System.out.println("SSH session creation succeeded!");
		 session.setPassword(sshPass);
		 
		 Properties config = new Properties();
		 config.put("StrictHostKeyChecking", "no");
		 session.setConfig(config);
		 
		 try {
			System.out.println("Attempting to connect...");
			session.connect();
		} catch (JSchException e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
			return 1;
		}
		 
		 System.out.println("Connection successful!");

		try {
			System.out.println("Attempting to open a channel...");
			channel = (ChannelShell) session.openChannel("shell");
		} catch (JSchException e1) {
			System.out.println("Failed to open channel!");
			e1.printStackTrace();
			return 1;
		}
		
		System.out.println("Channel opened!");
		
		//***********************************************************
	   //try{
		    
		try {
			channel = session.openChannel("shell");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));  
		DataOutputStream out = new DataOutputStream(channel.getOutputStream());
		
		System.out.println(in);
		
	    try {
			channel.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    

	    // send ls command to the server  
	    out.writeBytes("ls");  
	    out.flush();  
	    
	    System.out.println(in);
	    
	    boolean flag = false;
	    byte[] tmp = new byte[1024];
	    String msg = "";
	    while(!flag){
	    	System.out.println();
	    	int str = in.read();
	    	if(str >= 0){
	    		String line = new String(tmp, 0, str);
	    		msg = msg + line;
	    	}
	    	else{
	    		System.out.println(msg);
	    		flag = true;
	    	}
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    /*
		  byte[] tmp = new byte[2048];
		  while (true)
		  {
		    while (in.available() > 0)
		    {
		      int i = in.read(tmp, 0, 2048);
		      if (i < 0)
		        break;
		      System.out.print(new String(tmp, 0, i));
		    }
		    if (channel.isClosed())
		    {
		      System.out.println("exit-status: " + channel.getExitStatus());
		      break;
		    }
		    try
		    {
		      Thread.sleep(1000);
		    }
		    catch (Exception ee)
		    {
		    }
		  }

		    in.close();  
		    out.close();  
		    channel.disconnect();  
		    session.disconnect();
		}
		catch (Exception e)
		{
		  System.out.println(e.getMessage());
		}
		
		*/
		
		   
		   /*
		
		
		 
		 try {
			System.out.println("Attempting to connect to channel...");
			channel.connect();
		} catch (JSchException e) {
			System.out.println("Failed to open channel!");
			e.printStackTrace();
			return 1;
		}
		 
		 System.out.println("Connected to channel!");

		 String msg=null;
		 
			while((msg=in.readLine())!=null){
			  System.out.println(msg);
			}
			
		
		/*
		try{
			String host = "jdbc:mysql://172.16.60.193/var/lib/mysql/";
			String uName = "root";
			String uPass = "gradMay17";
			System.out.println("Attempting connection...");
			c = DriverManager.getConnection(host, uName, uPass);
		}
		catch(SQLException err){
			System.out.println("Connection failed!");
			System.out.println(err.getMessage());
			err.printStackTrace();
			return 1;
		}
		
		System.out.println("Connection successful!");
		System.out.println("Verifying connection...");
		
		if (c != null) {
			
		    System.out.println("Connection confirmed! Ready to access database.");
		    return 0;
		    
		} else {
			
		    System.out.println("Failed to make connection!");
		    return 2;
		    
		}
		*/
			return 0;
	}
	
	/**
	 * This function sets up a connection to the MySQL Database
	 * 
	 * @param sqlUser - Username to use for MySQL Database
	 * @param sqlPass - Password to use for MySQL Database
	 * @return 0 - Successful connection
	 * @return 1 = Connect attempt failed (Bad user/pass or 
	 * 			   no connection to database available)
	 * @return 2 = Connection verification failed (The program
	 * 			   thought it connected, but didn't)
	 */
	
	private int getConnectionDirect(String sqlUser, String sqlPass)
	{
		//Return 0 = Successful connection
		//Return 1 = Connection attempt failed
		//Return 2 = Connection verification failed
		
		String sqlIP = "jdbc:mysql://172.16.60.193";
		
		 jsch = new JSch();//Define new SSH connector
		 
		try{
			System.out.println("Attempting connection to "
					+ sqlIP + "...\nUsername: " + sqlUser
					+ "\nPassword: " + sqlPass);
			c = DriverManager.getConnection(sqlIP, sqlUser, sqlPass);
		}
		catch(SQLException err){
			System.out.println("Connection failed!");
			System.out.println(err.getMessage());
			err.printStackTrace();
			return 1;
		}
		
		System.out.println("Connection successful!");
		System.out.println("Verifying connection...");
		
		if (c != null) {
			
		    System.out.println("Connection confirmed! Ready to access database.");
		    return 0;
		    
		} else {
			
		    System.out.println("Failed to make connection!");
		    return 2;
		    
		}
	}
	
	/**
	 * Sends a command to the SQL database
	 * 
	 * @param sqlCommand - SQL statement to be sent to the database
	 * @return ResultSet - The information block returned by the database
	 * 
	 */
	
	public ResultSet tell(String sqlCommand)
	{
		ResultSet rsfinal = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
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
		    
		    //Print table from ResultSet
		    printTable(rs);
		    
		    //Return ResultSet
		    rsfinal = rs;
		}
		catch (SQLException ex){
		    //Error handler
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {

		    if (rs != null) { //Close ResultSet resources
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } //Ignore Exception

		        rs = null;
		    }

		    if (stmt != null) { //Close Statement resources
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } //Ignore Exception

		        stmt = null;
		    }
		}
		
		return rsfinal;
	}
	
	/**
	 * Prints the table associated with the ResultSet
	 * 
	 * @param rs - ResultSet returned by tell() function
	 * @return none
	 */
	
	public void printTable(ResultSet rs)
	{
		try{
			
		ResultSetMetaData rsd = rs.getMetaData();
	    int colsize = rsd.getColumnCount();
	    
	    for(int i = 1; i <= colsize; i++){
	    	String r = rs.getString(i);
	    	System.out.println(r);
	    	}
	    
		}
	    catch(SQLException sqlEx){
	    	 //Error Handler
		    System.out.println("SQLException: " + sqlEx.getMessage());
		    System.out.println("SQLState: " + sqlEx.getSQLState());
		    System.out.println("VendorError: " + sqlEx.getErrorCode());
	    }
	}
	
}