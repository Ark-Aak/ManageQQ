package plugin.manageqq;

import org.bson.Document;
import plugin.manageqq.database.MongoUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class cave {
    public static void addCave(String content,String user){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        String dt = df.format(date);
        MongoUtil.createCollection("cave");
        MongoUtil.insertOne("cave",new Document("sender",user).append("content",content).append("date",dt));
    }

    public static String getCave(){
        Random rd = new Random();
        return Objects.requireNonNull(MongoUtil.find("cave")).get(rd.nextInt(Objects.requireNonNull(MongoUtil.find("cave")).size())).getString("content");
    }
}
