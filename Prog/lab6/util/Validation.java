package util;


public final class Validation {
    private Validation() {
    }


    public static <T> T requireNotNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }


    public static String requireNotBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }


    public static long requireGreaterThan(long value, long minExclusive, String message) {
        if (value <= minExclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }


    public static Integer requireIntegerGreaterThan(Integer value, int minExclusive, String message) {
        requireNotNull(value, message);
        if (value <= minExclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }


    public static Integer requireNullableIntegerGreaterThan(Integer value, int minExclusive, String message) {
        if (value != null && value <= minExclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }


    public static Long requireLongGreaterThan(Long value, long minExclusive, String message) {
        requireNotNull(value, message);
        if (value <= minExclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
