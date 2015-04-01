package Shared.Gradients;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;


public class GradientFrame extends JButton {

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
    public float startx = 0;
    public float starty = 0;
    public float endx = 0;
    public float endy = getHeight();
    public boolean cyclic = false;
    
    
    public GradientFrame() {
    	orig = color1;
    	this.setOpaque(false);
    	this.repaint();
    }
  
    
    public GradientFrame(Color c1) {
    	orig = color1;
    	this.setOpaque(true);
    	color1 = c1;
    	color2 = color1.darker();
    	
    }
    
    public GradientFrame(Color c1, Color c2) {
    	orig = color1;
    	this.setOpaque(true);
    	color1 = c1;
    	color2 = c2;
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
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(
            0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

   public void toggle()
   {
   	if(this.isVisible()==true){
   		this.setVisible(false);
   	}
   	else{
   		this.setVisible(true);
   	}
   }
}