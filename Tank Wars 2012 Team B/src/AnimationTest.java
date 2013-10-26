
 

import java.awt.*;
import javax.swing.ImageIcon;
import ScreenManagement.*;

public class AnimationTest {
   
    public AnimationTest() {
    }
      
    public void frameTest()
    	{
    	Image TestImage;
    		Image TestImage2;
       	TestImage = loadImage("images/Tank.jpg");
       	    	TestImage2 = loadImage("images/Tank.jpg");
        Animation AnimTest = new Animation();
        AnimTest.addFrame(TestImage,200);
        
        Animation AnimTest2 = new Animation();
        AnimTest2.addFrame(TestImage2,500);
        System.out.println(AnimTest.getImage());
        System.out.println(AnimTest2.getImage());
        
        
    	}
   	 
    private Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }
    
}
