package domain.power_ups;

import domain.power_ups.job.*;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class ProtectionVest extends PowerUp implements Serializable{

    public ProtectionVest(String name, ImageIcon image){
        super(name, image);
        //collectBehavior = new AutoCast();
        jobBehavior = new AddProtection();
        key = KeyEvent.VK_P;
    }

    @Override
    public void performJob() {
        // TODO Auto-generated method stub
        jobBehavior.job();
        
        
    }
    
}
