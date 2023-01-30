package domain.handler;

import java.io.Serializable;
import java.util.ArrayList;

import domain.power_ups.*;
import ui.RunningModeFrame;

public class BagHandler implements Serializable{

    public ArrayList<PowerUp> powerUps;
    public int capacity;
    public int currentPowerUpCount;
    public int hintCount;
    public int protectionVestCount;
    public int plasticBottleCount;
    public static BagHandler instance; //Singleton
    
    private BagHandler(){
        powerUps = new ArrayList<PowerUp>();
        capacity = 6;
        currentPowerUpCount = 0;
        hintCount = 0;
        protectionVestCount = 0;
        plasticBottleCount = 0;
    }
    

     //getters
    public static BagHandler getInstance(){ //Singleton
        if (instance == null){
            instance = new BagHandler();
        }
        return instance;
    }
    public int getHintCount(){
        return hintCount;
    }
    public int getProtectionVestCount(){
        return protectionVestCount;
    }
    public int getPlasticBottleCount(){
        return plasticBottleCount;
    }
    public ArrayList<PowerUp> getPowerUps(){
        return powerUps;
    }

    
    

    public void addToBag(PowerUp powerUp){

        

        if (powerUp.getKey() == -1){ //if powerUp is autocast, cast it instead of adding to bag
            powerUp.performJob(); //cast it
        }
        else { //if powerUp is not autocast, add it to bag
            if (currentPowerUpCount < capacity){ //bag is not full

                powerUps.add(powerUp);
                
                currentPowerUpCount++;
                if (powerUp.getName().equals("Hint")) hintCount++;
                if (powerUp.getName().equals("ProtectionVest")) protectionVestCount++;
                if (powerUp.getName().equals("PlasticBottle")) plasticBottleCount++;

                RunningModeFrame.getInstance().getRightPanel().setItemsDisplay(powerUp.getName());

           
            }
            
        }
        
    }

    public void decrementPowerUp(PowerUp powerUp){
        powerUps.remove(powerUp);
        currentPowerUpCount--;
        if (powerUp.getName() == "Hint") hintCount--;
        if (powerUp.getName() == "ProtectionVest") protectionVestCount--;
        if (powerUp.getName() == "PlasticBottle") plasticBottleCount--;


        RunningModeFrame.getInstance().getRightPanel().setItemsDisplay(powerUp.getName());
    }

    public void castPowerUp(int keySource){
        for (PowerUp powerUp : powerUps){ 
            if (powerUp.checkKey(keySource)){ // powerUp exists
                RunningModeHandler.getInstance().setLastPressedKey(keySource);
                powerUp.performJob(); //cast the powerUp
                decrementPowerUp(powerUp); //remove it from bag

                break;
            }
        }

    }

    
    public void resetBag(){
        powerUps = new ArrayList<PowerUp>();
        currentPowerUpCount = 0;
        //hint dışındakiler kalsın
        hintCount = 0;
        protectionVestCount = 0;
        plasticBottleCount = 0;
        RunningModeFrame.getInstance().getRightPanel().resetItemDisplay();
    }
}
