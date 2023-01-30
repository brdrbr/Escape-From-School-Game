package domain.save_load.codec;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import domain.power_ups.job.*;

public class JobBehaviorCodec implements Codec<IJobBehavior>{

    private Codec<String> nameCodec;

    public JobBehaviorCodec(CodecRegistry registry){

        this.nameCodec = registry.get(String.class);
    }

    @Override
    public void encode(BsonWriter writer, IJobBehavior value, EncoderContext encoderContext) {
        // TODO Auto-generated method stub

    

        if (value.getClass().equals(AddProtection.class)){

            writer.writeStartDocument();
            writer.writeName("jobBehavior");
            nameCodec.encode(writer, "AddProtection".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else if (value.getClass().equals(GiveHint.class)){

            writer.writeStartDocument();
            writer.writeName("jobBehavior");
            nameCodec.encode(writer, "GiveHint".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else if (value.getClass().equals(GainLife.class)){

            writer.writeStartDocument();
            writer.writeName("jobBehavior");
            nameCodec.encode(writer, "GainLife".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else if (value.getClass().equals(GainTime.class)){

            writer.writeStartDocument();
            writer.writeName("jobBehavior");
            nameCodec.encode(writer, "GainTime".toString(), encoderContext);
            writer.writeEndDocument();
        }
        else {

            writer.writeStartDocument();
            writer.writeName("jobBehavior");
            nameCodec.encode(writer, "ThrowPlasticBottle".toString(), encoderContext);
            writer.writeEndDocument();
        }


        
    }

    @Override
    public Class<IJobBehavior> getEncoderClass() {
        // TODO Auto-generated method stub
        return IJobBehavior.class;
    }

    @Override
    public IJobBehavior decode(BsonReader reader, DecoderContext decoderContext) {
        // TODO Auto-generated method stub
        IJobBehavior jobBehavior = new ThrowPlasticBottle();

        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            String jobName = nameCodec.decode(reader, decoderContext).toString();
            if (fieldName.equals("jobBehavior")) {
                if (jobName.equals("AddProtection")) {
                    jobBehavior = new AddProtection();
                }
                else if (jobName.equals("GiveHint")) {
                    jobBehavior = new GiveHint();
                }
                else if (jobName.equals("GainTime")) {
                    jobBehavior = new GainTime();
                }
                else if (jobName.equals("GainLife")) {
                    jobBehavior = new GainLife();
                }
            }
        }
        reader.readEndDocument();
        return jobBehavior;
    }
    
}
