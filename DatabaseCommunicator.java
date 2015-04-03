package Shared;

import java.sql.*;

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
	
	protected int connect(String sqlUser, String sqlPass)
	{
		
		String sqlIP = "jdbc:mysql://172.16.60.193";
		 
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
	 * This function disconnects the client from the SQL database
	 * 
	 * @param none
	 * @return none
	 * 
	 */
	
	protected void disconnect()
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
		
		return;
	}
	
	/**
	 * Sends a command to the SQL database
	 * 
	 * Accepts queries only, no database changing commands
	 * 
	 * @param sqlCommand - SQL statement to be sent to the database
	 * @return ResultSet - The information block returned by the database
	 * 
	 */
	
	protected ResultSet tell(String sqlCommand)
	{
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
		    
		}
		catch (SQLException ex){
		    //Error handler
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		
		//Return ResultSet
		   return rs;
	}
	
	/**
	 * Sends a command to the SQL database
	 * 
	 * Accepts database changing commands
	 * 
	 * @param sqlCommand - SQL statement to be sent to the database
	 * @return row count (if applicable) or 0 (if nothing)
	 * 
	 */
	
	protected int update(String sqlCommand)
	{
		Statement stmt = null;
		
		try {
		    stmt = c.createStatement();
		    return(stmt.executeUpdate(sqlCommand));
		    
		}
		catch (SQLException ex){
		    //Error handler
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return -1;
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
	
	public void consolePrintTable(ResultSet rs)
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
	}
	
}