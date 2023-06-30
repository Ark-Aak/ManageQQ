package lwgame.manageqq.Utils;

import com.mongodb.BasicDBObject;
import lwgame.manageqq.Logger;
import lwgame.manageqq.Databases.MongoUtil;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BindUtil {
    private static final ConcurrentHashMap<String,String> nameToToken = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String,Long> nameToQQ = new ConcurrentHashMap<>();

    public static String genNewToken(long id,String playerName){
        String token = StringUtil.getRandomString(16);
        nameToToken.put(playerName,token);
        nameToQQ.put(playerName,id);
        return token;
    }

    public static boolean checkToken(String playerName,String token){
        if(nameToToken.get(playerName) == null) return false;
        return nameToToken.get(playerName).equals(token);
    }

    public static void addBind(String playerName){
        if(!nameToQQ.containsKey(playerName) || !nameToToken.containsKey(playerName)){
            return;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        String dt = df.format(date);
        if(!MongoUtil.insertOne("bind",new Document("playerName",playerName).append("bindId",nameToQQ.get(playerName)).append("date",dt))){
            Logger.warn("警告！数据库出现错误！");
        }
        nameToToken.remove(playerName);
        nameToQQ.remove(playerName);
    }

    public static Document getBindById(long id){
        BasicDBObject query = new BasicDBObject();
        query.put("bindId",id);
        List<Document> docs = MongoUtil.find("bind",query);
        if(docs == null){
            return null;
        }
        if(docs.size() == 0){
            return null;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！绑定ID重复！");
        }
        return docs.get(0);
    }

    public static Document getBindByName(String name){
        BasicDBObject query = new BasicDBObject();
        query.put("playerName",name);
        List<Document> docs = MongoUtil.find("bind",query);
        if(docs == null){
            return null;
        }
        if(docs.size() == 0){
            return null;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！玩家名重复！");
        }
        return docs.get(0);
    }

    public static boolean deleteBindByName(String name){
        BasicDBObject query = new BasicDBObject();
        query.put("playerName",name);
        List<Document> docs = MongoUtil.find("bind",query);
        if(docs == null){
            return false;
        }
        if(docs.size() == 0){
            return false;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！玩家名重复！");
        }
        return MongoUtil.deleteOne("bind",query);
    }

    public static boolean deleteBindById(long id){
        BasicDBObject query = new BasicDBObject();
        query.put("bindId",id);
        List<Document> docs = MongoUtil.find("bind",query);
        if(docs == null){
            return false;
        }
        if(docs.size() == 0){
            return false;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！绑定ID重复！");
        }
        return MongoUtil.deleteOne("bind",query);
    }
}
