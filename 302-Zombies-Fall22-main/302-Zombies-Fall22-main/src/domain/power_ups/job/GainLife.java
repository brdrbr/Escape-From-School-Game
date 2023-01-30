package domain.power_ups.job;

import java.io.Serializable;

import domain.running_mode.Player;
import ui.RunningModeFrame;

public class GainLife implements IJobBehavior, Serializable {

    @Override
    public void job() {
        // add 1 life
        Player.getInstance().incrementLifeCount();  
        RunningModeFrame.getInstance().getRightPanel().setLifeCount();
        }
    
}
