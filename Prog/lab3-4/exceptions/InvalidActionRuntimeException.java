package exceptions;

public class InvalidActionRuntimeException extends RuntimeException {
    private final String message;

    public InvalidActionRuntimeException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "InvalidActionRuntimeException: " + message;
    }
}