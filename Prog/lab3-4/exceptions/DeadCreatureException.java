package exceptions;

public class DeadCreatureException extends Exception {
    private final String message;

    public DeadCreatureException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "DeadCreatureException: " + message;
    }
}