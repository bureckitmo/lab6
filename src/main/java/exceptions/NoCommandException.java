package exceptions;

public class NoCommandException extends RuntimeException {
    public NoCommandException(String message) {
        super(message);
    }
}
