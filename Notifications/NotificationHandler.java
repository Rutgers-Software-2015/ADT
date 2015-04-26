package Shared.Notifications;

import java.sql.ResultSet;
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
	 * @tester Samuel Baysting
	 * @debugger Samuel Baysting
	 * 
	 */
	
	private String actor = null;
	private int empID = 0;
	
	/**
	 * Class constructor - Will intialize database communicator
	 * 
	 * @param emp = Employee ID number associated with logged in account (1, 2, 3, etc)
	 * @param actorfiled = Employee class associated with logged in account (Waiter, Host, Manager, etc)
	 * 
	 */
	
	public NotificationHandler(int emp, String actorfield)
	{
		super();
		empID = emp;
		actor = actorfield;
		System.out.println("LISTENING FOR NOTIFICATIONS ON EMPID = " + emp + " AND ACTOR = " + actorfield);
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
		if(getConnectionStatus()!=0){
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
	 * @return LinkedList<NotificationEntry> with notifications
	 * @return Empty LinkedList<NotificationEntry> if no notifications
	 * @return null if no connection
	 * 
	 */
	
	public LinkedList<NotificationEntry> getNotifications()
	{
		if(status == 0){ //If connection valid
			LinkedList<NotificationEntry> notifications = new LinkedList<NotificationEntry>();
			String s = "SELECT * FROM MAINDB.Notifications WHERE actor = \""+actor+"\" OR id = "+empID+" OR actor = \"All\";";
			ResultSet rs = tell(s);
			
			try{

				if(rs != null){
					
					rs.first();
					System.out.println("NOTIFICATION ENTRIES RECEIVED");
					do{
					if((Integer)Integer.parseInt(rs.getString(1)) != (Integer)0){
						notifications.add(new NotificationEntry(rs.getString(1),rs.getString(3),this));
						}
					else{
						notifications.add(new NotificationEntry(rs.getString(2),rs.getString(3),this));
					}
					}while(rs.next());
					
					return notifications;
				}					
				
				System.out.println("NO NOTIFICATIONS RECEIVED");
				return notifications;
				
				}
			    catch(SQLException sqlEx){
			    	 //Error Handler
				    System.out.println("SQLException: " + sqlEx.getMessage());
				    System.out.println("SQLState: " + sqlEx.getSQLState());
				    System.out.println("VendorError: " + sqlEx.getErrorCode());
			    }
				
		}
		
		System.out.println("NO DATABASE CONNECTION");
		return null;
	}
	
	/**
	 * This function will create and store a message in the notification table 
	 * within the database (a.k.a send a message to another person or group)
	 * 
	 * @param target - who is it going to (Waiter, Customer, KitchenStaff, Host, Busboy, Manager, or ID number)
	 * @param message - the message to send
	 * @param type - private (1) or public (0) message
	 * @return 0 - success
	 * @return 1 - failure
	 * 
	 */
	
	public int sendMessage(String target, String message, int type)
	{
		if(type == 1){
			try{
				int empID = (Integer)Integer.parseInt(target);
				String s = "INSERT INTO MAINDB.Notifications (id,actor,message) VALUES ("+empID+",null,\""+message+"\");";
				update(s);
				return 0;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Employee ID must be an integer. Please try again.","Error", JOptionPane.ERROR_MESSAGE);
				return 1;
			}
		}
		if(type == 0){
			String s = "INSERT INTO MAINDB.Notifications (id,actor,message) VALUES (0,\""+target+"\",\""+message+"\");";
			update(s);
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * This function acknowledges the message and removes it from the database
	 * 
	 * @param entry - which entry to remove
	 * @return 0 - success
	 * @return 1 - failure
	 * 
	 */
	public int removeMessage(NotificationEntry entry)
	{
		if(entry.actorClass != null){
			String s = "DELETE FROM MAINDB.Notifications WHERE actor=\""+entry.actorClass+"\" AND message=\""+entry.message+"\";";
			update(s);
		}
		else{
			String s = "DELETE FROM MAINDB.Notifications WHERE id="+entry.employeeID+" AND message=\""+entry.message+"\";";
			update(s);
		}
		
		return 0;
		
	}

}
