package domain.power_ups;


import domain.power_ups.job.*;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.Serializable;

public class ExtraTime extends PowerUp implements Serializable{

    

    public ExtraTime(String name, ImageIcon icon){
        super(name, icon);
        jobBehavior = new GainTime();
        key = -1;
    }

    @Override
    public void performJob() {
        // TODO Auto-generated method stub
        jobBehavior.job();
        
    }
}
