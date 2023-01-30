package domain.aliens.attack;

import domain.aliens.Alien;
import domain.aliens.Bullet;
import domain.handler.RunningModeHandler;
import domain.running_mode.Player;
import ui.RunningModeFrame;

import java.awt.*;
import java.io.Serializable;

public class RangedAttack implements IAttackBehavior, Serializable {

    @Override
    public void attack(Alien alien) {
        // TODO Auto-generated method stub

        //Bullet bullet = new Bullet(alien.getXCoordinate() - alien.getWidth(), alien.getHeight());

        //if ()

        Bullet bullet;

        if (alien.getImageCorner().getX() <= RunningModeFrame.getInstance().getGameAreaWidth() / 2) {
            bullet = new Bullet(alien.getXCoordinate() + alien.getWidth(), alien.getYCoordinate(), 1);

        } else {

            bullet = new Bullet(alien.getXCoordinate() - alien.getWidth(), alien.getYCoordinate(), 0);

        }


        RunningModeHandler.getInstance().addBullet(bullet);

    }



    
}
