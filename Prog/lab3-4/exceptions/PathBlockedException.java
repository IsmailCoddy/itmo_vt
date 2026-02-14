package exceptions;

public class PathBlockedException extends Exception {
    private final String message;

    public PathBlockedException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "PathBlockedException: " + message;
    }
}