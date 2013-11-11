import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ScreenManagement.*;

import java.io.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import  sun.audio.*;

//Game class_
public class Game implements KeyListener {
   

    public static void main(String args[]) {
        Game tankGame = new Game(); 
        tankGame.run();
        DataClass.readFile();
    }

    protected static final DisplayMode POSSIBLE_MODES[] = {
      // new DisplayMode(1280, 800, 32, 0),  
      //Support for higher Resolutions, maybe? 
     //Would need lots of additional resizing.
       new DisplayMode(800, 600, 32, 0),
       new DisplayMode(800, 600, 24, 0),
       new DisplayMode(800, 600, 16, 0),
    };
    
    // Testing 
    protected boolean testingUpdate = true;
    protected boolean testingDraw = false;
    
    //Credits running?
    public static int credits = 0;

    //Variable Base
    public static DataClass dc = new DataClass();
    protected ScreenManager screen; // creates object to control screen
    public static MusicPlayer music;
    String playlistnames[] = {"Another One Bites The Dust","Beautiful Disaster",
          	 "Frankenstein",
          	 "HeLLMaRch","Magneto","Molossus",
          	 "Xmas Special"}; // used to display song names in options screen
    protected SoundPlayer soundShot;
    protected SoundPlayer clap;
    protected Image bgImage; // background image
    protected Image TankImage; // tank's image
    protected Image terrainTexture;
    protected Image headerBar; //top header image
    protected Image healthIcon; // health icon
    protected Image healthBar; // health bar full
    protected Image powerBar; // power bar full
    protected Image powerIcon; //power bar up arrow icon
    protected Image weaponIndicatorBG; // background for weapon indicator
    protected Image weaponNormal;
    protected Image weaponHersco;
    protected Image weaponNuclear;
    protected Image weaponJoker;
    protected Image modemove;
    protected Image modeangle;
    protected Image modepower;
    protected Image modeweapon;
    protected Image modeBG;
    protected Image fuelBar;
    protected Image fuelIcon;
    protected Image arrow;
    
    // Tutorial images
    protected Image blackarrowleft = loadImage("images/blackarrowleft.png");
    protected Image blackarrowright = loadImage("images/blackarrowright.png");
    protected Image blackarrowup = loadImage("images/blackarrowup.png");
    protected Image blackarrowdown = loadImage("images/blackarrowdown.png");
    protected Image keyarrowleft = loadImage("images/keyarrowleft.png");
    protected Image keyarrowright = loadImage("images/keyarrowright.png");
    protected Image keyarrowup = loadImage("images/keyarrowup.png");
    protected Image keyarrowdown = loadImage("images/keyarrowdown.png");
    protected Image keyspacebar = loadImage("images/keyspacebar.png");
    
    //Used to count shots before un-paralyzed (Start of un-paralyzed)
    int PARALYZED = 4; //edit this to change amount of shots until un-paralyzed
    int shotcounter = PARALYZED;
    int shotcounter2 = PARALYZED;
    
    // Tanks
    static Tank Tank1 = new Tank();
    static Tank Tank2 = new Tank();
    
    //Projectiles -This needs to be added in we did not have time
    Projectile shot1= new Projectile();//player 1 shot
    Projectile shot2= new Projectile();//player 2 shot
    
    //**Objects in Game
    protected Sprite Shot; // displays weapon 1 for player 1 in the top left corner
    protected Sprite SecondShot; //displays weapon 2 for player 1 in the top left corner
    protected Sprite Shot2; // displays weapon 1 for player 2 in the top right corner 
    protected Sprite SecondShot2; // displays weapon 2 for player 2 in the top right corner
    protected Sprite ThirdShot;
    protected Sprite ThirdShot2;  
    protected Sprite FourthShot;
    protected Sprite FourthShot2;
    protected Sprite BOOM; // explosion
    protected Sprite TankBoom; //tank explosion
    protected Sprite TankShot; //tanks shot
    protected Sprite TankShot2;
    protected Sprite SecondaryTankShot; //second tank shot
    protected Sprite SecondaryTankShot2;
    protected Sprite ThirdTankShot;//HEEEERSCOOOO BOMB
    protected Sprite ThirdTankShot2;
    protected Sprite FourthTankShot;
    protected Sprite FourthTankShot2;//blake bomb
    protected Sprite cloudSunny1; //cloud sunny
    protected Sprite cloudSunny2; //cloud sunny
    protected Sprite cloudSunny3; //cloud sunny
    protected Sprite cloudDark1; //cloud dark
    protected Sprite cloudDark2; //cloud dark
    protected Sprite cloudDark3; //cloud dark
    protected Sprite rainSprite;
    protected Sprite herscoJetLeftSprite;
    protected Sprite herscoJetRightSprite;
    
    //*******************
    ////Necessary Global Variables////
    public int restartgame = 0; //added by Ryan Kleckner 4/8/08
    public boolean firstfall = true;//sees if the tank is falling for the first time
    public boolean firstfall2 = true;//sees if the tank is falling for the first time
    public boolean PauseMenuOpen = false;  // added 11/05/08 by Devin Barna
    public boolean ControlsOverlayOpen = false;
    public boolean tutorialmode = false;
    public int GameSTATE=1; //determines what gameplay
    public int tankExplosionLoop = 0; // Deals with the infinite explosion loop at the end of the game
   
    //0=dynamic
    //1=turn based
    //3=restart game
    public boolean GameSTATEchanged = true; // has GameSTATE been changed since starting the game
    // (needed in order to change GameSTATE during the game)
    public static int turn=1;
    //TEST
    //if turn =1 player 1 turn
    //if turn =2 player 2 turn
    public int hitTest=0; 
    //hittest=2 hit ground or tank
    //hittest=0 when shot is reset
    public int hitTest2=0;
    //hittest=2 hit ground or tank
    //hittest=0 when shot is reset
    public int tankshoot=0; //only time it equals zero
    //tankshoot=1 means shot was just fired
    //tankshoot>1 means nothing besides that a shot was not just fired. need to fix this
    public int tankshoot2=0; //only time it equals zero
    //tankshoot=1 means shot was just fired
    //tankshoot>1 means nothing besides that a shot was not just fired. need to fix this
    public float Gravf; //Gravity in the game
    //////////// rename these variables...
    public static int start=0;
    public boolean TankCreated = false; //Gate to detect if a tank was randomly placed onto the map or not
    /***Holder and Counters***/
    int a=0;
    public int windcount=0;//determines if the wind needs to be changed
    public float Windf = .00f;
    public int WindVar=0;
    //////////////////////
    ///Dynamic Ground variables////
    public int basex=0;
    public int basey=900;
    public int topx=0;
    public Integer topy[] = new Integer[9500]; 
    public int AirStrikeAmmo1 = 1;
    public int AirStrikeAmmo2 = 1;
        
    public int sleepTime = 25;
        
    // moving and jumping
    public int maxMove = 100; 
    public int tankMove = maxMove;
    public boolean moving = false;
    public boolean jumpUp = false; 
    public boolean jumpFall = false;
    public int maxJump = 25; 
    public int currJump = 0;
    public int fuel = 110;
    public int curfuel = fuel;  //Fuel variable used for both tanks.
    public int fueldepletion = 2;

    
    // test suite
    public int ptLevel = 1, ptX = 0, ptY = 0;
    protected boolean testing = false;
    // used when a method doesn't set any checkable values
    protected boolean check = true;
    protected boolean caseChecks[] = new boolean[7];
    
    public boolean nextTurn = true;
    
     //added by Ryan Kleckner 4/14/08

    int p1secondShots = 2;
    int p2secondShots = 2;
    
    
    //random terrain selector
    /*******************************************************
     *Added by Ryan K
     *Selects one of the terrains at random
     *******************************************************
     */
    String[] nums = {"1", "2", "3"};
    int ran = (int)(Math.random() * nums.length);
    String levelNumber = nums[ran];
    
    int freqValue;
    int terrainNum;
    int firstNum = 900;
    int secondNum = 950;    
    

    public void ChangeLevel(String num) {
        firstNum = 600;
        secondNum = 950;    
        freqValue = 0;
        terrainNum = 0;
        basex=0;
        basey=900;
        topx=0;
        topy = new Integer[9500]; 
        topy[basex]= null;
        levelNumber = num;  
    }
    
    public void loadImages() {
        // loads images into buffer
        bgImage = loadImage("images/newbackground.jpg"); // Background Image
        headerBar = loadImage("images/headerBarBlue.png"); // Header Image
        healthIcon = loadImage("images/healthIcon.png"); // Health Icon
        healthBar = loadImage("images/healthBar.png"); // Health Bar full
        powerBar = loadImage("images/powerBar.png"); // Power Bar full
        powerIcon = loadImage("images/uparrowicon.png"); //power icon
        fuelBar = loadImage("images/FuelBar.png"); // Fuel Bar full
        fuelIcon = loadImage("images/FuelBarrel.png"); // Icon next to fuel bar
        arrow = loadImage("images/newarrow2.png"); //Arrow indicating which tank is moving
        
        // Weapon Indicator Images
        weaponIndicatorBG = loadImage("images/weaponIndicatorBG.png"); // BG
        weaponNormal = loadImage("images/weaponNormal.png");
        weaponHersco = loadImage("images/weaponHersco.png");
        weaponNuclear = loadImage("images/weaponNuclear.png");
        weaponJoker = loadImage("images/weaponJoker.png");
        modemove = loadImage("images/MMode.png");
        modeangle = loadImage("images/TMode.png");
        modepower = loadImage("images/PMode.png");
        modeweapon = loadImage("images/WMode.png");
        modeBG = loadImage("images/ModeBG.png");
        
        TankImage = loadImage(DataClass.getP1Tank());
        Image TankImage2 = loadImage(DataClass.getP2Tank());
        Image Explode = loadImage("images/explosion.gif");
        Image Bullet = loadImage("images/Bullet.JPG");
        Image Bullet2 = loadImage("images/Bullet2.JPG");
        Image Bullet3 = loadImage("images/Bullet3.JPG");
        Image lilBoom = loadImage("images/lilboom.gif");
        Image bomb = loadImage("images/newweapon1.png");
        Image bomb2 = loadImage("images/newweapon2.png");
        Image bomb3 = loadImage("images/newweapon3.png");
        Image bomb4 = loadImage("images/newweapon4.png");
        Image bomb5 = loadImage("images/newweapon5.png");
        Image hersco = loadImage("images/hersco.png");
        Image smallhersco = loadImage("images/herscosmall.png");
        Image smallSalmon = loadImage("images/fish4.png");
        Image cloud1 = loadImage("images/Cloud1.png");
        Image cloud2 = loadImage("images/Cloud2.png");
        Image cloud3 = loadImage("images/Cloud3.png");
        //Image rainImage = loadImage("images/rain.png");
        Image blake = loadImage("images/joker.png");
        Image smallblake = loadImage("images/smalljoker.png");
        Image herscoJetLeftImage = loadImage("images/herscoJetGoingLeft.png");
        Image herscoJetRightImage = loadImage("images/herscoJetGoingRight.png");
        
        //Initiate Sounds
        soundShot = new SoundPlayer();
    
        // create sprites
        
        // Animation rainfallAnimation = new Animation();
        // rainfallAnimation.addFrame(rainImage, 300);
        
        Animation TankStill = new Animation();
        TankStill.addFrame(TankImage,300);
         
        Animation TankStill2 = new Animation();
        TankStill2.addFrame(TankImage2,300);
        
        Animation TankHit = new Animation();
        TankHit.addFrame(lilBoom,450);
        
        Animation blowUp = new Animation();
        blowUp.addFrame(Explode,300);
        
        Animation bul = new Animation();
        bul.addFrame(Bullet,300);
        bul.addFrame(Bullet2,200);
        
        Animation bul2 = new Animation();
        bul2.addFrame(bomb, 300);
        
        Animation shoot = new Animation();
            shoot.addFrame(Bullet,200);
            shoot.addFrame(Bullet2, 200);
            shoot.addFrame(Bullet3,200);  
        
        Animation shoot2 = new Animation();
        shoot2.addFrame(bomb,200);
        shoot2.addFrame(bomb2,200);
        shoot2.addFrame(bomb3,200);
        shoot2.addFrame(bomb4,200);
        shoot2.addFrame(bomb5,200);
        
        Animation shoot3 = new Animation();
        shoot3.addFrame(hersco, 200);
        
        Animation shoot5 = new Animation();
        shoot5.addFrame(blake, 200);
        
        Animation bul3 = new Animation();
        bul3.addFrame(smallhersco, 200);
        
        Animation bul5 = new Animation();
        bul5.addFrame(smallblake,200);
        Animation cloudSun1 = new Animation();
        cloudSun1.addFrame(cloud1, 200);
        
        Animation herscoJetLeftAnim = new Animation();
        herscoJetLeftAnim.addFrame(herscoJetLeftImage, 300);
        
        Animation herscoJetRightAnim = new Animation();
        herscoJetRightAnim.addFrame(herscoJetRightImage, 300);
        
        // Take the Image Frames and Create and Object(Sprite). Sprites have Attributes for locating its position, movement, and State. See Sprite.Java
		  Tank1.setTankSprite(new Sprite(TankStill));
		  Tank2.setTankSprite(new Sprite(TankStill2));
		  Shot = new Sprite(bul);
		  SecondShot = new Sprite(bul2);
		  Shot2 = new Sprite(bul);
		  SecondShot2 = new Sprite(bul2);
		  ThirdShot = new Sprite(bul3);
		  ThirdShot2 = new Sprite(bul3);
		  FourthShot = new Sprite(bul5);
		  FourthShot2 = new Sprite(bul5);
		  TankShot = new Sprite(shoot);
		  TankShot2 = new Sprite(shoot);
		  SecondaryTankShot = new Sprite(shoot2);
		  SecondaryTankShot2 = new Sprite(shoot2);
		  ThirdTankShot = new Sprite(shoot3);
		  ThirdTankShot2 = new Sprite(shoot3);
		  TankBoom = new Sprite(TankHit);
		  BOOM = new Sprite(blowUp);
		  FourthTankShot = new Sprite(shoot5);//new weapon
		  FourthTankShot2 = new Sprite(shoot5); 
		  herscoJetLeftSprite = new Sprite(herscoJetLeftAnim);
		  herscoJetRightSprite = new Sprite(herscoJetRightAnim);
    }


