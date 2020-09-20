package codec;

import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

@Slf4j
public class AwesomePropertyCodec<T> implements Codec<T> {

    private final Class<T> clazz;
    private final String property;

    public AwesomePropertyCodec(Class<T> clazz, String property) {
        this.clazz = clazz;
        this.property = property;
    }

    @Override
    public T decode(BsonReader reader, DecoderContext decoderContext) {
        log.info("DECODING AWESOME " + property);

        if (reader.getCurrentBsonType() == BsonType.BINARY) {
            BsonBinary payload = reader.readBinaryData();
            return (T) new String(payload.getData());
        } else {
            return (T) reader.readString();
        }
    }

    @Override
    public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
        log.info("ENCODING AWESOME " + property);

        BsonBinary data = new BsonBinary(value.toString().getBytes());

        writer.writeBinaryData(data);

    }

    @Override
    public Class<T> getEncoderClass() {
        return clazz;
    }
}
