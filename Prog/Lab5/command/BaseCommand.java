package command;

import model.Flat;

/**
 * Базовый класс для команд.
 *
 * <p>Здесь лежат общие проверки аргументов, чтобы не копировать их в каждую команду.</p>
 */
abstract class BaseCommand implements Command {
    private final String name;
    private final String description;

    BaseCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    protected void requireNoArguments(String arguments) {
        if (!arguments.isEmpty()) {
            throw new IllegalArgumentException("Команда " + name + " не принимает аргументы");
        }
    }

    protected long parseSingleLongArgument(String arguments, String argumentName) {
        String raw = requireSingleToken(arguments, argumentName);
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(argumentName + " должен быть целым числом");
        }
    }

    protected boolean parseSingleBooleanArgument(String arguments, String argumentName) {
        String raw = requireSingleToken(arguments, argumentName).toLowerCase();
        if ("true".equals(raw) || "yes".equals(raw) || "да".equals(raw)) {
            return true;
        }
        if ("false".equals(raw) || "no".equals(raw) || "нет".equals(raw)) {
            return false;
        }
        throw new IllegalArgumentException(argumentName + " должен быть true или false");
    }

    protected String requireSingleToken(String arguments, String argumentName) {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new IllegalArgumentException("Не указан аргумент " + argumentName);
        }
        String[] tokens = arguments.trim().split("\\s+");
        if (tokens.length != 1) {
            throw new IllegalArgumentException("Аргумент " + argumentName + " должен быть один");
        }
        return tokens[0];
    }

    protected String requireStringArgument(String arguments, String argumentName) {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new IllegalArgumentException("Не указан аргумент " + argumentName);
        }
        return arguments.trim();
    }

    protected void printFlatOrEmpty(Flat flat, String emptyMessage) {
        if (flat == null) {
            System.out.println(emptyMessage);
        } else {
            System.out.println(flat);
        }
    }
}
