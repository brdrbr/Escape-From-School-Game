package domain.factory;

import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;

import domain.handler.RunningModeHandler;
import domain.power_ups.ExtraLife;
import domain.power_ups.ExtraTime;
import domain.power_ups.Hint;
import domain.power_ups.PlasticBottle;
import domain.power_ups.ProtectionVest;
import domain.power_ups.PowerUp;


public class PowerUpFactory implements Serializable{

    private static PowerUpFactory instance;
    public Random random;
    ImageIcon icon;

    private PowerUpFactory(){
        random = new Random();
    }
    
    public static PowerUpFactory getInstance(){
        if (instance == null){
            instance = new PowerUpFactory();
        }
        return instance;
    }

    public void spawnPowerUp(){
        int type = random.nextInt(5);
        //int type = 4;
        
        PowerUp powerUp;
        if (type == 0){
            ImageIcon extraTimeIcon = new ImageIcon("src/images/extra_time.png");
            powerUp = new ExtraTime("ExtraTime", extraTimeIcon);
        }
        else if (type == 1){
            ImageIcon extraLifeIcon = new ImageIcon("src/images/extra_life.png");
            powerUp = new ExtraLife("ExtraLife", extraLifeIcon);
        }
        else if (type == 2){
            ImageIcon hintIcon = new ImageIcon("src/images/hint.png");
            powerUp = new Hint("Hint",hintIcon);
        }
        else if (type == 3){
            ImageIcon vestIcon = new ImageIcon("src/images/protection_vest.png");
            powerUp = new ProtectionVest("ProtectionVest", vestIcon);
        }
        else { //type == 4
            ImageIcon plasticBottleIcon = new ImageIcon("src/images/plastic_bottle.png");
            powerUp = new PlasticBottle("PlasticBottle", plasticBottleIcon);
        }

        RunningModeHandler.getInstance().setPowerUp(powerUp);
    }
    
}
