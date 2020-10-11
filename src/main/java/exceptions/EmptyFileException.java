package exceptions;

import java.io.IOException;

public class EmptyFileException extends IOException {

    public EmptyFileException() { }

    public EmptyFileException(String message) {
        super(message);
    }
}