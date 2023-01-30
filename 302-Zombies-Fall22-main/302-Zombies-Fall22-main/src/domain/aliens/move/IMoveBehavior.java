package domain.aliens.move;

import domain.aliens.Alien;

import java.awt.Point;
public interface IMoveBehavior {

    int dx = 10;
    int dy = 10;

    public abstract void move(Alien alien); //moves towards the destination corner

    public abstract void setDestination(Alien alien); // sets the new destination corner

    public abstract Point getSoundPoint();
    
}
