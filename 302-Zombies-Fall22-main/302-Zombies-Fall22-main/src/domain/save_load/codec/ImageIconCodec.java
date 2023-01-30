package domain.save_load.codec;

import javax.swing.ImageIcon;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class ImageIconCodec implements Codec<ImageIcon>{

    private Codec<String> pathCodec;


    public ImageIconCodec(CodecRegistry registry) {

        this.pathCodec = registry.get(String.class);
        
    }

    @Override
    public void encode(BsonWriter writer, ImageIcon value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub

        writer.writeStartDocument();
        writer.writeName("path");
        pathCodec.encode(writer, value.toString(), encoderContext);
        writer.writeEndDocument();
        
    }

    @Override
    public Class<ImageIcon> getEncoderClass() {
        // TODO Auto-generated method stub

        return ImageIcon.class;
    }

    @Override
    public ImageIcon decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub
        ImageIcon icon = new ImageIcon();
        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            if (fieldName.equals("path")) {
                icon = new ImageIcon(pathCodec.decode(reader, decoderContext).toString());
            }
        }
        reader.readEndDocument();
        return icon;
    }
    
}
