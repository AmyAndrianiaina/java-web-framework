package exception;

public class UrlNotFoundException extends Exception {
    public UrlNotFoundException() {
        super("Url Not Found");
    }

    public UrlNotFoundException(String message) {
        super(message);
    }
}