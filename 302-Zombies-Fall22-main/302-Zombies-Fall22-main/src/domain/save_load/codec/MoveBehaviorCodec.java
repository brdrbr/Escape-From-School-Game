package domain.save_load.codec;

import java.awt.Point;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import domain.aliens.move.IMoveBehavior;
import domain.aliens.move.MoveToSound;
import domain.aliens.move.NoMove;
import domain.aliens.move.RandomMove;

public class MoveBehaviorCodec implements Codec<IMoveBehavior>{

    private Codec<String> nameCodec;
    private PointCodec pointCodec;
    private CodecRegistry registry;

    public MoveBehaviorCodec(CodecRegistry registry) {

        this.nameCodec = registry.get(String.class);
        this.registry = registry;
        
        
    }

    @Override
    public void encode(BsonWriter writer, IMoveBehavior value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub

        if (value.getClass().equals(MoveToSound.class)){

            writer.writeStartDocument();
            writer.writeName("moveBehavior");
            nameCodec.encode(writer, "MoveToSound".toString(), encoderContext);
            writer.writeName("soundPoint");
            this.pointCodec = new PointCodec(registry);
            pointCodec.encode(writer, value.getSoundPoint(), encoderContext);
            writer.writeEndDocument();
        }
        else if (value.getClass().equals(NoMove.class)){

            writer.writeStartDocument();
            writer.writeName("moveBehavior");
            nameCodec.encode(writer, "NoMove".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else {
            writer.writeStartDocument();
            writer.writeName("moveBehavior");
            nameCodec.encode(writer, "RandomMove".toString(), encoderContext);
            writer.writeEndDocument();
        }
        
    }

    @Override
    public Class<IMoveBehavior> getEncoderClass() {
        // TODO Auto-generated method stub
        return IMoveBehavior.class;
    }

    @Override
    public IMoveBehavior decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub
        IMoveBehavior moveBehavior = new NoMove();
        
        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            String moveName = nameCodec.decode(reader, decoderContext).toString();
            if (fieldName.equals("moveBehavior")) {
                if (moveName.equals("MoveToSound")) {
                    Point soundPoint = pointCodec.decode(reader, decoderContext);
                    moveBehavior = new MoveToSound(soundPoint);
                }
                else if (moveName.equals("RandomMove")) {
                    moveBehavior = new RandomMove();
                }
               
            }
        }
        reader.readEndDocument();
        return moveBehavior;
    }
    
}
