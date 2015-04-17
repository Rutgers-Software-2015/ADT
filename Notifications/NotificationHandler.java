package Shared.Notifications;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import Shared.Communicator.DatabaseCommunicator;

public class NotificationHandler extends DatabaseCommunicator{
	
	
	/**
	 * This class facilitates notification interaction between the system
	 * and the database
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	private String test;
	public LinkedList notifications = new LinkedList();
	
	/**
	 * Class constructor - Will intialize database communicator
	 * 
	 * @param none
	 * 
	 */
	
	public NotificationHandler()
	{
		super();
	}
	
	/**
	 * Function will display reconnect dialog and attempt database
	 * reconnect
	 * 
	 * @param none
	 * @return 0 - connection successful 
	 * @return 1 or 2 - connection failed
	 * 
	 */
	
	public int reconnect()
	{
		if(status!=0){
		JOptionPane.showMessageDialog(null, "Attempting connection to database...","InfoBox", JOptionPane.INFORMATION_MESSAGE);
		int exit = connect(sqlUser,sqlPass);
		if(exit == 0){
			JOptionPane.showMessageDialog(null, "Database connection successful!","InfoBox", JOptionPane.INFORMATION_MESSAGE);
			return 0;
		}
		JOptionPane.showMessageDialog(null, "Database connection failed!","InfoBox", JOptionPane.ERROR_MESSAGE);
		return exit;
		}
		return 0;
	}
	
	/**
	 * This function will access and retrieve all notifications relevant to
	 * the current user from the database
	 * 
	 * @return number of new notifications
	 * @return -1 if no connection
	 * 
	 */
	
	public int getNotifications()
	{
		if(status == 0){ //If connection valid
			int counter = 0;
			ResultSet rs = tell("SELECT * FROM MAINDB.Notifications WHERE actor = \"Waiter\" OR id = 1");
			
			try{
			    
				if(rs != null){
					
					do{
						notifications.add(rs.getString(3));
						System.out.println(rs.getString(3));
						counter++;
					}while(rs.next());
			    
					}
				}
			    catch(SQLException sqlEx){
			    	 //Error Handler
				    System.out.println("SQLException: " + sqlEx.getMessage());
				    System.out.println("SQLState: " + sqlEx.getSQLState());
				    System.out.println("VendorError: " + sqlEx.getErrorCode());
			    }
				
				return counter;
		}
		
		return 0;
	}

}
