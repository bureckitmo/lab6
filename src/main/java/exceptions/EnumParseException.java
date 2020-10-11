package exceptions;

public class EnumParseException extends RuntimeException  {

    public EnumParseException() { }

    public EnumParseException(String message) {
        super("For input string: " + "\"" + message + "\"");
    }
}
