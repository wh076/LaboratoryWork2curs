package exceptions;

public class ZeroValueException extends RuntimeException {
    public ZeroValueException(String message) {
        super(message);
    }
}