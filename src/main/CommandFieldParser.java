package main;

public class CommandFieldParser implements FieldParser {

    @Override
    public Object parse(String expression, CronFieldType fieldType) {
        return expression;
    }
}
