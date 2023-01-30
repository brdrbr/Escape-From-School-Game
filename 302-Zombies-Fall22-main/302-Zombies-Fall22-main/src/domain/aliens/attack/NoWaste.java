package domain.aliens.attack;

import domain.aliens.Alien;
import domain.handler.RunningModeHandler;

import java.io.Serializable;
import java.util.Random;

public class NoWaste implements IAttackBehavior,Serializable {

    Random random = new Random();

    @Override
    public void attack(Alien alien) {
        // timer is below 30%
        // change location of key once and then disappear

        alien.setHasAttacked(true);

        int oldIndex = RunningModeHandler.getInstance().getCurrentBuilding().getKeyIndex();

        RunningModeHandler.getInstance().getCurrentBuilding().setKeyIndex(random.nextInt(RunningModeHandler.getInstance().getCurrentBuilding().getObjCount()));

        int newIndex = RunningModeHandler.getInstance().getCurrentBuilding().getKeyIndex();

        //System.out.print("Old index: " + oldIndex + "New index: " + newIndex);

        while (newIndex == oldIndex) {
            RunningModeHandler.getInstance().getCurrentBuilding().setKeyIndex(random.nextInt(RunningModeHandler.getInstance().getCurrentBuilding().getObjCount()));
            newIndex = RunningModeHandler.getInstance().getCurrentBuilding().getKeyIndex();
            //System.out.print("Old index: " + oldIndex + "New index: " + newIndex);
        }


        System.out.println("Old index: " + oldIndex + "New index: " + newIndex + "\n");

        alien.setIsVisible(false);

    }

}
