//This is a simple program which creates
// which gives sprites or animated objects
// properties to look and behave differently.
//Simple behavior like speed is applied
// though it could go into much more detail.

package ScreenManagement;

import java.awt.Image;

public class Sprite {

    private Animation anim;
    // x and y poos
    private float x;
    private float y;
    // velocity 
    private float vX;
    private float vY;
    private int state;

//Creates a new object with animation.
    public Sprite(Animation anim) {
        this.anim = anim;
    }

    public void update(long elapsedTime) {
        x += vX * elapsedTime; //updates pos by taking timepassed*speed = new X
        y += vY * elapsedTime;
        anim.update(elapsedTime);
    }

 
    public float getX() { //gets x pos
        return x;
    }

    public float getY() { //^
        return y;
    }

    public void setX(float x) { //sets x pos
        this.x = x;
    }

    public void setY(float y) { //^
        this.y = y;
    }
    
    public int getWidth() { //sizes of object
        return anim.getImage().getWidth(null);
    }

  
    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    public float getVelocityX() { //gets V
        return vX;
    }

    public float getVelocityY() {
        return vY;
    }
    
    public void setState(int state) 
    {
    this.state = state;	
    }
    
    public int getState()
    {
    return state;
    	
    }

 
    public void setVelocityX(float vX) { //Sets V
        this.vX = vX;
    }

    public void setVelocityY(float vY) {
        this.vY = vY;
    }

    public Image getImage() {
        return anim.getImage();
    }
}
