import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;

public class CronParserTest {
    private CronParser cronParser;

    @Before
    public void setUp() {
        cronParser = new CronParser();
    }

    @Test
    public void testParseWithValidCronExpression() {
        CronExpressionResult result = cronParser.parse("*/15 0 1,15 * 1-5 /usr/bin/find");

        Set<Integer> expectedMinutes = new HashSet<>(Arrays.asList(0, 15, 30, 45));
        Set<Integer> expectedHours = new HashSet<>(Arrays.asList(0));
        Set<Integer> expectedDayOfMonth = new HashSet<>(Arrays.asList(1, 15));
        Set<Integer> expectedMonth = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        Set<Integer> expectedDayOfWeek = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        assertEquals(expectedMinutes, result.getMinutes());
        assertEquals(expectedHours, result.getHours());
        assertEquals(expectedDayOfMonth, result.getDaysOfMonth());
        assertEquals(expectedMonth, result.getMonths());
        assertEquals(expectedDayOfWeek, result.getDaysOfWeek());
        assertEquals("/usr/bin/find", result.getCommand());
    }

    @Test
    public void testParseWithComplexCronExpression() {
        CronExpressionResult result = cronParser.parse("1-59/10 1-23/10 1-23/5 * * /usr/bin/find");

        Set<Integer> expectedMinutes = new HashSet<>(Arrays.asList(1, 11, 21, 31, 41, 51));
        Set<Integer> expectedHours = new HashSet<>(Arrays.asList(1, 11, 21));
        Set<Integer> expectedDayOfMonth = new HashSet<>(Arrays.asList(1, 6, 11, 16, 21));
        Set<Integer> expectedMonth = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        Set<Integer> expectedDayOfWeek = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));

        assertEquals(expectedMinutes, result.getMinutes());
        assertEquals(expectedHours, result.getHours());
        assertEquals(expectedDayOfMonth, result.getDaysOfMonth());
        assertEquals(expectedMonth, result.getMonths());
        assertEquals(expectedDayOfWeek, result.getDaysOfWeek());
        assertEquals("/usr/bin/find", result.getCommand());
    }

    @Test
    public void testParseWithWildcard() {
        CronExpressionResult result = cronParser.parse("* * * * * /usr/bin/find");

        Set<Integer> expectedMinutes = new TreeSet<>();
        for (int i = 0; i <= 59; i++) expectedMinutes.add(i);

        Set<Integer> expectedHours = new TreeSet<>();
        for (int i = 0; i <= 23; i++) expectedHours.add(i);

        Set<Integer> expectedDayOfMonth = new TreeSet<>();
        for (int i = 1; i <= 31; i++) expectedDayOfMonth.add(i);

        Set<Integer> expectedMonths = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        Set<Integer> expectedDayOfWeek = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));

        assertEquals(expectedMinutes, result.getMinutes());
        assertEquals(expectedHours, result.getHours());
        assertEquals(expectedDayOfMonth, result.getDaysOfMonth());
        assertEquals(expectedMonths, result.getMonths());
        assertEquals(expectedDayOfWeek, result.getDaysOfWeek());
        assertEquals("/usr/bin/find", result.getCommand());
    }

    @Test
    public void testParseWithRange() {
        CronExpressionResult result = cronParser.parse("0 1-5 * * * /usr/bin/find");

        Set<Integer> expectedMinutes = new HashSet<>(Arrays.asList(0));
        Set<Integer> expectedHours = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> expectedDayOfMonth = new TreeSet<>();
        for (int i = 1; i <= 31; i++) expectedDayOfMonth.add(i);
        Set<Integer> expectedMonth = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        Set<Integer> expectedDayOfWeek = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));

        assertEquals(expectedMinutes, result.getMinutes());
        assertEquals(expectedHours, result.getHours());
        assertEquals(expectedDayOfMonth, result.getDaysOfMonth());
        assertEquals(expectedMonth, result.getMonths());
        assertEquals(expectedDayOfWeek, result.getDaysOfWeek());
        assertEquals("/usr/bin/find", result.getCommand());
    }

    @Test
    public void testParseWithList() {
        CronExpressionResult result = cronParser.parse("0 1,3,5 * * * /usr/bin/find");

        Set<Integer> expectedMinutes = new HashSet<>(Arrays.asList(0));
        Set<Integer> expectedHours = new HashSet<>(Arrays.asList(1, 3, 5));
        Set<Integer> expectedDayOfMonth = new TreeSet<>();
        for (int i = 1; i <= 31; i++) expectedDayOfMonth.add(i);
        Set<Integer> expectedMonth = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        Set<Integer> expectedDayOfWeek = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));

        assertEquals(expectedMinutes, result.getMinutes());
        assertEquals(expectedHours, result.getHours());
        assertEquals(expectedDayOfMonth, result.getDaysOfMonth());
        assertEquals(expectedMonth, result.getMonths());
        assertEquals(expectedDayOfWeek, result.getDaysOfWeek());
        assertEquals("/usr/bin/find", result.getCommand());
    }

    @Test
    public void testParseWithStep() {
        CronExpressionResult result = cronParser.parse("*/15 * * * * /usr/bin/find");

        Set<Integer> expectedMinutes = new HashSet<>(Arrays.asList(0, 15, 30, 45));
        Set<Integer> expectedHours = new TreeSet<>();
        for (int i = 0; i <= 23; i++) expectedHours.add(i);
        Set<Integer> expectedDayOfMonth = new TreeSet<>();
        for (int i = 1; i <= 31; i++) expectedDayOfMonth.add(i);
        Set<Integer> expectedMonth = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        Set<Integer> expectedDayOfWeek = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));

        assertEquals(expectedMinutes, result.getMinutes());
        assertEquals(expectedHours, result.getHours());
        assertEquals(expectedDayOfMonth, result.getDaysOfMonth());
        assertEquals(expectedMonth, result.getMonths());
        assertEquals(expectedDayOfWeek, result.getDaysOfWeek());
        assertEquals("/usr/bin/find", result.getCommand());
    }

    @Test
    public void testParseWithStepFromNonZero() {
        CronExpressionResult result = cronParser.parse("5/15 * * * * /usr/bin/find");

        Set<Integer> expectedMinutes = new HashSet<>(Arrays.asList(5, 20, 35, 50));
        Set<Integer> expectedHours = new TreeSet<>();
        for (int i = 0; i <= 23; i++) expectedHours.add(i);
        Set<Integer> expectedDayOfMonth = new TreeSet<>();
        for (int i = 1; i <= 31; i++) expectedDayOfMonth.add(i);
        Set<Integer> expectedMonth = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        Set<Integer> expectedDayOfWeek = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));

        assertEquals(expectedMinutes, result.getMinutes());
        assertEquals(expectedHours, result.getHours());
        assertEquals(expectedDayOfMonth, result.getDaysOfMonth());
        assertEquals(expectedMonth, result.getMonths());
        assertEquals(expectedDayOfWeek, result.getDaysOfWeek());
        assertEquals("/usr/bin/find", result.getCommand());
    }

    @Test(expected = CronParseException.class)
    public void testParseWithTooFewFields() {
        cronParser.parse("* * * * /usr/bin/find");
    }

    @Test(expected = NumberFormatException.class)
    public void testParseWithInvalidMinuteFormat() {
        cronParser.parse("a * * * * /usr/bin/find");
    }

    @Test(expected = CronValidationException.class)
    public void testParseWithOutOfRangeMinute() {
        cronParser.parse("60 * * * * /usr/bin/find");
    }

    @Test(expected = CronValidationException.class)
    public void testParseWithOutOfRangeHour() {
        cronParser.parse("* 24 * * * /usr/bin/find");
    }

    @Test(expected = CronValidationException.class)
    public void testParseWithOutOfRangeWeek() {
        cronParser.parse("* * * * 1-8 /usr/bin/find");
    }
    @Test(expected = CronParseException.class)
    public void testParseWithEmptyString() {
        cronParser.parse(" ");
    }

    @Test(expected = CronValidationException.class)
    public void testParseWithInvalidRangeOrder() {
        cronParser.parse("30-10 * * * * /usr/bin/find");
    }

    @Test(expected = CronParseException.class)
    public void testParseWithIncompleteRange() {
        cronParser.parse("1- * * * * /usr/bin/find");
    }
}
