package domain.save_load.codec;

import java.util.ArrayList;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.Document;

import domain.building_mode.Building;
import domain.building_mode.Object;
import domain.save_load.converter.ObjectConverter;

public class BuildingCodec implements CollectibleCodec<Building>{

    private CodecRegistry registry;
    private Codec<String> nameCodec;
    private Codec<Integer> objCountCodec;
    private Codec<Object> objectCodec;
    private Codec<Integer> keyIndexCodec;
    private Codec<Document> docCodec;
    private ObjectConverter objConverter;

    public BuildingCodec(CodecRegistry registry) {
        this.registry = registry;
        this.nameCodec = registry.get(String.class);
        this.objCountCodec = registry.get(Integer.class);
        this.docCodec = this.registry.get(Document.class);
        this.keyIndexCodec = registry.get(Integer.class);
        this.objConverter = new ObjectConverter(registry);
    }
    @Override
    public void encode(BsonWriter writer, Building value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub

        Document doc = new Document();
        doc.put("name", value.getName());
        doc.put("keyIndex", value.getKeyIndex());
        doc.put("objCount", value.getObjCount());
        doc.put("objects", value.getObjects());
        
        docCodec.encode(writer, doc, encoderContext);
    }

    @Override
    public Class<Building> getEncoderClass() {
        // TODO Auto-generated method stub
        return Building.class;
    }

    @Override
    public Building decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub
        Document doc = docCodec.decode(reader, decoderContext);
        Building building;
        String name = doc.get("name", String.class);
        int keyIndex = doc.get("keyIndex", Integer.class);
        int objCount = doc.get("objCount", Integer.class);

        building = new Building(name, objCount);


        ArrayList<Document> objDocList = (ArrayList) doc.get("objects");

        
        
        for (Document docObj : objDocList) {
            
            Object obj = objConverter.convert(docObj);
            building.addObject(obj);
        }


        return building;
    }
    @Override
    public boolean documentHasId(Building arg0) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public Building generateIdIfAbsentFromDocument(Building arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public BsonValue getDocumentId(Building arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
