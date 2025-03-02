package main;

import java.util.Set;
import java.util.TreeSet;


public class CronParser {

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
            CronFieldType type = CronConstants.FIELD_ORDER[i];
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