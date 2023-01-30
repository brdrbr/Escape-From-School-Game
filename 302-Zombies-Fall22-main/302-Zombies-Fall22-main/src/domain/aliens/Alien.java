package domain.aliens;

import domain.aliens.move.*;
import domain.handler.RunningModeHandler;
import domain.aliens.attack.*;
import ui.RunningModeFrame;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

public abstract class Alien implements Serializable{

    String name;
    IMoveBehavior moveBehavior;
    IAttackBehavior attackBehavior;
    ImageIcon icon;
    ImageIcon normalImage;
    Point image_corner;
    int height;
    int width;
    Point destination_corner; //for blind alien only
    Random random;
    Boolean hasAttacked;
    boolean isVisible;
    int typeForTimeAttack; //???????? (open for discussion - BARTU) ???????

    public abstract void performAttack();
    public abstract void performMove();
    public abstract void performSetDestination();

    public Alien(String name, ImageIcon icon){
        this.name = name;
        this.icon = icon;
        this.normalImage = icon;
        this.height = icon.getIconHeight();
        this.width = icon.getIconWidth();
        this.hasAttacked = false;
        this.isVisible = true;
        this.typeForTimeAttack = 0; // TimeWastingAlien did not set any attack behavior
        random = new Random();
        this.image_corner = new Point(-1, -1);
    }

    //getters
    public String getName(){
        return name;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public Point getImageCorner(){
        return image_corner;
    }
    public int getXCoordinate(){
        return (int) image_corner.getX();
    }
    public int getYCoordinate(){
        return (int) image_corner.getY();
    }
    public ImageIcon getIcon(){
        return icon;
    }
    public ImageIcon getNormalImage(){
        return normalImage;
    }
    public IMoveBehavior getMoveBehavior(){
        return moveBehavior;
    }
    public IAttackBehavior getAttackBehavior(){
        return attackBehavior;
    }
    public Point getDestinationCorner(){
        return destination_corner;
    }
    public boolean getHasAttacked(){
        return hasAttacked;
    }
    public int getTypeForTimeAttack() {
        return typeForTimeAttack; 
    }

    public Boolean getIsVisible() {
        return this.isVisible; 
    }



    //setters
    public void setDestinationCorner(Point dest){
        destination_corner = dest;
    }
    public void setImageCorner(Point image_corner){
        this.image_corner = image_corner;
    }
    public void setXCoordinate(int xCoordinate){
        this.image_corner.setLocation(xCoordinate, this.image_corner.getY());
    }
    public void setYCoordinate(int yCoordinate){
        this.image_corner.setLocation(this.image_corner.getX(), yCoordinate);
    }
    public void setHasAttacked(boolean hasAttacked){
        this.hasAttacked = hasAttacked;
    }
    public void setMoveBehavior(IMoveBehavior moveBehavior){
        this.moveBehavior = moveBehavior;
    }
    public void setAttackBehavior(IAttackBehavior attackBehavior){
        this.attackBehavior = attackBehavior;
    }

    public void setIsVisible(boolean isVisible) { this.isVisible = isVisible; }
    public void setTypeForTimeAttack(int typeForTimeAttack) { this.typeForTimeAttack = typeForTimeAttack; }

    public void setLocation(){
        int xCoordination;
        int yCoordinate;
        do {
            xCoordination = random.nextInt(RunningModeFrame.getInstance().getGameAreaWidth() - this.width);
            yCoordinate = random.nextInt(RunningModeFrame.getInstance().getGameAreaHeight() - this.height);
        } while (RunningModeHandler.getInstance().checkSpawnCollision(xCoordination, yCoordinate, this.width, this.height));

        

        image_corner = new Point(xCoordination, yCoordinate);
    }
    
    
}