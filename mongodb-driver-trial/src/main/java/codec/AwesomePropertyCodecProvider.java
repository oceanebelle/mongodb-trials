package codec;

import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.Codec;
import org.bson.codecs.pojo.PropertyCodecProvider;
import org.bson.codecs.pojo.PropertyCodecRegistry;
import org.bson.codecs.pojo.TypeWithTypeParameters;

@Slf4j
public class AwesomePropertyCodecProvider implements PropertyCodecProvider {
    @Override
    public <T> Codec<T> get(TypeWithTypeParameters<T> type, PropertyCodecRegistry registry) {

        log.info("INspecting fields");
        if (type.getType().isAnnotationPresent(Awesome.class)) {
            log.info("Awesome Property FOUND!");
        }
        return null;
    }
}
