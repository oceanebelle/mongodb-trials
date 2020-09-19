package codec;

import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

@Slf4j
public class AwesomeCodec<T> implements Codec<T> {
    @Override
    public T decode(BsonReader reader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {

    }

    @Override
    public Class<T> getEncoderClass() {
        return null;
    }

//    private final Class<T> clazz;
//    private final CodecRegistry registry;
//
//    public AwesomeCodec(Class<T> clazz, CodecRegistry registry) {
//        this.clazz = clazz;
//        this.registry = registry;
//    }
//
//    @Override
//    public T decode(BsonReader reader, DecoderContext decoderContext) {
//        reader.readStartDocument();
//        if (reader.getCurrentBsonType().isContainer()) {
//
//        } else {
//            registry.get(reader.getCurrentBsonType())
//            decoderContext.decodeWithChildContext()
//        }
//        reader.readEndDocument();
//    }
//
//    @Override
//    public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
//        writer.writeStartDocument();
//
//
//
//        writer.writeEndDocument();
//    }
//
//    @Override
//    public Class<T> getEncoderClass() {
//        return clazz;
//    }
}
