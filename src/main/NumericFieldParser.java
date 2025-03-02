package main;

import java.util.Set;
import java.util.TreeSet;

class NumericFieldParser implements FieldParser {
    @Override
    public Object parse(String expression, CronFieldType fieldType) {
        Set<Integer> values = new TreeSet<>();
        
        if (expression.equals(CronConstants.WILDCARD)) {
            return parseWildcard(fieldType);
        }
        
        String[] parts = expression.split(CronConstants.COMMA);
        for (String part : parts) {
            if (part.contains(CronConstants.SLASH)) {
                parseStepExpression(part, fieldType, values);
            } else if (part.contains(CronConstants.HYPHEN)) {
                parseRangeExpression(part, fieldType, values);
            } else {
                parseSingleValue(part, fieldType, values);
            }
        }

        return values;
    }
    
    private Set<Integer> parseWildcard(CronFieldType fieldType) {
        Set<Integer> values = new TreeSet<>();
        for (int i = fieldType.getMin(); i <= fieldType.getMax(); i++) {
            values.add(i);
        }
        return values;
    }

    private void parseStepExpression(String part, CronFieldType fieldType, Set<Integer> values) {
        // Handles step expression like this:
        //"*/15" (every 15th value starting from min)
        //"5/15" (every 15th value starting from 5)
        //"10-40/15" (every 15th value from 10 to 40)
        String[] stepParts = part.split(CronConstants.SLASH);
        CronValidator.validateStepFormat(stepParts, part);

        String baseExpression = stepParts[0];
        try {
            int interval = Integer.parseInt(stepParts[1]);
            CronValidator.validatePositiveValue(interval, "Step value");

            int start, end;
            if (baseExpression.equals(CronConstants.WILDCARD)) {
                start = fieldType.getMin();
                end = fieldType.getMax();
            } else if (baseExpression.contains(CronConstants.HYPHEN)) {
                // Handle range with step
                String[] rangeParts = baseExpression.split(CronConstants.HYPHEN);
                CronValidator.validateRangeFormat(rangeParts, baseExpression);
                
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
    }

    private void parseRangeExpression(String part, CronFieldType fieldType, Set<Integer> values) {
        // Handle range without step like "10-40"
        String[] rangeParts = part.split(CronConstants.HYPHEN);
        CronValidator.validateRangeFormat(rangeParts, part);
        
        try {
            int start = Integer.parseInt(rangeParts[0]);
            int end = Integer.parseInt(rangeParts[1]);
            CronValidator.validateRange(start, end, fieldType.getMin(), fieldType.getMax(), fieldType.getLabel());
            
            for (int i = start; i <= end; i++) {
                values.add(i);
            }
        } catch (NumberFormatException e) {
            throw new CronParseException("Invalid range values in: " + part);
        }
    }
    
    private void parseSingleValue(String part, CronFieldType fieldType, Set<Integer> values) {
        // Handle single value like "5"
        try {
            int value = Integer.parseInt(part);
            CronValidator.validateValue(value, fieldType.getMin(), fieldType.getMax(), fieldType.getLabel());
            values.add(value);
        } catch (NumberFormatException e) {
            throw new CronParseException("Invalid numeric value: " + part);
        }
    }
}