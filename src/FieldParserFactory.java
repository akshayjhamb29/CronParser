class FieldParserFactory {
    private static final FieldParser NUMERIC_PARSER = new NumericFieldParser();
    private static final FieldParser COMMAND_PARSER = new CommandFieldParser();

    private FieldParserFactory() {
    }

    public static FieldParser getParser(CronFieldType fieldType) {
        if (fieldType == CronFieldType.COMMAND) {
            return COMMAND_PARSER;
        } else {
            return NUMERIC_PARSER;
        }
    }
}
