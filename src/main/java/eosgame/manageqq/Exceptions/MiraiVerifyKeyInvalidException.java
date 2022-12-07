package eosgame.manageqq.Exceptions;

public class MiraiVerifyKeyInvalidException extends Exception{

    private final String verifyKey;

    public MiraiVerifyKeyInvalidException(String verifyKey){
        super("The verify key is invalid");
        this.verifyKey = verifyKey;
    }

    public String getVerifyKey(){
        return verifyKey;
    }
}
