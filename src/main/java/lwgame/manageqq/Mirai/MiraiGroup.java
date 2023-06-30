package lwgame.manageqq.Mirai;

import lwgame.manageqq.Network.Json;

public class MiraiGroup {

    private final long id;
    private final String name;

    public MiraiGroup(Json resource) {
        id = resource.getLong("id");
        name = resource.getString("id");
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
