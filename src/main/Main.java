package main;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(CronConstants.SINGLE_ARGUMENT_NOT_PASSED);
            return;
        }

        try {
            String cronExpression = args[0];
            //String cronExpression = "1-59/10 1-23/10 1-23/5 * * /usr/bin/find";
            //String cronExpression = "* * * * *  /usr/bin/find rm-rf";
            CronExpressionResult result = new CronParser().parse(cronExpression);
            System.out.println(result);
        } catch (CronParseException ex) {
            System.out.println(CronConstants.ERROR_MESSAGE + ex.getMessage());
        }
    }
}