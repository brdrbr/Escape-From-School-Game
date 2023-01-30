package domain.save_load.codec;

import java.awt.Point;

import javax.naming.directory.InvalidAttributeIdentifierException;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class PointCodec implements Codec<Point>{

    private Codec<Integer> xCoordinateCodec;
    private Codec<Integer> yCoordinateCodec;


    public PointCodec(CodecRegistry registry) {

        this.xCoordinateCodec = registry.get(Integer.class);
        this.yCoordinateCodec = registry.get(Integer.class);
        
    }

    @Override
    public void encode(BsonWriter writer, Point value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub

        

        writer.writeStartDocument();
        writer.writeName("xCoordinate");
        xCoordinateCodec.encode(writer, (int) value.getX(), encoderContext);
        writer.writeName("yCoordinate");
        xCoordinateCodec.encode(writer, (int) value.getY(), encoderContext);
        writer.writeEndDocument();
        
    }

    @Override
    public Class<Point> getEncoderClass() {
        // TODO Auto-generated method stub
        
        return Point.class;
    }

    @Override
    public Point decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub


        Point point = new Point();
        int xCoordinate = -999;
        int yCoordinate = -999;
        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            if (fieldName.equals("xCoordinate")) {
                xCoordinate = xCoordinateCodec.decode(reader, decoderContext);
            }
            else if (fieldName.equals("yCoordinate")) {
                yCoordinate = yCoordinateCodec.decode(reader, decoderContext);
            }
        }
        reader.readEndDocument();
        point.setLocation(xCoordinate, yCoordinate);
        return point;
    }
    
}
