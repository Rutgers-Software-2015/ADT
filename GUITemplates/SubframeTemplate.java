
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Login.LoginWindow;
import Shared.Gradients.*;

import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;



public class SubframeTemplate extends JFrame implements ActionListener{

	
		//Parent Panels
		private JPanel rootPanel,titlePanel,buttonPanel;
		private GradientPanel backgroundPanel,buttonPanelBackground,cardPanel;
		private GradientPanel card1,card2,card3;
		//Swing Objects
		private GradientButton backButton,statusButton,orderQueueButton,acceptPaymentButton,refundButton;
		private JButton payWithCash,payWithCard;
		private JLabel titleLabel,dateAndTime;
		//Swing Layouts
		private CardLayout c;
		//Other Variables
		private Timer timer;
		
		
		public SubframeTemplate()
		{
			super();
			init();
		}


		public void init()
		{
			this.setTitle("Manage Tables");
			this.setResizable(true);
			this.setSize(1200,700);
			this.frameManipulation();
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			this.setResizable(false);
			getContentPane().add(rootPanel);
			
			addWindowListener(new WindowAdapter() // To open main window again if you hit the corner "X"
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	                new MainTemplate();
	                dispose();
	            }
	        });
			
			c = (CardLayout)(cardPanel.getLayout());
			
			this.setVisible(true);
		}

		public void frameManipulation()
		{
			rootPanel = new JPanel();
			rootPanel.setLayout(null);
			setBackgroundPanel();
			setTitlePanel();
			setCardPanel();
			setButtonPanel();
			setRootPanel();
		}
		
		private void setRootPanel()
		{
			rootPanel.add(titlePanel);
			rootPanel.add(cardPanel);
			rootPanel.add(buttonPanel);
			rootPanel.add(buttonPanelBackground);
			rootPanel.add(backgroundPanel);
		}
		
		private void setBackgroundPanel()
		{
			// Create Button Background Panel
			buttonPanelBackground = new GradientPanel();
			buttonPanelBackground.setGradient(new Color(255,220,220), new Color(255,110,110));
			buttonPanelBackground.setBounds(0, 55, 250, 617);
			buttonPanelBackground.setBorder(new LineBorder(new Color(0, 0, 0)));
			
			// Create Background Panel
			backgroundPanel = new GradientPanel();
			backgroundPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			backgroundPanel.setGradient(new Color(255,255,255), new Color(255,110,110));
			backgroundPanel.setLayout(null);
			backgroundPanel.setBounds(0,0,1194,672);
		}
		
		private void setTitlePanel()
		{
			// Create Title Panel
			titlePanel = new JPanel();
			titlePanel.setBounds(0, 0, 1194, 56);
			titlePanel.setLayout(null);
			titlePanel.setOpaque(false);
			// Set Title
			titleLabel = new JLabel("Table Management Interface");
			titleLabel.setHorizontalAlignment(JLabel.CENTER);
			titleLabel.setFont(titleLabel.getFont().deriveFont(38f));
			titleLabel.setBorder(BorderFactory.createLineBorder(Color.black));
			titleLabel.setBounds(new Rectangle(0, 0, 793, 56));
			
						// Add components to Title Panel
						titlePanel.add(titleLabel);
						// Set Date and Time
						dateAndTime = new JLabel();
						dateAndTime.setBounds(792, 0, 402, 56);
						titlePanel.add(dateAndTime);
						dateAndTime.setHorizontalAlignment(JLabel.CENTER);
						dateAndTime.setFont(dateAndTime.getFont().deriveFont(28f));
						dateAndTime.setBorder(BorderFactory.createLineBorder(Color.black));
						updateClock();
						// Create a timer to update the clock
						timer = new Timer(500,this);
			            timer.setRepeats(true);
			            timer.setCoalesce(true);
			            timer.setInitialDelay(0);
			            timer.start();
		}
		
		private void setButtonPanel()
		{
			// Create Button Panel
			buttonPanel = new JPanel();
			buttonPanel.setBounds(7, 61, 236, 604);
			buttonPanel.setOpaque(false);
			buttonPanel.setBorder(null);
			buttonPanel.setLayout(new GridLayout(5, 0, 5, 5));
			
			// Set Request Table Status Change Button
			statusButton = new GradientButton("<html>Request Table<br />Status Change</html>");
			statusButton.addActionListener(this);
			statusButton.setFont(statusButton.getFont().deriveFont(16.0f));
			statusButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			statusButton.setFocusPainted(false);
			
			// Set Manage Order Queue Button
			orderQueueButton = new GradientButton("Manage Order Queue");
			orderQueueButton.addActionListener(this);
			orderQueueButton.setFont(orderQueueButton.getFont().deriveFont(16.0f));
			orderQueueButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			orderQueueButton.setFocusPainted(false);
			
			// Set Accept Payment Button
			acceptPaymentButton = new GradientButton("Accept Payment");
			acceptPaymentButton.addActionListener(this);
			acceptPaymentButton.setFont(acceptPaymentButton.getFont().deriveFont(16.0f));
			acceptPaymentButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			acceptPaymentButton.setFocusPainted(false);
			
			// Set Request Refund Button
			refundButton = new GradientButton("Request Refund");
			refundButton.addActionListener(this);
			refundButton.setFont(refundButton.getFont().deriveFont(16.0f));
			refundButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			refundButton.setFocusPainted(false);
			// Set Back Button
			backButton = new GradientButton("BACK");
			backButton.addActionListener(this);												
			backButton.setFont(backButton.getFont().deriveFont(16.0f));
			backButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			backButton.setFocusPainted(false);
			
			buttonPanel.add(statusButton);
			buttonPanel.add(orderQueueButton);
			buttonPanel.add(acceptPaymentButton);
			buttonPanel.add(refundButton);
			buttonPanel.add(backButton);
		}
		
		private void setCardPanel()
		{
			cardPanel = new GradientPanel();
			cardPanel.setLayout(new CardLayout()); // How to define a Card Layout
			cardPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
			cardPanel.setGradient(new Color(255,255,255), new Color(255,110,110));
			cardPanel.setBounds(273, 79, 896, 569);
			
			card1 = new GradientPanel(); // Create card with a button YES
			card1.add(new JButton("YES"));
			card1.setLayout(new GridLayout(1,0));
			
			card2 = new GradientPanel(); // Create card with a button NO
			card2.setLayout(new GridLayout(1,0));
			card2.add(new JButton("NO"));
			
			card3 = new GradientPanel(); // Create blank card
			
			cardPanel.add(card1,"YES"); // How to add cards to a Card Layout
			cardPanel.add(card2,"NO"); // The string can be named anything, the string is how you call the card
			cardPanel.add(card3,"BLANK");
			
			cardPanel.setVisible(true);
		}
		
		// Action Listener
		public void actionPerformed(ActionEvent e) 
		{
			Object a = e.getSource();
			if(a == backButton)
				{
					new MainTemplate();
					dispose();
				}
			if(a == statusButton)
				{
			    c.show(cardPanel, "YES"); //Example of how to show card panel
				}
			if(a == acceptPaymentButton)
				{
			    c.show(cardPanel, "NO"); //Example of how to show card panel
				}
			if(a == refundButton)
				{
				c.show(cardPanel, "BLANK"); //Example of how to show card panel
				}
			if(a == orderQueueButton)
				{
				
				
				}
			if(a == payWithCash)
				{

				}
			if(a == payWithCard)
				{

				}
			if(a == timer)
				{
					updateClock();
				}
		}
		
		private void updateClock() {
            dateAndTime.setText(DateFormat.getDateTimeInstance().format(new Date()));
        }
}