    private Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }
    
    public void TestTurret(Sprite tank)
    {
        tank.setY(100f);
    }

    
	public class MouseClass extends MouseAdapter{
	    
	        public void mousePressed(MouseEvent e)
	        {
		}
	}

	public void run() {
        String x = DataClass.getStart();
        if(x.compareTo("0")==0)
        {   
        	MainScreen.run();   //open up main screen
        }
        //else continue with game data...
        screen = new ScreenManager();
            

        String playlist[] = {"AnotherOneBitesTheDust.wav","BeautifulDisaster.wav",
       	 "Frankenstein.wav",
       	 "HeLLMaRch.wav","Magneto.wav","Molossus.wav",
       	 "XmasSpecial.wav"};
        String track = playlist[HelpScreen.tracknumber];
    	music = new MusicPlayer(track);
		music.l();
        try
        {
        DisplayMode displayMode =
        screen.findFirstCompatibleMode(POSSIBLE_MODES);
        screen.setFullScreen(displayMode);

        Window window = screen.getFullScreenWindow();
        window.addKeyListener(this);
        window.addMouseListener(new MouseClass());
        loadImages();
        //CountdownTimer.startCounter();  //starts the timer
        while (restartgame < 1)
            {
            animationLoop();
            }
        }   
            finally
            {
            System.out.println("Escaping");
            System.out.println("RESTART Variable: "+ restartgame);
            //is only called when restartgame is set to 1 to exit the animationloop
            //and restart the game
    
                screen.restoreScreen();//restores active screen
                Game.main(null);//calls Game.java's main class to start a new game
                
            }
        
        
        // for testing: since there is no other value to check here
        if (testing)
            check = true;
    }

    public void animationLoop() {
        long startTime = System.currentTimeMillis();
        long currTime = startTime;

       // while (currTime - startTime < TIME) {
          while (restartgame < 1) { //run loop infinitely till user exits.
          //***************************************************************
          //added restartgame variable Ryan K 4/8/08
          //***************************************************************
          
            long elapsedTime =
                System.currentTimeMillis() - currTime;

            currTime += elapsedTime;

            // update the sprites
            update(elapsedTime);
            
            // draw and update the screen            
            Graphics2D g = screen.getGraphics();            
            draw(g);
           // drawFade(g, currTime-startTime);
            g.dispose();
            screen.update();
            
            // break to do tests
            if (testing) {
                check = true;
                break;
            }
            
            // take a nap for garbage
            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException ex) { }
        }   
        
        
          
    }
    
    public void update(long elapsedTime)
    {

		if (credits==1 && Credits.creditsOn && Credits.count < Credits.loop){
            Credits.up(); 
		}
        
        
        //Checks if a jump needs to be ended
        if (jumpUp) {
            if (currJump > maxJump) {
                currJump = 0;
                jumpUp = false;
                jumpFall = true;
            }
            else 
                currJump+=1;
        }
        
        
        // needed for point test
        //if the tank shot goes off the screen, set the state to 0. UPDATED 4/5
        if ((TankShot.getState()==1) || (TankShot2.getState()==1)
        ||  (SecondaryTankShot.getState()==1) || (SecondaryTankShot2.getState()==1))
        {
	        if(TankShot.getX() < 0 || TankShot.getX() > 800)
	        { 
	        	hitTest = 2;
	            TankShot=resetShot(TankShot,1);            
	        }
	        else if((SecondaryTankShot.getX() < 0 || SecondaryTankShot.getX() > 800))
	        { 
	        	hitTest=2;
	            SecondaryTankShot=resetShot(SecondaryTankShot,1);
	      
	        }
	        if((TankShot2.getX() < 0 || TankShot2.getX() > 800))
	        {    
	        	hitTest2 = 2;
	            TankShot2=resetShot(TankShot2,2);
	        
	        }
	        else if((SecondaryTankShot2.getX() < 0 || SecondaryTankShot2.getX() > 800))
	        {
	           hitTest2 = 2;
	           SecondaryTankShot2=resetShot(SecondaryTankShot2,2);    
	        }
        }
       //To create gravity, there needs to be a decay on the velocity at a constant rate.
       //The decay must be at the correct interval to create a natural trajectory for the shot.
        if(GameSTATE != 3 && GameSTATEchanged == false)
        {
            GameSTATE=DataClass.getGameState();
        }
        String x = DataClass.getGravity();
        Gravf = Float.valueOf(x.trim()).floatValue();
        WindVar = DataClass.getWind();
        if(WindVar==1)
        {
            if(tankshoot==1||tankshoot2==1)
            {
                Windf=.001f+.004f*(float)Math.random();
                if(Math.random()>.5)Windf=Windf*-1f;
            }
        }
        else if(WindVar==0)
                Windf=.00f;
            else if(Windf==.00f&&WindVar==2)
                {
                Windf=.001f+.004f*(float)Math.random();
                if(Math.random()>.5)Windf=Windf*-1f;
                }
            else if (WindVar==3)
            {
                if(windcount<20+(int)20*Math.random())
                    windcount++;
                else
                {
                    windcount=0;
                    Windf=.001f+.004f*(float)Math.random();
                    if(Math.random()>.5)Windf=Windf*-1f;
                }
            }

        if(TankShot.getState()==1) {
            TankShot.setVelocityX(TankShot.getVelocityX() - Windf); //WIND
            TankShot.setVelocityY(TankShot.getVelocityY() + (Gravf));   //Gravity
		} else if(SecondaryTankShot.getState()==1) {
            SecondaryTankShot.setVelocityX(SecondaryTankShot.getVelocityX() - Windf); //WIND
            SecondaryTankShot.setVelocityY(SecondaryTankShot.getVelocityY() + (Gravf)); //Gravity
        }
            
        if(TankShot2.getState()==1) {
            TankShot2.setVelocityX(TankShot2.getVelocityX() - Windf); //WIND
            TankShot2.setVelocityY(TankShot2.getVelocityY() + (Gravf)); //Gravity
        } else if(SecondaryTankShot2.getState()==1) {
            SecondaryTankShot2.setVelocityX(SecondaryTankShot2.getVelocityX() - Windf); //WIND
            SecondaryTankShot2.setVelocityY(SecondaryTankShot2.getVelocityY() + (Gravf));   //Gravity
        }
        
        if(FourthTankShot.getState()==1) {
            FourthTankShot.setVelocityX(FourthTankShot.getVelocityX() - Windf); //WIND
            FourthTankShot.setVelocityY(FourthTankShot.getVelocityY() + (Gravf)); //Gravity
        } else if(FourthTankShot2.getState()==1) {
            FourthTankShot2.setVelocityX(FourthTankShot2.getVelocityX() - Windf); //WIND
            FourthTankShot2.setVelocityY(FourthTankShot2.getVelocityY() + (Gravf));   //Gravity
        }

        
        
        
       if(TankShot.getState()==1)
       {            
           if (ShotCollision(Tank1.getTankSprite(), TankShot,1)) 
           {
               Tank1.setHealth(Tank1.getHealth()-10);// subtract 10 points off player 1's health if hit
               hitTest=2;
                  
           }
           if (ShotCollision(Tank2.getTankSprite(), TankShot,1)) {
               Tank2.setHealth(Tank2.getHealth()-10);//subtract 10
               hitTest=2;
               
           }
       }
       else if (SecondaryTankShot.getState()==1){
           if (ShotCollision(Tank1.getTankSprite(), SecondaryTankShot,1)) {
               Tank1.setHealth(Tank1.getHealth()-20); // subtract 20
               hitTest=2;
               
           }
           if (ShotCollision(Tank2.getTankSprite(), SecondaryTankShot,1)) {
               Tank2.setHealth(Tank2.getHealth()-20); // subtract 20
               hitTest=2;
               
            }
       }
       else if(ThirdTankShot.getState()==1)
       {
           if (ShotCollision(Tank1.getTankSprite(), ThirdTankShot,1)) {
               Tank1.setHealth(Tank1.getHealth()-30); // subtract 30
               hitTest=2;
               
           }
           if (ShotCollision(Tank2.getTankSprite(), ThirdTankShot,1)) {
               Tank2.setHealth(Tank2.getHealth()-30); // subtract 30
               hitTest=2;
               
            }
       }
       else if(FourthTankShot.getState()==1)
       {
           if (ShotCollision(Tank1.getTankSprite(), FourthTankShot,1)) {
               Tank1.setHealth(Tank1.getHealth()-25);  //subtract 25
               hitTest=2;
           }
           if (ShotCollision(Tank2.getTankSprite(), FourthTankShot,1)) {
               Tank2.setHealth(Tank2.getHealth()-25);  //subtract 25
               hitTest=2;
            }
       }
       
       
       
       if(TankShot2.getState()==1)
            {           
             if (ShotCollision(Tank1.getTankSprite(), TankShot2,2)) 
             {
                 Tank1.setHealth(Tank1.getHealth()-10); // subtract 10 points off player 1's health if hit
                 hitTest2=2;
                 
             }
             if (ShotCollision(Tank2.getTankSprite(), TankShot2,2)) {
                 Tank2.setHealth(Tank2.getHealth()-10); //subtract 10
                 hitTest2=2;
                 
             }
            }
         else if (SecondaryTankShot2.getState()==1){
             if (ShotCollision(Tank1.getTankSprite(), SecondaryTankShot2,2)) {
                 Tank1.setHealth(Tank1.getHealth()-20); // subtract 20
                 hitTest2=2;
                 
             }
             if (ShotCollision(Tank2.getTankSprite(), SecondaryTankShot2,2)) {
                 Tank2.setHealth(Tank2.getHealth()-20); // subtract 20
                 hitTest2=2;
                 
             }         
           }
         else if(ThirdTankShot2.getState()==1)
         {
               if (ShotCollision(Tank1.getTankSprite(), ThirdTankShot2,2)) {
                   Tank1.setHealth(Tank1.getHealth()-30); // subtract 30
                   hitTest2=2;
                   
               }
               if (ShotCollision(Tank2.getTankSprite(), ThirdTankShot2,2)) {
                   Tank2.setHealth(Tank2.getHealth()-30); // subtract 30
                   hitTest2=2;
                   
                }
         }
         else if(FourthTankShot2.getState()==1)
         {
               if (ShotCollision(Tank1.getTankSprite(), FourthTankShot2,2)) {
                   Tank1.setHealth(Tank1.getHealth()-25);   //subtract 50
                   hitTest=2;
               }
               if (ShotCollision(Tank2.getTankSprite(), FourthTankShot2,2)) {
                   Tank2.setHealth(Tank2.getHealth()-25);   //subtract 50
                   hitTest=2;
                }
         }
       
        // update objects on the screen bring the current time with them
       //(Can be used for timing events.
        Tank1.getTankSprite().update(elapsedTime);
        Tank2.getTankSprite().update(elapsedTime);
        Shot.update(elapsedTime);
        SecondShot.update(elapsedTime);
        Shot2.update(elapsedTime);
        SecondShot2.update(elapsedTime);
        TankShot.update(elapsedTime);
        SecondaryTankShot.update(elapsedTime);
        TankShot2.update(elapsedTime);
        SecondaryTankShot2.update(elapsedTime);
        ThirdTankShot.update(elapsedTime);
        ThirdTankShot2.update(elapsedTime);
        FourthTankShot.update(elapsedTime);
        FourthTankShot2.update(elapsedTime);
        herscoJetLeftSprite.update(elapsedTime);
        herscoJetRightSprite.update(elapsedTime);

		
        
        
        if (herscoJetRightSprite.getX() > 900) {
			System.out.println("Hersco Jet went off the screen");
			hitTest=1;
			herscoJetRightSprite=resetShot(herscoJetRightSprite,1);
		}
		if (herscoJetLeftSprite.getX() < -100) {
			System.out.println("Hersco Jet went off the screen");
			hitTest2=1;
			herscoJetLeftSprite=resetShot(herscoJetLeftSprite,2);
		}
		
       
    }
    
    public boolean isWithin(Sprite jet, Sprite tankX) {
		if (Math.abs(jet.getX() - tankX.getX()) < 15) {
			return true;
		}
		return false;
	}
    
    public boolean ShotCollision(Sprite tank, Sprite shot, int player) {        
        boolean ret = false; 
    
        int PlayerLowX = ((Math.round(tank.getX())));
        int PlayerLowY = ((Math.round(tank.getY())));
        int PlayerHighX = ((Math.round(tank.getX())) + (tank.getWidth()));
        int PlayerHighY = ((Math.round(tank.getY())) + (tank.getHeight()));
        int ShotLowX = ((Math.round(shot.getX())));
        int ShotLowY = ((Math.round(shot.getY())));
        int ShotHighX = ((Math.round(shot.getX())) + (shot.getWidth()));
        
        //added by Ryan Kleckner to make the second shot path act like a homing missile
        //4/29/08
        if(SecondaryTankShot.getState()==1 && Math.round(SecondaryTankShot.getX()) >= Math.round(Tank2.getTankSprite().getX()))
            {
                SecondaryTankShot.setVelocityX(0f);
                SecondaryTankShot.setVelocityY(0.5f);
            }
        if(SecondaryTankShot2.getState()==1 && Math.round(SecondaryTankShot2.getX()) <= Math.round(Tank1.getTankSprite().getX()+30))
            {
                SecondaryTankShot2.setVelocityX(0f);
                SecondaryTankShot2.setVelocityY(0.5f);
            }
        
        if (ShotLowX-23 > PlayerLowX||ShotHighX+23>PlayerLowX) {
            if(ShotLowY> PlayerLowY) {         
                if (ShotHighX+23 < PlayerHighX||ShotLowX-23<PlayerHighX) {                        
                    if (ShotLowY < PlayerHighY) {
                        CreateHole(shot,1, player);
                        ret = true;     
                    }
                }
            }
        }
        
        return ret; 
    }
    
       
    public BufferedImage rotateImage(Image image, double angle) {
        // Create the BufferedImage
        BufferedImage img = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
        Graphics bg = img.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        
        // Rotate the image
        BufferedImage rotated = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform rot = new AffineTransform();
        rot.rotate(angle, 19, 14);
        g2d.transform(rot);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        
        return rotated; 
    }

    public float textVisibility=1000;
    public synchronized void draw(Graphics2D g)
    {
		if(credits==0){
			g.drawImage(bgImage, 0, 0, null);  // draws background
		
		DrawTerrain(g); // draws a flat surface for the tanks to land on. Use this function to implement a dynamic terrain generation
		
        if (TankCreated != true) // if no tank has been created, then create one.
        {
            CreateTank(g);
            Tank1.setTurretAngleX(Math.round(Tank1.getTankSprite().getX() + (int)(Tank1.getTankSprite().getWidth()*.5)));
            Tank1.setTurretAngleY(Math.round(Tank1.getTankSprite().getY() - (int)(Tank1.getTankSprite().getHeight())));
            Tank2.setTurretAngleX(Math.round(Tank2.getTankSprite().getX() + (int)(Tank2.getTankSprite().getWidth()*.5)));
            Tank2.setTurretAngleY(Math.round(Tank2.getTankSprite().getY() - (int)(Tank2.getTankSprite().getHeight())));
            TankCreated = true;
        }
        
        // Update the Selected Weapon Indicator and Ammo
        updateWeaponandModeIndicator(g);
        
        // using new GroundCollision parameters -- Eric M.  
        GroundCollision(Tank1.getTankSprite(), 1);
        GroundCollision(Tank2.getTankSprite(), 2); // check if there is ground underneath a tank, if not let it fall. This function is crudely done.
        // creating a center of gravity for the objects and doing a better check on when a tank should fall can be changed in this function.

        //  Draw the tank's turret. 19 = 1/2 centering the position of the turret on the tank. 3 = making the turret at the correct height of the tank.
        float[] dash = {25f,25f};
        g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
        		1.0f, dash, 0f));
        g.setColor(new Color(0f, 0f, 0f, 0.3f));
        AffineTransform tr = g.getTransform();
        Tank Tank = Tank1;
        if (turn == 2) Tank = Tank2;
        
        //if (Mode1 == 1 || Mode2 == 1)
        //{
	        // don't touch the magic numbers!
	        g.rotate(Tank.getAngle()*-0.018+1.625, Math.round(Tank.getTankSprite().getX())+(int)(Tank.getTankSprite().getWidth()*.5),
	        		Math.round(Tank.getTankSprite().getY()));
	        g.drawLine(Math.round(Tank.getTankSprite().getX())+(int)(Tank.getTankSprite().getWidth()*.5),
	        		Math.round(Tank.getTankSprite().getY()),
	        		Math.round(Tank.getTankSprite().getX())+(int)(Tank.getTankSprite().getWidth()*.5),
	        		Math.round(Tank.getTankSprite().getY())-800);
	        g.setTransform(tr);
        //}
        g.setColor(Color.black);
        g.drawLine(Math.round(Tank1.getTankSprite().getX())+(int)(Tank1.getTankSprite().getWidth()*.5),
        		Math.round(Tank1.getTankSprite().getY()),
        		Tank1.getTurretAngleX(),Tank1.getTurretAngleY());
        g.drawLine(Math.round(Tank2.getTankSprite().getX())+(int)(Tank2.getTankSprite().getWidth()*.5),
        		Math.round(Tank2.getTankSprite().getY()),
        		Tank2.getTurretAngleX(),Tank2.getTurretAngleY());
        g.setStroke(new BasicStroke(1));
        
        if (nextTurn == true)
        {
        	g.setFont(new Font("SansSerif", Font.BOLD, 15));
        	g.setColor(new Color(0f, 0f, 0f, (float)textVisibility/1000f));
        	if (turn == 1)
        		g.drawString("Your turn!", Tank1.getTankSprite().getX()-15,
        					Tank1.getTankSprite().getY()-40);
        	else
        		g.drawString("Your turn!", Tank2.getTankSprite().getX()-15,
    					Tank2.getTankSprite().getY()-40);
        	if (textVisibility > 0)
        		textVisibility -= 15;
        	else
        	{
        		nextTurn = false;
        		textVisibility = 1000;
        	}
        }
             
        //redraw tank in its new position
        g.drawImage(rotateImage(Tank1.getTankSprite().getImage(), Tank1.getTankSlant()), Math.round(Tank1.getTankSprite().getX()), Math.round(Tank1.getTankSprite().getY()), null);
            
        //player 2 tank image
        g.drawImage(rotateImage(Tank2.getTankSprite().getImage(), Tank2.getTankSlant()), Math.round(Tank2.getTankSprite().getX()), Math.round(Tank2.getTankSprite().getY()), null);

    	
        g.setColor(Color.GRAY);
            
            //Tank 1
            if(Tank1.getWeapon2() == 0)
            {
                g.drawImage(Shot.getImage(), 8, 10, null);
            }
            else if(Tank1.getWeapon2() == 1)
            {
                g.drawImage(SecondShot.getImage(), 0, 0, null);
            }
            else if(Tank1.getWeapon2() == 2)
            {
                g.drawImage(ThirdShot.getImage(), 0, 0, null);
            }
            else if(Tank1.getWeapon2() == 4)
            {
                g.drawImage(FourthShot.getImage(),0,0,null);
            }
            //Tank2
            if(Tank2.getWeapon2() == 0)
            {
                g.drawImage(Shot2.getImage(), 516, 10, null);
            }
            else if(Tank2.getWeapon2() == 1)
            {
                g.drawImage(SecondShot2.getImage(), 508, 0, null);
            }
            else if(Tank2.getWeapon2() == 2)//Can't display till image is smaller.
            {
                g.drawImage(ThirdShot2.getImage(), 508, 0, null);
            }
            else if(Tank2.getWeapon2() == 4)
            {
                g.drawImage(FourthShot2.getImage(), 508, 0, null);
            
            }
        
        DrawMessages(g); // draw messages such as tank health, power..ect
        
        //checks if Player 1's health is 0, this could be made into a function.
        checkHealth(Tank1.getHealth(), Tank1.getTankSprite(), g);
        checkHealth(Tank2.getHealth(), Tank2.getTankSprite(), g);
        
            //checks if bullet hit ground if it did creates hole and sets state=0
            if (TankShot.getState()== 1) { // if a shot has been fired   
                fireShot(TankShot, g, 1,1);
			} else if (SecondaryTankShot.getState()== 1) { // if a shot has been fired
                fireShot(SecondaryTankShot, g, 2,1);
			} else if(ThirdTankShot.getState()==1) {
                fireShot(ThirdTankShot, g, 3,1);
            } else if(FourthTankShot.getState()==1) {
                fireShot(FourthTankShot,g,5,1);
            } else if (herscoJetLeftSprite.getState()==1) {
				fireShot(herscoJetLeftSprite, g, 3, 2);
			}
			
            if (TankShot2.getState()== 1) { // if a shot has been fired   
                fireShot(TankShot2, g, 1,2);
            } else if (SecondaryTankShot2.getState()== 1) { // if a shot has been fired
                fireShot(SecondaryTankShot2, g, 2,2);
            } else if(ThirdTankShot2.getState()==1) {
                fireShot(ThirdTankShot2, g, 3,2);
            } else if(FourthTankShot2.getState()==1) {
                fireShot(FourthTankShot2, g, 5, 2);
            } else if(herscoJetRightSprite.getState()==1) {
				fireShot(herscoJetRightSprite, g, 3, 1);
            }


            //if player 1 fired checks to see what he fired and fires that shot.
         if (tankshoot == 1)
            {
            if(Tank1.getWeapon2() == 1 && Tank1.getSecondWeaponAmmo()> 0)//if w2 and ammo
                {
                    TankFire2(g,1);
                    Tank1.setSecondWeaponAmmo(Tank1.getSecondWeaponAmmo()-1);
                }
            else if(Tank1.getWeapon2() == 1 && Tank1.getSecondWeaponAmmo() == 0)//check to see if something is in air
                {
                    Tank1.setWeapon2(0);
                    TankShot.setState(1);
                    SecondaryTankShot=resetShot(SecondaryTankShot,1);
                    
                }
            if(Tank1.getWeapon2() == 2 && Tank1.getThirdWeaponAmmo()> 0)//if w3 and ammo
            {
                TankFire3(g,1);
                Tank1.setThirdWeaponAmmo(Tank1.getThirdWeaponAmmo()-1);
            }
            else if(Tank1.getWeapon2() == 2 && Tank1.getThirdWeaponAmmo() == 0)//check to see if something is in air
            {
                Tank1.setWeapon2(0);
                TankShot.setState(1);
                ThirdTankShot=resetShot(ThirdTankShot,1);   
            }
            
            if(Tank1.getWeapon2() == 3 && Tank1.getFourthWeaponAmmo()> 0)//if w4 and ammo
            {
                TankFire4(g,1);
                Tank1.setFourthWeaponAmmo(Tank1.getFourthWeaponAmmo()-1);
            }
            else if(Tank1.getWeapon2() == 3 && Tank1.getFourthWeaponAmmo() == 0)//check to see if something is in air
            {
                Tank1.setWeapon2(0);
                TankShot.setState(1);
                FourthTankShot=resetShot(FourthTankShot,1);
            }
            
             if (Tank1.getWeapon2()==0)//if no ammo for w2 or w3 or if w1 then fire
                {
                    TankFire(g,1);
                }
            tankshoot++;
            }
            
            if (isWithin(herscoJetLeftSprite, Tank1.getTankSprite())) {
			JetDrop(g, 2);
		}
		
		if (isWithin(herscoJetRightSprite, Tank2.getTankSprite())) {
			JetDrop(g, 1);
		}

        
        
        if (tankshoot2 == 1)
        {
             if(Tank2.getWeapon2() == 1 && Tank2.getSecondWeaponAmmo()> 0)
                {
                    TankFire2(g,2);
                    Tank2.setSecondWeaponAmmo(Tank2.getSecondWeaponAmmo()-1);
                }
            else if(Tank2.getWeapon2() == 1 && Tank2.getSecondWeaponAmmo() == 0)//check to see if something is in air
                {
                    Tank2.setWeapon2(0);
                    TankShot2.setState(1);
                    SecondaryTankShot2=resetShot(SecondaryTankShot2,2);
                }
             
            if(Tank2.getWeapon2() == 2 && Tank2.getThirdWeaponAmmo()> 0)
                {
                    TankFire3(g,2);
                    Tank2.setThirdWeaponAmmo(Tank2.getThirdWeaponAmmo()-1);
                }
            else if(Tank2.getWeapon2() == 2 && Tank2.getThirdWeaponAmmo() == 0)//check to see if something is in air
                {
                    Tank2.setWeapon2(0);
                    TankShot2.setState(1);
                    ThirdTankShot2=resetShot(ThirdTankShot2,2);
                }
            if(Tank2.getWeapon2() == 3 && Tank2.getFourthWeaponAmmo()> 0)
            {
                TankFire4(g,2);
                Tank2.setFourthWeaponAmmo(Tank2.getFourthWeaponAmmo()-1);
            }
            else if(Tank2.getWeapon2() == 3 && Tank2.getFourthWeaponAmmo() == 0)//check to see if something is in air
            {
                Tank2.setWeapon2(0);
                TankShot2.setState(1);
                FourthTankShot2=resetShot(FourthTankShot2,2);
            }
            
            if (Tank2.getWeapon2()==0)
            {
                TankFire(g,2);
            }
            tankshoot2++;
        }
        
        
        
        
        //if the shot is currently in the air, update the image of the shot.
        if (TankShot.getState()==1 )
        {
                    g.setColor(Color.black);
                    g.drawImage(TankShot.getImage(), Math.round(TankShot.getX()), Math.round(TankShot.getY()), null); 
        }
        else if(SecondaryTankShot.getState()==1)
        {
                    g.setColor(Color.black);
                    g.drawImage(SecondaryTankShot.getImage(), Math.round(SecondaryTankShot.getX()), Math.round(SecondaryTankShot.getY()),null);
                
        }
        else if(ThirdTankShot.getState()==1)
        {
                g.setColor(Color.black);
                g.drawImage(ThirdTankShot.getImage(), Math.round(ThirdTankShot.getX()-66), Math.round(ThirdTankShot.getY()-150),null);
                
        }
        else if(FourthTankShot.getState()==1)
        {
                    g.setColor(Color.black);
                    g.drawImage(FourthTankShot.getImage(), Math.round(FourthTankShot.getX()-66), Math.round(FourthTankShot.getY() - 150),null);
        }
        else if(herscoJetLeftSprite.getState()==1)
        {
			g.setColor(Color.black);
			g.drawImage(herscoJetLeftSprite.getImage(), Math.round(herscoJetLeftSprite.getX()-66), Math.round(herscoJetLeftSprite.getY() - 150),null);
		}
		
        if (TankShot2.getState()==1 )
        {
                    g.setColor(Color.black);
                    g.drawImage(TankShot2.getImage(), Math.round(TankShot2.getX()), Math.round(TankShot2.getY()), null); 
                
        }
        else if(SecondaryTankShot2.getState()==1)
        {
                    g.setColor(Color.black);
                    g.drawImage(SecondaryTankShot2.getImage(), Math.round(SecondaryTankShot2.getX()), Math.round(SecondaryTankShot2.getY()),null);
                
        }       
        else if(ThirdTankShot2.getState()==1)
        {
                g.setColor(Color.black);
                g.drawImage(ThirdTankShot2.getImage(), Math.round(ThirdTankShot2.getX()-66), Math.round(ThirdTankShot2.getY()-150),null);
                
        }    
        else if(FourthTankShot2.getState()==1)
        {
                    g.setColor(Color.black);
                    g.drawImage(FourthTankShot2.getImage(), Math.round(FourthTankShot2.getX()-66), Math.round(FourthTankShot2.getY() - 150),null);
        }    
        else if(herscoJetRightSprite.getState()==1)
        {
			g.setColor(Color.black);
			g.drawImage(herscoJetRightSprite.getImage(), Math.round(herscoJetRightSprite.getX()-66), Math.round(herscoJetRightSprite.getY()-150),null);
		}
  

      //tried making this more efficient and tank 2 shot always explodes right out of the turret
        if (hitTest == 2)
        {

                if(TankShot.getState()==1)
                {
                    g.drawImage(TankBoom.getImage(), Math.round(TankShot.getX()), (Math.round(TankShot.getY())), null);
                    TankShot = resetShot(TankShot,1);
                }
                else if(SecondaryTankShot.getState()==1)
                {
                    g.drawImage(TankBoom.getImage(), Math.round(SecondaryTankShot.getX()), (Math.round(SecondaryTankShot.getY())), null);               
                    SecondaryTankShot = resetShot(SecondaryTankShot,1);
                }
                else if(ThirdTankShot.getState()==1)
                {
                    g.drawImage(TankBoom.getImage(), Math.round(ThirdTankShot.getX()), (Math.round(ThirdTankShot.getY())), null);
                    ThirdTankShot = resetShot(ThirdTankShot,1);
                }
                else if(FourthTankShot.getState()==1)
                {
                    g.drawImage(TankBoom.getImage(), Math.round(FourthTankShot.getX()), (Math.round(FourthTankShot.getY())), null);
                    FourthTankShot = resetShot(FourthTankShot,1);
               
                }
                
                                
        }
        if(hitTest2==2)
        {
            
                if(TankShot2.getState()==1)
                {
                    g.drawImage(TankBoom.getImage(), Math.round(TankShot2.getX()), (Math.round(TankShot2.getY())), null);
                    TankShot2 = resetShot(TankShot2,2);
                }
                else if(SecondaryTankShot2.getState()==1)
                {
                    g.drawImage(TankBoom.getImage(), Math.round(SecondaryTankShot2.getX()), (Math.round(SecondaryTankShot2.getY())), null);
                    SecondaryTankShot2 = resetShot(SecondaryTankShot2,2);
                }
                else if(ThirdTankShot2.getState()==1)
                {
                    g.drawImage(TankBoom.getImage(), Math.round(ThirdTankShot2.getX()), (Math.round(ThirdTankShot2.getY())), null);
                    ThirdTankShot2 = resetShot(ThirdTankShot2,2);
                }
                else if(FourthTankShot2.getState()==1)
                {
                    g.drawImage(TankBoom.getImage(), Math.round(FourthTankShot2.getX()), (Math.round(FourthTankShot2.getY())), null);
                    FourthTankShot2 = resetShot(FourthTankShot2,2);
                }
                
            
        }
       
    }   
		
		if (tutorialmode) 
		{
			drawTutorial(g);
			g.setColor(Color.white);
			g.drawString("Press 'T' to exit tutorial mode", 300, 562);
			g.setColor(Color.black);
			g.drawString("Tutorial Mode", 355, 45);
		}
		else
		{
			g.setColor(Color.white);
			g.drawString("Press 'T' to enter tutorial mode", 300, 562);
		}
		
		g.setColor(Color.white);
		g.drawString("Press 'O' for options", 300, 575);
		
		g.drawString("Press 'M' to mute the music", 300, 588);
		
		//Wouldn't have worked without Zach K. Can't break out of while loops but can break out of if loops.
		if (credits==1 && Credits.creditsOn && Credits.count < Credits.loop){// Draws credits
            Credits.draw(g); 
		}
    }//end of Draw phase...
    
    private void drawTutorial(Graphics g)
    {
    	
    	// draw text boxes and all that
    	if (turn == 1)    
    	{
    		g.drawImage(blackarrowleft, 170, 75, null);
    		g.drawImage(keyarrowup, 240, 70, null);
    		g.drawImage(keyarrowdown, 290, 70, null);
    		g.drawString("to change mode", 240, 140);
    	}
    	else if (turn == 2)
    	{
    		g.drawImage(blackarrowright, 580, 75, null);
    		g.drawImage(keyarrowup, 520, 70, null);
    		g.drawImage(keyarrowdown, 470, 70, null);
    		g.drawString("to change mode", 470, 140);
    	}
    	
    	int barX = 0;
    	int barY = 0;
        int barXOffset = 50;
        int barYOffset = 150;
        
        if (turn == 1)
        {
        	if (Tank1.getTankSprite().getY() > 520) barYOffset = -barYOffset;
        	barX = (int)Tank1.getTankSprite().getX()-barXOffset;
        	barY = (int)Tank1.getTankSprite().getY()-barYOffset;
        }
        else if (turn == 2)
        {
        	if (Tank2.getTankSprite().getY() > 520) barYOffset = -barYOffset;
        	barX = (int)Tank2.getTankSprite().getX()-barXOffset;
        	barY = (int)Tank2.getTankSprite().getY()-barYOffset;
        }
        if (barX < 35) barX = 35;
        else if (barX > 675) barX = 675;
        
        g.drawImage(keyspacebar, barX, barY, null);
        g.drawImage(keyarrowleft, barX, barY+35, null);
        g.drawImage(keyarrowright, barX+35, barY+35, null);
    	
        if (turn == 1)
        {
	        if (Mode1 == 0) g.drawString("to move!", barX+80, barY+55);
	        if (Mode1 == 1) g.drawString("to aim!", barX+80, barY+55);
	        if (Mode1 == 2) g.drawString("to adjust power!", barX+80, barY+55);
	        if (Mode1 == 3) 
	        {
	        	g.drawString("to change weapon!", barX+80, barY+55);
	        	g.drawImage(blackarrowup, 30, 110, null);
	        	g.drawString("Ammo", 10, 160);
	        }
        }
        else if (turn == 2)
        {
	        if (Mode2 == 0) g.drawString("to move!", barX+80, barY+55);
	        if (Mode2 == 1) g.drawString("to aim!", barX+80, barY+55);
	        if (Mode2 == 2) g.drawString("to adjust power!", barX+80, barY+55);
	        if (Mode2 == 3) 
	        {
	        	g.drawString("to change weapon!", barX+80, barY+55);
	        	g.drawImage(blackarrowup, 775, 110, null);
	        	g.drawString("Ammo", 750, 160);
	        }
        }
    }

    //Used to tell when to play sound... if not included sound repeats
    boolean EndingActive = false;
    private void checkHealth (int health, Sprite player, Graphics g) {
        
        
        if (health <= 0) {
            
            int playerNumber = 2;
            if (player.equals(Tank2.getTankSprite())) {
                playerNumber = 1;
            }
            Color c = new Color(1.0f, 1.0f, 1.0f, 0.6f);
            g.setColor(c);          
            g.fillRoundRect(260, 205, 250, 60, 15, 15); 
            g.setColor(Color.black);
            int deathPositionX = Math.round(player.getX()-14);
            int deathPositionY = Math.round(player.getY()-75);
            
            // This little piece of code checks if  the explosion animation has run its
            // full course once.  If it has, it will stop drawing the image and then move
            // the tank off the screen -- added by Wes B. 10-3-13
            // tankKilled is a public int found at the top of Game.java, set initially to 0
            
            if (tankExplosionLoop < 18) {
                g.drawImage(BOOM.getImage(), deathPositionX, deathPositionY, null);
                tankExplosionLoop = tankExplosionLoop+1;
            } else if (tankExplosionLoop >= 18) {
                player.setX(1000);
            }
            
            if (playerNumber == 1) {
                Tank1.getTankSprite().setVelocityX(0.06f);
                if (Tank1.getTankSprite().getX() > deathPositionX) {
                    Tank1.getTankSprite().setVelocityX(0f);
                }
                g.setColor(Color.MAGENTA);
                g.drawString("WINNER", (int)Tank1.getTankSprite().getX(), (int)Tank1.getTankSprite().getY()+5);
            } else if (playerNumber == 2) {
                Tank2.getTankSprite().setVelocityX(-0.06f);
                if (Tank1.getTankSprite().getX() < deathPositionX) {
                    Tank1.getTankSprite().setVelocityX(0f);
                }
                g.setColor(Color.CYAN);
                g.drawString("WINNER", (int)Tank2.getTankSprite().getX(), (int)Tank2.getTankSprite().getY()+5);
            }
            
            // Message that displays when game is over
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER: PLAYER " + playerNumber + " WINS",300,230);
            g.drawString("Press 'R' to play again", 310, 250);
            turn = 3; // When turn is equal to 3, neither player can go, this will prevent players from continuing to fire
             //Adds sounds after game
             if(playerNumber==1){ // Player 1 wins
                 String Ending[] = {"Winner1.wav", "1Winner2.wav", "toasty.wav", "salmonwin.wav", "herscowin.wav"};
                 int ran = (int)Math.floor((Math.random() * Ending.length));
                String EndingNumber = Ending[ran];
                 if(EndingActive == false) {
                    try {
                        InputStream iStream = new FileInputStream(EndingNumber);
                        AudioStream aStream = new AudioStream(iStream );
                        AudioPlayer.player.start(aStream );
                    } catch(Exception e) {
                        
                    System.out.println(e);
                    
                    }
                    EndingActive = true;
                 }
             }
             else //Player 2 Wins
             {
                 String Ending[] = {"Winner2.wav", "2Winner2.wav", "toasty.wav", "salmonwin.wav", "herscowin.wav"};
                 int ran = (int)Math.floor((Math.random() * Ending.length));
                String EndingNumber = Ending[ran];   
                 if(EndingActive == false){
                 try
                {
                InputStream iStream = new FileInputStream(EndingNumber);
                AudioStream aStream = new AudioStream(iStream );
                AudioPlayer.player.start(aStream );
                }
                catch(Exception e)
                {
                System.out.println(e);
                }
                EndingActive = true;
             }
        }
            }
        
    }
 
    
  //Creates the indentation if it hits land otherwise leaves Shot alone.
    private int good=0;
    private void fireShot(Sprite shot, Graphics g, int weapon, int player)
    {   
        if(shot.getX() > 0)//inside the screen
        {   
            if(topy[Math.round(shot.getX())] < Math.round(shot.getY()))//above the top of the terrain
            {
            	good++;
                CreateHole(shot,weapon, player);
            }
        }
        
        
    }
    
    private void CreateHole(Sprite shot,int weapon, int player)
    {
            soundShot.close(); //resets sound after being shot
            if(player==1) hitTest = 2;//says the object hit
            else hitTest2 = 2;
            int counter3=Math.round(shot.getX())-14; //Left/Right Starting Square pos adjustment on hole
            int holderSin=0;
            int holderCos=1;
            double groundHolder=180;        
            while (groundHolder <= 359)//creates hole starting at 180 degrees and going to 360
            {
                holderSin = (int)(Math.floor((Math.sin(groundHolder/57.3))*weapon*5));//uses a length of 10 for hole
                if (counter3>3)
                {
                    topy[counter3] = topy[counter3]- holderSin;//reduces terrain
                }//depth of square //

                //creates an unequal cirlce                     
                if (holderCos==3)
                {
                    counter3++;
                    holderCos=0;    
                }

                holderCos++;
                groundHolder++; 
            }
            
    }

    private Sprite resetShot(Sprite shot,int player)
    {

        if (hitTest == 2 || hitTest2 == 2)
        {
        	
            turn=(2/turn);  //switches the other player's turn
            curfuel = 110;
            nextTurn = true;
        }
        shot.setState(0);
        shot.setVelocityX(0f);
        shot.setVelocityY(0f);
        if(player==1)hitTest = 0;
        else hitTest2=0;
        shot.setX(1f);
        shot.setY(-4000f);
        
        return shot;
    }
    
    //If true can change terrain
    //Switches to false when first shot is fired
    boolean terrainChange = true;
    
    //KEY COMMANDS

    
