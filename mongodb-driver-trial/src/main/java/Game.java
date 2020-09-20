import codec.annotation.Awesome;
import codec.annotation.AwesomeField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

    private String type;

    @AwesomeField
    private String publisher;
}
