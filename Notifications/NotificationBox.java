package Shared.Notifications;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import Shared.Gradients.GradientPanel;

public class NotificationBox extends GradientPanel implements MouseListener,ActionListener{

	/**
	 * This class facilitates notification interaction between the user and
	 * the system
	 * 
	 * @author Samuel Baysting
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
	 * @param none
	 * 
	 */
	
	public NotificationBox()
	{
		super();
		init();
		c = new NotificationHandler();
		c.connect(sqlUser,sqlPass);
		c.getNotifications();
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
	 * 
	 * @param none
	 * @return none
	 * 
	 */
	
	public void close()
	{
		c.disconnect();
		c = null;
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
		System.out.println("MOUSE CLICK");
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
			System.out.println("openState = " + openState);
			return;
		}
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
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		if(openState == false){
		title.setVisible(false);
		titleFailed.setVisible(false);
		this.clearBackground();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		if(openState == false){
		this.setOpaque(true);
		this.setGradient(new Color(240,240,240),Color.white);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		if(openState == false){
		this.setOpaque(true);
		this.setGradient(Color.white,new Color(240,240,240));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		}
	}
	
	/**
	 * Action Listener implementation
	 * 
	 * @param none
	 * @return none
	 * 
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		Object a = e.getSource();
		
		//if(a == timer)
		//{
			
		//}
		
	}
	
}
