public final class CronValidator {
    private CronValidator() {
    }
    public static void validateRange(int start, int end, int min, int max, String fieldLabel) {
        if (start < min || start > max || end < min || end > max) {
            throw new CronValidationException(
                    String.format("Invalid range %d-%d for field '%s'. Allowed range is %d-%d", start, end, fieldLabel, min, max));
        }
        if (start > end) {
            throw new CronValidationException(String.format("Invalid range %d-%d for field '%s'. Start cannot be greater than end", start, end, fieldLabel));
        }
    }

    public static void validateValue(int value, int min, int max, String fieldLabel) {
        if (value < min || value > max) {
            throw new CronValidationException(String.format("Value %d is out of range for field '%s'. Allowed range is %d-%d", value, fieldLabel, min, max)
            );
        }

    }
}