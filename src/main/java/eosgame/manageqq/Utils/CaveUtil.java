package eosgame.manageqq.Utils;

import org.bson.Document;
import eosgame.manageqq.Databases.MongoUtil;
import eosgame.manageqq.ManageQQ;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class CaveUtil {
    public static void addCave(String content,String user,long id){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        String dt = df.format(date);
        MongoUtil.createCollection("cave");
        if(!MongoUtil.insertOne("cave",new Document("sender",user).append("content",content).append("senderId",id).append("date",dt))){
            ManageQQ.log.warning("数据库出现错误...");
        }
    }

    public static Document getCave(){
        Random rd = new Random();
        return Objects.requireNonNull(MongoUtil.find("cave")).get(rd.nextInt(Objects.requireNonNull(MongoUtil.find("cave")).size()));
    }
}
