package domain.power_ups.job;

import domain.handler.RunningModeHandler;
import ui.RunningModeFrame;
import domain.aliens.*;
import domain.aliens.move.MoveToSound;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.awt.Point;

public class ThrowPlasticBottle implements IJobBehavior, Serializable{

    @Override
    public void job() {
        // TODO Auto-generated method stub

        System.out.println("blind alien moves towards the sound");
        
        int directionKey = RunningModeHandler.getInstance().getLastPressedKey();

        int xCoor;
        int yCoor;
        Point soundPoint;

        System.out.println("direction key: " + directionKey);

        if (directionKey == KeyEvent.VK_W) { //throw to upside
            xCoor = RunningModeFrame.getInstance().getGameAreaWidth() / 2;
            yCoor = 20;
        }
        else if (directionKey == KeyEvent.VK_D) { //throw to rightside
            xCoor = RunningModeFrame.getInstance().getGameAreaWidth() - 100;
            yCoor = RunningModeFrame.getInstance().getGameAreaHeight() / 2;
        }
        else if (directionKey == KeyEvent.VK_X) { //throw to downside
            xCoor = RunningModeFrame.getInstance().getGameAreaWidth() / 2;
            yCoor = RunningModeFrame.getInstance().getGameAreaHeight() - 100;
        }
        else { //throw to leftside ( directionKey == KeyEvent.VK_A)
            xCoor = 20;
            yCoor = RunningModeFrame.getInstance().getGameAreaHeight() / 2;
        }

        soundPoint = new Point(xCoor, yCoor);

        for (Alien alien : RunningModeHandler.getInstance().getAliens()){
            if (alien.getName().equals("BlindAlien")){
                alien.setMoveBehavior(new MoveToSound(soundPoint));
                alien.performSetDestination();
            }
        }
        
        
    }
    
}
