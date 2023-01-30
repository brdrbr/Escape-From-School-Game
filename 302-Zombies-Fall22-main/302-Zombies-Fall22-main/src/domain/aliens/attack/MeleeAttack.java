package domain.aliens.attack;

import java.io.Serializable;

import domain.aliens.Alien;
import domain.handler.RunningModeHandler;
import domain.running_mode.Player;



public class MeleeAttack implements IAttackBehavior, Serializable{

    @Override
    public void attack(Alien alien) {
        // TODO Auto-generated method stub
        //insta-kill if hit
        alien.setHasAttacked(true);
        
        if (RunningModeHandler.getInstance().checkIfNextTo(alien.getXCoordinate(), alien.getYCoordinate(), alien.getWidth(), alien.getHeight())){
            Player.getInstance().death();
        }
        
    }
    
}