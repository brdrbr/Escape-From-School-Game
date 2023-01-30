package domain.aliens;

import domain.aliens.move.*;
import domain.aliens.attack.*;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.Serializable;

public class BlindAlien extends Alien implements Serializable{

    ImageIcon attackImage;
    //ImageIcon normalImage;

    public BlindAlien(String name, ImageIcon icon){

        super(name, icon);
        moveBehavior = new RandomMove();
        attackBehavior = new MeleeAttack();
        //normalImage = icon;
        attackImage = new ImageIcon("src/images/blind_alien_attack.png");
    }

    public Point getDestinationCorner(){
        return destination_corner;
    }

    @Override
    public void setHasAttacked(boolean hasAttacked){
        this.hasAttacked = hasAttacked;
        if (hasAttacked){
            this.icon = attackImage;
        }
        else {
            this.icon = normalImage;
        }
    }

    public void setMoveBehavior(IMoveBehavior moveBehavior){
        this.moveBehavior = moveBehavior;
    }

    //public ImageIcon getNormalImage(){
    //    return this.normalImage;
    //}
    public ImageIcon getAttackImage(){
        return this.attackImage;
    }

    @Override
    public void performAttack() {
        // TODO Auto-generated method stub
        attackBehavior.attack(this);
        
    }

    @Override
    public void performMove() {
        // TODO Auto-generated method stub
        moveBehavior.move(this);
    }


    @Override
    public void performSetDestination() {
        // TODO Auto-generated method stub
        moveBehavior.setDestination(this);
    }

    
    
}
