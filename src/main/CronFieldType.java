package main;

enum CronFieldType {
    //SECOND(main.CronConstants.SECOND_LABEL,0 ,59 ),
    MINUTE(CronConstants.MINUTE_LABEL, 0, 59),
    HOUR(CronConstants.HOUR_LABEL, 0, 23),
    DAY_OF_MONTH(CronConstants.DAY_OF_MONTH_LABEL, 1, 31),
    MONTH(CronConstants.MONTH_LABEL, 1, 12),
    DAY_OF_WEEK(CronConstants.DAY_OF_WEEK_LABEL, 0, 6),
    COMMAND(CronConstants.COMMAND_LABEL, -1, -1);


    private final String label;
    private final int min;
    private final int max;

    CronFieldType(String label, int min, int max) {
        this.label = label;
        this.min = min;
        this.max = max;
    }

    public String getLabel() {
        return label;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}