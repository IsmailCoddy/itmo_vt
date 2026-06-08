package storage;

import model.Flat;
import model.House;

import java.util.Collection;

/**
 * Собирает JSON-строку для сохранения коллекции.
 *
 * <p>JSON сделан с отступами, чтобы файл можно было нормально открыть и прочитать.</p>
 */
class SimpleJsonWriter {
    private SimpleJsonWriter() {
    }

    /**
     * Преобразует коллекцию квартир в JSON-массив.
     *
     * @param flats элементы коллекции
     * @return JSON-представление
     */
    public static String writeFlats(Collection<Flat> flats) {
        StringBuilder builder = new StringBuilder();
        builder.append("[\n");
        int index = 0;
        for (Flat flat : flats) {
            if (index > 0) {
                builder.append(",\n");
            }
            writeFlat(builder, flat);
            index++;
        }
        builder.append("\n]\n");
        return builder.toString();
    }

    private static void writeFlat(StringBuilder builder, Flat flat) {
        builder.append("  {\n");
        appendField(builder, "id", String.valueOf(flat.getId()), true, 4);
        appendField(builder, "name", quote(flat.getName()), true, 4);
        builder.append("    \"coordinates\": {\n");
        appendField(builder, "x", String.valueOf(flat.getCoordinates().getX()), true, 6);
        appendField(builder, "y", String.valueOf(flat.getCoordinates().getY()), false, 6);
        builder.append("    },\n");
        appendField(builder, "creationDate", quote(flat.getCreationDate().toString()), true, 4);
        appendField(builder, "area", String.valueOf(flat.getArea()), true, 4);
        appendField(builder, "numberOfRooms", nullableNumber(flat.getNumberOfRooms()), true, 4);
        appendField(builder, "centralHeating", String.valueOf(flat.isCentralHeating()), true, 4);
        appendField(builder, "furnish", quote(flat.getFurnish().name()), true, 4);
        appendField(builder, "view", quote(flat.getView().name()), true, 4);
        writeHouse(builder, flat.getHouse());
        builder.append("  }");
    }

    private static void writeHouse(StringBuilder builder, House house) {
        builder.append("    \"house\": {\n");
        appendField(builder, "name", nullableString(house.getName()), true, 6);
        appendField(builder, "year", String.valueOf(house.getYear()), true, 6);
        appendField(builder, "numberOfFloors", String.valueOf(house.getNumberOfFloors()), true, 6);
        appendField(builder, "numberOfFlatsOnFloor", String.valueOf(house.getNumberOfFlatsOnFloor()), true, 6);
        appendField(builder, "numberOfLifts", String.valueOf(house.getNumberOfLifts()), false, 6);
        builder.append("    }\n");
    }

    private static void appendField(StringBuilder builder, String name, String value, boolean comma, int spaces) {
        appendSpaces(builder, spaces);
        builder.append('"').append(name).append("\": ").append(value);
        if (comma) {
            builder.append(',');
        }
        builder.append('\n');
    }

    private static void appendSpaces(StringBuilder builder, int count) {
        for (int i = 0; i < count; i++) {
            builder.append(' ');
        }
    }

    private static String nullableString(String value) {
        return value == null ? "null" : quote(value);
    }

    private static String nullableNumber(Integer value) {
        return value == null ? "null" : String.valueOf(value);
    }

    private static String quote(String value) {
        StringBuilder builder = new StringBuilder();
        builder.append('"');
        for (int i = 0; i < value.length(); i++) {
            char current = value.charAt(i);
            switch (current) {
                case '"':
                    builder.append("\\\"");
                    break;
                case '\\':
                    builder.append("\\\\");
                    break;
                case '\b':
                    builder.append("\\b");
                    break;
                case '\f':
                    builder.append("\\f");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                default:
                    if (current < 32) {
                        builder.append(String.format("\\u%04x", (int) current));
                    } else {
                        builder.append(current);
                    }
                    break;
            }
        }
        builder.append('"');
        return builder.toString();
    }
}
