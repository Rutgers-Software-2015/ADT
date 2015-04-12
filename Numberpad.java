package Shared;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sun.org.mozilla.javascript.internal.ObjToIntMap.Iterator;
import Shared.Gradients.GradientButton;

public class Numberpad extends JFrame implements ActionListener{

	/**
	 * Class to create a pop-up numberpad and return the input value
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	//JComponents
	private JPanel padButtons;
	private GradientButton one;
	private GradientButton two;
	private GradientButton three;
	private GradientButton four;
	private GradientButton five;
	private GradientButton six;
	private GradientButton seven;
	private GradientButton eight;
	private GradientButton nine;
	private GradientButton zero;
	private AbstractButton period;
	private GradientButton back;
	private GradientButton submit;
	private float payment = 0;
	private JPanel padTitle;
	private JLabel padInput;
	
	public Numberpad()
	{
		super();
		init();
	}
	
	/**
	 * This function initializes the pop-up number pad associated with the Accept
	 * Payment function
	 * 
	 * @returns none
	 * 
	 **/
	
	 private void init()
	 {
		this.setLayout(null);
		this.setSize(300, 375);
		this.setLocation(250, 300);
		this.setTitle("Number Pad");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setVisible(false);
		this.setResizable(false);
		
		// Input field setup
		padTitle = new JPanel();
		padTitle.setLayout(null);
		padTitle.setBounds(0,0,316,40);
		padTitle.setBorder(BorderFactory.createLineBorder(Color.black));
		padTitle.setBackground(Color.white);
		padInput = new JLabel();
		padInput.setBounds(60,0,200,40);
		padInput.setOpaque(false);
		padInput.setHorizontalAlignment(JLabel.RIGHT);
		padInput.setFont(padInput.getFont().deriveFont(16.0f));
		padTitle.add(padInput);
		this.add(padTitle);
		
		// Button setup
		padButtons = new JPanel();
		padButtons.setLayout(new GridLayout(4,3));
		padButtons.setBounds(0,40,300,260);
		one = new GradientButton("1");
		one.addActionListener(this);
		one.setFocusPainted(false);
		two = new GradientButton("2");
		two.addActionListener(this);
		two.setFocusPainted(false);
		three = new GradientButton("3");
		three.addActionListener(this);
		three.setFocusPainted(false);
		four = new GradientButton("4");
		four.addActionListener(this);
		four.setFocusPainted(false);
		five = new GradientButton("5");
		five.addActionListener(this);
		five.setFocusPainted(false);
		six = new GradientButton("6");
		six.addActionListener(this);
		six.setFocusPainted(false);
		seven = new GradientButton("7");
		seven.addActionListener(this);
		seven.setFocusPainted(false);
		eight = new GradientButton("8");
		eight.addActionListener(this);
		eight.setFocusPainted(false);
		nine = new GradientButton("9");
		nine.addActionListener(this);
		nine.setFocusPainted(false);
		zero = new GradientButton("0");
		zero.addActionListener(this);
		zero.setFocusPainted(false);
		period = new GradientButton(".");
		period.addActionListener(this);
		period.setFocusPainted(false);
		back = new GradientButton("Erase");
		back.addActionListener(this);
		back.setFocusPainted(false);
		
		padButtons.add(one);
		padButtons.add(two);
		padButtons.add(three);
		padButtons.add(four);
		padButtons.add(five);
		padButtons.add(six);
		padButtons.add(seven);
		padButtons.add(eight);
		padButtons.add(nine);
		padButtons.add(back);
		padButtons.add(zero);
		padButtons.add(period);
		this.add(padButtons);
		
		// Submit button
		submit = new GradientButton("Submit");
		submit.setBounds(0,300,300,50);
		submit.addActionListener(this);
		this.add(submit);
		
	 }
	 
	 /**
	  * Gets the most recent number submit
	  * 
	  * @return float payment
	  * 
	  */
	 
	 public float get()
	 {
		 return payment;
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object a = e.getSource();
		if(a == one)
		{
			padInput.setText(padInput.getText()+"1");
		}
		if(a == two)
		{
			padInput.setText(padInput.getText()+"2");
		}
		if(a == three)
		{
			padInput.setText(padInput.getText()+"3");
		}
		if(a == four)
		{
			padInput.setText(padInput.getText()+"4");
		}
		if(a == five)
		{
			padInput.setText(padInput.getText()+"5");
		}
		if(a == six)
		{
			padInput.setText(padInput.getText()+"6");
		}
		if(a == seven)
		{
			padInput.setText(padInput.getText()+"7");
		}
		if(a == eight)
		{
			padInput.setText(padInput.getText()+"8");
		}
		if(a == nine)
		{
			padInput.setText(padInput.getText()+"9");
		}
		if(a == zero)
		{
			padInput.setText(padInput.getText()+"0");
		}
		if(a == period)
		{
			if(padInput.getText().isEmpty()){
	    		padInput.setText("0.");
	    	}
	    	else{
	    		padInput.setText(padInput.getText()+".");
	    	}
		}
		if(a == submit)
		{
			payment = (float)Float.parseFloat(padInput.getText());
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		if(a == back)
		{
			if(padInput.getText() == "0."){
	    		padInput.setText("");
	    	}
	    	else{
	    		String temp = "";
	    		String text = padInput.getText();
	    		int size = text.length();
	    		for(int i = 0;i < size-1;i++){
	    			temp = temp + text.charAt(i);
	    		}
	    		padInput.setText(temp);
	    	}
		}
		
		
	}
}
