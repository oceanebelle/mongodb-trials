package codec;

import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Arrays;

@Slf4j
public class AwesomeCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {

        //log.info("Checking if class is annotated " + clazz);
        if (Arrays.stream(clazz.getAnnotations()).anyMatch(x -> x instanceof Awesome)) {
            log.info("Use Awesome Custom provider for " + clazz);



            //return (Codec<T>) new AwesomeCodec(clazz, registry);
        }

        return null;
    }
}
