package domain.aliens.move;

import java.awt.Point;
import java.io.Serializable;

import domain.aliens.Alien;

public class MoveToSound implements IMoveBehavior, Serializable{

    public Point soundPoint;

    public MoveToSound(Point soundPoint){
        this.soundPoint = soundPoint;
    }

    public Point getSoundPoint(){
        return soundPoint;
    }

    @Override
    public void move(Alien alien) {
        // TODO Auto-generated method stub
        if (!alien.getImageCorner().equals(alien.getDestinationCorner())){ //alien is not in the destination

            if (alien.getImageCorner().getX() > alien.getDestinationCorner().getX()){ // destination is to the left
                if (alien.getImageCorner().getX() - alien.getDestinationCorner().getX() < 10){
                    alien.setXCoordinate((int) alien.getDestinationCorner().getX());
                }
                else{
                    alien.setXCoordinate((int) alien.getImageCorner().getX() - dx);
                }
            }
            else if (alien.getImageCorner().getX() < alien.getDestinationCorner().getX()){ // destination is to the right
                if (alien.getDestinationCorner().getX() - alien.getImageCorner().getX() < 10){
                    alien.setXCoordinate((int) alien.getDestinationCorner().getX());
                }
                else {
                    alien.setXCoordinate((int) alien.getImageCorner().getX() + dx);
                }
            }

            if (alien.getImageCorner().getY() > alien.getDestinationCorner().getY()){ // destination is to the upside
                if (alien.getImageCorner().getY() - alien.getDestinationCorner().getY() < 10){
                    alien.setYCoordinate((int) alien.getDestinationCorner().getY());
                }
                else {
                    alien.setYCoordinate((int) alien.getImageCorner().getY() - dy);
                }
            }
            else if (alien.getImageCorner().getY() < alien.getDestinationCorner().getY()){ // destination is to the downside
                if (alien.getDestinationCorner().getY() - alien.getImageCorner().getY() < 10){
                    alien.setYCoordinate((int) alien.getDestinationCorner().getY());
                }
                else {
                    alien.setYCoordinate((int) alien.getImageCorner().getY() + dy);
                }
            }
        }
    }

    @Override
    public void setDestination(Alien alien) {
        // TODO Auto-generated method stub
        alien.setDestinationCorner(soundPoint);
        System.out.println("sound comes from:" + soundPoint);
        alien.setMoveBehavior(new RandomMove());
        
        
    }
    
}