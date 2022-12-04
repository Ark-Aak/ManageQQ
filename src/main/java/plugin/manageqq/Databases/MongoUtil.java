package plugin.manageqq.Databases;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

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
        } catch (Exception e) {
            throw new RuntimeException(e);
            //反正我是不信你创建集合会报错的
            //咱也没办法对吧
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
