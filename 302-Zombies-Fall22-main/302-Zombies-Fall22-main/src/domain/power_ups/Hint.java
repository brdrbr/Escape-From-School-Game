package domain.power_ups;

import domain.power_ups.job.*;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Hint extends PowerUp implements Serializable{

    public Hint(String name, ImageIcon image){
        super(name, image);
        jobBehavior = new GiveHint();
        key = KeyEvent.VK_H;
    }

    @Override
    public void performJob() {
        // TODO Auto-generated method stub
        jobBehavior.job();
        
        
    }
    
}
