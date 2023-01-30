package domain.power_ups.job;

import java.io.Serializable;

import domain.handler.TimerHandler;

public class GainTime implements IJobBehavior, Serializable {

    int extraTime = 5;

    @Override
    public void job() {
        // add 5 seconds to timer
        TimerHandler.getInstance().setDuration(extraTime);
        System.out.println("time added");
    }
    
}
