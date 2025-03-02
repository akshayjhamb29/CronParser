public class CronConstants {
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String SLASH = "/";
    public static final String WILDCARD = "*";
    public static final String SECOND_LABEL = "second";
    public static final String MINUTE_LABEL = "minute";
    public static final String HOUR_LABEL = "hour";
    public static final String DAY_OF_MONTH_LABEL = "day of month";
    public static final String MONTH_LABEL = "month";
    public static final String DAY_OF_WEEK_LABEL = "day of week";
    public static final String COMMAND_LABEL = "command";
    
    public static final String USAGE_MESSAGE = "Usage: java CronParser \"<cron expression>\"";
    public static final String ERROR_MESSAGE = "Error parsing cron expression: ";
    public static final String INVALID_CRON_FORMAT = "Invalid cron format. Expected 5 time fields + command.";
    public static final int PARTS_LENGTH = 6;
}
