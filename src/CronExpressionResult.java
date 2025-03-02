import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CronExpressionResult {
    private final Set<Integer> minutes;
    private final Set<Integer> hours;
    private final Set<Integer> daysOfMonth;
    private final Set<Integer> months;
    private final Set<Integer> daysOfWeek;
    private final String command;

    public CronExpressionResult(Set<Integer> minutes, Set<Integer> hours, Set<Integer> daysOfMonth,
                                Set<Integer> months, Set<Integer> daysOfWeek, String command) {
        this.minutes = new TreeSet<>(minutes);
        this.hours = new TreeSet<>(hours);
        this.daysOfMonth = new TreeSet<>(daysOfMonth);
        this.months = new TreeSet<>(months);
        this.daysOfWeek = new TreeSet<>(daysOfWeek);
        this.command = command;
    }

    public Set<Integer> getMinutes() { return minutes; }
    public Set<Integer> getHours() { return hours; }
    public Set<Integer> getDaysOfMonth() { return daysOfMonth; }
    public Set<Integer> getMonths() { return months; }
    public Set<Integer> getDaysOfWeek() { return daysOfWeek; }
    public String getCommand() { return command; }

    @Override
    public String toString() {
        return String.format("%-14s%s%n", "minute", formatSet(minutes)) +
                String.format("%-14s%s%n", "hour", formatSet(hours)) +
                String.format("%-14s%s%n", "day of month", formatSet(daysOfMonth)) +
                String.format("%-14s%s%n", "month", formatSet(months)) +
                String.format("%-14s%s%n", "day of week", formatSet(daysOfWeek)) +
                String.format("%-14s%s%n", "command", command);
    }

    private String formatSet(Set<Integer> values) {
        return values.stream().map(String::valueOf).collect(Collectors.joining(" "));
    }
}