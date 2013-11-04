/**
 * HelpScreen is for display
 */
 
 //IMPORTANT - Screen flashes like a strobe light when displayed on some windows computers
 //Glitch has no clear cause, only appears to happen on Windows computers
 
import java.awt.*;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
//import javax.swing.JOptionPane;
//import javax.swing.JTextArea;

import ScreenManagement.*;

public class HelpScreen {

    public HelpScreen() {
    }
     public static MusicPlayer music;
     public static ScreenManager HelpScreen;
     public static Image bgImage;
     public static Image title;
     public static int setState = 0;
     public static int wind = 0;
     public static int GameState =1;
     public static String tankColors[] = {"Green", "Red", "Blue", "Yellow", "White"};
     public static int p1Color = 0;
     public static int p2Color = 1;
     public static String playlist[] = {"Another One Bites The Dust","Beautiful Disaster",
    	 "Frankenstein",
    	 "HeLLMaRcH","Magneto","Molossus",
    	 "Xmas Special"};
     public static int tracknumber;
     public static String song;
     public static DataClass dc = new DataClass();
    // public static JTextArea Name1 = new JTextArea();
    // public static String player1Name="Player 1";
    // public static String player2Name="Player 2";
     
     
       private static final DisplayMode POSSIBLE_MODES[] = {
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 24, 0),
        new DisplayMode(800, 600, 16, 0),
        new DisplayMode(640, 480, 32, 0),
        new DisplayMode(640, 480, 24, 0),
        new DisplayMode(640, 480, 16, 0)
    };


    private static Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }
     
     public static void run() {
    	HelpScreen = new ScreenManager();;
    	tracknumber = 0;
        try {
        	//Initializes music for options screen and loops it
        	music = new MusicPlayer("Frankenstein.wav");
			music.l();
            DisplayMode displayMode =
            HelpScreen.findFirstCompatibleMode(POSSIBLE_MODES);
            HelpScreen.setFullScreen(displayMode);
           // Name1.setEditable(true);
           // Name1.setAlignmentX(500);
           // Name1.setAlignmentY(500);
           // Name1.setEnabled(true);
           // HelpScreen.getFullScreenWindow().add(Name1);
            Window windowStart = HelpScreen.getFullScreenWindow();
            windowStart.addKeyListener(new KeyClass());
            loadImages();
            animationLoop();
            // create another animationloop in StartScreen.Java which will display Start selection
            //After user makes selections and begins game, return Start=1 to intiate Game phase.
        	}
     finally {
    	 HelpScreen.restoreScreen();
        }
    }
    
    public static void loadImages() {
        //loads images into buffer
         bgImage = loadImage("images/HelpScreen.jpg");
         title = loadImage("images/title.png");
		//add animation here for start screen
    }
    
        public static void animationLoop() 
        {
        	while(setState == 0)
        	{
        		Graphics2D g = HelpScreen.getGraphics();
        		draw(g);
        		g.dispose();
        		HelpScreen.update();
        		
           
        		// take a nap to catch up garage stuff
        		try {
        			Thread.sleep(20);
        		}
        		catch (InterruptedException ex) { }
        	}
        	if (setState == 1)
        	{
        		setState = 0;
        		MainScreen ms = new MainScreen();
        		HelpScreen.restoreScreen();
        		ms.run();
        	}
        
     }
     
     //Altered help screen text to display the new controls - Zach
     public static synchronized void draw(Graphics g) {
    	 g.drawImage(bgImage, 0, 0, null);
		  g.setColor(Color.black);
		  Font f = g.getFont();
		  g.setFont(new Font("SansSerif", Font.BOLD, 12));
		  g.drawString("Controls: Both tanks use the same controls during their turn",200,75);
		  g.setFont(f);
		  //g.setColor(Color.blue);
		  g.setFont(new Font("SansSerif", Font.BOLD, 12));
          //g.drawString("P1:",406,250);
          g.setFont(f);
		  g.setColor(Color.black);
          g.drawString("A & D keys",200,100);
          //g.drawString("A, D",406,275);
          g.drawString("Left & Right Arrows",200,125);
          //g.drawString("W, S",406,300);
          g.drawString("Q & E keys",200,150);
          //g.drawString("Q, E",406,325);
          g.drawString("1, 2, 4, and 4 keys",200,175);
          //g.drawString("1, 2, 3, 4, 5",406,350);
		  g.drawString("1-Default Bomb 2-Homing Bomb 3-Hersco Bomb 4-Joker Bomb",200,200);
		  g.drawString("Spacebar",200,225);
		  //g.drawString("Capslock",406,375);
		  //g.setColor(Color.RED);
		  g.setFont(new Font("SansSerif", Font.BOLD, 12));
          //g.drawString("P2:",511,250);
          g.setFont(f);
		  g.setColor(Color.black);
          g.drawString("Move Tank",475,100);
   	   	  g.drawString("Aim Turret",475,125);
   	   	  g.drawString("Adjust Power",475,150);
   	   	  g.drawString("Switch Weapons",475,175);
		  g.drawString("",475,200); 
		  g.drawString("Fire & End Turn",475,225); 
		  
		  
		  g.setFont(new Font("SansSerif", Font.BOLD, 12));
		  g.setColor(Color.black);
		  g.drawString("Settings:", 200, 270);
		  
          g.setFont(f);  
   	   	  g.setColor(Color.black);
		  g.drawString("Cycle Wind: W", 200, 290);
		  g.setColor(Color.black);
		  
		  if(wind==2){
			  g.setColor(Color.white);
			  g.drawString("Wind is completely randomized.", 395, 290);
			  g.drawString("Wind is randomized after every shot.", 375, 290);
			  g.setColor(Color.black);
			  g.drawString("Wind is on and constant.", 435, 290);
		  }
		  else if(wind==3){
			  g.setColor(Color.white);
			  g.drawString("Wind is on and constant.", 435, 290);
			  g.setColor(Color.black);
			  g.drawString("Wind is completely randomized.", 395, 290);
		  }
		  else if(wind==1){
			  g.setColor(Color.white);
			  g.drawString("Wind is Off.", 475, 290);
			  g.drawString("Wind is on and constant.", 435, 290);
			  g.setColor(Color.black);
			  g.drawString("Wind is randomized after every shot.", 375, 290);
		  }
		  else{
			  g.setColor(Color.white);
			  g.drawString("Wind is randomized after every shot.", 375, 290);
			  g.drawString("Wind is completely randomized.", 395, 290);
			  g.setColor(Color.black);
			  g.drawString("Wind is Off.", 475, 290);  
		  }
		  g.setColor(Color.black);
		  g.drawString("P1 Tank Color: Up/Down Arrows", 200, 310);
		  g.drawString("P2 Tank Color: Left/Right Arrows", 200, 330);

		  
		  g.setColor(Color.white);
		  if(p1Color > 0) 
		  g.drawString("" + tankColors[p1Color-1], 475, 310);
		  if(p2Color > 0) 
		  g.drawString("" + tankColors[p2Color-1], 475, 330);
		  if(p1Color < 4) 
		  g.drawString("" + tankColors[p1Color+1], 475, 310);
		  if(p2Color < 4) 
		  g.drawString("" + tankColors[p2Color+1], 475, 330);
		  
		  g.setColor(Color.black);
		  g.drawString("" + tankColors[p1Color], 475, 310);
		  g.drawString("" + tankColors[p2Color], 475, 330);
		 
		  
		  g.setColor(Color.black);
		  g.drawString("Cycle Music: < > ", 200, 350);
		  g.setColor(Color.white);
		  g.drawString(playlist[(tracknumber+6)%7], 475,350);
		  g.setColor(Color.white);
		  g.drawString(playlist[(tracknumber+1)%7], 475,350);
		  g.setColor(Color.black);
		  g.drawString(playlist[tracknumber], 475,350);
		 
		  g.setFont(new Font("SansSerif", Font.BOLD, 12));
		  g.setColor(Color.black);
		  g.drawString("Weapon:", 200, 400);
		  g.drawString("Weapon's Effect", 475, 400);
		  
          g.setFont(f);
		  g.setColor(Color.black);
		  g.drawString("Tank Round:", 200, 420);
		  g.drawString("Homing Bomb:", 200, 440);
		  g.drawString("Herso Bomb:", 200, 460);
		  g.drawString("The Joker:", 200, 480);
		  
		  g.drawString("10 Damage", 475, 420);
		  g.drawString("20 Damage", 475, 440);
		  g.drawString("30 Damage", 475, 460);
		  g.drawString("50 Damage", 475, 480);
		  
		  g.setFont(new Font("SansSerif", Font.BOLD, 12));
		  g.setColor(Color.black);
		  g.drawString("Press Esc to Return to the Main Menu.",277,535); 

		  //g.drawString("Press any key to return to the main menu.", 275, 575);
     //John Danz 10/21/08: made Esc the universal "bring me back to the main menu button", 
     //except if you are at the main menu then you exit the game
      }
     
      public static class KeyClass extends KeyAdapter

      {
    
	       public void keyPressed(KeyEvent e) 
	       {
	    	   int keyCode = e.getKeyCode();
	
			   if (keyCode == KeyEvent.VK_ESCAPE) 
			   {
				   DataClass.setP1Tank(p1Color);
				   DataClass.setP2Tank(p2Color);
				   DataClass.setwind(wind);
				   DataClass.setGameState(GameState);
				   music.s();
				   setState = 1;
			   }
			   else if (keyCode == KeyEvent.VK_W)
			   {
				   if(wind<3)
				   {
					   wind++;
				   }
				   else
				   {
					   wind=0;
				   }
			   }
			   else if (keyCode == KeyEvent.VK_G)
			   {
				   if(GameState==0)
				   {
					   GameState=1;
				   }
				   else
				   {
					   GameState=0;
				   }
			   }
			   else if (keyCode == KeyEvent.VK_LEFT)
			   {
				   if(p2Color > 0)
					   p2Color--;
			   }
			   else if (keyCode == KeyEvent.VK_RIGHT)
			   {
				   if(p2Color < tankColors.length - 1)
					   p2Color++;
			   }
			   else if (keyCode == KeyEvent.VK_UP)
			   {
				   if(p1Color < tankColors.length - 1)
					   p1Color++;
			   }
			   else if (keyCode == KeyEvent.VK_DOWN)
			   {
				   if(p1Color > 0)
					   p1Color--;
			   }
			 
			   else if (keyCode==KeyEvent.VK_COMMA)
			   {
				   tracknumber = (tracknumber+6)%7;

			   }
			   else if (keyCode==KeyEvent.VK_PERIOD)
			   {
				   tracknumber = (tracknumber+1)%7;
			   }
			 
			}
	    }


      
}