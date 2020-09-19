import codec.Awesome;
import codec.AwesomeField;
import codec.Encrypted;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Awesome
public class Game {
    private ObjectId id;
    private String name;

    @AwesomeField
    private String type;

    private String publisher;
}
