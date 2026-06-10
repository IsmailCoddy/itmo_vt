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


public class CollectionManager implements FlatCollection {
    private final PriorityQueue<Flat> flats = new PriorityQueue<>();
    private final ZonedDateTime initializationDate = ZonedDateTime.now();
    private long nextId = 1;


    public CollectionManager(Collection<Flat> loadedFlats) {
        Set<Long> usedIds = new HashSet<>();
        loadedFlats.stream()
                .filter(flat -> {
                    if (!usedIds.add(flat.getId())) {
                        System.out.println("Предупреждение: элемент с повторяющимся id " + flat.getId() + " пропущен.");
                        return false;
                    }
                    return true;
                })
                .forEach(flats::add);

        long maxId = flats.stream()
                .mapToLong(Flat::getId)
                .max()
                .orElse(0);
        nextId = maxId == Long.MAX_VALUE ? 1 : maxId + 1;
    }


    public String getCollectionType() {
        return flats.getClass().getName();
    }


    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }


    public int size() {
        return flats.size();
    }


    public boolean isEmpty() {
        return flats.isEmpty();
    }


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


    public void add(Flat flat) {
        if (containsId(flat.getId())) {
            throw new IllegalArgumentException("Элемент с id " + flat.getId() + " уже существует");
        }
        flats.add(flat);
        if (flat.getId() >= nextId) {
            nextId = flat.getId() == Long.MAX_VALUE ? 1 : flat.getId() + 1;
        }
    }


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


    public Flat removeById(long id) {
        Flat flat = getById(id);
        if (flat != null) {
            flats.remove(flat);
        }
        return flat;
    }


    public void clear() {
        flats.clear();
    }


    public Flat head() {
        return flats.peek();
    }


    public Flat removeHead() {
        return flats.poll();
    }


    public Flat getById(long id) {
        return flats.stream()
                .filter(flat -> flat.getId() == id)
                .findFirst()
                .orElse(null);
    }


    public boolean containsId(long id) {
        return flats.stream()
                .anyMatch(flat -> flat.getId() == id);
    }


    public List<Flat> sortedElements() {
        return flats.stream()
                .sorted(Comparator.naturalOrder())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }


    public List<Flat> sortedByName() {
        return flats.stream()
                .sorted(Comparator.comparing(Flat::getName))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }


    public Flat minByCentralHeating() {
        return flats.stream()
                .min(Comparator.comparing(Flat::isCentralHeating).thenComparing(Flat::getName))
                .orElse(null);
    }


    public long countLessThanCentralHeating(boolean centralHeating) {
        return flats.stream()
                .filter(flat -> Boolean.compare(flat.isCentralHeating(), centralHeating) < 0)
                .count();
    }


    public List<Flat> filterContainsName(String name) {
        return flats.stream()
                .filter(flat -> flat.getName().contains(name))
                .sorted(Comparator.comparing(Flat::getName))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
