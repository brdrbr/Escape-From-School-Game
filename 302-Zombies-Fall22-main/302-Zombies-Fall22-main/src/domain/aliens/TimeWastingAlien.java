package domain.aliens;

import domain.aliens.move.*;
import domain.aliens.attack.*;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.Serializable;

public class TimeWastingAlien extends Alien implements Serializable{


    ImageIcon attackImage;
    ImageIcon normalImage;

    public TimeWastingAlien(String name, ImageIcon icon){

        super(name, icon);
        moveBehavior = new NoMove();
        //attackBehavior = new MeleeAttack();
        normalImage = icon;

        attackImage = new ImageIcon("src/images/time_waster_reversed.png");
    }

    public void setAttackBehavior(IAttackBehavior attackBehavior){
        this.attackBehavior = attackBehavior;
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
    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
        if (hasAttacked){
            this.icon = attackImage;
        }
        else {
            this.icon = normalImage;
        }
    }

    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return ("attacked: " + this.hasAttacked + 
        "\nattack behavior: " + this.attackBehavior + 
        "\nmove behavior: " + this.moveBehavior +
        "\ntypeforattack: " + this.getTypeForTimeAttack());
    }
}