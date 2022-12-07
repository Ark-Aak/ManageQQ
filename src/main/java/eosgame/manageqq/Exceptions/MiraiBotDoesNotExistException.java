package eosgame.manageqq.Exceptions;

public class MiraiBotDoesNotExistException extends Exception{

    private final String session;
    private final long botId;

    public MiraiBotDoesNotExistException(String session,long botId){
        super("bot's session is not this session");
        this.session = session;
        this.botId = botId;
    }

    public String getSession(){
        return session;
    }

    public long getBotId(){
        return botId;
    }
}
