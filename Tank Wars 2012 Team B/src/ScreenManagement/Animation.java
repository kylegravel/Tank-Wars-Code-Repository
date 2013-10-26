package ScreenManagement;

import java.awt.Image;
import java.util.ArrayList;

/*This program allows objects to have mutliple images
*/
public class Animation {

    private ArrayList frames;
    private int currFrameIndex;
    private long animTime;
    private long totalDuration;


    public Animation() { //creates a new animation object
        frames = new ArrayList();
        totalDuration = 0;
        start();
    }

    public synchronized void addFrame(Image image,
        long duration)
    	{
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration)); //adds image with duration shown
        System.out.println("Frame Added");
        System.out.println(image);
        System.out.println(duration);
        	
    	}

    public synchronized void start() 
    	{ //resets animation
        animTime = 0;
        currFrameIndex = 0;
        }

    public synchronized void update(long elapsedTime) 
    	{
        if (frames.size() > 1) {
            animTime += elapsedTime;

            if (animTime >= totalDuration) 
            	{
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
            }

            while (animTime > getFrame(currFrameIndex).endTime) 
            	{
                currFrameIndex++;
            	}
        }
    }

    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        }
        else {
            return getFrame(currFrameIndex).image; //grabs next frame in animation
        }
    }


    private AnimFrame getFrame(int i) {
        return (AnimFrame)frames.get(i);
    }


    private class AnimFrame {
		//animation class borrowed from game dev.
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
