package domain.aliens.move;

import domain.aliens.Alien;
import java.awt.Point;
import java.io.Serializable;

public class NoMove implements IMoveBehavior,Serializable {

    @Override
    public void move(Alien alien) {
        // TODO Auto-generated method stub
        //do nothing
        //System.out.println("i do not move!");
        
    }

    @Override
    public void setDestination(Alien alien) {
        // TODO Auto-generated method stub
        //do nothing
        
    }

    @Override
    public Point getSoundPoint(){
        return null;
    }
    
}
