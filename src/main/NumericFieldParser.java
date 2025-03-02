package main;

import java.util.Set;
import java.util.TreeSet;

class NumericFieldParser implements FieldParser {
    @Override
    public Object parse(String expression, CronFieldType fieldType) {
        Set<Integer> values = new TreeSet<>();
        if (expression.equals(CronConstants.WILDCARD)) {
            for (int i = fieldType.getMin(); i <= fieldType.getMax(); i++) {
                values.add(i);
            }
            return values;
        }
        String[] parts = expression.split(CronConstants.COMMA);
        for (String part : parts) {
            if (part.contains(CronConstants.SLASH)) {
                // Handles step expression like this "10-40/10"
                String[] stepParts = part.split(CronConstants.SLASH);
                if (stepParts.length != 2 || stepParts[0].isEmpty() || stepParts[1].isEmpty()) {
                    throw new CronParseException("Invalid step format: " + part);
                }

                String baseExpression = stepParts[0];
                try {
                    int interval = Integer.parseInt(stepParts[1]);
                    if (interval <= 0) {
                        throw new CronParseException("Step value must be positive: " + interval);
                    }

                    int start, end;
                    if (baseExpression.equals(CronConstants.WILDCARD)) {
                        start = fieldType.getMin();
                        end = fieldType.getMax();
                    } else if (baseExpression.contains(CronConstants.HYPHEN)) {
                        // Handle range with step
                        String[] rangeParts = baseExpression.split(CronConstants.HYPHEN);
                        if (rangeParts.length != 2 || rangeParts[0].isEmpty() || rangeParts[1].isEmpty()) {
                            throw new CronParseException("Invalid range format: " + baseExpression);
                        }
                        try {
                            start = Integer.parseInt(rangeParts[0]);
                            end = Integer.parseInt(rangeParts[1]);
                        } catch (NumberFormatException e) {
                            throw new CronParseException("Invalid range values in: " + baseExpression);
                        }
                    } else {
                        start = Integer.parseInt(baseExpression);
                        end = fieldType.getMax();
                    }

                    CronValidator.validateRange(start, end, fieldType.getMin(), fieldType.getMax(), fieldType.getLabel());
                    for (int i = start; i <= end; i += interval) {
                        values.add(i);
                    }
                } catch (NumberFormatException e) {
                    throw new CronParseException("Invalid step value: " + stepParts[1]);
                }

            } else if (part.contains(CronConstants.HYPHEN)) {
                // Handle range without step like "10-40"
                String[] rangeParts = part.split(CronConstants.HYPHEN);
                if (rangeParts.length != 2 || rangeParts[0].isEmpty() || rangeParts[1].isEmpty()) {
                    throw new CronParseException("Invalid range format: " + part);
                }
                int start = Integer.parseInt(rangeParts[0]);
                int end = Integer.parseInt(rangeParts[1]);
                CronValidator.validateRange(start, end, fieldType.getMin(), fieldType.getMax(), fieldType.getLabel());
                for (int i = start; i <= end; i++) {
                    values.add(i);
                }
            } else {
                // Handle single value like "5"
                int value = Integer.parseInt(part);
                CronValidator.validateValue(value, fieldType.getMin(), fieldType.getMax(), fieldType.getLabel());
                values.add(value);
            }
        }

        return values;
    }
}