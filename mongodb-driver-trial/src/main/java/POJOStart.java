import codec.AwesomeConvention;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
public class POJOStart {
    public static void main(String... args) {

        log.info("Starting the app");

        String user = "test";
        String password = "secret";
        String database = "test";
        int port = 27017;



        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();

        List<Convention> conventions = new ArrayList<>(Conventions.DEFAULT_CONVENTIONS);
        conventions.add(new AwesomeConvention());

        // Set the default codec registry
        // The registries are checked in order until one returns a codec for the requested class.
        // The DefaultCodecRegistry should be first in the list,
        // and the PojoCodecProvider should always be the last CodecProvider
        // since it can provide a codec for almost any class.
        CodecRegistry pojoCodecRegistry = fromRegistries(
                defaultCodecRegistry,
                fromProviders(
                        PojoCodecProvider.builder()
                                // Conventions are used by the PojoCodecProvider
                                .conventions(conventions)
                                .automatic(true)
                                .build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(String.format("mongodb://%s:%s@localhost:%d/%s?authSource=admin",
                        user, password, port, database)))
                .codecRegistry(pojoCodecRegistry)
                .build();


        log.info("Connecting to Mongo");
        MongoClient mongoClient = MongoClients.create(settings);


        MongoDatabase db = mongoClient.getDatabase(database);

        log.info("Getting collection");
        MongoCollection<Game> collection = db.getCollection("games", Game.class);
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection.createIndex(Indexes.compoundIndex(
                Indexes.ascending("name")),
                indexOptions);

        // create
        Game game = Game.builder()
                .name("codevein")
                .publisher("squareenix")
                .type(Instant.now().toString())
                .build();

        collection.insertOne(game);

        // find
        Game g = collection.find(eq("name", "codevein")).first();
        System.out.println(g);
        g.setPublisher("another!");

        // modify
        // TODO: What about awesomeness when updating POJO?
        // This updates a specific field only and will not trigger awesome
        //collection.updateOne(eq("name", "codevein"), combine(set("exported", "true")));

        // This replaces an entire document and triggers awesome codec!
        collection.replaceOne(eq("name", "codevein"), g);

        g = collection.find(eq("name", "codevein")).first();
        System.out.println(g);


        mongoClient.close();
    }

}

