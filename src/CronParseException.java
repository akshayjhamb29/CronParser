class CronParseException extends RuntimeException {
    public CronParseException(String message) {
        super(message);
    }

    public CronParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
