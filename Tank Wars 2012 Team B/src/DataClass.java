/**
Created by Alex Geden 2007
This is a simple function program which 
allows values(such as the strength of the wind)
 to be placed into a text file and allow a user
 to adjust the variables. 
 */

 import java.io.*;
import java.util.*;


public class DataClass 
{
	static String dataArray[][] = new String[9][2];
	public static String tankColors[] = {"Green", "Red", "Blue", "Yellow", "White"};

	
    public DataClass() 
    {
    	readFile();
    }
    
    public static void readFile()
    {
    	BufferedReader in = null;
  		try
			{
  				in = new BufferedReader(new FileReader("data.txt"));
			}
  			catch (FileNotFoundException fE)
			{ 
				System.out.println("<Data.txt>: File not found"); 
			}
  			try
			{	
  				String s = in.readLine();
				StringTokenizer st;
				String x;
				for (int y = 0; y < 9; y++)
				{
					if (s != null)
					{
						st = new StringTokenizer(s);
					    x = st.nextToken(); 
					    dataArray[y][0] = x;
					    dataArray[y][1] = st.nextToken();
						System.out.println(dataArray[y][0] + ", " + dataArray[y][1]);		
						s = in.readLine(); 
					}
					else
					{
						break;
					}
				}
			}
		catch (IOException iE)
		{
			System.out.println("ReadLine Error");
		}		
				
    }
 
    
	 public static String getGravity()
	 {
	 	String Gravity = "Gravity";
	 	BufferedReader in = null;
	  		try
				{
	  				in = new BufferedReader(new FileReader("data.txt"));
				}
			catch (FileNotFoundException fE)
				{ 
					System.out.println("<Data.txt>: File not found"); 
				}
			try
				{	
				String s = in.readLine();
					StringTokenizer st;
						String x;
						while(s != null) // read file while there is still a line to be read
							{
					    	st = new StringTokenizer(s);
					    	x = st.nextToken(); 
								if((Gravity.compareTo(x)==0)) // if match found, continue
								{
								x = st.nextToken();
								return x;
								}
								s = in.readLine(); 
							}
				}
			catch (IOException iE)
			{System.out.println("ReadLine Error");}
			System.out.println("");
			
			
							
	 	return "0";
	 }
	 public static int getWind()
	 {
	   int value = 0;
	   for (int x = 0; x < dataArray.length; x++)
	   {
		   //System.out.println(dataArray[x][0]);
		   if (dataArray[x][0].equals("Wind"))
		   {
			   value = Integer.parseInt(dataArray[x][1]);
			   break;
		   }
	   }
	   return value;
	 }
	 public static int getGameState()
	 {
	   int value = 0;
	   for (int x = 0; x < dataArray.length; x++)
	   {
		   //System.out.println(dataArray[x][0]);
		   if (dataArray[x][0].equals("GameState"))
		   {
			   value = Integer.parseInt(dataArray[x][1]);
			   break;
		   }
	   }
	   return value;
	 }
	 
	  public static String getStart()
	 {
	 	String Start = "Start";
	 	BufferedReader in = null;
	  		try
				{
				in = new BufferedReader(new FileReader("Data.txt"));
				
				}
			catch (FileNotFoundException fE)
				{ System.out.println("<Data.txt>: File not found"); }
			try
				{	
				String s = in.readLine();
					StringTokenizer st;
						String x;
						while(s != null) // read file while there is still a line to be read
							{
					    	st = new StringTokenizer(s);
					    	x = st.nextToken(); 
								if((Start.compareTo(x)==0)) // if match found, continue
								{
								x = st.nextToken();
								return x;
								}
								s = in.readLine(); 
							}
				}
			catch (IOException iE)
			{System.out.println("ReadLine Error");}
			System.out.println("");
			
			
							
	 	return "3";
	 }
	 	
	 public static int getp1Color()
	 {
		 return 1;
	 }
	
	 public static int getp2Color()
	 {
		 return 1;
	 }
	 
	 public static String numOfTanks()
	 {
	 	return "0";	
	 }
	 
	 public static String shotSpeed()
	 {
	 	
	 	return "0";
	 }
	  public static String getP1Health()
	 {
	 	
	 	return "0";
	 }
	   public static String getP2Health()
	 {
	 	
	 	return "0";
	 }
 
	   public static String getP1Tank()
	   {
		   int value = 0;
		   for (int x = 0; x < dataArray.length; x++)
		   {
			   if (dataArray[x][0].equals("player1Tank"))
			   {
				   value = Integer.parseInt(dataArray[x][1]);
				   break;
			   }
		   }
		   String tankString = "images/tank" + tankColors[value] + ".png";
		   System.out.println("p1 Tank = " + tankString);
		   return tankString;
	   }

	   public static String getP2Tank()
	   {
		   int value = 0;
		   for (int x = 0; x < dataArray.length; x++)
		   {
			   System.out.println(dataArray[x][0]);
			   if (dataArray[x][0].equals("player2Tank"))
			   {
				   value = Integer.parseInt(dataArray[x][1]);
				   break;
			   }
		   }
		   String tankString = "images/tank" + tankColors[value] + ".png";
		   System.out.println("p2 Tank = " + tankString);
		   return tankString;
	   }
	   
	   public static void writeFile()
	   {
		   try
		   {
			   BufferedWriter out = new BufferedWriter(new FileWriter("data.txt"));
			   for(int x = 0; x < dataArray.length; x++)
			   {
				   out.write(dataArray[x][0] + " " + dataArray[x][1]);
				   out.newLine();
			   }
			   out.close();
		   }
		   catch(IOException ex)
		   {
			   ex.printStackTrace();
		   }
		   
	   }
	   
	   public static void setP1Tank(int inputValue)
	   {
		   readFile();
		   for (int x = 0; x < dataArray.length; x++)
		   {
			   if (dataArray[x][0].equals("player1Tank"))
			   {
				   System.out.println("Setting p1 tank for value " + inputValue);
				   dataArray[x][1] = inputValue + "";
				   break;
			   }
		   }
		   writeFile();
	   }
	   
	   public static void setP2Tank(int inputValue)
	   {
		   readFile();
		   for (int x = 0; x < dataArray.length; x++)
		   {
			   if (dataArray[x][0].equals("player2Tank"))
			   {
				   System.out.println("Setting p2 tank for value " + inputValue);
				   dataArray[x][1] = inputValue + "";
				   break;
			   }
		   }
		   writeFile();
	   }
	   
	   public static void setwind(int inputValue)
	   {
		   readFile();
		   for (int x = 0; x < dataArray.length; x++)
		   {
			   if (dataArray[x][0].equals("Wind"))
			   {
				   System.out.println("Setting Wind for value " + inputValue);
				   dataArray[x][1] = inputValue + "";
				   break;
			   }
		   }
		   writeFile();
	   }
	   
	   public static void setGameState(int inputValue)
	   {
		   readFile();
		   for (int x = 0; x < dataArray.length; x++)
		   {
			   if (dataArray[x][0].equals("GameState"))
			   {
				   System.out.println("Setting Wind for value " + inputValue);
				   dataArray[x][1] = inputValue + "";
				   break;
			   }
		   }
		   writeFile();
	   }
	   
	   
	   
	   
    
    
}