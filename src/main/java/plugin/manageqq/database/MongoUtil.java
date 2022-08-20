package plugin.manageqq.database;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import plugin.manageqq.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MongoUtil {
    private static MongoDatabase db;

    public static boolean Initialization(){
        try{
            ServerAddress addr = new ServerAddress(
                    Config.getDatabaseInfo("Url"),
                    Integer.parseInt(Config.getDatabaseInfo("Port"))
            );
            List<ServerAddress> addrs = new ArrayList<>();
            addrs.add(addr);
            MongoCredential cre = MongoCredential.createScramSha1Credential(
                    Config.getDatabaseInfo("Username"),
                    Config.getDatabaseInfo(Config.getDatabaseInfo("Database")),
                    Config.getDatabaseInfo("Password").toCharArray()
            );
            List<MongoCredential> cres = new ArrayList<>();
            cres.add(cre);
            try (MongoClient client = new MongoClient(addrs, cres)) {
                db = client.getDatabase(Config.getDatabaseInfo("Database"));
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static MongoDatabase getDb(){
        return db;
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

    public static MongoCollection<Document> getCollection(String name){
        try{
            return db.getCollection(name);
        }
        catch (Exception e){
            return null;
        }
    }

    public static boolean insertOne(String coll,Document doc){
        try {
            Objects.requireNonNull(getCollection(coll)).insertOne(doc);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static List<Document> find(String coll) {
        try {
            FindIterable<Document> findIterable = Objects.requireNonNull(getCollection(coll)).find();
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
            Objects.requireNonNull(getCollection(coll)).deleteOne(filter);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
