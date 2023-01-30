package domain.building_mode;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.Serializable;


public class Object implements Serializable{

    String name;
    ImageIcon icon;
    Point image_corner;
    Point previousPoint;

    int IMG_HEIGHT;
    int IMG_WIDTH;

    

    //constructor
    public Object(String name, ImageIcon icon, Point image_corner){
        this.name = name;
        this.icon = icon;
        this.image_corner = image_corner;
        //this.IMG_HEIGHT = 70;
        //this.IMG_WIDTH = 60;
        this.IMG_HEIGHT = icon.getIconHeight();
        this.IMG_WIDTH = icon.getIconWidth();
    }

    //getters
    public String getName(){
        return name;
    }
    public ImageIcon getIcon(){
        return icon;
    }
    public Point getImageCorner(){
        return image_corner;
    }
    public int getPositionX(){
        return (int) image_corner.getX();
    }
    public int getPositionY(){
        return (int) image_corner.getY();
    }
    public Point getPreviousPoint(){
        return previousPoint;
    }
    public int getHeight(){
        return IMG_HEIGHT;
    }
    public int getWidth(){
        return IMG_WIDTH;
    }

    //setters
    public void setImageCorner(Point pt){
        this.image_corner = pt;
    }
    public void setPreviousPoint (Point prevPt){
        this.previousPoint = prevPt;
    }

    public void printObject(){
        //System.out.println("name: " + name + " point: " + image_corner);
        System.out.println("name: " + name + " width: " + IMG_WIDTH + " height: " + IMG_HEIGHT);
        System.out.println("------------------------------------------------");
    }




    
}
