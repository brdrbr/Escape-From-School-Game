package domain.save_load.converter;

import org.bson.BsonReader;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;

import java.awt.Point;
import java.lang.constant.DirectMethodHandleDesc.Kind;

import domain.aliens.move.*;
import domain.save_load.codec.PointCodec;

public class IMoveBehaviorConverter {

    CodecRegistry registry;

    public IMoveBehaviorConverter(CodecRegistry registry) {
        this.registry = registry;
    }

    public Document convert(IMoveBehavior moveBehavior) {
        Document doc = new Document();

        if (moveBehavior.getClass().equals(MoveToSound.class)) {
            doc.put("moveBehavior", "MoveToSound");
            doc.put("soundPoint", moveBehavior.getSoundPoint());
        }

        else if (moveBehavior.getClass().equals(NoMove.class)) {
            doc.put("moveBehavior", "NoMove");
        }
        else if (moveBehavior.getClass().equals(RandomMove.class)) {
            doc.put("moveBehavior", "RandomMove");
        }
        

        return doc;
    }

    public IMoveBehavior convert(Document doc) {

        IMoveBehavior moveBehavior;

        String name = doc.getString("moveBehavior");

        if (name.equals("MoveToSound")) {
            Document pointDocObj = doc.get("soundPoint", Document.class);
            PointCodec pointCodec = (PointCodec) registry.get(Point.class);
            BsonReader reader = new JsonReader(( pointDocObj).toJson());
            Point soundPoint = pointCodec.decode(reader, DecoderContext.builder().build());
            
            moveBehavior = new MoveToSound(soundPoint);
        }
        else if (name.equals("NoMove")) {
            moveBehavior = new NoMove();
        }
        else {
            moveBehavior = new RandomMove();
        }

        return moveBehavior;

    }
    
}
