package collection;

import model.Flat;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Интерфейс для работы с коллекцией квартир.
 *
 * <p>Команды работают через этот интерфейс, поэтому им не важно, как именно
 * внутри хранится коллекция.</p>
 */
public interface FlatCollection {
    /**
     * Возвращает имя класса используемой коллекции.
     *
     * @return тип коллекции
     */
    String getCollectionType();

    /**
     * Возвращает дату инициализации коллекции.
     *
     * @return дата инициализации
     */
    ZonedDateTime getInitializationDate();

    /**
     * Возвращает количество элементов.
     *
     * @return размер коллекции
     */
    int size();

    /**
     * Проверяет, пуста ли коллекция.
     *
     * @return {@code true}, если коллекция пуста
     */
    boolean isEmpty();

    /**
     * Генерирует новый уникальный положительный id.
     *
     * @return новый id
     */
    long generateId();

    /**
     * Добавляет элемент в коллекцию.
     *
     * @param flat квартира
     */
    void add(Flat flat);

    /**
     * Обновляет элемент по id.
     *
     * @param id идентификатор элемента
     * @param updated новое значение элемента
     * @return {@code true}, если элемент найден
     */
    boolean update(long id, Flat updated);

    /**
     * Удаляет элемент по id.
     *
     * @param id идентификатор
     * @return удаленный элемент или {@code null}
     */
    Flat removeById(long id);

    /**
     * Очищает коллекцию.
     */
    void clear();

    /**
     * Возвращает первый элемент очереди.
     *
     * @return первый элемент или {@code null}
     */
    Flat head();

    /**
     * Возвращает и удаляет первый элемент очереди.
     *
     * @return удаленный первый элемент или {@code null}
     */
    Flat removeHead();

    /**
     * Ищет элемент по id.
     *
     * @param id идентификатор
     * @return найденный элемент или {@code null}
     */
    Flat getById(long id);

    /**
     * Возвращает элементы в порядке сортировки по умолчанию.
     *
     * @return отсортированные элементы
     */
    List<Flat> sortedElements();

    /**
     * Возвращает элемент с минимальным centralHeating.
     *
     * @return найденный элемент или {@code null}
     */
    Flat minByCentralHeating();

    /**
     * Считает элементы, у которых centralHeating меньше заданного.
     *
     * @param centralHeating значение для сравнения
     * @return количество элементов
     */
    long countLessThanCentralHeating(boolean centralHeating);

    /**
     * Находит элементы, имя которых содержит подстроку.
     *
     * @param name подстрока
     * @return список найденных элементов
     */
    List<Flat> filterContainsName(String name);
}
