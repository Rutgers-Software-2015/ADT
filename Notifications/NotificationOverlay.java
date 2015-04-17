package Shared.Notifications;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import Shared.Gradients.GradientLabel;
import Shared.Gradients.GradientPanel;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class NotificationOverlay extends GradientPanel implements ActionListener{

	
	private JLabel title;
	private GradientPanel panel;
	private GradientPanel titlePanel;
	private NotificationBox parent;
	private JButton xButton;

	public NotificationOverlay(NotificationBox n)
	{
		super();
		init();
		parent = n;
	}
	
	public void init()
	{
		this.setLayout(null);
		this.setBounds(0, 0, 402, 700);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		title();
		panel();
		this.setVisible(false);
	}
	
	public void title()
	{
	}
	
	public void panel()
	{
		panel = new GradientPanel();
		panel.setGradient(Color.white,Color.white.darker());
		panel.setBounds(0,56,402,700-56);
		panel.setVisible(true);
		this.add(panel);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		xButton = new JButton("");
		xButton.addActionListener(this);
		xButton.setIcon(new ImageIcon(NotificationOverlay.class.getResource("/Shared/Notifications/redx.jpg")));
		xButton.setBorder(BorderFactory.createEmptyBorder());
		xButton.setBounds(370, 1, 30, 30);
		panel.add(xButton);
		
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

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object a = arg0.getSource();
		
		if(a == xButton){
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
				System.out.println("openState = " + parent.openState);
				return;
			}
		}
		
}
