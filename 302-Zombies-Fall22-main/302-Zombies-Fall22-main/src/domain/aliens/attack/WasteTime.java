package domain.aliens.attack;

import domain.aliens.Alien;
import domain.handler.RunningModeHandler;

import java.io.Serializable;
import java.util.Random;

public class WasteTime implements IAttackBehavior, Serializable {

    Random random = new Random();

    @Override
    public void attack(Alien alien) {
    // timer is above 70%
    // changes key location in every 3 secs

        alien.setHasAttacked(true);

        int oldIndex = RunningModeHandler.getInstance().getCurrentBuilding().getKeyIndex();

        RunningModeHandler.getInstance().getCurrentBuilding().setKeyIndex(random.nextInt(RunningModeHandler.getInstance().getCurrentBuilding().getObjCount()));

        int newIndex = RunningModeHandler.getInstance().getCurrentBuilding().getKeyIndex();

        while (newIndex == oldIndex) {
            RunningModeHandler.getInstance().getCurrentBuilding().setKeyIndex(random.nextInt(RunningModeHandler.getInstance().getCurrentBuilding().getObjCount()));
            newIndex = RunningModeHandler.getInstance().getCurrentBuilding().getKeyIndex();
        }

    }
}
