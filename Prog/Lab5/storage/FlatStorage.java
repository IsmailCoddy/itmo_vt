package storage;

import model.Flat;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс для сохранения и загрузки квартир.
 *
 * <p>Сейчас реализация одна - JSON-файл, но через интерфейс код получается
 * аккуратнее.</p>
 */
public interface FlatStorage {
    /**
     * Возвращает имя файла или другой идентификатор хранилища.
     *
     * @return имя хранилища
     */
    String getFileName();

    /**
     * Загружает элементы из хранилища.
     *
     * @return список корректно прочитанных элементов
     */
    List<Flat> load();

    /**
     * Сохраняет элементы в хранилище.
     *
     * @param flats элементы для сохранения
     * @throws StorageException если сохранить не удалось
     */
    void save(Collection<Flat> flats) throws StorageException;
}
