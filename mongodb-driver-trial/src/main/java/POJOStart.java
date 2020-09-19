import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class POJOStart {
    public static void main(String ... args) {

        String user = "test";
        String password = "secret";
        String database = "test";
        int port = 27017;

        MongoClient mongoClient = MongoClients.create(String.format("mongodb://%s:%s@localhost:%d/%s?authSource=admin",
                user, password, port, database));

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        // Set the default codec registry
        // The registries are checked in order until one returns a codec for the requested class.
        // The DefaultCodecRegistry should be first in the list,
        // and the PojoCodecProvider should always be the last CodecProvider
        // since it can provide a codec for almost any class.
        MongoDatabase db = mongoClient.getDatabase(database)
                .withCodecRegistry(pojoCodecRegistry);

        MongoCollection<Game> collection = db.getCollection("games", Game.class);
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection.createIndex(Indexes.compoundIndex(
                Indexes.ascending("name"),
                Indexes.ascending("squareenix"),
                Indexes.ascending("rpg")),
                indexOptions);

        Game game = Game.builder()
                .name("codevein")
                .publisher("squareenix")
                .type("rpg")
                .build();

        collection.insertOne(game);

        collection.find(eq("name", "codevein"))
                .forEach((Consumer<? super Game>) System.out::println);

        mongoClient.close();
    }

}

