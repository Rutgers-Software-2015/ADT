package Shared.Notifications;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import Shared.Gradients.GradientPanel;

@SuppressWarnings("serial")
public class NotificationGUI extends GradientPanel implements MouseListener{

	/**
	 * This class facilitates notification interaction between the user and
	 * the system
	 * 
	 * @author Samuel Baysting
	 * @tester Samuel Baysting
	 * @debugger Samuel Baysting
	 * 
	 */
	
	private JLabel title;
	private JLabel titleFailed;
	public NotificationHandler c;
	private String sqlUser = "admin";
	private String sqlPass = "gradMay17";
	private NotificationOverlay n;
	public boolean openState = false;
	
	/**
	 * Class constructor - Will run the JPanel super constructor
	 * and run the setup function for the JPanel
	 * 
	 * @param empID = your assigned employee ID in integer representation
	 * @param actor = your assigned actor, which is one of six choices:
	 *                Waiter, Busboy, Manager, KitchenStaff, Customer, Host
	 * 
	 */
	
	public NotificationGUI(int empID, String actor)
	{
		super();
		c = new NotificationHandler(empID,actor);
		c.connect(sqlUser,sqlPass);
		init();
	}
	
	/**
	 * Initializes the JFrame with all set parameters
	 * 
	 * @param none
	 * @return none
	 * 
	 */
	
	private void init()
	{
		n = new NotificationOverlay(this);
		this.add(n);
		this.setLayout(null);
		this.addMouseListener(this);
		this.setBounds(792, 0, 402, 56);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		title();
		this.clearBackground();
		this.setVisible(true);
	}
	
	/**
	 * Set up Notification bar messages
	 * 
	 * @param none
	 * @return none
	 * 
	 */
	
	private void title()
	{
		title = new JLabel("Notifications");
		title.setBounds(0,0,402,56);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(30f));
		title.setOpaque(false);
		title.setVisible(false);
		this.add(title);
		titleFailed = new JLabel("<html>Notifications Disabled: Connection Failed<br>"
				+ "<center>Click to attempt reconnection...</center></html>");
		titleFailed.setBounds(0,0,402,56);
		titleFailed.setHorizontalAlignment(JLabel.CENTER);
		titleFailed.setFont(titleFailed.getFont().deriveFont(16f));
		titleFailed.setOpaque(false);
		titleFailed.setVisible(false);
		this.add(titleFailed);
	}
	
	/**
	 * Closes and nulls the handler/communicator
	 * Class will have to be re-initialized after calling this function
	 * to continue functioning correctly
	 * 
	 * @param none
	 * @return none
	 * 
	 */
	
	public void close()
	{
		System.out.println("Disabling notification clock...");
		n.timer.stop();
		n = null;
		c.disconnect();
		c = null;
	}
	
	/**
	 * Sends a message to be stored in the database and retrieved by
	 * another user
	 * 
	 * @param target - Destination of the message. These are your options:
	 * 				   - An employee ID number string to send a message privately to another employee
	 * 					    - Example: 1,2, 45, 8 (any integer > 0)
	 * 				   - An actor class to display the message to a group of people
	 * 						- Options for actors: Waiter, KitchenStaff, Host, Busboy, Customer, Manager, All
	 * @param message - String with the message you want to send
	 * @return exit code 0 - success
	 * @return exit code 1 - failure
	 * 
	 */
	
	public int sendMessage(String target, String message){
		try{ 
			Integer.parseInt(target); 
			return c.sendMessage(target,message,1);
		}
		catch(Exception e){ 
			return c.sendMessage(target,message,0); 
		}
	}
	
	/**
	 * Mouse Listener implementation
	 * 
	 * @param none
	 * @return none
	 * 
	 */

	@Override
	public void mouseClicked(MouseEvent arg0) {

		if(openState == false){
		int stat = c.getConnectionStatus();
		System.out.println("CONNECTION STATUS: " + stat);
		if(stat != 0){
			c.reconnect();
		}
		
		if(stat == 0 && openState == false){
			this.setVisible(false);
			this.setBounds(792, 0, 402, 700);
			n.setVisible(true);
			this.setVisible(true);
			openState = true;
			return;
		}
		}
		else{
			
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(openState == false){
		this.setOpaque(true);
		if(c.status == 0){
			title.setVisible(true);
		}
		else{
			titleFailed.setVisible(true);
		}
		this.setGradient(Color.white,new Color(240,240,240));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		else{
			
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		try 
		{
			if(openState == false){
				title.setVisible(false);
				titleFailed.setVisible(false);
				this.clearBackground();
			}
			else{
			
			}
		}
		catch(NullPointerException e)
		{
			
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		if(openState == false){
		this.setOpaque(true);
		this.setGradient(new Color(240,240,240),Color.white);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		}
		else{
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		if(openState == false){
		this.setOpaque(true);
		this.setGradient(Color.white,new Color(240,240,240));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		else{
			
		}
	}
	
}
