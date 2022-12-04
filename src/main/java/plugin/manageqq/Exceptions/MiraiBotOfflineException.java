package plugin.manageqq.Exceptions;

public class MiraiBotOfflineException extends Exception{

    private final long botId;

    public MiraiBotOfflineException(long botId){
        super("Bot is offline");
        this.botId = botId;
    }

    public long getBotId(){
        return botId;
    }
}