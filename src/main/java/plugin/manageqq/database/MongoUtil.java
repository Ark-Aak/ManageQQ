package plugin.manageqq.database;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import plugin.manageqq.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MongoUtil {
    private static MongoDatabase db;
    private static MongoClient client;

    public static boolean Initialization(String ddb){
        try{
            client = MongoClients.create("mongodb://"+ Config.getDatabaseInfo("Username")+ ":"+Config.getDatabaseInfo("Password")+ "@"+ Config.getDatabaseInfo("Url")+ ":"+ Config.getDatabaseInfo("Port")+ "/"+ ddb);
            db = client.getDatabase(Config.getDatabaseInfo("Database"));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean createCollection(String name){
        try{
            db.createCollection(name);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean insertOne(String coll,Document doc){
        try {
            db.getCollection(coll).insertOne(doc);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static List<Document> find(String coll) {
        try {
            FindIterable<Document> findIterable = db.getCollection(coll).find();
            List<Document> doc;
            try (MongoCursor<Document> mongoCursor = findIterable.iterator()) {
                doc = new ArrayList<>();
                while (mongoCursor.hasNext()) {
                    doc.add(mongoCursor.next());
                }
            }
            return doc;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean deleteOne(String coll, Bson filter){
        try{
            db.getCollection(coll).deleteOne(filter);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
