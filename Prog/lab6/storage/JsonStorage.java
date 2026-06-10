package storage;

import model.Coordinates;
import model.Flat;
import model.Furnish;
import model.House;
import model.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class JsonStorage implements FlatStorage {
    private final String fileName;


    public JsonStorage(String fileName) {
        this.fileName = fileName;
    }


    public String getFileName() {
        return fileName;
    }


    public List<Flat> load() {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Файл не найден. Коллекция будет создана пустой: " + fileName);
            return new ArrayList<>();
        }
        if (!file.isFile()) {
            System.out.println("Указанный путь не является файлом. Коллекция будет создана пустой: " + fileName);
            return new ArrayList<>();
        }
        if (!file.canRead()) {
            System.out.println("Нет прав на чтение файла. Коллекция будет создана пустой: " + fileName);
            return new ArrayList<>();
        }

        String json;
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
            scanner.useDelimiter("\\A");
            json = scanner.hasNext() ? scanner.next() : "";
        } catch (FileNotFoundException exception) {
            System.out.println("Не удалось открыть файл. Коллекция будет создана пустой: " + exception.getMessage());
            return new ArrayList<>();
        }

        if (json.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            Object root = SimpleJsonParser.parse(json);
            return parseRoot(root);
        } catch (IllegalArgumentException exception) {
            System.out.println("Файл содержит некорректный JSON. Коллекция будет создана пустой.");
            System.out.println("Подробность: " + exception.getMessage());
            return new ArrayList<>();
        }
    }


    public void save(Collection<Flat> flats) throws StorageException {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)
        )) {
            writer.write(SimpleJsonWriter.writeFlats(flats));
        } catch (IOException exception) {
            throw new StorageException("Не удалось сохранить коллекцию в файл: " + fileName, exception);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Flat> parseRoot(Object root) {
        Object source = root;
        if (root instanceof Map) {
            source = ((Map<String, Object>) root).get("flats");
        }
        if (!(source instanceof List)) {
            throw new IllegalArgumentException("Корневой JSON должен быть массивом или объектом с полем flats");
        }

        List<Flat> result = new ArrayList<>();
        List<Object> rawFlats = (List<Object>) source;
        for (int i = 0; i < rawFlats.size(); i++) {
            try {
                result.add(parseFlat(rawFlats.get(i)));
            } catch (IllegalArgumentException exception) {
                System.out.println("Предупреждение: элемент JSON #" + (i + 1) + " пропущен: " + exception.getMessage());
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Flat parseFlat(Object value) {
        Map<String, Object> map = requireMap(value, "flat");
        long id = readLong(map, "id");
        String name = readString(map, "name", false);
        Coordinates coordinates = parseCoordinates(map.get("coordinates"));
        ZonedDateTime creationDate = parseCreationDate(readString(map, "creationDate", false));
        Integer area = readInteger(map, "area", false);
        Integer numberOfRooms = readInteger(map, "numberOfRooms", true);
        boolean centralHeating = readBoolean(map, "centralHeating");
        Furnish furnish = readEnum(map, "furnish", Furnish.class);
        View view = readEnum(map, "view", View.class);
        House house = parseHouse(map.get("house"));

        return new Flat(
                id,
                name,
                coordinates,
                creationDate,
                area,
                numberOfRooms,
                centralHeating,
                furnish,
                view,
                house
        );
    }

    private Coordinates parseCoordinates(Object value) {
        Map<String, Object> map = requireMap(value, "coordinates");
        Long x = readLongObject(map, "x", false);
        Double y = readDoubleObject(map, "y", false);
        return new Coordinates(x, y);
    }

    private House parseHouse(Object value) {
        Map<String, Object> map = requireMap(value, "house");
        String name = readString(map, "name", true);
        long year = readLong(map, "year");
        long numberOfFloors = readLong(map, "numberOfFloors");
        long numberOfFlatsOnFloor = readLong(map, "numberOfFlatsOnFloor");
        long numberOfLifts = readLong(map, "numberOfLifts");
        return new House(name, year, numberOfFloors, numberOfFlatsOnFloor, numberOfLifts);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> requireMap(Object value, String fieldName) {
        if (!(value instanceof Map)) {
            throw new IllegalArgumentException(fieldName + " должен быть JSON-объектом");
        }
        return (Map<String, Object>) value;
    }

    private ZonedDateTime parseCreationDate(String value) {
        try {
            return ZonedDateTime.parse(value);
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException("creationDate должен быть строкой ZonedDateTime", exception);
        }
    }

    private String readString(Map<String, Object> map, String fieldName, boolean nullable) {
        Object value = map.get(fieldName);
        if (value == null) {
            if (nullable) {
                return null;
            }
            throw new IllegalArgumentException(fieldName + " не может быть null");
        }
        if (!(value instanceof String)) {
            throw new IllegalArgumentException(fieldName + " должен быть строкой");
        }
        return (String) value;
    }

    private boolean readBoolean(Map<String, Object> map, String fieldName) {
        Object value = map.get(fieldName);
        if (!(value instanceof Boolean)) {
            throw new IllegalArgumentException(fieldName + " должен быть boolean");
        }
        return (Boolean) value;
    }

    private long readLong(Map<String, Object> map, String fieldName) {
        Long value = readLongObject(map, fieldName, false);
        return value;
    }

    private Long readLongObject(Map<String, Object> map, String fieldName, boolean nullable) {
        Object value = map.get(fieldName);
        if (value == null) {
            if (nullable) {
                return null;
            }
            throw new IllegalArgumentException(fieldName + " не может быть null");
        }
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException(fieldName + " должен быть числом");
        }
        return toLong((Number) value, fieldName);
    }

    private Integer readInteger(Map<String, Object> map, String fieldName, boolean nullable) {
        Long value = readLongObject(map, fieldName, nullable);
        if (value == null) {
            return null;
        }
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(fieldName + " выходит за границы Integer");
        }
        return value.intValue();
    }

    private Double readDoubleObject(Map<String, Object> map, String fieldName, boolean nullable) {
        Object value = map.get(fieldName);
        if (value == null) {
            if (nullable) {
                return null;
            }
            throw new IllegalArgumentException(fieldName + " не может быть null");
        }
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException(fieldName + " должен быть числом");
        }
        double result = ((Number) value).doubleValue();
        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException(fieldName + " должен быть конечным числом");
        }
        return result;
    }

    private long toLong(Number number, String fieldName) {
        if (number instanceof Long || number instanceof Integer || number instanceof Short || number instanceof Byte) {
            return number.longValue();
        }
        double value = number.doubleValue();
        if (!Double.isFinite(value) || value != Math.rint(value)) {
            throw new IllegalArgumentException(fieldName + " должен быть целым числом");
        }
        if (value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
            throw new IllegalArgumentException(fieldName + " выходит за границы Long");
        }
        return (long) value;
    }

    private <T extends Enum<T>> T readEnum(Map<String, Object> map, String fieldName, Class<T> enumType) {
        String value = readString(map, fieldName, false);
        try {
            return Enum.valueOf(enumType, value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(fieldName + " содержит неизвестную enum-константу: " + value, exception);
        }
    }
}
