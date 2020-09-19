package codec;

import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.pojo.ClassModelBuilder;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.PropertyModelBuilder;

import java.lang.reflect.Field;
import java.util.stream.Stream;

@Slf4j
public class AwesomeConvention implements Convention {

    @Override
    public void apply(final ClassModelBuilder<?> classModelBuilder) {
        Class<?> type = classModelBuilder.getType();
        if (type.isAnnotationPresent(Awesome.class)) {
            log.info("Modifying this model builder  " + type);

            // Set a custom Codec for AwesomeField
            Stream.of(type.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(AwesomeField.class))
                    .forEach(field -> modifyPropertyBuilder(field, classModelBuilder.getProperty(field.getName())));
        }
    }

    private void modifyPropertyBuilder(Field field, PropertyModelBuilder<?> property) {
        log.info("Setting to AwesomeCodec! " + field.getName());
        property.readName("awesome_" + field.getName());
        property.writeName("awesome_" + field.getName());
        property.codec(new AwesomePropertyCodec(field.getType(), field.getName()));
    }
}
