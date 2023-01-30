package domain.power_ups;

import domain.power_ups.job.*;
import ui.RunningModeFrame;
import domain.handler.RunningModeHandler;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

public abstract class PowerUp implements Serializable {

    String name;
    IJobBehavior jobBehavior;
    ImageIcon icon;
    Point image_corner;
    int height;
    int width;
    Random random;

    //corresponds to the keyboard key that will cast the powerup
    //e.g. key = KeyEvent.VK_H if powerup will be cast when player presses H
    //if key equals -1, then the powerup is autocast
    int key; 

    public abstract void performJob();
    //public abstract void performCollect();

    public PowerUp() {};

    public PowerUp(String name, ImageIcon icon){
        this.name = name;
        this.icon = icon;
        this.height = icon.getIconHeight();
        this.width = icon.getIconWidth();
        random = new Random();
    }

    //getters
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public int getPositionX(){
        return (int) image_corner.getX();
    }
    public int getPositionY(){
        return (int) image_corner.getY();
    }
    public Point getImageCorner(){
        return image_corner;
    }
    public ImageIcon getIcon(){
        return icon;
    }
    public int getKey(){
        return key;
    }
    public IJobBehavior getJobBehavior(){
        return jobBehavior;
    }
    public String getName(){
        return name;
    }

    //setters
    public void setImageCorner(Point image_corner){
        this.image_corner = image_corner;
    }
    public void setJobBehavior(IJobBehavior jobBehavior){
        this.jobBehavior = jobBehavior;
    }

    public boolean checkKey(int key){
        return this.key == key;
    }


    public void setLocation(){
        int xCoor;
        int yCoor;
        do {
            xCoor = random.nextInt(RunningModeFrame.getInstance().getGameAreaWidth() - this.width);
            yCoor = random.nextInt(RunningModeFrame.getInstance().getGameAreaHeight() - this.height);
        } while (RunningModeHandler.getInstance().checkSpawnCollision(xCoor, yCoor, this.width, this.height));

        image_corner = new Point(xCoor, yCoor);
    }
    
    
    
}
