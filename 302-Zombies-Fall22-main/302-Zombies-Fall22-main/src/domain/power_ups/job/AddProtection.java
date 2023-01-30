package domain.power_ups.job;

import java.io.Serializable;

import domain.handler.RunningModeHandler;
import domain.handler.TimerHandler;
import ui.RunningModeFrame;

public class AddProtection implements IJobBehavior, Serializable{

    @Override
    public void job() {
        // TODO Auto-generated method stub
        if (!RunningModeHandler.getInstance().getHasVest()){ //no vest yet
            RunningModeHandler.getInstance().setHasVest(true);
        }
        else {// already wears a vest
            TimerHandler.getInstance().resetVestTime();
        }
        
    }
    
}
