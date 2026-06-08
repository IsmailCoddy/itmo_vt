package input;

import model.Coordinates;
import model.Flat;
import model.Furnish;
import model.House;
import model.View;

import java.time.ZonedDateTime;

/**
 * Читает квартиру с консоли или из скрипта.
 *
 * <p>По заданию объект надо вводить не целиком, а по одному полю. Поэтому тут
 * собраны методы для чтения строк, чисел, boolean и enum с проверками.</p>
 */
public class FlatInputReader implements FlatReader {
    /**
     * Создает обычный считыватель квартир.
     */
    public FlatInputReader() {
    }

    /**
     * Считывает квартиру с уже сгенерированными служебными полями.
     *
     * @param input источник ввода
     * @param id автоматически сгенерированный или сохраненный id
     * @param creationDate автоматически сгенерированная или сохраненная дата создания
     * @return считанная квартира
     * @throws InputReadException если ввод закончился раньше времени
     */
    public Flat readFlat(InputContext input, long id, ZonedDateTime creationDate) throws InputReadException {
        String name = readString(input, "name", false);
        Long x = readLong(input, "coordinates.x", false, -740);
        Double y = readDouble(input, "coordinates.y", false);
        Coordinates coordinates = new Coordinates(x, y);
        Integer area = readInteger(input, "area", false, 0);
        Integer numberOfRooms = readInteger(input, "numberOfRooms", true, 0);
        boolean centralHeating = readBoolean(input, "centralHeating");
        Furnish furnish = readEnum(input, "furnish", Furnish.class);
        View view = readEnum(input, "view", View.class);
        House house = readHouse(input);

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

    private House readHouse(InputContext input) throws InputReadException {
        String name = readString(input, "house.name", true);
        long year = readLong(input, "house.year", false, 0);
        long numberOfFloors = readLong(input, "house.numberOfFloors", false, 0);
        long numberOfFlatsOnFloor = readLong(input, "house.numberOfFlatsOnFloor", false, 0);
        long numberOfLifts = readLong(input, "house.numberOfLifts", false, 0);
        return new House(name, year, numberOfFloors, numberOfFlatsOnFloor, numberOfLifts);
    }

    private String readString(InputContext input, String fieldName, boolean nullable) throws InputReadException {
        while (true) {
            String raw = input.readLine(makeInputMessage(fieldName, nullable ? "пустая строка = null" : null));
            if (raw.trim().isEmpty()) {
                if (nullable) {
                    return null;
                }
                printInputError(fieldName + " не может быть пустым");
                continue;
            }
            return raw;
        }
    }

    private Long readLong(InputContext input, String fieldName, boolean nullable, long minExclusive)
            throws InputReadException {
        while (true) {
            String raw = input.readLine(makeInputMessage(fieldName, nullable ? "пустая строка = null" : "целое число"));
            if (raw.trim().isEmpty()) {
                if (nullable) {
                    return null;
                }
                printInputError(fieldName + " не может быть null");
                continue;
            }

            try {
                long value = Long.parseLong(raw.trim());
                if (value <= minExclusive) {
                    printInputError(fieldName + " должен быть больше " + minExclusive);
                    continue;
                }
                return value;
            } catch (NumberFormatException exception) {
                printInputError(fieldName + " должен быть целым числом");
            }
        }
    }

    private Integer readInteger(InputContext input, String fieldName, boolean nullable, int minExclusive)
            throws InputReadException {
        while (true) {
            String raw = input.readLine(makeInputMessage(fieldName, nullable ? "пустая строка = null" : "целое число"));
            if (raw.trim().isEmpty()) {
                if (nullable) {
                    return null;
                }
                printInputError(fieldName + " не может быть null");
                continue;
            }

            try {
                int value = Integer.parseInt(raw.trim());
                if (value <= minExclusive) {
                    printInputError(fieldName + " должен быть больше " + minExclusive);
                    continue;
                }
                return value;
            } catch (NumberFormatException exception) {
                printInputError(fieldName + " должен быть числом типа Integer");
            }
        }
    }

    private Double readDouble(InputContext input, String fieldName, boolean nullable) throws InputReadException {
        while (true) {
            String raw = input.readLine(makeInputMessage(fieldName, nullable ? "пустая строка = null" : "число"));
            if (raw.trim().isEmpty()) {
                if (nullable) {
                    return null;
                }
                printInputError(fieldName + " не может быть null");
                continue;
            }

            try {
                double value = Double.parseDouble(raw.trim());
                if (!Double.isFinite(value)) {
                    printInputError(fieldName + " должен быть конечным числом");
                    continue;
                }
                return value;
            } catch (NumberFormatException exception) {
                printInputError(fieldName + " должен быть числом");
            }
        }
    }

    private boolean readBoolean(InputContext input, String fieldName) throws InputReadException {
        while (true) {
            String raw = input.readLine(makeInputMessage(fieldName, "true/false"));
            String normalized = raw.trim().toLowerCase();
            if ("true".equals(normalized) || "yes".equals(normalized) || "да".equals(normalized)) {
                return true;
            }
            if ("false".equals(normalized) || "no".equals(normalized) || "нет".equals(normalized)) {
                return false;
            }
            printInputError(fieldName + " должен быть true или false");
        }
    }

    private <T extends Enum<T>> T readEnum(InputContext input, String fieldName, Class<T> enumType)
            throws InputReadException {
        String constants = enumConstantsToString(enumType);

        while (true) {
            if (input.isInteractive()) {
                System.out.println("Доступные значения " + fieldName + ": " + constants);
            }
            String raw = input.readLine(makeInputMessage(fieldName, "enum"));
            try {
                return Enum.valueOf(enumType, raw.trim().toUpperCase());
            } catch (IllegalArgumentException exception) {
                printInputError(fieldName + " должен быть одним из: " + constants);
            }
        }
    }

    private String makeInputMessage(String fieldName, String hint) {
        if (hint == null) {
            return "Введите " + fieldName + ": ";
        }
        return "Введите " + fieldName + " (" + hint + "): ";
    }

    private <T extends Enum<T>> String enumConstantsToString(Class<T> enumType) {
        StringBuilder builder = new StringBuilder();
        T[] constants = enumType.getEnumConstants();
        for (int i = 0; i < constants.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(constants[i].name());
        }
        return builder.toString();
    }

    private void printInputError(String message) {
        System.out.println("Ошибка ввода: " + message + ". Повторите ввод поля.");
    }
}
