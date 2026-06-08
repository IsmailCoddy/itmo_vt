package input;

/**
 * Ошибка при чтении ввода.
 *
 * <p>Например, если в скрипте начался `add`, но полей для квартиры не хватило.</p>
 */
public class InputReadException extends Exception {
    /**
     * Создает ошибку чтения.
     *
     * @param message описание ошибки
     */
    public InputReadException(String message) {
        super(message);
    }
}
