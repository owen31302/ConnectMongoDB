import com.mongodb.client.*;
import org.bson.Document;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author yuchlin on 3/14/20
 */
public class ConnectToMongoDB {
    public static void main(String[] args) {

        // Version = 3.12.2

        if(args.length != 2){
            System.out.println("Please enter your username and password");
            return;
        }

        try {
            // Creates credential and connect to MongoDB
            String username = URLEncoder.encode(args[0], "UTF-8");
            String password = URLEncoder.encode(args[1], "UTF-8");
            String dBName = "InsiderBuyNews";

            MongoClient mongoClient = MongoClients.create(
                    "mongodb+srv://" + username + ":" + password + "@insiderbuynews-omcdb.mongodb.net/" + dBName + "?retryWrites=true&w=majority");

            // Gets the database from the MongoDB instance.
            MongoDatabase database = mongoClient.getDatabase(dBName);


            // Print all the collection in this database
            MongoCursor<String> cursor = database.listCollectionNames().iterator();

            while (cursor.hasNext()){
                System.out.println("name: " + cursor.next());
            }


            // 1. Gets the Members collections from the database.
            MongoCollection<Document> collection = database.getCollection("Members");

            // 2. Count documents
            long cnt = collection.countDocuments();
            System.out.println("cnt: " + cnt);

            // 3. Prints out documents from this collection.
            FindIterable<Document> docs = collection.find();
            for (Document doc : docs) {
                System.out.println(doc.toString());
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
