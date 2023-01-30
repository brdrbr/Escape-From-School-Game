package domain.save_load.codec;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.bson.Document;

import domain.building_mode.Object;
import domain.save_load.converter.ObjectConverter;

public class ObjectCodec implements CollectibleCodec<Object>{

    private CodecRegistry registry;
    private Codec<Document> docCodec;
    private ObjectConverter converter;

    public ObjectCodec(CodecRegistry registry) {
        this.converter = new ObjectConverter(registry);
        this.registry = registry;
        this.docCodec = this.registry.get(Document.class);
        
    }

    @Override
    public void encode(BsonWriter writer, Object value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub

        Document doc = this.converter.convert(value);
        docCodec.encode(writer, doc, encoderContext);
        
    }

    @Override
    public Class<Object> getEncoderClass() {
        // TODO Auto-generated method stub
        return Object.class;
    }

    @Override
    public Object decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub
        Document doc = docCodec.decode(reader, decoderContext);
        Object obj = converter.convert(doc);
        return obj;
    }

    @Override
    public boolean documentHasId(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object generateIdIfAbsentFromDocument(Object obj) {
        // TODO Auto-generated method stub
        //if (!documentHasId(obj)) obj.setId(new ObjectId());
        return null;
    }

    @Override
    public BsonValue getDocumentId(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
