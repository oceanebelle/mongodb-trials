import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


import java.util.function.Consumer;

public class Start {
    public static void main(String ... args) {

        String user = "test";
        String password = "secret";
        String authDb = "test";
        int port = 27017;

        MongoClient mongoClient = MongoClients.create(String.format("mongodb://%s:%s@localhost:%d/%s?authSource=admin",
                user, password, port, authDb));

        // list databases
        mongoClient.listDatabases()
                .nameOnly(true)
                .forEach((Consumer<? super Document>) Start::print);

        MongoDatabase db = mongoClient.getDatabase(authDb);

        db.listCollectionNames().forEach((Consumer<? super String>) Start::print);

        MongoCollection<Document> collection = db.getCollection("testCollection3");

        Document doc = Document.parse("{}");

        collection.insertOne(doc);

        mongoClient.close();
    }

    public static void print(Document document) {
        System.out.println(document);
    }

    public static void print(String document) {
        System.out.println(document);
    }
}
