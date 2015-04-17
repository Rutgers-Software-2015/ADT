package Shared.Communicator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Shared.Gradients.GradientPanel;

public class NotificationGUI extends GradientPanel implements MouseListener,ActionListener{

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
	
	/**
	 * Class constructor - Will run the JPanel super constructor
	 * and run the setup function for the JPanel
	 * 
	 * @param none
	 * 
	 */
	
	public NotificationGUI()
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
		this.addMouseListener(this);
		this.setBounds(792, 0, 402, 56);
		this.clearBackground();
		title();
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
		title.setBounds(0,0,1000,1000);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(30f));
		title.setOpaque(false);
		title.setVisible(false);
		this.add(title);
		titleFailed = new JLabel("<html>Notifications Disabled: Connection Failed<br>"
				+ "<center>Click to attempt reconnection...</center></html>");
		titleFailed.setBounds(0,0,1000,1000);
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

		int stat = c.getConnectionStatus();
		System.out.println("CONNECTION STATUS: " + stat);
		if(stat != 0){
			c.reconnect();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
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

	@Override
	public void mouseExited(MouseEvent arg0) {

		title.setVisible(false);
		titleFailed.setVisible(false);
		this.clearBackground();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		this.setOpaque(true);
		this.setGradient(new Color(240,240,240),Color.white);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		this.setOpaque(true);
		this.setGradient(Color.white,new Color(240,240,240));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
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
