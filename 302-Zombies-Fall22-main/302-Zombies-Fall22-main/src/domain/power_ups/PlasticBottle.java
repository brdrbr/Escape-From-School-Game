package domain.power_ups;

import domain.handler.RunningModeHandler;
import domain.power_ups.job.*;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class PlasticBottle extends PowerUp implements Serializable{

    public PlasticBottle() {
        super();
    }

    public PlasticBottle(String name, ImageIcon image){
        super(name, image);
        //collectBehavior = new AutoCast();
        jobBehavior = new ThrowPlasticBottle();
        key = KeyEvent.VK_B;
    }

    @Override
    public void performJob() {
        // TODO Auto-generated method stub
        jobBehavior.job();
        
    }

    @Override
    public boolean checkKey(int keySource){
        boolean flag = false;
        if (keySource == KeyEvent.VK_W || keySource == KeyEvent.VK_D || keySource == KeyEvent.VK_X || keySource == KeyEvent.VK_A) {
            if (RunningModeHandler.getInstance().getLastPressedKey() == KeyEvent.VK_B) {
                flag = true;
            }
        }
        return flag;
    }
    
}
