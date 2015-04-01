import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Login.LoginWindow;
import Shared.Gradients.*;


public class MainTemplate extends JFrame implements ActionListener{

		//Swing Variables
		private JPanel rootPanel,titlePanel;
		private GradientPanel backgroundPanel;
		private GradientButton logoutButton;
		//private GradientButton logoutButton;
		private JLabel titleLabel,dateAndTime;
		//Other Variables
		private Timer timer;
		private JPanel buttonPanel;
		private GradientButton Button1;
		private GradientButton Button2;
		private GradientButton Button3;
		private GradientButton Button4;
		
		
		public MainTemplate()
		{
			super();
			init();
		}


		public void init()
		{
			this.setTitle("Your GUI");
			this.setVisible(false);
			this.setResizable(true);
			this.setSize(1200,700);
			this.frameManipulation();
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			this.setResizable(false);
			getContentPane().add(rootPanel);
			
			 addWindowListener(new WindowAdapter() // To open login again if you hit the corner "X"
		        {
		            @Override
		            public void windowClosing(WindowEvent e)
		            {
		                new LoginWindow();
		                dispose();
		            }
		        });
			
			this.setVisible(true);
		}

		public void frameManipulation()
		{
			setBackgroundPanel();
			setTitlePanel();
			setButtonPanel();
			setRootPanel();
		}
		
		private void setRootPanel()
		{
			rootPanel = new JPanel();
			rootPanel.setLayout(null);
			rootPanel.add(titlePanel);
			rootPanel.add(buttonPanel);
			rootPanel.add(backgroundPanel);
			rootPanel.setVisible(true);
		}
		
		private void setBackgroundPanel()
		{
			// Create Background Panel
			backgroundPanel = new GradientPanel();
			backgroundPanel.setGradient(Color.white,new Color(153,230,255));
			//backgroundPanel.setBrightness(backgroundPanel.getColor2(),1);
			backgroundPanel.setLayout(null);
			backgroundPanel.setBounds(0,0,1200,700);
			
			logoutButton = new GradientButton("LOGOUT");
			logoutButton.setBounds(187, 550, 825, 66);
			logoutButton.setFocusPainted(false);
			logoutButton.setFont(logoutButton.getFont().deriveFont(16.0f));
			backgroundPanel.add(logoutButton);
			logoutButton.addActionListener(this);
			
			backgroundPanel.setVisible(true);
		}
		
		private void setTitlePanel()
		{
			// Create Title Panel
			titlePanel = new JPanel();
			titlePanel.setLayout(null);
			titlePanel.setOpaque(false);
			titlePanel.setBounds(new Rectangle(0, 0, 1200, 56));
			// Set Title
			titleLabel = new JLabel("Your Main Window Interface");
			titleLabel.setHorizontalAlignment(JLabel.CENTER);
			titleLabel.setFont(titleLabel.getFont().deriveFont(38f));
			titleLabel.setBorder(BorderFactory.createLineBorder(Color.black));
			titleLabel.setBounds(new Rectangle(0, 0, 793, 56));
			// Create a timer to update the clock
			timer = new Timer(500,this);
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
            timer.addActionListener(this);
            timer.start();

			// Add components to Title Panel
			titlePanel.add(titleLabel);
			// Set Date and Time
			dateAndTime = new JLabel();
			dateAndTime.setBounds(792, 0, 402, 56);
			titlePanel.add(dateAndTime);
			dateAndTime.setHorizontalAlignment(JLabel.CENTER);
			dateAndTime.setFont(dateAndTime.getFont().deriveFont(28f));
			dateAndTime.setBorder(BorderFactory.createLineBorder(Color.black));
			
			titlePanel.setVisible(true);
		}
		
		private void setButtonPanel()
		{
			buttonPanel = new JPanel();
			buttonPanel.setBounds(200, 105, 800, 388);
			buttonPanel.setLayout(new GridLayout(2, 2, 150, 100));
			
			Button1 = new GradientButton("New button");
			Button1.setFont(Button1.getFont().deriveFont(16.0f));
			Button1.addActionListener(this);
			buttonPanel.add(Button1);
			
			Button2 = new GradientButton("New button");
			Button2.setFont(Button2.getFont().deriveFont(16.0f));
			buttonPanel.add(Button2);
			
			Button3 = new GradientButton("New button");
			Button3.setFont(Button3.getFont().deriveFont(16.0f));
			buttonPanel.add(Button3);
			
			Button4 = new GradientButton("New button");
			Button4.setFont(Button4.getFont().deriveFont(16.0f));
			buttonPanel.add(Button4);
			
			buttonPanel.setOpaque(false);
			buttonPanel.setVisible(true);
			
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			Object a = e.getSource();
			if(a == Button1)
			{
				new SubframeTemplate();
				dispose();
			}
			if(a == logoutButton)
				{
					new LoginWindow();
					dispose();
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