/*   	ModeIndex
Mode 0 is movement
Mode 1 is Turret angle
Mode 2 is Power
Mode 3 is Weapon Selection


	WeaponIndex
0 = Normal
1 = Nuclear
2 = Hersco
3 = Joker

Modes for Tank 1 (first player) disabled 10/28/13
*/
    int Mode1 = 0; //Mode of the arrow keys controlled by up and down
    int M1i = 0;
    int[] Modeindex1 = {0,1,2,3}; // current mode player 1
    int[] weapons1 = {0,1,2,3}; // current weapon
    int weaponindex1 = 0; // used to cycle thru weapons
    int Mode2 = 0; //Mode of the arrow keys controlled by up and down PLayer2
    int M2i = 0; // allows cycling thru modes 
    int[] Modeindex2 = {0,1,2,3};
    int[] weapons2 = {0,1,2,3,4};
    int weaponindex2 = 0;
    boolean powerMode = false;
	boolean moveMode = true;
 // song chosen from Help Screen
    String playlist[] = {"AnotherOneBitesTheDust.wav","BeautifulDisaster.wav",
   	 "Frankenstein.wav",
   	 "HeLLMaRch.wav","Magneto.wav","Molossus.wav",
   	 "XmasSpecial.wav"};



    
    public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
      if (credits==0){  
        // control and n together ends game
      if ((keyCode == KeyEvent.VK_N) && (e.isControlDown())) {
            Tank1.setHealth(0);
            Tank2.setHealth(0);
            hitTest = 1;
            hitTest2=1;
      } 
      

      if (keyCode == KeyEvent.VK_O) {
        if (PauseMenuOpen == false)// open pause menu
        {   
            PauseMenuOpen = true;
            //CountdownTimer.counterTimer.stop();
        }
        else {
            PauseMenuOpen = false; // close pause menu
            //CountdownTimer.counterTimer.restart();
        }
      }
		

      
      if (keyCode == KeyEvent.VK_U) {
            if (ControlsOverlayOpen == false) // if controls overlay isnt open
            {   
                ControlsOverlayOpen = true; // open the controls overlay
            }
            else {
                ControlsOverlayOpen = false; // close controls overlay
            }
      }
      if (keyCode == KeyEvent.VK_W) {
          if (PauseMenuOpen == true) {
               if(WindVar<3)
               {
                   WindVar++;
               }
               else
               {
                   WindVar=0;
               }
          }
          DataClass.setwind(WindVar);
      }  
      if (keyCode == KeyEvent.VK_M && PauseMenuOpen){// Cycle through Music in Options Screen
    	  	music.s();
    	    String track = playlist[HelpScreen.tracknumber=((HelpScreen.tracknumber+1)%7)];
    		music = new MusicPlayer(track);
    		music.l();
      }
      if (keyCode == KeyEvent.VK_R)  { // Resets game
    	  //This will reset the terrain, replenish both player's health and set turn to player 1.
    	  //Set Y sets the tanks to start on the ground
          //Therefore not falling and losing health
  	  	music.s();
	    String track = playlist[HelpScreen.tracknumber=((HelpScreen.tracknumber+1)%7)];
		music = new MusicPlayer(track);
		music.l();
          //If a shot is fired, can't change terrain
           Tank1.getTankSprite().setY(599);
           Tank2.getTankSprite().setY(599);
           Tank1.getTankSprite().setX(200);
           Tank2.getTankSprite().setX(550);
           if (levelNumber == "1")
           {
              ChangeLevel("2");
           }
           else if (levelNumber == "2")
              ChangeLevel("3");
           else if (levelNumber == "3")
           {
              ChangeLevel("1");
           }
              //JumpUp resets the turrets look
           jumpUp = true;
           Tank1.setHealth(100);
           Tank2.setHealth(100);
           Tank1.setWeapon2(0); //Set weapon to original
           Tank2.setWeapon2(0);	//Set weapon to original
           turn = 1;
           curfuel = 110; //Sets initial fuel value to equal 110
           Mode1 = 0; // Makes it so both tanks are back to default weapon when restared
           //This will give both tanks the same power when restarted
           Tank1.setShotPower(30);
           Tank2.setShotPower(30);
           
           //Reset ammo for each weapon back to full after restarting game
           Tank1.setSecondWeaponAmmo(2);
           Tank2.setSecondWeaponAmmo(2);
           Tank1.setThirdWeaponAmmo(1);
           Tank2.setThirdWeaponAmmo(1);
           Tank1.setFourthWeaponAmmo(1);
           Tank2.setFourthWeaponAmmo(1);
      }
      
      if (keyCode == KeyEvent.VK_Z) {
            //Set Y sets the tanks to start on the ground
            //Therefore not falling and losing health

            //If a shot is fired, can't change terrain
          if (terrainChange == true)
          {
             Tank1.getTankSprite().setY(599);
             Tank2.getTankSprite().setY(599);
             if (levelNumber == "1")
             {
                ChangeLevel("2");
             }
             else if (levelNumber == "2")
                ChangeLevel("3");
             else if (levelNumber == "3")
             {
                ChangeLevel("1");
             }
                //JumpUp resets the turrets look
             jumpUp = true;
          }
       }
       
       if (keyCode == KeyEvent.VK_M && !PauseMenuOpen) {
		   music.mute();
	   }

       if(!PauseMenuOpen)
       {
            if (GameSTATE==1)      // Fuel Turn-Based Mode
            {
                if (keyCode == KeyEvent.VK_T) 
                {
              	  tutorialmode = !(tutorialmode);
              	  
                }

                if (turn==1) // Player one's turn
                {
                  
                  
                  
					if (keyCode == KeyEvent.VK_1) {
						weaponindex1=0;
					}else if (keyCode == KeyEvent.VK_2) {
						weaponindex1=1;
					}else if (keyCode == KeyEvent.VK_3) {
						weaponindex1=2;
					}else if (keyCode == KeyEvent.VK_4) {
						weaponindex1=3;
					}
					Tank1.setWeapon2(weapons1[weaponindex1]);
					
                    
                    
                  
                   if (keyCode == KeyEvent.VK_E) {
				    moveMode = false;
					powerMode = true;
                        Tank1.setShotPower(-.01f + Tank1.getShotPower());
                        if (Tank1.getShotPower() <= -1) {
                            Tank1.setShotPower(-1);
                        }
                   }
                  
                   if (keyCode == KeyEvent.VK_Q) {
					moveMode = false;
					powerMode = true;
                        Tank1.setShotPower(.01f + Tank1.getShotPower());
                        if (Tank1.getShotPower() >= -.01) {
                            Tank1.setShotPower((float)-.01);
                        }
                   }
                  
                  
                  
                   if (keyCode == KeyEvent.VK_LEFT) {
                            Tank1.increaseAngle();      
                    }
                  
                   if (keyCode == KeyEvent.VK_RIGHT) { 
                        Tank1.decreaseAngle();      
                   }
                 
                   if ((keyCode == KeyEvent.VK_A) && (curfuel > 0)) {  //This will move the tank if the left key is pressed and fuel isn't empty.
                        //if(Tank1.getMovesLeft()>0) {
						powerMode = false;
						moveMode = true;
						
                            if (Tank1.getTankSprite().getX() >= 3){
                                System.out.println("Player 1's tank fuel is at: " + curfuel);
                                moving=true;
                                Tank1.setMovesLeft(Tank1.getMovesLeft()-3);
                                Tank1.getTankSprite().setX(Tank1.getTankSprite().getX()-3);
                                jumpUp = false;
                                curfuel -= fueldepletion; //Takes fuel away at a constant rate while the tank is moving.
                            }
                        //}
                   }
                  
                  
                   if ((keyCode == KeyEvent.VK_A) && (curfuel <= 0)) {  // If the left key is pressed and fuel is empty: Don't move
                      powerMode = false;
					  moveMode = true;
					  
					  moving = false;
                   }
                  
                  
                   if ((keyCode == KeyEvent.VK_D) && (curfuel > 0)) {
                        //if(Tank1.getMovesLeft()>0) {
						powerMode = false;
						moveMode = true;

                            if (Tank1.getTankSprite().getX()<345){
                                    System.out.println("Player 1'S tank fuel is at: " + curfuel);
                                    moving=true;
                                    Tank1.setMovesLeft(Tank1.getMovesLeft()-3);
                                    Tank1.getTankSprite().setX(Tank1.getTankSprite().getX()+3);
                                    jumpUp = false;
                                    curfuel -= fueldepletion; //Takes fuel away at a constant rate while the tank is moving.
                            }
                        //}
                  }
                  
                  if ((keyCode == KeyEvent.VK_D) && (curfuel <= 0)){
                      powerMode = false;
					  moveMode = true;
					  
					  moving = false;
                  }
                  //}
                  
                  if (keyCode == KeyEvent.VK_SPACE) //When space is pressed, the turn will switch to the other player
                  {
                     // curfuel = 110; //This will make sure the other player has full fuel at the beginning of his/her turn.
                      System.out.println(curfuel);
                      shotcounter2++;
                      terrainChange = false;
                      //Makes sure tank is no longer paralyzed
                      if(shotcounter2 > PARALYZED){
                        if (TankShot.getState() == 0 && SecondaryTankShot.getState() == 0 && ThirdTankShot.getState()==0 && FourthTankShot.getState()==0)
                        {

                            if(Tank1.getWeapon2()== 0){
                                TankShot.setState(1);
                                //soundShot.load("gunshot.mid");
                                soundShot.play();
                                }
                            else if(Tank1.getWeapon2()== 1)
                            {
                                //soundShot.load("gunshot.mid");
                                soundShot.play();
                                SecondaryTankShot.setState(1);
                            }
                            else if(Tank1.getWeapon2()==2)
                            {
                                herscoJetRightSprite.setState(1);
                            }
                            else if(Tank1.getWeapon2()==3)
                            {
                                FourthTankShot.setState(1);
                            }
                            tankshoot = 1;
                            
                        }
                        
                        
                  }
					powerMode = false;
					moveMode = true;
                      //resets both players' mode to move mode
                      //M1i = 0; 
                      //Mode1 = Modeindex1[M1i % 4]; 
                      //M2i = 0; 
                      //Mode2 = Modeindex2[M2i % 4]; 
                    //CountdownTimer.counterTimer.restart(); //restarts the timer
                    //CountdownTimer.counter=HelpScreen.time; //sets the timer to 30
                  }
                
                }
                else if (turn==2) //Players two's turn
                {
                    
                 //if (Mode2==3){
                    if (keyCode == KeyEvent.VK_1) {
						weaponindex2=0;
					}else if (keyCode == KeyEvent.VK_2) {
						weaponindex2=1;
					}else if (keyCode == KeyEvent.VK_3) {
						weaponindex2=2;
					}else if (keyCode == KeyEvent.VK_4) {
						weaponindex2=3;
					}
					Tank2.setWeapon2(weapons2[weaponindex2]);
					
					/*if (keyCode == KeyEvent.VK_LEFT) { // Normal
                    	weaponindex2=weaponindex2+3;
                      Tank2.setWeapon2(weapons2[weaponindex2%4]);
                      }
                  
                  if (keyCode == KeyEvent.VK_RIGHT) { // Nuclear Weapon
                	  weaponindex2=weaponindex2+1;
                      Tank2.setWeapon2(weapons2[weaponindex2%4]);
                  }*/
                //}
                  
                //if (Mode2==2) {
                  if (keyCode == KeyEvent.VK_E) {
						moveMode = false;
						powerMode = true;
                        Tank2.setShotPower(-.01f + Tank2.getShotPower());
                        if (Tank2.getShotPower() <= -1) {
                            Tank2.setShotPower(-1);
                        }
                  }
                  
                  if (keyCode == KeyEvent.VK_Q) {
						moveMode = false;
						powerMode = true;
                        Tank2.setShotPower(.01f + Tank2.getShotPower());
                        if (Tank2.getShotPower() >= -.01) {
                            Tank2.setShotPower((float)-.01);
                        }
                  }
                //}
                    
                //if (Mode2==1) {
                  if (keyCode == KeyEvent.VK_LEFT) { 
                        Tank2.increaseAngle();      
                    }
                  
                  if (keyCode == KeyEvent.VK_RIGHT) {
                        Tank2.decreaseAngle();      
                  }
                //}
                  
                //if (Mode2==0) {
                  if ((keyCode == KeyEvent.VK_A) && (curfuel > 0)) {   //Makes it so tank can move when fuel isn't empty.
                        //if(Tank2.getMovesLeft()>0) {
							powerMode = false;
							moveMode = true;
                            if (Tank2.getTankSprite().getX() >395){
                                moving=true;
                                Tank2.setMovesLeft(Tank2.getMovesLeft()-3);
                                Tank2.getTankSprite().setX(Tank2.getTankSprite().getX()-3);
                                jumpUp = false;
                                curfuel -= fueldepletion; //Takes fuel away at a constant rate while the tank is moving.
                                System.out.println("Player 2's tank fuel is at: " + curfuel);
                            }
                        //}
                  }
                  
                  if ((keyCode == KeyEvent.VK_A) && (curfuel <= 0)) {
                        powerMode = false;
						moveMode = true;
						moving = false;
                  }
                  
                  if ((keyCode == KeyEvent.VK_D) && (curfuel > 0)) {
                        //if(Tank2.getMovesLeft()>0) {
							powerMode = false;
							moveMode = true;
                            if (Tank2.getTankSprite().getX()<= 796 ) {
                                    moving=true;
                                    Tank2.setMovesLeft(Tank2.getMovesLeft()-3);
                                    Tank2.getTankSprite().setX(Tank2.getTankSprite().getX()+3);
                                    jumpUp = false;
                                    curfuel -= fueldepletion; //Takes fuel away at a constant rate while the tank is moving.
                                    System.out.println("Player 2's tank fuel is at: " + curfuel);
                            }
                        //}
                  }
                  
                  if ((keyCode == KeyEvent.VK_D) && (curfuel <= 0)){
                      powerMode = false;
					  moveMode = true;
					  moving = false;
                  }
                //}
                  
                  if (keyCode == KeyEvent.VK_SPACE)
                  {
                      //curfuel = 110; //Makes sure that the other play will have full fuel at the beginning of his/her turn
                      System.out.println(curfuel);
                      shotcounter++;
                      terrainChange = false;
                      System.out.println(shotcounter);
                      
                      //Makes sure tank is no longer paralyzed
                      if(shotcounter > PARALYZED){
                          
                        if (TankShot2.getState() == 0 && SecondaryTankShot2.getState() == 0 && ThirdTankShot2.getState()==0 && FourthTankShot2.getState()==0)
                        {


                            if(Tank2.getWeapon2()== 0)
                            {
                                TankShot2.setState(1);
                                //soundShot.load("gunshot.mid");
                                soundShot.play();
                            }
                            else if(Tank2.getWeapon2()== 1)
                            {
                                //soundShot.load("gunshot.mid");
                                soundShot.play();
                                SecondaryTankShot2.setState(1);
                            }
                            else if(Tank2.getWeapon2()== 2)
                            {
                                herscoJetLeftSprite.setState(1);
                            }
                            else if(Tank2.getWeapon2()== 3)
                            {
                                FourthTankShot2.setState(1);
                            }
                            tankshoot2 = 1;
                            
                        }
                      
                     } 
					powerMode = false;
					moveMode = true;
                    //resets both players' mode to move mode
                      //M1i = 0; 
                      //Mode1 = Modeindex1[M1i % 4]; 
                      //M2i = 0; 
                      //Mode2 = Modeindex2[M2i % 4]; 
                    //CountdownTimer.counterTimer.restart(); //restarts the timer
                    //CountdownTimer.counter=HelpScreen.time; //sets the timer to 30
                    
                  }
                }
            }
            
                  
               if (GameSTATE == 3) 
            {
                restartgame = 1;
            }
            

                        
                
                     
            
        // exit the program
        if (keyCode == KeyEvent.VK_ESCAPE) {
             music.s();
    		music = new MusicPlayer("OFortuna.wav");
    		music.l();
             credits=1;
 			if (Credits.creditsOn)
 				Credits.creditsOn = false;
			else
				Credits.creditsOn = true;
        }
        else {
            System.out.println("You Pressed: " + KeyEvent.getKeyText(keyCode) ); //print out which key the user presses.
            e.consume(); //erases the key event.
        }
       }
      }
      else if (credits==1){
          if (keyCode == KeyEvent.VK_ESCAPE) {
              System.exit(-1);
         }
      }
  }
            
        

 
  public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        
        if (keyCode == KeyEvent.VK_LEFT)
        {
            moving = false;
            //jump up resets turret after moving need to add a method that
            //rests the turret constantly while moving jump up doesn't work
            jumpUp = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT) 
        {
            moving = false;
            //jump up resets turret after moving need to add a method that
            //rests the turret constantly while moving jump up doesn't work
            jumpUp = true;
        }
        
                     e.consume(); //erases the key event.
    }

    public void keyTyped(KeyEvent e) {
// takes the input and eats it

        e.consume();
    }
    ///////////////////////////////////////
    ///Moved to KeyPressed for enter key///
    ////////Ryan Crean 4/3/08//////////////
    ///////////////////////////////////////
    
       
    //This method draws the in-game display and menus.
    public void DrawMessages(Graphics g)
    {   

    	
        g.drawImage(headerBar, 0, 0, null); // header background
        
        // Player 1 Health Bar
        int healthBar1X = 35;
        int healthBar1Y = 29;
        
        int healthBar1Value = (int) (110 - (Tank1.getHealth()*1.1)); // Used to determine how much of the health bar is covered
        int healthBar1Display = (int) (Tank1.getHealth()); // Used to display the value of a player 1's health
        
        g.drawImage(healthIcon, 5, 28, null); // health icon
        g.drawImage(healthBar, healthBar1X, healthBar1Y, null); // health bar
        g.setFont(new Font("SansSerif", Font.BOLD, 20)); // Sets the font and size of displayed health number
        g.setColor(Color.darkGray); // Makes the rectangle that covers health dark gray
        
        // Draws rectangle that covers Player 1 Health Bar when P1 loses health
        g.drawRect(healthBar.getWidth(null)+(healthBar1X-1)-healthBar1Value,
                healthBar1Y+1, healthBar1Value - 1, 20);
        g.fillRect(healthBar.getWidth(null)+(healthBar1X-1)-healthBar1Value,
                healthBar1Y+1, healthBar1Value - 1, 20);
        g.setColor(Color.white); // Makes displayed health number white
        g.drawString(Integer.toString(healthBar1Display), (healthBar1X + 5), (healthBar1Y + 20)); // Displays the number value of player 1's health
        
        
        //Fuel display in the center of the screen
        int barX = 355; //Location of fuel bar on x axis
        int barY = 29;  //Location on y axis
        
        int barXOffset = 30;
        int barYOffset = 40;
        
        if (turn == 1)
        {
        	if (Tank1.getTankSprite().getY() > 520) barYOffset = -barYOffset;
        	barX = (int)Tank1.getTankSprite().getX()-barXOffset;
        	barY = (int)Tank1.getTankSprite().getY()+barYOffset;
        }
        else if (turn == 2)
        {
        	if (Tank2.getTankSprite().getY() > 520) barYOffset = -barYOffset;
        	barX = (int)Tank2.getTankSprite().getX()-barXOffset;
        	barY = (int)Tank2.getTankSprite().getY()+barYOffset;
        }
        if (barX < 35) barX = 35;
        else if (barX > 675) barX = 675;
        
        int fuelBarValue = (int) (110 - curfuel);
        //if ((Mode1 == 0) && (Mode2 == 0))
        if(moveMode == true)
		{
	        g.drawImage(fuelIcon, barX-25, barY-7, null); // fuel icon
	        g.drawImage(fuelBar, barX, barY, null); // Place fuel bar at specified part of the screen
	        g.setColor(new Color( (fuel-curfuel)/(float)fuel * 0.5f,0,0) ); // Makes color behind fuel bar black
	        // Draws a rectangle to cover what has been used of the fuel so far.
	        g.drawRect(fuelBar.getWidth(null)+(barX-1)-fuelBarValue,
	        		barY+1, fuelBarValue - 1, 20);
	        g.fillRect(fuelBar.getWidth(null)+(barX-1)-fuelBarValue,
	        		barY+1, fuelBarValue - 1, 20);
	        
	        if (curfuel <= 0) 
	        {
	            g.setColor(Color.WHITE);
	            g.setFont(new Font("SansSerif", Font.BOLD, 15));
	        	g.drawString("Out of fuel!", barX+5, barY+17);
	        }
        }
        
        
        int powerBarValue = 0;
        if (turn == 1) powerBarValue = (int) (Tank1.getShotPower() * -111);
        else if (turn == 2) powerBarValue = (int) (Tank2.getShotPower() * -111);
        
        //if ((Mode1 == 2) || (Mode2 == 2))
		if (powerMode == true)
        {
	        g.drawImage(powerIcon, barX-28, barY-3, null); //power icon
	        g.drawImage(powerBar, barX, barY, null); // power bar
	        
	        // Draws rectangle that covers Player 1 Power Bar
	        g.setColor(Color.black);
	        g.drawRect(barX + powerBarValue, barY+1, 
	                barX - (barX-110) - powerBarValue, 20);
	        g.fillRect(barX + powerBarValue, barY+1, 
	                barX - (barX-110) - powerBarValue, 20);
        }
        
        // Player 2 Health Bar
        int healthBar2X = 682;
        int healthBar2Y = 29;
        
        int healthBar2Value = (int) (110 - (Tank2.getHealth()*1.1)); //Used to determine how much of player 2's health bar is covered
        int healthBar2Display = (int) (Tank2.getHealth());  // Used to display the number value of player 2's health
        
        g.drawImage(healthIcon, 652, 28, null); // health icon
        g.drawImage(healthBar, healthBar2X, healthBar2Y, null); // health bar
        g.setFont(new Font("SansSerif", Font.BOLD, 20)); // Sets the font and size of the displayed number
        g.setColor(Color.darkGray); // Makes the rectangle that covers health dark gray
        // Draws rectangle that covers Player 2 Health Bar when P2 loses health
        g.drawRect(healthBar.getWidth(null)+(healthBar2X-1)-healthBar2Value,
                healthBar2Y+1, healthBar2Value - 1, 20);
        g.fillRect(healthBar.getWidth(null)+(healthBar2X-1)-healthBar2Value,
                healthBar2Y+1, healthBar2Value - 1, 20);
        g.setColor(Color.white); // Makes displayed health number white
        g.drawString(Integer.toString(healthBar2Display), (healthBar2X + 5), (healthBar2Y + 20)); // Displays the number value of player 2's health
        g.setColor(Color.darkGray); // For player 2's power bar
        
        int textAlignLeft = 230;
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.setColor(Color.WHITE);
        
        //Player One Display
        if(GameSTATE==0||GameSTATE==1){
        g.drawString("Player 1", 5, 15);
         
         
         // Player Two Display
         g.drawString("Player 2", 743, 15);
         
        }
         if(Windf>0f)
            g.drawString("Wind: " + (int)(Windf*10000f) + " mph W" ,353, 15);
         else
            g.drawString("Wind: " + (int)(Windf*-10000f) + " mph E" ,353, 15);
         
        
        if (GameSTATE==1&&turn==1) // If Player 1's Turn
         {
             g.setColor(Color.BLACK);
             //g.drawString("<= PLAYER1", 350, 75);
                     }
         else if (GameSTATE==1&&turn==2) // If Player 2's Turn
         {
             g.setColor(Color.BLACK);
             //g.drawString("PLAYER2 =>", 350, 75);
            
         }
         
         if (PauseMenuOpen == true) {
            Color c = new Color(1.0f, 1.0f, 1.0f, 0.6f);
            g.setColor(c);
            g.fillRoundRect(200, 100, 400, 360, 15, 17);
            g.setColor(Color.darkGray);
            Font f = g.getFont();
            g.setFont(new Font("SansSerif", Font.BOLD, 17));
            g.drawString("OPTIONS", textAlignLeft, 120);
            g.setFont(f);
            g.setColor(Color.darkGray);
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            g.drawString("Controls:", textAlignLeft, 140);
            g.setFont(f);
            g.setColor(Color.black);
            g.drawString("A: moves the tank left D: Moves the tank right", textAlignLeft, 165);
            g.drawString("Left & Right Arrows to aim.", textAlignLeft, 190);
            g.drawString("Q: decrease shot power E: increase shot power", textAlignLeft, 215);
            g.drawString("Weapons: 1- Default Bomb 2-Homing Bomb",textAlignLeft, 240);
            g.drawString("3- Hersco Bomb 4- Joker Bomb", textAlignLeft, 265);
            g.drawString("Spacebar to fire & end turn.", textAlignLeft, 290);
            g.setColor(Color.darkGray);
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            g.setFont(f);
            g.setColor(Color.darkGray);
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            g.drawString("Press 'W' to cycle wind options:", textAlignLeft, 335);
            g.setFont(f);
            g.setColor(Color.black);

            if (WindVar == 2){
                g.drawString("Wind is on and constant.", textAlignLeft, 350);
            }else if (WindVar == 1){
                g.drawString("Wind is randomized after every shot.", textAlignLeft, 350);
            }else if (WindVar == 3){
                g.drawString("Wind is completely randomized.", textAlignLeft, 350);
            }else{
                g.drawString("Wind is Off.", textAlignLeft, 350);
            }
            g.setColor(Color.darkGray);
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            g.drawString("Press 'M' to Change Music:", textAlignLeft, 367);
            g.setFont(f);
            g.setColor(Color.black);
            g.drawString(playlistnames[HelpScreen.tracknumber], textAlignLeft, 382);

            // for testing because no values changed
            check = true;
            
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            g.drawString("Press Z to change terrain.", textAlignLeft, 410);
            g.drawString("Press R to restart the game.", textAlignLeft, 425);
            g.drawString("Press Esc to end game.", textAlignLeft, 440);
            g.drawString("Press O to close option screen.", textAlignLeft, 455);
         }
         if (ControlsOverlayOpen == true) {
         
         }
    }
    
    // Update/Draw the Selected Weapon Indicator
    public void updateWeaponandModeIndicator(Graphics g) {
        int currentWeapon = 0;
        int currentAmmo = 0;
        int tankX;
        int tankY;
        Tank currentTank;
        String strAmmo;
        int mi = 0;
        int mi2 = 0;
        String md = "Mode";
        Image[] modeindicator = {modemove,modeangle,modepower,modeweapon};
        
        g.drawImage(weaponIndicatorBG, -4, 60, null); // P1 Background
        g.drawImage(weaponIndicatorBG, 743, 60, null); // P2 Background
        //g.drawImage(modeBG, 638, 53, null); // P1 ModeBackground
        //g.drawImage(modeBG, 66, 53, null); // P2 ModeBackground
        
        for(int i=0; i<2; i++) { // for each of the two tanks
            
            if(i==0) {
                currentTank = Tank1;
                // Position Weapon Images for Player 1
                tankX = 5;
                tankY = 65;
            } else {
                //Position Weapon Images for Player 2
                currentTank = Tank2;
                tankX = 751;
                tankY = 65;
            }
            
            currentWeapon = currentTank.getWeapon2();
            
            if(currentWeapon == 0) { // Normal
                g.drawImage(weaponNormal, tankX, tankY, null);
                currentAmmo = 1;
            } else if (currentWeapon == 1) { // Nuclear
                g.drawImage(weaponNuclear, tankX, tankY, null);
                currentAmmo = currentTank.getSecondWeaponAmmo();
            } else if(currentWeapon == 2) { // Hersco
                g.drawImage(weaponHersco, tankX, tankY, null);
                currentAmmo = currentTank.getThirdWeaponAmmo();
            } else { // Joker
                g.drawImage(weaponJoker, tankX, tankY, null);
                currentAmmo = currentTank.getFourthWeaponAmmo();
            }
            
            if(currentWeapon==0) {
                strAmmo = "x";
            } else {
                strAmmo = Integer.toString(currentAmmo); // Convert int to String
            }
            
            if(currentTank==Tank1) { // For P1 side indicator
                g.drawString(strAmmo, 40, 100);
            } else { // it's P2's Indicator
                g.drawString(strAmmo, 790, 100);
            }
            
            if(Mode1==0){
            	mi = 0;
            }
            else if(Mode1==1){
            	mi = 1;
            }
            else if(Mode1==2){
            	mi = 2;
            }
            else if(Mode1==3){
            	mi = 3;
            }
            
            if(Mode2==0){
            	mi2 = 0;
            }
            else if(Mode2==1){
            	mi2 = 1;
            }
            else if(Mode2==2){
            	mi2 = 2;
            }
            else if(Mode2==3){
            	mi2 = 3;
            }
            
            if(currentTank==Tank1) { // For P1 side indicator
                //g.drawImage(modeindicator[mi], 77, 83, null);
            } 
            else{ // it's P2's Indicator
                    //g.drawImage(modeindicator[mi2], 650, 83, null);
                }
           
        }
    }
    
    
    
    
    
    // Update/Draw the Selected Weapon Indicator

 
