package storage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


class SimpleJsonParser {
    private final String text;
    private int index;

    private SimpleJsonParser(String text) {
        this.text = text;
    }


    public static Object parse(String text) {
        SimpleJsonParser parser = new SimpleJsonParser(text);
        Object value = parser.parseValue();
        parser.skipWhitespace();
        if (!parser.isEnd()) {
            throw new IllegalArgumentException("Лишние символы после конца JSON на позиции " + parser.index);
        }
        return value;
    }

    private Object parseValue() {
        skipWhitespace();
        if (isEnd()) {
            throw new IllegalArgumentException("Неожиданный конец JSON");
        }

        char current = text.charAt(index);
        if (current == '"') {
            return parseString();
        }
        if (current == '{') {
            return parseObject();
        }
        if (current == '[') {
            return parseArray();
        }
        if (current == 't') {
            return parseLiteral("true", Boolean.TRUE);
        }
        if (current == 'f') {
            return parseLiteral("false", Boolean.FALSE);
        }
        if (current == 'n') {
            return parseLiteral("null", null);
        }
        if (current == '-' || Character.isDigit(current)) {
            return parseNumber();
        }

        throw new IllegalArgumentException("Неожиданный символ '" + current + "' на позиции " + index);
    }

    private Map<String, Object> parseObject() {
        expect('{');
        Map<String, Object> object = new LinkedHashMap<>();
        skipWhitespace();
        if (tryConsume('}')) {
            return object;
        }

        while (true) {
            skipWhitespace();
            if (isEnd() || text.charAt(index) != '"') {
                throw new IllegalArgumentException("Ожидалось имя поля JSON-объекта на позиции " + index);
            }
            String key = parseString();
            skipWhitespace();
            expect(':');
            Object value = parseValue();
            object.put(key, value);
            skipWhitespace();
            if (tryConsume('}')) {
                return object;
            }
            expect(',');
        }
    }

    private List<Object> parseArray() {
        expect('[');
        List<Object> array = new ArrayList<>();
        skipWhitespace();
        if (tryConsume(']')) {
            return array;
        }

        while (true) {
            array.add(parseValue());
            skipWhitespace();
            if (tryConsume(']')) {
                return array;
            }
            expect(',');
        }
    }

    private String parseString() {
        expect('"');
        StringBuilder result = new StringBuilder();
        while (!isEnd()) {
            char current = text.charAt(index++);
            if (current == '"') {
                return result.toString();
            }
            if (current == '\\') {
                if (isEnd()) {
                    throw new IllegalArgumentException("Некорректная escape-последовательность в строке JSON");
                }
                char escaped = text.charAt(index++);
                switch (escaped) {
                    case '"':
                        result.append('"');
                        break;
                    case '\\':
                        result.append('\\');
                        break;
                    case '/':
                        result.append('/');
                        break;
                    case 'b':
                        result.append('\b');
                        break;
                    case 'f':
                        result.append('\f');
                        break;
                    case 'n':
                        result.append('\n');
                        break;
                    case 'r':
                        result.append('\r');
                        break;
                    case 't':
                        result.append('\t');
                        break;
                    case 'u':
                        result.append(parseUnicodeEscape());
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестная escape-последовательность \\" + escaped);
                }
            } else {
                result.append(current);
            }
        }
        throw new IllegalArgumentException("Строка JSON не закрыта кавычкой");
    }

    private char parseUnicodeEscape() {
        if (index + 4 > text.length()) {
            throw new IllegalArgumentException("Некорректный unicode escape в JSON-строке");
        }
        String hex = text.substring(index, index + 4);
        index += 4;
        try {
            return (char) Integer.parseInt(hex, 16);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Некорректный unicode escape: " + hex, exception);
        }
    }

    private Number parseNumber() {
        int start = index;
        if (tryConsume('-')) {
            if (isEnd()) {
                throw new IllegalArgumentException("Некорректное число JSON на позиции " + start);
            }
        }

        consumeDigits();
        boolean fractional = false;

        if (!isEnd() && text.charAt(index) == '.') {
            fractional = true;
            index++;
            consumeDigits();
        }

        if (!isEnd() && (text.charAt(index) == 'e' || text.charAt(index) == 'E')) {
            fractional = true;
            index++;
            if (!isEnd() && (text.charAt(index) == '+' || text.charAt(index) == '-')) {
                index++;
            }
            consumeDigits();
        }

        String raw = text.substring(start, index);
        try {
            if (fractional) {
                return Double.parseDouble(raw);
            }
            return Long.parseLong(raw);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Некорректное число JSON: " + raw, exception);
        }
    }

    private void consumeDigits() {
        int start = index;
        while (!isEnd() && Character.isDigit(text.charAt(index))) {
            index++;
        }
        if (start == index) {
            throw new IllegalArgumentException("Ожидались цифры на позиции " + index);
        }
    }

    private Object parseLiteral(String literal, Object value) {
        if (!text.startsWith(literal, index)) {
            throw new IllegalArgumentException("Ожидалось '" + literal + "' на позиции " + index);
        }
        index += literal.length();
        return value;
    }

    private void skipWhitespace() {
        while (!isEnd() && Character.isWhitespace(text.charAt(index))) {
            index++;
        }
    }

    private void expect(char expected) {
        if (isEnd() || text.charAt(index) != expected) {
            throw new IllegalArgumentException("Ожидался символ '" + expected + "' на позиции " + index);
        }
        index++;
    }

    private boolean tryConsume(char expected) {
        if (!isEnd() && text.charAt(index) == expected) {
            index++;
            return true;
        }
        return false;
    }

    private boolean isEnd() {
        return index >= text.length();
    }
}
