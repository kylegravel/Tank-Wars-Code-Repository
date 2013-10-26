/**
 * HelpScreen is for display
 */
 
 //IMPORTANT - Screen flashes like a strobe light when displayed on some windows computers
 //Glitch has no clear cause, only appears to happen on Windows computers
 
import java.awt.*;

import javax.swing.ImageIcon;
//import javax.swing.JOptionPane;
//import javax.swing.JTextArea;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

//Created Fall 2011
public class Credits extends JComponent {

    public Credits() {
    }
     public static Image bgImage;

     public static boolean creditsOn = false;

	 public static int x = 300;
	 public static int y = 600;
	 public static int count = 0;
	 public static int loop = 1550; //needs to be 610+ highest number to the last name; 1550 for Fall 2011

    private static Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }
     
    
    
    public static void loadImages() {
        //loads images into buffer
         Credits.bgImage = Credits.loadImage("images/CreditsScreen.jpg");
		//add animation here for start screen
    }
    
     
     public static synchronized void draw(Graphics g) {
    	 Credits.loadImages();
    	 
    			 g.drawImage(Credits.bgImage, 0, 0, null);
    			 Font f = g.getFont();
   		  
    			 g.setFont(f);  
    			 g.setFont(new Font("SansSerif", Font.BOLD, 20));
    			 g.setColor(Color.white);
    			 g.drawString("The Teams",x+35,y-10);

    			 g.setFont(new Font("SansSerif", Font.BOLD, 12));
    			 g.drawString("Spring 2008:", x+50,(y + 35));
    			 g.drawString("Creator             --------- Alex Geden", x,(y + 60));
    			 g.drawString("Programmer   --------- Amanda Kassay", x,(y + 80));
    			 g.drawString("Programmer   --------- Eric Marcarelli", x,(y + 100));
    			 g.drawString("Programmer   --------- Anh Pham", x,(y + 120));
    			 g.drawString("Programmer   --------- Ryan Kleckner", x,(y + 140));
    			 g.drawString("Programmer   --------- Ryan Crean", x,(y + 160));
    			 
    			 g.drawString("Fall 2008:", x+65,(y + 205));
    			 g.drawString("Programmer   --------- William Cipolli", x,(y + 230));
    			 g.drawString("Programmer   --------- Michael Ciarlo", x,(y + 250));
    			 g.drawString("Programmer   --------- Michel Coutermarsh", x,(y + 270));
    			 g.drawString("Programmer   --------- Philip Dudley", x,(y + 290));
    			 g.drawString("Programmer   --------- Tim Ocskasy", x,(y + 310));
    			 g.drawString("Programmer   --------- Timothy Squires", x,(y + 330));
    			 g.drawString("Programmer   --------- John Danz", x,(y + 350));
    			 g.drawString("Programmer   --------- Devin Barna", x,(y + 370));
    			 
    			 g.drawString("Spring 2009:", x+50,(y + 415));
    			 g.drawString("Programmer   --------- Benjamin Beattie-Chapnick", x,(y + 440));
    			 g.drawString("Programmer   --------- Kaitlyn Boresky", x,(y + 460));
    			 g.drawString("Programmer   --------- Taylor Busch", x,(y + 480));
    			 g.drawString("Programmer   --------- Sarah Samantha Canieso", x,(y + 500));
    			 g.drawString("Programmer   --------- Alicia Marino", x,(y + 520));
    			 g.drawString("Programmer   --------- Brian Meyers", x,(y + 540));
    			 g.drawString("Programmer   --------- Jonathan Mucha", x,(y + 560));
    			 g.drawString("Programmer   --------- Ryan Murphy", x,(y + 580));
    			 g.drawString("Programmer   --------- Bryan Randall", x,(y + 600));
    			 g.drawString("Programmer   --------- Andrew Richardson", x,(y + 620));
    			 g.drawString("Programmer   --------- Pamela Rizzo", x,(y + 640));
    			 g.drawString("Programmer   --------- Gregory Tempesta", x,(y + 660));
    			 g.drawString("Programmer   --------- Jebish Tuladhar", x,(y + 680));
    			 
    			 g.drawString("Fall 2010:", x+65,(y + 725));
    			 g.drawString("Programmer   --------- Thomas Hammer", x,(y + 750));
    			 g.drawString("Programmer   --------- Dennis Juhasz", x,(y + 770));
    			 g.drawString("Programmer   --------- Eric Kelly", x,(y + 790));
    			 g.drawString("Programmer   --------- Corey Lynch", x,(y + 810));

    			 g.drawString("Fall 2011:", x+65,(y + 855));
    			 g.drawString("Scrum Master --------- Zachary Kohlberg",x,(y + 880));
    			 g.drawString("Programmer   --------- Zachary Singer", x,(y + 900));
    			 g.drawString("Programmer   --------- Jamar Paris", x,(y + 920));
    			 g.drawString("Programmer   --------- Albert Holtermann", x,(y + 940));
    			 
    			 g.drawString("Fall 2012:" , x+65,(y + 985));
    			 g.drawString("Scrum Master --------- Patrick Merritt" ,x,(y+1010));
    			 g.drawString("Programmer   --------- Anthony Domenico",x,(y+1030));
    			 g.drawString("Programmer   --------- Ryan Schwarz",x,(y+1050));
    			 g.drawString("Programmer   --------- Zach Freed",x,(y+1070));
    			 
    			 //Insert New Names Here
    			 
    			 if(loop-count>2)
    			 {
        			 g.setFont(new Font("SansSerif", Font.BOLD, 20));
    			 g.drawString("Press Escape", x+50,(y + 1240));//needs to be 300+ highest number to the last name; 1240 for Fall 2011
    			 }
    			 else
    			 {
        			 g.setFont(new Font("SansSerif", Font.BOLD, 20));
        			 g.setColor(Color.red);
    			 g.drawString("ROCK ON!", x+55,(y + 1240));//needs to be 300+ highest number to the last name
    			 }

      }
     public static void up(){
		 Credits.y--;
		 Credits.count++;
     }


      
}