package lwgame.manageqq.Utils;

import com.mongodb.BasicDBObject;
import lwgame.manageqq.Configs.InvitationConfig;
import lwgame.manageqq.Configs.MiraiConfig;
import lwgame.manageqq.Databases.MongoUtil;
import lwgame.manageqq.Logger;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserUtil {

    public static boolean addCoin(long user,long amount){
        addUser(user);
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return false;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return false;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        Document from = docs.get(0);
        Document to = new Document("$set",new Document("balance",(long)from.get("balance")+amount));
        return MongoUtil.updateOne("user",from,to);
    }

    public static long getCoin(long user){
        addUser(user);
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return 0;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return 0;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        Document doc = docs.get(0);
        return (long) doc.get("balance");
    }

    public static boolean hasUser(long user){
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        return MongoUtil.find("user", query) != null && Objects.requireNonNull(MongoUtil.find("user", query)).size() != 0;
    }

    public static void addUser(long user){
        if(hasUser(user)){
            return;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        String dt = df.format(date);
        Document doc = new Document();
        doc = doc.append("username",user)
                .append("date",dt)
                .append("balance",0L)
                .append("score",100)
                .append("lastSignIn",0L)
                .append("invitationCode",StringUtil.getRandomString(InvitationConfig.getCodeLength()))
                .append("invitationTime",0L)
                .append("hasInvited", 1);
        MongoUtil.insertOne("user",doc);
    }

    public static void setSignInTime(long user){
        addUser(user);
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        Document from = docs.get(0);
        Document to = new Document("$set",new Document("lastSignIn",new Date().getTime()));
        MongoUtil.updateOne("user",from,to);
    }

    public static long getSignInTime(long user){
        addUser(user);
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return 0;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return 0;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        Document doc = docs.get(0);
        return (long) doc.get("lastSignIn");
    }

    public static boolean canSignIn(long user){
        addUser(user);
        long now = new Date().getTime();
        long last = getSignInTime(user);
        return now - last >= MiraiConfig.getSignInTimeLimit();
    }

    public static void generateInvitationCode(long user){
        addUser(user);
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        Document from = docs.get(0);
        Document to = new Document("$set",new Document("invitationCode",StringUtil.getRandomString(InvitationConfig.getCodeLength())));
        MongoUtil.updateOne("user",from,to);
    }

    public static String getInvitationCode(long user){
        addUser(user);
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return null;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return null;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        Document doc = docs.get(0);
        return (String) doc.get("invitationCode");
    }

    public static void setInvitationTime(long user,long val){
        addUser(user);
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        Document from = docs.get(0);
        Document to = new Document("$set",new Document("invitationTime",val));
        MongoUtil.updateOne("user",from,to);
    }

    public static long getInvitationTime(long user){
        addUser(user);
        BasicDBObject query = new BasicDBObject();
        query.put("username",user);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return 0;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return 0;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        Document doc = docs.get(0);
        return (long) doc.get("invitationTime");
    }

    public static Document getUserByCode(String code){
        BasicDBObject query = new BasicDBObject();
        query.put("invitationCode",code);
        List<Document> docs = MongoUtil.find("user",query);
        if(docs == null){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return null;
        }
        if(docs.size() == 0){
            Logger.warn("警告！数据库可能出现错误！找不到玩家数据！");
            return null;
        }
        if(docs.size() != 1){
            Logger.warn("警告！数据库可能出现错误！用户名重复！");
        }
        return docs.get(0);
    }
}
