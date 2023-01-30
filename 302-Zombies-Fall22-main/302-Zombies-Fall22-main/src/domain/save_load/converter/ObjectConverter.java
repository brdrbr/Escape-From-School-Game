package domain.save_load.converter;

import javax.swing.ImageIcon;
import java.awt.Point;

import org.bson.BsonReader;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;

import domain.building_mode.Object;
import domain.save_load.codec.ImageIconCodec;
import domain.save_load.codec.PointCodec;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ObjectConverter {

    CodecRegistry registry;

    public ObjectConverter(CodecRegistry registry) {
        this.registry = registry;
    }
    
    public Document convert(Object obj) {
        Document doc = new Document();
        doc.put("name", obj.getName()); //1
        //doc.put("height", obj.getHeight());//2
        //doc.put("width", obj.getWidth());//3
        doc.put("icon", obj.getIcon());//4
        doc.put("imageCorner", obj.getImageCorner());//5

        return doc;
    }

    public Object convert(Document doc) {
        Object obj;
        String name = doc.getString("name");

        Document pointDocObj = doc.get("imageCorner", Document.class);
        PointCodec pointCodec = new PointCodec(registry);
        BsonReader reader = new JsonReader(( pointDocObj).toJson());
        Point image_corner = pointCodec.decode(reader, DecoderContext.builder().build());

        Document iconDocObj = doc.get("icon", Document.class);
        ImageIconCodec imageIconCodec = new ImageIconCodec(registry);
        BsonReader reader2 = new JsonReader(( iconDocObj).toJson());
        ImageIcon icon = imageIconCodec.decode(reader2, DecoderContext.builder().build());

        obj = new Object(name, icon, image_corner);
        return obj;
    }
}
