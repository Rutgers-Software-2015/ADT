package Shared.Notifications;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import Shared.Gradients.GradientPanel;

import javax.swing.SwingConstants;

import java.awt.Font;

public class NotificationEntry extends GradientPanel implements MouseListener{

	
	/**
	 * Custom ADT to assist with reading/writing notification entries
	 * 
	 * @author Samuel Baysting
	 * @tester Samuel Baysting
	 * @debugger Samuel Baysting
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private NotificationHandler parent = null;
	public int employeeID = 0;
	public String actorClass = null;
	public String message = null;
	private Color color1 = Color.white;
	private Color color2 = new Color(215,215,215);
	private JLabel notificationLabel;
	private JLabel notificationTitle;
	
	/**
	 * Class constructor
	 * 
	 * @param actororID - destination for the message
	 * @param message2 - message to be read
	 * @param parent - NotificationHandler
	 * @return none
	 * 
	 */
	public NotificationEntry(String actororID, String message2, NotificationHandler parent)
	{
		super();
		this.parent = parent;
		try{ employeeID = (Integer)Integer.parseInt(actororID); }
		catch(Exception e){ actorClass = (String)actororID; }
		message = message2;
		init();
	}
	
	/**
	 * Initialize the notification entry panel and set parameters
	 * 
	 * @return none
	 * 
	 */

	public void init()
	{
		this.setLayout(null);
		this.addMouseListener(this);
		this.setPreferredSize(new Dimension(402, 60));
		this.setMinimumSize(new Dimension(402, 60));
		this.setMaximumSize(new Dimension(402, 60));
		this.setGradient(color1,color2);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		box();
		this.setVisible(true);
	}
	
	/**
	 * Sets the size and font of the notification entry box
	 * 
	 * @return none
	 * 
	 */
	
	public void box()
	{
		notificationTitle = new JLabel("Test");
		notificationTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
		notificationTitle.setHorizontalAlignment(SwingConstants.CENTER);
		if(actorClass != null){
			if(actorClass.toUpperCase().contains("ALL") == true){
				notificationTitle.setText("Message to all employees:");
			}
			else{
				notificationTitle.setText("Message to all " + this.actorClass + "s:");
			}
		}
		if(actorClass == null){
			notificationTitle.setText("Private message to Employee " + this.employeeID + ":");
		}
		notificationTitle.setBounds(0,0,402,20);
		notificationLabel = new JLabel(this.message);
		notificationLabel.setVerticalAlignment(SwingConstants.TOP);
		notificationLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notificationLabel.setBounds(0,26,402,34);
		notificationTitle.setVisible(true);
		notificationLabel.setVisible(true);
		this.add(notificationTitle);
		this.add(notificationLabel);
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
		
		JLabel greeting = new JLabel();
		greeting.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabel messageText = new JLabel(this.message);
		JLabel ack = new JLabel("Acknowledge Notification?");
		ack.setFont(new Font("Tahoma",Font.BOLD,13));
		if(actorClass != null){
			if(this.actorClass.toUpperCase().contains("ALL") == true){
				greeting.setText("<html>Message to all employees:<br></html>");
			}
			else{
				greeting.setText("<html>Message to all " + this.actorClass + "s:<br></html>");
			}
		}
		if(this.actorClass == null){
			greeting.setText("<html>Private message to Employee " + this.employeeID + ":<br></html>");
		}
		Object[] message = {
			    greeting, messageText,
			    ack
			};
		int option = JOptionPane.showConfirmDialog(null, message, "Confirmation", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			System.out.println("REMOVE NOTIFICATION ITEM TRIGGERED");
			parent.removeMessage(this);
		}
		else{
			return;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setGradient(color2,color1);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		this.setGradient(color1,color2);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		this.setGradient(color2.darker(),color1.darker());
		this.setBorder(BorderFactory.createLoweredBevelBorder());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		this.setGradient(color2,color1);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
