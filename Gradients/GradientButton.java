package Shared.Gradients;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GradientButton extends JButton{

/*	Constructors:
 * 
 * 	GradientPanel() - Will construct a regular JPanel without any background
 * 	GradientPanel(Color c1) - Will construct a Gradient of light to dark from one color
 *	GradientPanel(Color c1, Color c2) - Will construct a Gradient from two colors
 *
 *	Public Functions:
 *
 *	void setGradient() - Will construct a gradient from the current background color
 *	void setGradient(Color c1) - Will construct a gradient from the given color
 *	void setGradient(Color c1, Color c2) - Will construct a gradient from both given colors
 *	void setBackground(Color c1) - Will create a solid background color from the given color
 *	void removeBackground() - Will set the current background to transparent and remove all color
 *	void setTransparent(boolean b) - Will set the current background to transparent/non-transparent
 *	void setBrighter(Color color, int multiplier) - Will brighten the given color, if it exists,
 *													to a brightness multiplier that you specify.
 *													NOTE: color objects change EVERYTIME you lighten it
 *													Use getColor1() if you need the current color object
 *	void setDarker(Color color, int multiplier) - Will darken the given color, if it exists,
 *												  to a darkness multiplier that you specify
 *												  NOTE: color objects change EVERYTIME you darken it
 *												  Use getColor1() if you need the current color object
 *	Color getColor1() - Will return Color object 1
 *	Color getColor2() - Will return Color object 2
 *
 */

   // private Graphics g;
	private Color color1;
    private Color color2;
    private Color orig;
    private Color highlight = Color.black;
    private Color color1old;
    private Color color2old;
    public float startx = 0;
    public float starty = 0;
    public float endx = 0;
    public float endy = getHeight();
    public boolean cyclic = false;
    
    
    public GradientButton() {
        super("Gradient Button");
        setContentAreaFilled(false);
        setFocusPainted(false); // used for demonstration
    	orig = color1;
    	this.setOpaque(false);
    	this.repaint();
    }
  
    
    public GradientButton(Color c1) {
        super("Gradient Button");
        setContentAreaFilled(false);
        setFocusPainted(false); // used for demonstration
    	orig = color1;
    	this.setOpaque(true);
    	color1 = c1;
    	color2 = color1.darker();
    	this.repaint();
    	
    }
    
    public GradientButton(Color c1, Color c2) {
        super("Gradient Button");
        setContentAreaFilled(false);
        setFocusPainted(false); // used for demonstration
    	orig = color1;
    	this.setOpaque(true);
    	color1 = c1;
    	color2 = c2;
    	this.repaint();
    }
    
    public void setGradient() {
    	
    	this.setVisible(false);
    	this.setOpaque(true);
    	color2 = color1.darker();
    	this.repaint();
    	this.setVisible(true);
    	
    }
    
    public void setGradient(Color c1) {
    	
    	this.setVisible(false);
    	this.setOpaque(true);
    	color1 = c1;
    	color2 = color1.darker();
    	this.repaint();
    	this.setVisible(true);
    	
    }
    
    public void setGradient(Color c1, Color c2) {
    	this.setVisible(false);
    	this.setOpaque(true);
    	color1 = c1;
    	color2 = c2;
    	this.repaint();
    	this.setVisible(true);
    	
    }
    
    public void removeBackground(){
    	this.setVisible(false);
    	setOpaque(false);
    	setBackground(orig);
    	this.repaint();
    	this.setVisible(true);
    }
    
    public void setBackground(Color c1){
    	this.setVisible(false);
    	this.setOpaque(true);
    	setGradient(c1,c1);
    	this.repaint();
    	this.setVisible(true);
    }
    
    public void setTransparent(boolean b){
    	if(b == true){
    		this.setOpaque(false);
    	}
    	else{
    		this.setOpaque(true);
    	}
    }
    
    public void setBrightness(Color color,int multiplier){
    	if(color == color1 && multiplier > 0){
    		this.setVisible(false);
    		for(int i = 0;i < multiplier;i++){
    			color1 = color1.brighter();
    		}
    		this.repaint();
    		this.setVisible(true);
    	}
    	if(color == color2 && multiplier > 0){
    		this.setVisible(false);
    		for(int i = 0;i < multiplier;i++){
    			color2 = color2.brighter();
    		}
    		this.repaint();
    		this.setVisible(true);
    	}
    }
    
    public void setDarkness(Color color,int multiplier){
    	if(color == color1 && multiplier > 0){
    		this.setVisible(false);
    		for(int i = 0;i < multiplier;i++){
    			color1 = color1.darker();
    		}
    		this.repaint();
    		this.setVisible(true);
    	}
    	if(color == color2 && multiplier > 0){
    		this.setVisible(false);
    		for(int i = 0;i < multiplier;i++){
    			color2 = color2.darker();
    		}
    		this.repaint();
    		this.setVisible(true);
    	}
    }
    
    public Color getColor1(){
    	return color1;
    }
    
    public Color getColor2(){
    	return color2;
    }
    

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0), 
                color1, 
                new Point(0, getHeight()), 
                color2));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }

/*
   @Override
    public void mouseEntered(MouseEvent e) {
    	color1old = color1;
    	color2old = color2;
       this.setBackground(Color.red);
       System.out.println("YOU ENTERED THE BORDER");
    }

    @Override
    public void mouseExited(MouseEvent e) { 
    	this.setBackground(Color.green);
    	System.out.println("YOU EXITED THE BORDER");
    	//this.setVisible(true);
    	repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) { 
    	System.out.println("CLICKED");
    	this.setBackground(Color.red);
    }

    @Override
    public void mousePressed(MouseEvent e) { 
    	System.out.println("PRESSED");
    	setBackground(Color.red);
    }

    @Override
    public void mouseReleased(MouseEvent e) { 
    	
    } */
}