package domain.save_load.codec;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import domain.aliens.attack.IAttackBehavior;
import domain.aliens.attack.IndecisiveWaste;
import domain.aliens.attack.MeleeAttack;
import domain.aliens.attack.NoWaste;
import domain.aliens.attack.RangedAttack;
import domain.aliens.attack.WasteTime;

public class AttackBehaviorCodec implements Codec<IAttackBehavior>{

    private Codec<String> nameCodec;

    public AttackBehaviorCodec(CodecRegistry registry) {

        this.nameCodec = registry.get(String.class);
    }

    @Override
    public void encode(BsonWriter writer, IAttackBehavior value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub

        if (value.getClass().equals(IndecisiveWaste.class)){

            writer.writeStartDocument();
            writer.writeName("attackBehavior");
            nameCodec.encode(writer, "IndecisiveWaste".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else if (value.getClass().equals(MeleeAttack.class)){

            writer.writeStartDocument();
            writer.writeName("attackBehavior");
            nameCodec.encode(writer, "MeleeAttack".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else if (value.getClass().equals(NoWaste.class)){
            writer.writeStartDocument();
            writer.writeName("attackBehavior");
            nameCodec.encode(writer, "NoWaste".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else if (value.getClass().equals(RangedAttack.class)){
            writer.writeStartDocument();
            writer.writeName("attackBehavior");
            nameCodec.encode(writer, "RangedAttack".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else {
            writer.writeStartDocument();
            writer.writeName("attackBehavior");
            nameCodec.encode(writer, "WasteTime".toString(), encoderContext);
            writer.writeEndDocument();
        }
        
    }

    @Override
    public Class<IAttackBehavior> getEncoderClass() {
        // TODO Auto-generated method stub
        return IAttackBehavior.class;
    }

    @Override
    public IAttackBehavior decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub
        IAttackBehavior attackBehavior = new NoWaste();

        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            String attackName = nameCodec.decode(reader, decoderContext).toString();
            if (fieldName.equals("attackBehavior")) {
                if (attackName.equals("IndecisiveWaste")) {
                    attackBehavior = new IndecisiveWaste();
                }
                else if (attackName.equals("MeleeAttack")) {
                    attackBehavior = new MeleeAttack();
                }
                else if (attackName.equals("RangedAttack")) {
                    attackBehavior = new RangedAttack();
                }
                else if (attackName.equals("WasteTime")) {
                    attackBehavior = new WasteTime();
                }
            }
        }
        reader.readEndDocument();
        return attackBehavior;
    }
    
}
