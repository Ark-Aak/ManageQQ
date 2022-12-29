package eosgame.manageqq.Databases;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MongoUtil {
    private static MongoDatabase db;
    private static MongoClient client;

    public static boolean Initialization(String constr,String Db){
        try{
            client = MongoClients.create(constr);
            db = client.getDatabase(Db);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static void createCollection(String name){
        try{
            db.createCollection(name);
        } catch (Exception ignored) {}
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
            return getDocuments(findIterable);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Document> find(String coll, BasicDBObject obj){
        try {
            FindIterable<Document> findIterable = db.getCollection(coll).find(obj);
            return getDocuments(findIterable);
        } catch (Exception e) {
            return null;
        }
    }

    @NotNull
    private static List<Document> getDocuments(FindIterable<Document> findIterable) {
        List<Document> doc;
        try (MongoCursor<Document> mongoCursor = findIterable.iterator()) {
            doc = new ArrayList<>();
            while (mongoCursor.hasNext()) {
                doc.add(mongoCursor.next());
            }
        }
        return doc;
    }

    public static boolean deleteOne(String coll, BasicDBObject filter){
        try{
            db.getCollection(coll).deleteOne(filter);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
