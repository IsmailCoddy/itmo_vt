package util;

/**
 * Простые проверки для полей классов.
 *
 * <p>Я вынес их сюда, чтобы в моделях не повторять одни и те же условия.</p>
 */
public final class Validation {
    private Validation() {
    }

    /**
     * Проверяет, что значение не равно {@code null}.
     *
     * @param value проверяемое значение
     * @param message сообщение об ошибке
     * @param <T> тип значения
     * @return исходное значение
     */
    public static <T> T requireNotNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Проверяет, что строка не равна {@code null} и не является пустой.
     *
     * @param value проверяемая строка
     * @param message сообщение об ошибке
     * @return исходная строка
     */
    public static String requireNotBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Проверяет, что число типа {@code long} больше заданной границы.
     *
     * @param value проверяемое число
     * @param minExclusive строгая нижняя граница
     * @param message сообщение об ошибке
     * @return исходное число
     */
    public static long requireGreaterThan(long value, long minExclusive, String message) {
        if (value <= minExclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Проверяет, что число типа {@code Integer} не равно {@code null} и больше границы.
     *
     * @param value проверяемое число
     * @param minExclusive строгая нижняя граница
     * @param message сообщение об ошибке
     * @return исходное число
     */
    public static Integer requireIntegerGreaterThan(Integer value, int minExclusive, String message) {
        requireNotNull(value, message);
        if (value <= minExclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Проверяет nullable-число типа {@code Integer}: если оно задано, оно должно быть больше границы.
     *
     * @param value проверяемое число или {@code null}
     * @param minExclusive строгая нижняя граница
     * @param message сообщение об ошибке
     * @return исходное число
     */
    public static Integer requireNullableIntegerGreaterThan(Integer value, int minExclusive, String message) {
        if (value != null && value <= minExclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Проверяет, что число типа {@code Long} не равно {@code null} и больше границы.
     *
     * @param value проверяемое число
     * @param minExclusive строгая нижняя граница
     * @param message сообщение об ошибке
     * @return исходное число
     */
    public static Long requireLongGreaterThan(Long value, long minExclusive, String message) {
        requireNotNull(value, message);
        if (value <= minExclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
