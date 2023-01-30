package domain.save_load.converter;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import domain.aliens.attack.IAttackBehavior;
import domain.aliens.attack.IndecisiveWaste;
import domain.aliens.attack.MeleeAttack;
import domain.aliens.attack.NoWaste;
import domain.aliens.attack.RangedAttack;
import domain.aliens.attack.WasteTime;

public class IAttackBehaviorConverter {

    CodecRegistry registry;

    public IAttackBehaviorConverter(CodecRegistry registry) {
        this.registry = registry;
    }

    public Document convert(IAttackBehavior attackBehavior) {
        Document doc = new Document();

        if (attackBehavior.getClass().equals(IndecisiveWaste.class)) {
            doc.put("attackBehavior", "IndecisiveWaste");
        }
        else if (attackBehavior.getClass().equals(MeleeAttack.class)) {
            doc.put("attackBehavior", "MeleeAttack");
        }
        else if (attackBehavior.getClass().equals(NoWaste.class)) {
            doc.put("attackBehavior", "NoWaste");
        }
        else if (attackBehavior.getClass().equals(RangedAttack.class)) {
            doc.put("attackBehavior", "RangedAttack");
        }
        else if (attackBehavior.getClass().equals(WasteTime.class)) {
            doc.put("attackBehavior", "WasteTime");
        }

        return doc;
    }

    public IAttackBehavior convert (Document doc) {
        IAttackBehavior attackBehavior;

        String name = doc.getString("attackBehavior");

        if (name.equals("IndecisiveWaste")) {
            attackBehavior = new IndecisiveWaste();
        }
        else if (name.equals("MeleeAttack")) {
            attackBehavior = new MeleeAttack();
        }
        else if (name.equals("NoWaste")) {
            attackBehavior = new NoWaste();
        }
        else if (name.equals("RangedAttack")) {
            attackBehavior = new RangedAttack();
        }
        else {
            attackBehavior = new WasteTime();
        }

        return attackBehavior;
    }
    
}
