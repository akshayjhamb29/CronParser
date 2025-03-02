package main;

interface FieldParser {
    Object parse(String expression, CronFieldType fieldType);
}
