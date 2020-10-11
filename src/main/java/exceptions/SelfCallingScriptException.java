package exceptions;

public class SelfCallingScriptException extends RuntimeException {
    public SelfCallingScriptException(String msg){
        super(msg);
    }
}