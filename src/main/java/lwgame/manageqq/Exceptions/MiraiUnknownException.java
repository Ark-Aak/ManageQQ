package lwgame.manageqq.Exceptions;

public class MiraiUnknownException extends Exception{

    private final int code;

    public MiraiUnknownException(int code){
        super("Unknown Error:" + code);
        this.code = code;
    }

    public int getErrorCode(){
        return code;
    }
}
