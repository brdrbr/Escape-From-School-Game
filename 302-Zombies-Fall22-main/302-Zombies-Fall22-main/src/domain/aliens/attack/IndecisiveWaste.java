package domain.aliens.attack;

import java.io.Serializable;

import domain.aliens.Alien;

public class IndecisiveWaste implements IAttackBehavior, Serializable {
    @Override
    public void attack(Alien alien) {
        // timer is between 30%-70%
        // disappear after 2 seconds without doing anything

        alien.setIsVisible(false);

    }
}
