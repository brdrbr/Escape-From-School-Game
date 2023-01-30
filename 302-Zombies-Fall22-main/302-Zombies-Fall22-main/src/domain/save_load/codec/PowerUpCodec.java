package domain.save_load.codec;

import javax.swing.ImageIcon;
import java.awt.Point;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;

import domain.power_ups.ExtraLife;
import domain.power_ups.ExtraTime;
import domain.power_ups.Hint;
import domain.power_ups.PlasticBottle;
import domain.power_ups.PowerUp;
import domain.power_ups.ProtectionVest;
import domain.power_ups.job.GiveHint;
import domain.power_ups.job.IJobBehavior;

public class PowerUpCodec implements Codec<PowerUp>{

    Codec<String> nameCodec;
    Codec<String> jobNameCodec;
    Codec<ImageIcon> imageCodec;
    Codec<Point> pointCodec;
    Codec<Integer> heightCodec;
    Codec<Integer> widthCodec;
    Codec<Document> docCodec;
    CodecRegistry registry;
    
    
    

    public PowerUpCodec(CodecRegistry registry){

        this.registry = registry;
        this.nameCodec = registry.get(String.class); //+
        this.jobNameCodec = registry.get(String.class); //
        this.imageCodec = new ImageIconCodec(registry);//+
        this.pointCodec = new PointCodec(registry);//+
        this.heightCodec = registry.get(Integer.class);//+
        this.widthCodec = registry.get(Integer.class);//
        this.docCodec = registry.get(Document.class);
        
    }

    @Override
    public void encode(BsonWriter writer, PowerUp value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub

        writer.writeStartDocument();
        writer.writeName("name");//
        nameCodec.encode(writer, value.getName(), encoderContext);
        writer.writeName("icon");//
        imageCodec.encode(writer, value.getIcon(), encoderContext);
        writer.writeName("imageCorner");//
        pointCodec.encode(writer, value.getImageCorner(), encoderContext);
        writer.writeName("height");
        heightCodec.encode(writer, value.getHeight(), encoderContext);
        writer.writeName("width");
        widthCodec.encode(writer, value.getWidth(), encoderContext);
        writer.writeEndDocument();
        
    }

    @Override
    public Class<PowerUp> getEncoderClass() {
        // TODO Auto-generated method stub
        return PowerUp.class;
    }

    
    @Override
    public PowerUp decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub

        Document doc = docCodec.decode(reader, decoderContext);

        PowerUp powerUp;
        String name = doc.get("name", String.class);

        IJobBehavior jobBehavior;

        Document iconDocObj = doc.get("icon", Document.class);
        BsonReader reader2 = new JsonReader(( iconDocObj).toJson());
        ImageIcon icon = imageCodec.decode(reader2, DecoderContext.builder().build());

        Document pointDocObj2 = doc.get("imageCorner", Document.class);
        BsonReader reader3 = new JsonReader(( pointDocObj2).toJson());
        Point image_corner = pointCodec.decode(reader3, DecoderContext.builder().build()); 

        if (name.equals("PlasticBottle")) {
            powerUp = new PlasticBottle(name, icon);
        }
        else if (name.equals("ProtectionVest")){
            powerUp = new ProtectionVest(name, icon);
        }
        else if (name.equals("ExtraLife")){
            powerUp = new ExtraLife(name, icon);
        }
        else if (name.equals("ExtraTime")){
            powerUp = new ExtraTime(name, icon);
        }
        else {
            powerUp = new Hint(name, icon);
        }

        powerUp.setImageCorner(image_corner);

        
        
        return powerUp;
    }
    
}
