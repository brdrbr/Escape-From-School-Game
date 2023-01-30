package domain.aliens;

import domain.aliens.move.*;
import domain.aliens.attack.*;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.Serializable;

public class ShooterAlien extends Alien implements Serializable{

    int bulletDirection;
    int bulletDx;
    int bulletDy;
    Point bulletOrigin;
    
    public ShooterAlien(String name, ImageIcon icon){
        super(name, icon);
        moveBehavior = new NoMove();
        attackBehavior = new RangedAttack();
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
        
    }

    @Override
    public void setHasAttacked(boolean hasAttacked){

    }

}

