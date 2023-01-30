package domain.save_load.codec;

import java.awt.Point;

import javax.swing.ImageIcon;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;

import domain.aliens.Alien;
import domain.aliens.BlindAlien;
import domain.aliens.ShooterAlien;
import domain.aliens.TimeWastingAlien;
import domain.aliens.attack.IAttackBehavior;
import domain.aliens.move.IMoveBehavior;
import domain.save_load.converter.IAttackBehaviorConverter;
import domain.save_load.converter.IMoveBehaviorConverter;

public class AlienCodec implements CollectibleCodec<Alien>{

    private CodecRegistry registry;
    private Codec<Document> docCodec;
    private IMoveBehaviorConverter moveBehaviorConverter;
    private IAttackBehaviorConverter attackBehaviorConverter;


    public AlienCodec(CodecRegistry registry) {
        this.registry = registry;
        this.docCodec = this.registry.get(Document.class);
        this.moveBehaviorConverter = new IMoveBehaviorConverter(registry);
        this.attackBehaviorConverter = new IAttackBehaviorConverter(registry);
    }
    


    @Override
    public void encode(BsonWriter writer, Alien value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub
        Document doc = new Document();
        doc.put("name", value.getName());
        doc.put("moveBehavior", moveBehaviorConverter.convert(value.getMoveBehavior()));
        doc.put("attackBehavior", attackBehaviorConverter.convert(value.getAttackBehavior()));
        doc.put("destinationCorner", value.getDestinationCorner());
        doc.put("hasAttacked", value.getHasAttacked());
        doc.put("icon", value.getIcon());
        doc.put("imageCorner", value.getImageCorner());
        doc.put("isVisible", value.getIsVisible());
        doc.put("typeForTimeAttack", value.getTypeForTimeAttack());

        docCodec.encode(writer, doc, encoderContext);
    }

    @Override
    public Class<Alien> getEncoderClass() {
        // TODO Auto-generated method stub
        return Alien.class;
    }

    @Override
    public Alien decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub
        Document doc = docCodec.decode(reader, decoderContext);

        Alien alien;

        String name = doc.getString("name"); //
        Document iconDocObj = doc.get("normalImage", Document.class);
        ImageIconCodec imageIconCodec = new ImageIconCodec(registry);
        BsonReader reader2 = new JsonReader(( iconDocObj).toJson());
        ImageIcon icon = imageIconCodec.decode(reader2, DecoderContext.builder().build()); //

        IMoveBehavior moveBehavior = moveBehaviorConverter.convert(doc.get("moveBehavior", Document.class)); //
        IAttackBehavior attackBehavior = attackBehaviorConverter.convert(doc.get("attackBehavior", Document.class)); //
        
        Document pointDocObj = doc.get("destinationCorner", Document.class);
        PointCodec pointCodec = new PointCodec(registry);
        BsonReader reader3 = new JsonReader(( pointDocObj).toJson());
        Point destination_corner = pointCodec.decode(reader3, DecoderContext.builder().build()); //
        
        boolean hasAttacked = doc.get("hasAttacked", Boolean.class); //

        Document pointDocObj2 = doc.get("imageCorner", Document.class);
        BsonReader reader4 = new JsonReader(( pointDocObj2).toJson());
        Point image_corner = pointCodec.decode(reader4, DecoderContext.builder().build()); //

        boolean isVisible = doc.get("isVisible", Boolean.class);//
        int typeForTimeAttack = doc.get("typeForTimeAttack", Integer.class);//

        if (name.equals("TimeWastingAlien")) {
            alien = new TimeWastingAlien(name, icon);
        }
        else if (name.equals("BlindAlien")) {
            alien = new BlindAlien(name, icon);
        }
        else {
            alien = new ShooterAlien(name, icon);
        }

        alien.setAttackBehavior(attackBehavior);
        alien.setMoveBehavior(moveBehavior);
        alien.setDestinationCorner(destination_corner);
        alien.setHasAttacked(hasAttacked);
        alien.setImageCorner(image_corner);
        alien.setIsVisible(isVisible);
        alien.setTypeForTimeAttack(typeForTimeAttack);


        return alien;
    }

    @Override
    public boolean documentHasId(Alien arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Alien generateIdIfAbsentFromDocument(Alien arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BsonValue getDocumentId(Alien arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
