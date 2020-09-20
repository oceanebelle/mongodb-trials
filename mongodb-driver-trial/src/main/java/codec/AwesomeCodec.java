package codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.pojo.ClassModel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class AwesomeCodec<T> implements Codec<T> {

    private final Class<T> clz;
    private final List<Map.Entry<Field, Codec<?>>> codec;
    private final ClassModel<T> model;

    AwesomeCodec(Class<T> clz, List<Map.Entry<Field, Codec<?>>> codec, ClassModel<T> model) {
        this.clz = clz;
        this.codec = codec;
        this.model = model;
    }

    @Override
    public T decode(BsonReader reader, DecoderContext decoderContext) {

        // TODO:
        // 1. AwesomeCode now has reference to the metadata of the class
        // 2. Need a way to instantiate the object and set the values via reflection
        //      - need conventions established such as
        //      - how to handle annotations etc
        //      - note higher level libraries may have their own Object CodecProvider and so
        //      - AwesomeConvention and this provider may not be used

        return null;
    }

    @Override
    public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {


    }

    @Override
    public Class<T> getEncoderClass() {
        return clz;
    }
}
