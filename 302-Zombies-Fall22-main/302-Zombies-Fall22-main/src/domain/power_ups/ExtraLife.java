package domain.power_ups;

import domain.power_ups.job.*;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.Serializable;

public class ExtraLife extends PowerUp implements Serializable{

    public ExtraLife(String name, ImageIcon image){
        super(name, image);
        jobBehavior = new GainLife();
        key = -1;
    }


    @Override
    public void performJob() {
        // TODO Auto-generated method stub
        jobBehavior.job();
    }

    
}
