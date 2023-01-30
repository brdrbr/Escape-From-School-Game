package domain.aliens;

import domain.handler.RunningModeHandler;
import domain.running_mode.Player;
import ui.RunningModeFrame;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Bullet implements Serializable {

    Point image_corner;
    ImageIcon bulletIcon;
    int height;
    int width;
    boolean playerCollision;
    int bulletDirection; // if bulletDirection = 1 then bullet move to right, if bulletDirection = 0 then bullet move to left
    boolean isVisible;


    public Bullet(int x, int y, int bulletDirection) {

        this.bulletDirection = bulletDirection;

        if (this.bulletDirection == 1) {
            bulletIcon = new ImageIcon("src/images/bullet.png");
        } else {
            bulletIcon = new ImageIcon("src/images/bullet_reversed.png");
        }

        this.height = bulletIcon.getIconHeight();
        this.width = bulletIcon.getIconWidth();

        this.image_corner = new Point(x, y);

        this.playerCollision = false;

        this.isVisible = true;

    }

    public ImageIcon getBulletIcon() {
        return bulletIcon;
    }

    public int getPositionX() {
        return (int) image_corner.getX();
    }

    public int getPositionY() {
        return (int) image_corner.getY();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean getPlayerCollision() {
        return playerCollision;
    }

    public int getBulletDirection() {
        return bulletDirection;
    }

    public boolean getIsVisible() {
        return isVisible;
    }
    public Point getImageCorner(){
        return image_corner;
    }

    public void setImageCorner(Point image_corner){
        this.image_corner = image_corner;
    }

    public void setBulletDirection(int direction) {
        this.bulletDirection = direction;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setPlayerCollision(boolean collision) {
        this.playerCollision = collision;
    }
    

    public void move(){

        Point newPoint = new Point((int) (image_corner.getX()), (int) (image_corner.getY()));

        /*if (image_corner.getX() <= RunningModeFrame.getInstance().getGameAreaWidth() / 2) {
            newPoint = new Point((int) (image_corner.getX() + 1), (int) (image_corner.getY()));

        } else {

            newPoint = new Point((int) (image_corner.getX() - 1), (int) (image_corner.getY()));

        }*/

        if (getIsVisible()) {

            if (bulletDirection == 1) {
                newPoint = new Point((int) (image_corner.getX() + 1), (int) (image_corner.getY()));
            } else {
                newPoint = new Point((int) (image_corner.getX() - 1), (int) (image_corner.getY()));
            }

            if (newPoint.getX() == 0) {
                setIsVisible(false);
            }

            if (newPoint.getX() + width == RunningModeFrame.getInstance().getGameAreaWidth()) {
                setIsVisible(false);
            }

            setImageCorner(newPoint);
        }

    }

    


}
