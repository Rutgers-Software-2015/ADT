package Shared.Notifications;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import Shared.Gradients.GradientButton;
import Shared.Gradients.GradientLabel;
import Shared.Gradients.GradientPanel;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.LinkedList;

public class NotificationOverlay extends GradientPanel implements ActionListener{

	/**
	 * This panel overlays the Notification GUI, showing the Notification entries
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	private JLabel title;
	private GradientPanel panel;
	private GradientPanel titlePanel;
	private NotificationGUI parent;
	private JButton xButton;
	public GradientPanel displayPanel;
	private GradientButton sendMessageButton;
	private JScrollPane scroll;
	private LinkedList<NotificationEntry> NotificationList = new LinkedList<NotificationEntry>();
	public Timer timer;
	private int stat;
	private JButton refreshButton;

	public NotificationOverlay(NotificationGUI n)
	{
		super();
		parent = n;
		init();
		setNotificationTitle();
	}
	
	public void init()
	{
		this.setLayout(null);
		this.setBounds(0, 0, 402, 700);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		title();
		panel();
		NotificationList = parent.c.getNotifications();
		setDisplay();
		// Create a timer to update the clock
		timer = new Timer(10000,this);
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.setInitialDelay(0);
		timer.start();
		
		this.setVisible(false);
	}
	
	public void title()
	{
		titlePanel = new GradientPanel();
		titlePanel.setGradient(Color.white,new Color(240,240,240));
		titlePanel.setBounds(0, 0, 402, 56);
		add(titlePanel);
		titlePanel.setLayout(null);
		title = new JLabel("Notifications");
		title.setBounds(0, 0, 402, 56);
		titlePanel.add(title);
		title.setBorder(BorderFactory.createLineBorder(Color.black));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(30f));
		title.setOpaque(false);
		title.setVisible(true);
	}
	
	public void panel()
	{
		panel = new GradientPanel();
		panel.setGradient(Color.white,Color.white.darker());
		panel.setBounds(0,56,402,700-56);
		panel.setVisible(true);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		xButton = new JButton("");
		xButton.addActionListener(this);
		xButton.setIcon(new ImageIcon(NotificationOverlay.class.getResource("/Shared/Notifications/redx.jpg")));
		xButton.setBorder(BorderFactory.createEmptyBorder());
		xButton.setBounds(370, 1, 30, 30);
		panel.add(xButton);
		
		sendMessageButton = new GradientButton("Send Message");
		sendMessageButton.addActionListener(this);
		sendMessageButton.setBounds(63, 6, 284, 23);
		sendMessageButton.setFocusPainted(false);
		panel.add(sendMessageButton);
		
		displayPanel = new GradientPanel();
		displayPanel.setPreferredSize(new Dimension(402,580));
		displayPanel.setGradient(Color.gray.brighter());
		displayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		scroll = new JScrollPane(displayPanel);
		displayPanel.setLayout(null);
		scroll.setBounds(0, 33, 402, 611);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		panel.add(scroll);
		
		refreshButton = new JButton("");
		refreshButton.setIcon(new ImageIcon(NotificationOverlay.class.getResource("/Shared/Notifications/refresh.jpg")));
		refreshButton.setBorder(BorderFactory.createEmptyBorder());
		refreshButton.setBounds(2, 1, 30, 30);
		refreshButton.addActionListener(this);
		panel.add(refreshButton);
		
		this.add(panel);
	}
	
	public void setDisplay()
	{
		this.setVisible(false);
		displayPanel.removeAll();
		if(NotificationList != null){
			int size = NotificationList.size();
			if(size != 0){
				displayPanel.setPreferredSize(new Dimension(402,60*size));
				displayPanel.setBounds(0,0,402,60*size+10);
				for(int i = 0; i < size; i++){
					NotificationEntry tempEntry = NotificationList.get(i);
					tempEntry.setBounds(0,i*60,402,60);
					tempEntry.addMouseListener(parent);
					displayPanel.add(tempEntry);
				}
			}
		}
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		Object a = arg0.getSource();
		
		if(a == xButton){
			setNotificationTitle();
			return;
			}
		if(a == sendMessageButton){
			Object[] options = {"Public",
            "Private"};
			int n = JOptionPane.showOptionDialog(null,
				    "Is this a public or private message?",
				    "Message Destination",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,     //do not use a custom Icon
				    options,  //the titles of buttons
				    options[0]); //default button title
			int sent = 1;
			if(n == 1){
				JTextField empID = new JTextField();
				JTextField text = new JTextField();
				Object[] message = {
					    "Employee ID:", empID,
					    "Message:", text
					};
				int option = JOptionPane.showConfirmDialog(null, message, "New Message", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					sent = parent.c.sendMessage(empID.getText(),text.getText(),1);
				}
				else{
					return;
				}
			}
			if(n == 0){
				JTextField actorClass = new JTextField();
				JTextField text = new JTextField();
				Object[] message = {
					    "Employee Class:", actorClass,
					    "Message:", text
					};
				int option = JOptionPane.showConfirmDialog(null, message, "New Message", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					sent = parent.c.sendMessage(actorClass.getText(),text.getText(),0);
				}
				else{
					return;
				}
			}
			if(sent == 0){
				JOptionPane.showMessageDialog(null, "Message Sent!","Success", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "Message failed to send. Please try again.","Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(a == refreshButton){
			if(parent.c.getConnectionStatus() == 0){
				System.out.println("REFRESH NOTIFICATION BAR");
				NotificationList = parent.c.getNotifications();
				setDisplay();
			}
			else{
				System.out.println("FAILED TO REFRESH - BAD CONNECTION");
			}
		}
		if(a == timer){
			if(parent.c.getConnectionStatus() == 0){
				System.out.println("NOTIFICATION CLOCK TICK");
				NotificationList = parent.c.getNotifications();
				setDisplay();
			}
			else{
				System.out.println("NOTIFICATION TICK DISABLED - BAD CONNECTION");
			}
			if(parent.openState == false){
				setNotificationTitle();
			}
		}
		
	}
	
	public void setNotificationTitle()
	{
		parent.setVisible(false);
		parent.setBounds(792, 0, 402, 56);
		this.setVisible(false);
		parent.openState = false;
		parent.clearBackground();
		parent.setVisible(true);
		MouseEvent me = new MouseEvent(parent, 0, 0, 0, 100, 100, 1, false);
		for(MouseListener ml: parent.getMouseListeners()){
		    ml.mouseEntered(me);
		    ml.mouseExited(me);
		}
	}
}