//Creates and sets the Shot that is fired
    public void TankFire(Graphics g, int player)
        {
        float p1xveloc;
        float p1yveloc;
        float p2xveloc;
        float p2yveloc;
        if (player==1)
            {
            //TEST
            //g.drawImage(TankShot.getImage(), xcoord, ycoord, null);
            
            //changing Player1AngleX and Player1AngleY does nothing
            
            g.drawImage(TankShot.getImage(), Tank1.getTurretAngleX(), Tank1.getTurretAngleY(), null);
            TankShot.setState(1);
            TankShot.setX(Tank1.getTurretAngleX());
            TankShot.setY(Tank1.getTurretAngleY());
            p1xveloc=(float)((-1)*Tank1.getShotPower()*Math.cos(Tank1.getAngle()/57.3));
            p1yveloc=(float)(Tank1.getShotPower()*Math.sin(Tank1.getAngle()/57.3));

            TankShot.setVelocityX(p1xveloc); 
            TankShot.setVelocityY(p1yveloc); 
            
            //soundShot.load("gunshot.mid");
            soundShot.play();
            
            }
            else
                {
                //g.drawImage(TankShot.getImage(), 400, 400, null);
                g.drawImage(TankShot2.getImage(), Tank2.getTurretAngleX(), Tank2.getTurretAngleY(), null);
                TankShot2.setState(1);
                TankShot2.setX(Tank2.getTurretAngleX());
                TankShot2.setY(Tank2.getTurretAngleY());
                p2xveloc=(float)((-1)*Tank2.getShotPower()*Math.cos(Tank2.getAngle()/57.3));
                p2yveloc=(float)(Tank2.getShotPower()*Math.sin(Tank2.getAngle()/57.3));

                TankShot2.setVelocityX(p2xveloc); //.7 //ANGLE Adjustment
                TankShot2.setVelocityY(p2yveloc); //.3 //POWER
                
                //soundShot.load("gunshot.mid");
                soundShot.play();
                
                }
        }
    
    
    
  //Creates and sets the Shot that is fired
    public void TankFire2(Graphics g,int player)
        {
        float p1xveloc;
        float p1yveloc;
        float p2xveloc;
        float p2yveloc;
        
        //soundShot.load("gunshot.mid");
        soundShot.play();
        
        
        if (player==1)
                {
                g.drawImage(SecondaryTankShot.getImage(), Tank1.getTurretAngleX(), Tank1.getTurretAngleY(), null);
                SecondaryTankShot.setState(1);
                SecondaryTankShot.setX(Tank1.getTurretAngleX());
                SecondaryTankShot.setY(Tank1.getTurretAngleY());
                p1xveloc=(float)((-1)*Tank1.getShotPower()*Math.cos(Tank1.getAngle()/57.3));
                p1yveloc=(float)(Tank1.getShotPower()*Math.sin(Tank1.getAngle()/57.3));
                
                
                SecondaryTankShot.setVelocityX(p1xveloc); //.7 //Player1AngleX Adjustment
                SecondaryTankShot.setVelocityY(p1yveloc); //.3 //POWER
                }
        else
                {
                g.drawImage(SecondaryTankShot2.getImage(), Tank2.getTurretAngleX(), Tank2.getTurretAngleY(), null);
                SecondaryTankShot2.setState(1);
                SecondaryTankShot2.setX(Tank2.getTurretAngleX());
                SecondaryTankShot2.setY(Tank2.getTurretAngleY());
                p2xveloc=(float)((-1)*Tank2.getShotPower()*Math.cos(Tank2.getAngle()/57.3));
                p2yveloc=(float)(Tank2.getShotPower()*Math.sin(Tank2.getAngle()/57.3));
                
                SecondaryTankShot2.setVelocityX(p2xveloc); //.7 //ANGLE Adjustment
                SecondaryTankShot2.setVelocityY(p2yveloc); //.3 //POWER
                }
        }


    public void TankFire3(Graphics g,int player)
    {
		
    if (player==1)
            {
            //g.drawImage(ThirdTankShot.getImage(), (int)(Tank2.getTankSprite().getX()), -200, null);
            g.drawImage(herscoJetRightSprite.getImage(), (int)(Tank1.getTankSprite().getX()), -110, null);
            herscoJetRightSprite.setState(1);
            herscoJetRightSprite.setX(-100);
            herscoJetRightSprite.setY(180);
            herscoJetRightSprite.setVelocityX(0.3f);
            System.out.println("Player 1 called in the Hersco Bomb!!!");
            }
    else if (player==2)
            {
            //g.drawImage(ThirdTankShot2.getImage(),(int)(Tank1.getTankSprite().getX()), -200, null);
            g.drawImage(herscoJetLeftSprite.getImage(), ((int)Tank2.getTankSprite().getX()), -110, null);
            herscoJetLeftSprite.setState(1);
            herscoJetLeftSprite.setX(900);
            herscoJetLeftSprite.setY(180);
            herscoJetLeftSprite.setVelocityX(-0.3f);
            System.out.println("Player 2 called in the Hersco Bomb!!!");
            }
    }
    
    public void JetDrop(Graphics g,int player)
    {
		
    if (player==1)
            {
            g.drawImage(ThirdTankShot.getImage(), (int)(herscoJetRightSprite.getX()), (int)(herscoJetRightSprite.getY()), null);
            ThirdTankShot.setState(1);
            ThirdTankShot.setX(herscoJetRightSprite.getX());
            ThirdTankShot.setY(herscoJetRightSprite.getY());
            ThirdTankShot.setVelocityY(0.2f);
            System.out.println("Dropping hersco bomb");
            }
    else if (player==2)
            {
            g.drawImage(ThirdTankShot2.getImage(), (int)(herscoJetLeftSprite.getX()), (int)(herscoJetLeftSprite.getY()), null);
            ThirdTankShot2.setState(1);
            ThirdTankShot2.setX(herscoJetLeftSprite.getX());
            ThirdTankShot2.setY(herscoJetLeftSprite.getY());
            ThirdTankShot2.setVelocityY(0.2f);
            System.out.println("Dropping hersco bomb");
            }
    }
    
    public void TankFire4(Graphics g,int player)
    {

    //Input music    
	try
        {
        	InputStream iStream = new FileInputStream("a.wav");//for sound
        	AudioStream aStream = new AudioStream(iStream );
        	AudioPlayer.player.start(aStream );
        }
        catch(Exception e)
        {
        	System.out.println(e);
        }

        //Sets variables for the velocity of the Joker Shotgun
        float p1xveloc;
        float p1yveloc;
        float p2xveloc;
        float p2yveloc;
        
	if (player==1)
    {
            
            //TEST
            //g.drawImage(FourthTankShot.getImage(), xcoord, ycoord, null);
            
            //changing Player1AngleX and Player1AngleY does nothing
            

            g.drawImage(FourthTankShot.getImage(), Tank1.getTurretAngleX(), Tank1.getTurretAngleY(), null);
            FourthTankShot.setState(1);      
            FourthTankShot.setX(Tank1.getTurretAngleX());
            FourthTankShot.setY(Tank1.getTurretAngleY());
            p1xveloc=(float)((-1)*Tank1.getShotPower()*Math.cos(Tank1.getAngle()/57.3));
            p1yveloc=(float)(Tank1.getShotPower()*Math.sin(Tank1.getAngle()/57.3));

            FourthTankShot.setVelocityX(p1xveloc); 
            FourthTankShot.setVelocityY(p1yveloc); 
            
            //soundShot.load("gunshot.mid");
            soundShot.play();
            
            }
    else
    {
            //g.drawImage(TankShot.getImage(), 400, 400, null);
            g.drawImage(FourthTankShot2.getImage(), Tank2.getTurretAngleX(), Tank2.getTurretAngleY(), null);
            FourthTankShot2.setState(1);
            FourthTankShot2.setX(Tank2.getTurretAngleX());
            FourthTankShot2.setY(Tank2.getTurretAngleY());
            p2xveloc=(float)((-1)*Tank2.getShotPower()*Math.cos(Tank2.getAngle()/57.3));
            p2yveloc=(float)(Tank2.getShotPower()*Math.sin(Tank2.getAngle()/57.3));
                
            FourthTankShot2.setVelocityX(p2xveloc); //.7 //ANGLE Adjustment
            FourthTankShot2.setVelocityY(p2yveloc); //.3 //POWER
                
                //soundShot.load("gunshot.mid");
                soundShot.play();
                
        }

    	/***************************
		try
		{
				InputStream iStream = new FileInputStream("a.wav");//for sound
				AudioStream aStream = new AudioStream(iStream );
				AudioPlayer.player.start(aStream );
        }
        
		catch(Exception e)
        {
        	System.out.println(e);
        }
	******************************/

        
		//Sets variables for the velocity of the Joker Shotgun
        float p1xveloc;
        float p1yveloc;
        float p2xveloc;
        float p2yveloc;
        
		if (player==1)
		{
            
			//TEST
			//g.drawImage(FourthTankShot.getImage(), xcoord, ycoord, null);
          
			//changing Player1AngleX and Player1AngleY does nothing
            
			g.drawImage(FourthTankShot.getImage(), Tank1.getTurretAngleX(), Tank1.getTurretAngleY(), null);
            FourthTankShot.setState(1);      
            FourthTankShot.setX(Tank1.getTurretAngleX());
            FourthTankShot.setY(Tank1.getTurretAngleY());
            p1xveloc=(float)((-1)*Tank1.getShotPower()*Math.cos(Tank1.getAngle()/57.3));
            p1yveloc=(float)(Tank1.getShotPower()*Math.sin(Tank1.getAngle()/57.3));
            FourthTankShot.setVelocityX(p1xveloc); 
            FourthTankShot.setVelocityY(p1yveloc); 
            
            //soundShot.load("gunshot.mid");
            soundShot.play();
            
        }
		else
		{
            //g.drawImage(TankShot.getImage(), 400, 400, null);
            g.drawImage(FourthTankShot2.getImage(), Tank2.getTurretAngleX(), Tank2.getTurretAngleY(), null);
            FourthTankShot2.setState(1);
            FourthTankShot2.setX(Tank2.getTurretAngleX());
            FourthTankShot2.setY(Tank2.getTurretAngleY());
            p2xveloc=(float)((-1)*Tank2.getShotPower()*Math.cos(Tank2.getAngle()/57.3));
            p2yveloc=(float)(Tank2.getShotPower()*Math.sin(Tank2.getAngle()/57.3));
                
            FourthTankShot2.setVelocityX(p2xveloc); //.7 //ANGLE Adjustment
            FourthTankShot2.setVelocityY(p2yveloc); //.3 //POWER
                
                //soundShot.load("gunshot.mid");
                soundShot.play();
                
		}
		
    }
     
      // new DrawTerrain() is below
      // I've added the testing lines to it, but as it generates a different terrain I think it should be turned into a new 
      // function.  
      
        /*
        public void DrawTerrain2(Graphics g)
        {   
        String enter = "DrawTerrain has been called!!";
        double terrainHeight = Math.random()+3.42;
        double terrainHeight2 = Math.random()+5;
        double abc = Math.random()*100;

        
        int freq = Math.round((float)Math.random()*100);
        int q = 200;
        int t=15;
        int v=0;
        int tester=342;
        boolean up = true;
        double ang;
        while (basex < 900)
            {
                if (t > 15)
                {
                    if(t < 90 )
                    {
                
                ang = (t/57.3);//abc
                tester = Math.abs((int)(Math.floor((Math.sin(ang))*freq)));
                //tester = Math.abs((int)(Math.floor((Math.sin(ang)*200))));
                //changing 50 to higher # makes higher terrain and lower makes flatter
                caseChecks[0] = true;
                }
            }
            if (t > 90)
                {
                up=false;
                caseChecks[1] = true;
                }
            if(t<15)
            {up=true;}
            if (topy[basex] == null)
                {
                    //topy[basex]= finalTerrain-tester;//normal random terrain
                    topy[basex]= q++/2;//decline
                    //topy[basex]= r--/2;//incline
                    //topy[basex]= 400;//constant
                    caseChecks[5] = true;
                } //342 - create a random terrain function from here.
            g.setColor(Color.red);
            g.drawLine(basex,basey,topx,topy[basex]);
            basex++;
            topx++;
   
            v++;
            if (up==true)
                {
                    caseChecks[2] = true;
                    t++;
                    }
            if(up==false)
                {   
                    caseChecks[3] = true;
                    t--;
                }           
            }
        while (basex < 2000)
            {
            topy[basex]=342;//342
            basex++;
            caseChecks[4] = true;
            }
        basex=0;
        basey=900;
        topx=0;
        g.setColor(Color.green);
        
        caseChecks[6] = true;
        return; 
      }
      * */

   //*******************************************************
    
    /*****************************************************
        *The following blocks of code correspond to the different levels
        *Ryan K
        *****************************************************
        */
    public void level() {
        if(levelNumber == "1")//level 1
            {
                topy[basex]= terrainNum-freqValue;//normal random terrain
            }
        if(levelNumber == "2")//level 3
            {
            int oo = 1080;
            for(int ko = 000; ko <= 400; ko++)
                {
                topy[ko] = oo--/2;
                }
                    
            for(int ko = 401; ko <= 801; ko++)
                {
                topy[ko] = oo++/2;
                }
            topy[basex]= terrainNum-freqValue;
            }                    
        if(levelNumber == "3")//level 5
            {
            topy[basex]= 400;
            }
        
            caseChecks[5] = true;
    }
   
    
    public void CreateTank(Graphics g)
        {
                
        String call = "The Program Create Tank has been called!";
        float rand = 0;
        float posY = 0;
        float p1rand = 0;
        float p2rand = 0;
        float finalrand = 0;
        float p1y = 0;
        float p2y = 0;
        
        rand = Math.round((float)(Math.random()*200));
        p1rand = rand;
        Tank1.getTankSprite().setX(rand);
        
        posY=(float)(topy[(int)rand]-13);
        Tank1.getTankSprite().setY(550);
        p1y = posY;
        
        rand = Math.round((float)(Math.random()*300));
        p2rand = rand;
        rand = 700-rand;
        
        finalrand = p2rand-p1rand;
 
            Tank2.getTankSprite().setX(rand);
        posY=(float)(topy[(int)rand]-13);
        Tank2.getTankSprite().setY(550);
        p2y = posY;
        TankCreated = true;
        
        try {
            if (testing) {
                FileWriter file = new FileWriter("output.doc", true);
                BufferedWriter out = new BufferedWriter(file);
                out.write("\n" + call + "\n");
                out.write("Player 1 Tank X has been set to " + p1rand + "\n");
                out.write("Player 1 Tank Y is set to " + p1y + "\n");
                out.write("Player 2 Tank X has been set to " + p2rand + "\n");
                out.write("Player 2 Tank Y is set to " + p2y + "\n");
                out.write("Distance between tanks is: " + finalrand + "\n");
                out.close();
            }
        }
        catch(Exception e){
             System.err.println("Error: " + e.getMessage());
        }
        return;
        }
    
    /*
     * GroundCollision()
     * By Eric Marcarelli, Alex Geden, Ryan Kleckner
     * Starts and stops the tank from falling, adjusts health, sets rotation 
     * angle for more realistic landings.
     */
     
     public void DrawTerrain(Graphics g) {
        
        double terrainHeight = Math.random()+3.42;
        double terrainHeight2 = Math.random()+5;
        int finalTerrain = Math.round((float)((terrainHeight2+terrainHeight)/2)*100);
        terrainNum = finalTerrain;
        int freq = Math.round((float)Math.random()*100);
        int t = 15;
        int tester = 342;
        boolean up = true;
        double ang;
        while (basex < 900) {
                if (t > 15) {
                    if(t < 90) {
                ang = (t/57.3);
                tester = Math.abs((int)(Math.floor((Math.sin(ang))*freq)));
                
                //***********************************************************
                //Added by Ryan K
                freqValue = tester;
                //***********************************************************
                
                caseChecks[0] = true;
                //tester = Math.abs((int)(Math.floor((Math.sin(ang)*200))));
                //changing 50 to higher # makes higher terrain and lower makes flatter
                }
            }
            if (t > 90)
                {
                up = false;
                caseChecks[1] = true;
                }
            if(t < 15) { up = true; }
            
            if (topy[basex] == null) {
                    //***********************************************************
                    //Added by Ryan K to call level function
                    //***********************************************************
                    level();
                    //***********************************************************   
            } 
             if(levelNumber == "1" || levelNumber == "2" || levelNumber == "5") {
                Color terrainColor = new Color(0x5e3b1f);//Terrain Color Adjustment
                g.setColor(terrainColor);
                g.drawImage(terrainTexture, 0, 400, null);

            }
            
            //sunny
            
            if(levelNumber == "3" || levelNumber == "4") {
                Color terrainColor = new Color(6175519); //Terrain Color Adjustment
                //Image terrainTexture = loadImage("images/terrainGreen.jpg");
                //g.drawImage(terrainTexture, 0, 400, null);
                g.setColor(terrainColor);
            }
            
            g.drawLine(basex,basey,topx,topy[basex]);
            basex++;
            topx++;
            if (up==true)
                {
                t++;
                caseChecks[2] = true;
                }
            if(up==false)
                {
                t--;
                caseChecks[3] = true;
                }           
            }
        while (basex < 2000)
            {
            topy[basex]=342;//342
            basex++;
            caseChecks[4] = true;
            }
        basex=0;
        basey=900;
        topx=0;
        // sets turret color
        g.setColor(Color.gray);
        caseChecks[6] = true;   
        return;
      }
  
    public void GroundCollision(Sprite tank, int player)
    {           
        double angle = 1;  
        boolean falling = false; 
        int ptx = 0, opp = 0, adj = 0, highest = 1000; // 1000 is an arbitrarly high value that will always be > tank.getY()
    
        // If the tank has fallen below ground (it moves multiple pixels between 
        // GroundCollision() calls), realign it with the center pixel. This is 
        // important for angle calculation below. 

            if (topy[(int)tank.getX()+13]-13 < (int)tank.getY()) //Allows tank to go up inclines
                tank.setY(topy[(int)tank.getX()+13]-13);
            if(tank.getY()<585) //makes the tank so the tank can't go below the bottom.
            {
            // If the tank's center point is above the ground make the tank fall. Checks to see if it should still be falling
            if (topy[(int)tank.getX()+13]-13 > (int)tank.getY())
                falling = true;
            }
        // Possibly add addional cases here that will stop it from falling for realism. 
        
        if (falling && !jumpUp) { 
            if (player == 1) {
                Tank1.fixTurret();  
            }
            else {
                Tank2.fixTurret();
            }
                
            tank.setVelocityY(.1f);
            
            /*if (player == 1 && !firstfall ) {
                if (!moving && !jumpFall)   
                    Tank1.setHealth(Tank1.getHealth()-1);//originally 5 changed to make for a more balanced game
                }   
            if (player == 2&& !firstfall2) {                    
                if (!moving && !jumpFall)
                    Tank2.setHealth(Tank2.getHealth()-1);//originally 5 changed to make for a more balanced game
            
            }*/
        
        } 
        else if (!falling && !jumpUp) {
            if(player ==1) firstfall=false;
            if(player==2)firstfall2=false;
            
            tank.setVelocityY(0f);
                   
            // Now that it stopped, calculate the rotation angle. This is done by 
            // forming a right triangle and calculating the angle using arctan(opposite/adjacent).                      
                        
            // We first determine if it will rotate to the left or right side. 
            // It makes the decision based on the ground at the x location of each side 
            // and the mid pt between middle and side. More cases may be necessary for 
            // additional realism. 
            
            // These loops find the "highest" (actually lowest on the game's y axis) ground
            // point within the range. It will use this to build the opposide side in the
            // triangle. The adjacent side is formed from that pt's x to the center point.
            
            if ((topy[(int)tank.getX()]-13 < (int)tank.getY()) && (topy[(int)tank.getX()+9]-13 < (int)tank.getY())) {
                // Rotating right
                for (int i = (int)(tank.getX()+13); i > tank.getX(); i--) {
                    if (topy[i] < (tank.getY()+13))  {
                        if (topy[i] < highest) {
                            highest = topy[i];
                            ptx = i;
                        }
                    }
                }
                
                opp = 13 - (topy[ptx] - (int)tank.getY());
                adj = 13 - (ptx - (int)tank.getX());                                
            }
            
            if ((topy[(int)tank.getX()+38]-13 < (int)tank.getY()) && (topy[(int)tank.getX()+29]-14 < (int)tank.getY())) {
                // Rotating left
                
                for (int i = (int)(tank.getX()+13); i < (tank.getX()+38); i++) {
                    if (topy[i] < (tank.getY()+13))  {
                        if (topy[i] < highest) {
                            highest = topy[i];
                            ptx = i;
                        }
                    }
                }
            
                opp = 13 - (topy[ptx] - (int)tank.getY());
                adj = ptx - (int)tank.getX() - 13;  
                angle *= -1;        
            }
            
            // Calculate the angle. Multiplying by the number will let us preserve the angle's sign. 
            if (adj != 0) 
                angle *= Math.atan((double)opp/adj); 
            else 
                angle = 0;
            //commented out to try no jumping game play
            // If the tank lands at the bottom of a cliff and there is room, straighten it out 
            if (((topy[(int)tank.getX()+57]) > (int)tank.getY()) && (angle > 1.5)) 
                tank.setX(tank.getX()+14);
            
            if (tank.getX() > 57) { // fix 56px bug
                if (((topy[(int)tank.getX()-57]) > (int)tank.getY()) && (angle < -1.5)) 
                    tank.setX(tank.getX()-13);
            }
            
            if (player == 1){
                Tank1.setTankSlant(angle);
                Tank1.fixTurret();
            }else{
                Tank2.setTankSlant(angle);
                Tank2.fixTurret();
            }
                
        }
        else if (jumpUp) {
            if (player == 1) {
                Tank1.fixTurret();
                }
            else {
                Tank2.fixTurret();

            }           
        }
                
        return;
    }


    
    public void setTesting(boolean t) { testing = t; }
    
    public void Restart()
    {
        restartgame=1;
    }   
    
       public static class FloatHealth {
        static int health1counter = (int)Tank1.getTankSprite().getY()-50;  
        private final static int HCOUNTER_DELAY=10000; 
        static Timer health1Timer;
        
        public static void paintComponent(Graphics g)
        {
            if (health1Timer.isRunning())
            {
                health1counter--;  
                if (health1counter<=40)
                    health1Timer.stop();          
                
            }
            
        }
        public static void startCounter() {
            if (health1Timer==null){
                health1counter = (int) Tank1.getTankSprite().getY()-50;
                health1Timer=new Timer(HCOUNTER_DELAY, new TimeHandler()); 
                health1Timer.start();    
            }
            else {
               if (!health1Timer.isRunning())
               {
                   health1Timer.restart();
                   
               }
            } 
           
        }
        
        public static void stopCounter(){
            health1Timer.stop();
        }
        private static class TimeHandler implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                paintComponent(null);   
            }
        }
        
    }
    
    public static class FloatHealth2 {
    
        static int health2counter=(int)Tank2.getTankSprite().getY()-50;
        private final static int H2COUNTER_DELAY=10000; 
        static Timer health2Timer;
        
        public static void paintComponent(Graphics g)
        {
            
            if (health2Timer.isRunning())
            {
                health2counter--;
                

                if (health2counter<=40)
                    health2Timer.stop();
            }
        }
        
        public static void start2Counter(){
            if (health2Timer==null)
               {
                  health2counter=(int)Tank2.getTankSprite().getY()-50;
                  health2Timer=new Timer(H2COUNTER_DELAY, new TimeHandler());
                  health2Timer.start();
               }
               else
               {
                   if (!health2Timer.isRunning())
                       health2Timer.restart();
               }
        }
        public static void stopCounter(){
            health2Timer.stop();
        }
        private static class TimeHandler implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                paintComponent(null);   
            }
        }
        
    


    }
}
