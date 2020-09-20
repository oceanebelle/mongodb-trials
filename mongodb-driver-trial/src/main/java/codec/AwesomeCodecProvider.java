package codec;

import codec.annotation.Awesome;
import codec.annotation.AwesomeField;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.ClassModelBuilder;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Providers allows lookup of other dependent Codecs via the CodecRegistry.
 *
 * Handle the annotated class specifically if it contains awesome fields
 * See codec for issues.
 */
@Slf4j
public class AwesomeCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {

        if (clazz.isAnnotationPresent(Awesome.class)) {
            if (Stream.of(clazz.getDeclaredFields())
                    .anyMatch(field -> field.isAnnotationPresent(AwesomeField.class))) {

                log.info("Use Awesome provider for " + clazz);

                // Build metadata for the class and its properties
                // Customise instance creators, set conventions etc
                // -
                ClassModelBuilder<T> model = ClassModel.builder(clazz);

                List<Map.Entry<Field, Codec<?>>> customCodec = Stream.of(clazz.getDeclaredFields())
                        .flatMap(field -> buildCustomCodec(field, registry, model))
                        .collect(Collectors.toList());
                return new AwesomeCodec<>(clazz, customCodec, model.build());
            }
        }

        // This provider will not handle this class
        return null;
    }

    @SuppressWarnings("rawtypes")
    private Stream<Map.Entry<Field, Codec<?>>> buildCustomCodec(Field field, CodecRegistry registry, ClassModelBuilder<?> model) {

        if (field.isAnnotationPresent(AwesomeField.class)) {
            //noinspection unchecked
            Codec codec = new AwesomePropertyCodec(field.getType(), field.getName());
            Map.Entry<Field, Codec<?>> entry = new AbstractMap.SimpleImmutableEntry<>(field, codec);

            model.getProperty(field.getName()).codec(codec);

            return Stream.of(entry);
        }

        return Stream.empty();
    }
}
