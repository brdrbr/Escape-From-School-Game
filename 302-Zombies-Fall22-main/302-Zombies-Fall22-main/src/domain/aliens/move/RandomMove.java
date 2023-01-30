package domain.aliens.move;

import java.util.Random;
import java.awt.Point;
import java.io.Serializable;

import domain.handler.RunningModeHandler;
import ui.RunningModeFrame;
import domain.aliens.Alien;

public class RandomMove implements IMoveBehavior, Serializable {

    Random random;
    public RandomMove(){

        random = new Random();
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
    public void setDestination(Alien alien) { //sets the new destination corner randomly
        // TODO Auto-generated method stub
        int XCoordinate;
        int yCoordinate;
        Point destination_corner;
        do {
            XCoordinate = random.nextInt(RunningModeFrame.getInstance().getGameAreaWidth() - alien.getWidth());
            yCoordinate = random.nextInt(RunningModeFrame.getInstance().getGameAreaHeight() - alien.getHeight());
            } while (RunningModeHandler.getInstance().checkSpawnCollision(XCoordinate, yCoordinate, alien.getWidth(), alien.getHeight()));
    
            destination_corner = new Point(XCoordinate, yCoordinate);
            alien.setDestinationCorner(destination_corner);
        
    }

    @Override
    public Point getSoundPoint(){
        return null;
    }
    
}
