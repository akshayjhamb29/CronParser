package main;

import java.util.Set;
import java.util.TreeSet;


public class CronParser {
    private static final CronFieldType[] FIELD_ORDER = {
            //main.CronFieldType.SECOND,
            CronFieldType.MINUTE,
            CronFieldType.HOUR,
            CronFieldType.DAY_OF_MONTH,
            CronFieldType.MONTH,
            CronFieldType.DAY_OF_WEEK,
            CronFieldType.COMMAND
    };

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(CronConstants.USAGE_MESSAGE);
            return;
        }

        try {
             String cronExpression = args[0];
//            //String cronExpression = "1-59/10 1-23/10 1-23/5 * * /usr/bin/find";
//            String cronExpression = "* * * * *  /usr/bin/find rm-rf";
            CronExpressionResult result = new CronParser().parse(cronExpression);
            System.out.println(result);
        } catch (CronParseException ex) {
            System.out.println(CronConstants.ERROR_MESSAGE + ex.getMessage());
        }
    }

    public CronExpressionResult parse(String cronExpression) {
        String[] parts = cronExpression.trim().split("\\s+");
        if (parts.length != CronConstants.PARTS_LENGTH) {
            throw new CronParseException(CronConstants.INVALID_CRON_FORMAT);
        }

        Set<Integer> minutes = new TreeSet<>();
        Set<Integer> hours = new TreeSet<>();
        Set<Integer> daysOfMonth = new TreeSet<>();
        Set<Integer> months = new TreeSet<>();
        Set<Integer> daysOfWeek = new TreeSet<>();
        String command = "";

        for (int i = 0; i < CronConstants.PARTS_LENGTH; i++) {
            CronFieldType type = FIELD_ORDER[i];
            FieldParser parser = FieldParserFactory.getParser(type);
            Object parsedValue = parser.parse(parts[i], type);

            if (type == CronFieldType.COMMAND) {
                command = (String) parsedValue;
            } else {
                @SuppressWarnings("unchecked")
                Set<Integer> values = (Set<Integer>) parsedValue;
                switch (type) {
                    case MINUTE:
                        minutes.addAll(values);
                        break;
                    case HOUR:
                        hours.addAll(values);
                        break;
                    case DAY_OF_MONTH:
                        daysOfMonth.addAll(values);
                        break;
                    case MONTH:
                        months.addAll(values);
                        break;
                    case DAY_OF_WEEK:
                        daysOfWeek.addAll(values);
                        break;
                }
            }
        }
        return new CronExpressionResult(minutes, hours, daysOfMonth, months, daysOfWeek, command);
    }
}