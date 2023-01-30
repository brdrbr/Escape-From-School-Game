package domain.power_ups.job;

import domain.handler.RunningModeHandler;
import domain.handler.TimerHandler;

import java.io.Serializable;

public class GiveHint implements IJobBehavior, Serializable {

    @Override
    public void job() {
        // TODO Auto-generated method stub
        if (!RunningModeHandler.getInstance().getShowHint()){ //no prior hint
            RunningModeHandler.getInstance().setHintArea();
            RunningModeHandler.getInstance().setShowHint(true);
        }
        else { //already has hint on
            TimerHandler.getInstance().resetHintTime();
        }
        
    }
    
}
