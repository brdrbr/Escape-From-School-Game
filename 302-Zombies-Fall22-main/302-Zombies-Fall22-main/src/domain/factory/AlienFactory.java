package domain.factory;

import java.io.Serializable;
import java.util.Random;
import javax.swing.ImageIcon;

import domain.aliens.Alien;
import domain.aliens.BlindAlien;
import domain.aliens.ShooterAlien;
import domain.aliens.TimeWastingAlien;
import domain.handler.RunningModeHandler;

public class AlienFactory implements Serializable{

    private static AlienFactory instance;
    public Random random;
    ImageIcon icon;
    
    private AlienFactory(){
        random = new Random();
    }

    public static AlienFactory getInstance(){
        if (instance == null){
            instance = new AlienFactory();
        }
        return instance;
    }

    public void spawnAlien(){
        int type = random.nextInt(3);

        Alien alien;
        if (type == 0){
            ImageIcon blindAlienIcon = new ImageIcon("src/images/blind_alien.png");
            alien = new BlindAlien("BlindAlien", blindAlienIcon);
        }
        else if (type == 1) { // for now
            ImageIcon timeWastingAlienIcon = new ImageIcon("src/images/time_waster.png");
            alien = new TimeWastingAlien("TimeWastingAlien", timeWastingAlienIcon);

        }
        else {
            ImageIcon shooterAlienIcon = new ImageIcon("src/images/shooting_alien.png");
            alien = new ShooterAlien("ShooterAlien", shooterAlienIcon);
        }

        RunningModeHandler.getInstance().addAlien(alien);
    }

}