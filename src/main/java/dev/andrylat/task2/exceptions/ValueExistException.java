package dev.andrylat.task2.exceptions;

public class ValueExistException extends RuntimeException {

    public ValueExistException() {
        super();
    }

    public ValueExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueExistException(String message) {
        super(message);
    }
}
