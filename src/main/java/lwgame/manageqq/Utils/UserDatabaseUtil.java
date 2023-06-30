package lwgame.manageqq.Utils;

import lwgame.manageqq.Databases.MongoUtil;
import lwgame.manageqq.Mirai.MiraiUtil;

import java.util.ArrayList;

import static lwgame.manageqq.ManageQQ.session;

public class UserDatabaseUtil {
    public static void clear(){
        MongoUtil.dropCollection("user");
    }

    public static void rebuild(long groupId){
        ArrayList<Long> memberIds = MiraiUtil.getMemberList(session, groupId);
        for(long memberId : memberIds){
            UserUtil.addUser(memberId);
        }
    }
}
