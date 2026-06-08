package storage;

/**
 * Ошибка при работе с файлом коллекции.
 */
public class StorageException extends Exception {
    /**
     * Создает ошибку хранилища.
     *
     * @param message описание ошибки
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Создает ошибку хранилища с исходной причиной.
     *
     * @param message описание ошибки
     * @param cause исходная ошибка
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
