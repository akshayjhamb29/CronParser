package main;

public class CronValidationException extends CronParseException {
    public CronValidationException(String message) {
        super(message);
    }
}
