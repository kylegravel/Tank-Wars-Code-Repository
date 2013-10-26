
/**
 * HelpScreen is for display
 */
import java.awt.*;
import javax.swing.ImageIcon;


import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import ScreenManagement.*;


public class TutorialScreen {

    public TutorialScreen() {
    }
     public static MusicPlayer music;
     public static ScreenManager TutorialScreen;
     public static Image screenshot1;
     public static Image screenshot2;
     public static Image screenshot3;
     public static Image screenshot4;
     public static Image screenshot5;
     public static Image screenshot6;
     public static Image screenshot7;
     public static Image screenshot8;
     public static Image screenshot9;
     public static Image screenshot10;
     public static Image esckey;
     public static Image rightarrow;
     public static Image leftarrow;
     public static int setState = 0;
     public static int page = 0;
     public static DataClass dc = new DataClass();
     
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
    	TutorialScreen = new ScreenManager();;
        try {
        	//Initializes music for options screen and loops it
        	music = new MusicPlayer("Magneto.wav");
			music.l();
        	
            DisplayMode displayMode =
            TutorialScreen.findFirstCompatibleMode(POSSIBLE_MODES);
            TutorialScreen.setFullScreen(displayMode);
            Window windowStart = TutorialScreen.getFullScreenWindow();
            windowStart.addKeyListener(new KeyClass());
            
            loadImages();
            animationLoop();
        	}
     finally {
    	 TutorialScreen.restoreScreen();
        }
    }
   
    
    public static void loadImages() {
        // loads images into buffer
         screenshot1 = loadImage("images/Tutorial/initialgamestate.png");
         screenshot2 = loadImage("images/Tutorial/ingamehelp.png");
         screenshot3 = loadImage("images/Tutorial/ingameoptions.png");
         screenshot4 = loadImage("images/Tutorial/movemode.png");
	 screenshot5 = loadImage("images/Tutorial/turretmode.png");
	 screenshot6 = loadImage("images/Tutorial/powermode.png");
	 screenshot7 = loadImage("images/Tutorial/weaponsmode.png");
	 screenshot8 = loadImage("images/Tutorial/shotfired.png");
         screenshot9 = loadImage("images/Tutorial/damage.png");
	// screenshot10 = loadImage("images/Tutorial/shotfired.png");


         esckey = loadImage("images/EKey.png");
         rightarrow = loadImage("images/RArrow.png");
         leftarrow = loadImage("images/LArrow.png");
    }
    
        public static void animationLoop() 
        {
        	while(setState == 0)
        	{
        		Graphics2D g = TutorialScreen.getGraphics();
        		draw(g);
          
        		g.dispose();
        		TutorialScreen.update();
           
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
        		TutorialScreen.restoreScreen();
        		ms.run();
        	}
        
    }
     public static synchronized void draw(Graphics g) {
    	 if (page==0)
    		 g.drawImage(screenshot1, 0, 0, null);
    	 else if (page==1)
    		 g.drawImage(screenshot2, 0, 0, null);
    	 else if (page==2)
    		 g.drawImage(screenshot3, 0, 0, null);
    	 else if (page==3)
    		 g.drawImage(screenshot4, 0, 0, null);
    	 else if (page==4)
    		 g.drawImage(screenshot5, 0, 0, null);
    	 else if (page==5)
    		 g.drawImage(screenshot6, 0, 0, null);
    	 else if (page==6)
    		 g.drawImage(screenshot7, 0, 0, null);
    	 else if (page==7)
    		 g.drawImage(screenshot8, 0, 0, null);
    	 else if (page==8)
    		 g.drawImage(screenshot9, 0, 0, null);
   /*	 else if (page==9)
    		 g.drawImage(screenshot10, 0, 0, null);
   /* 	 else if (page==10
    		 g.drawImage(screenshot11, 0, 0, null);
    	 else if (page==11
    		 g.drawImage(screenshot12, 0, 0, null);
    	 
*/
    	 // This is changed to 8 because there is one less tutorial image
    	 if (page==8){
   		  g.drawImage(esckey, 730, 530, null);
    	 }
    	 else{
		  g.setColor(Color.black);
		  g.drawImage(rightarrow, 730, 530, null);
		  g.drawString("Next", 740, 550);
		  g.drawString("Page", 740, 565);
    	 }

		  if (page==0){
		  //g.setColor(Color.black);
		  //g.drawImage(esckey, 0, 530, null);
		  //g.drawString("Main Menu", 10, 550);
		  }
		  else {
		  g.setColor(Color.black);
		  g.drawImage(leftarrow, 0, 530, null);
		  g.drawString("Previous", 10, 550);
		  g.drawString("Page", 10, 565);
		  }
     }
     

     
      public static class KeyClass extends KeyAdapter
      {
    
	       public void keyPressed(KeyEvent e) 
	       {
	    	   int keyCode = e.getKeyCode();
	
	    	   if (keyCode == KeyEvent.VK_ESCAPE)
			   {
			       music.s();
			       //music.close();
				   setState = 1;
			   }
	    	   // the page + x was updated as well as the mod to reflect one less tutorial image
			   else if ((page!=0)&&(keyCode == KeyEvent.VK_LEFT))
			   {
				   page = (page+8)%9;
			   }
	    	   
			   else if ((page!=8)&&(keyCode == KeyEvent.VK_RIGHT))
			   {
				   page = (page+1)%9;
				 
			   }

			}
	    }
     
      	
}
