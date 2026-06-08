package collection;

import model.Flat;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Класс, который реально хранит квартиры и выполняет операции с ними.
 *
 * <p>Внутри стоит {@link PriorityQueue}, как и написано в задании. Для `show`
 * я делаю копию и сортирую ее, потому что сама очередь при обычном обходе не
 * обещает вывести элементы по порядку.</p>
 */
public class CollectionManager implements FlatCollection {
    private final PriorityQueue<Flat> flats = new PriorityQueue<>();
    private final ZonedDateTime initializationDate = ZonedDateTime.now();
    private long nextId = 1;

    /**
     * Создает менеджер коллекции и заполняет его элементами из файла.
     *
     * @param loadedFlats элементы, прочитанные из внешнего хранилища
     */
    public CollectionManager(Collection<Flat> loadedFlats) {
        Set<Long> usedIds = new HashSet<>();
        long maxId = 0;

        for (Flat flat : loadedFlats) {
            if (usedIds.contains(flat.getId())) {
                System.out.println("Предупреждение: элемент с повторяющимся id " + flat.getId() + " пропущен.");
                continue;
            }
            flats.add(flat);
            usedIds.add(flat.getId());
            maxId = Math.max(maxId, flat.getId());
        }

        nextId = maxId == Long.MAX_VALUE ? 1 : maxId + 1;
    }

    /**
     * Возвращает имя класса используемой коллекции.
     *
     * @return тип коллекции
     */
    public String getCollectionType() {
        return flats.getClass().getName();
    }

    /**
     * Возвращает дату создания менеджера коллекции.
     *
     * @return дата инициализации коллекции
     */
    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    /**
     * Возвращает размер коллекции.
     *
     * @return количество элементов коллекции
     */
    public int size() {
        return flats.size();
    }

    /**
     * Проверяет, пуста ли коллекция.
     *
     * @return {@code true}, если коллекция пуста
     */
    public boolean isEmpty() {
        return flats.isEmpty();
    }

    /**
     * Генерирует новый уникальный идентификатор.
     *
     * @return положительный уникальный id
     * @throws IllegalStateException если свободный положительный id найти не удалось
     */
    public long generateId() {
        long candidate = nextId <= 0 ? 1 : nextId;
        long start = candidate;

        while (containsId(candidate)) {
            candidate = candidate == Long.MAX_VALUE ? 1 : candidate + 1;
            if (candidate == start) {
                throw new IllegalStateException("Не удалось найти свободный положительный id");
            }
        }

        nextId = candidate == Long.MAX_VALUE ? 1 : candidate + 1;
        return candidate;
    }

    /**
     * Добавляет квартиру в коллекцию.
     *
     * @param flat квартира
     * @throws IllegalArgumentException если id уже существует
     */
    public void add(Flat flat) {
        if (containsId(flat.getId())) {
            throw new IllegalArgumentException("Элемент с id " + flat.getId() + " уже существует");
        }
        flats.add(flat);
        if (flat.getId() >= nextId) {
            nextId = flat.getId() == Long.MAX_VALUE ? 1 : flat.getId() + 1;
        }
    }

    /**
     * Обновляет квартиру по id.
     *
     * @param id id обновляемой квартиры
     * @param updated новое значение с тем же id
     * @return {@code true}, если элемент был найден и обновлен
     */
    public boolean update(long id, Flat updated) {
        if (updated.getId() != id) {
            throw new IllegalArgumentException("При update нельзя менять id элемента");
        }

        Flat old = getById(id);
        if (old == null) {
            return false;
        }

        flats.remove(old);
        flats.add(updated);
        return true;
    }

    /**
     * Удаляет квартиру по id.
     *
     * @param id идентификатор
     * @return удаленная квартира или {@code null}, если id не найден
     */
    public Flat removeById(long id) {
        Flat flat = getById(id);
        if (flat != null) {
            flats.remove(flat);
        }
        return flat;
    }

    /**
     * Очищает коллекцию.
     */
    public void clear() {
        flats.clear();
    }

    /**
     * Возвращает первый элемент очереди без удаления.
     *
     * @return первый элемент или {@code null}
     */
    public Flat head() {
        return flats.peek();
    }

    /**
     * Возвращает и удаляет первый элемент очереди.
     *
     * @return первый элемент или {@code null}
     */
    public Flat removeHead() {
        return flats.poll();
    }

    /**
     * Ищет квартиру по id.
     *
     * @param id идентификатор
     * @return квартира или {@code null}
     */
    public Flat getById(long id) {
        for (Flat flat : flats) {
            if (flat.getId() == id) {
                return flat;
            }
        }
        return null;
    }

    /**
     * Проверяет существование id.
     *
     * @param id идентификатор
     * @return {@code true}, если id уже занят
     */
    public boolean containsId(long id) {
        return getById(id) != null;
    }

    /**
     * Возвращает элементы в порядке сортировки по умолчанию.
     *
     * @return отсортированный список квартир
     */
    public List<Flat> sortedElements() {
        List<Flat> result = new ArrayList<>(flats);
        result.sort(Comparator.naturalOrder());
        return result;
    }

    /**
     * Возвращает элемент с минимальным значением centralHeating.
     *
     * <p>Для boolean используется естественная логика: {@code false < true}.</p>
     *
     * @return найденный элемент или {@code null}
     */
    public Flat minByCentralHeating() {
        Flat result = null;
        for (Flat flat : flats) {
            if (result == null || Boolean.compare(flat.isCentralHeating(), result.isCentralHeating()) < 0) {
                result = flat;
            }
        }
        return result;
    }

    /**
     * Считает элементы, у которых centralHeating меньше заданного значения.
     *
     * @param centralHeating значение для сравнения
     * @return количество элементов
     */
    public long countLessThanCentralHeating(boolean centralHeating) {
        long count = 0;
        for (Flat flat : flats) {
            if (Boolean.compare(flat.isCentralHeating(), centralHeating) < 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Фильтрует элементы, имя которых содержит заданную подстроку.
     *
     * @param name подстрока
     * @return список найденных элементов
     */
    public List<Flat> filterContainsName(String name) {
        List<Flat> result = new ArrayList<>();
        for (Flat flat : sortedElements()) {
            if (flat.getName().contains(name)) {
                result.add(flat);
            }
        }
        return result;
    }
}
