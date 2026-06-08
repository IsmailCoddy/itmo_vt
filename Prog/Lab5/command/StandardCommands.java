package command;

import collection.FlatCollection;
import input.FlatReader;
import input.InputContext;
import input.InputReadException;
import model.Flat;
import storage.FlatStorage;
import storage.StorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Команды, которые нужны по заданию.
 *
 * <p>Они лежат в одном файле, чтобы проект не превратился в кучу совсем маленьких файлов.</p>
 */
final class StandardCommands {
    private StandardCommands() {
    }
}

/**
 * Команда help.
 */
class HelpCommand extends BaseCommand {
    private final CommandRegistry registry;

    HelpCommand(CommandRegistry registry) {
        super("help", "help : вывести справку по доступным командам");
        this.registry = registry;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        System.out.println("Доступные команды:");
        for (Command command : registry.allCommands()) {
            System.out.println(command.getDescription());
        }
        return true;
    }
}

/**
 * Команда info.
 */
class InfoCommand extends BaseCommand {
    private final FlatCollection collection;
    private final FlatStorage storage;

    InfoCommand(FlatCollection collection, FlatStorage storage) {
        super("info", "info : вывести информацию о коллекции");
        this.collection = collection;
        this.storage = storage;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        System.out.println("Тип коллекции: " + collection.getCollectionType());
        System.out.println("Дата инициализации: " + collection.getInitializationDate());
        System.out.println("Количество элементов: " + collection.size());
        System.out.println("Файл хранения: " + storage.getFileName());
        return true;
    }
}

/**
 * Команда show.
 */
class ShowCommand extends BaseCommand {
    private final FlatCollection collection;

    ShowCommand(FlatCollection collection) {
        super("show", "show : вывести все элементы коллекции");
        this.collection = collection;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        List<Flat> flats = collection.sortedElements();
        if (flats.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return true;
        }
        for (Flat flat : flats) {
            System.out.println(flat);
        }
        return true;
    }
}

/**
 * Команда add.
 */
class AddCommand extends BaseCommand {
    private final FlatCollection collection;
    private final FlatReader flatReader;

    AddCommand(FlatCollection collection, FlatReader flatReader) {
        super("add", "add : добавить новый элемент");
        this.collection = collection;
        this.flatReader = flatReader;
    }

    @Override
    public boolean execute(String arguments, InputContext input) throws InputReadException {
        requireNoArguments(arguments);
        long id = collection.generateId();
        Flat flat = flatReader.readFlat(input, id, ZonedDateTime.now());
        collection.add(flat);
        System.out.println("Элемент добавлен. id = " + id);
        return true;
    }
}

/**
 * Команда update.
 */
class UpdateCommand extends BaseCommand {
    private final FlatCollection collection;
    private final FlatReader flatReader;

    UpdateCommand(FlatCollection collection, FlatReader flatReader) {
        super("update", "update id : обновить элемент по id");
        this.collection = collection;
        this.flatReader = flatReader;
    }

    @Override
    public boolean execute(String arguments, InputContext input) throws InputReadException {
        long id = parseSingleLongArgument(arguments, "id");
        Flat old = collection.getById(id);
        if (old == null) {
            System.out.println("Элемент с id " + id + " не найден.");
            return true;
        }

        Flat updated = flatReader.readFlat(input, id, old.getCreationDate());
        collection.update(id, updated);
        System.out.println("Элемент с id " + id + " обновлен.");
        return true;
    }
}

/**
 * Команда remove_by_id.
 */
class RemoveByIdCommand extends BaseCommand {
    private final FlatCollection collection;

    RemoveByIdCommand(FlatCollection collection) {
        super("remove_by_id", "remove_by_id id : удалить элемент по id");
        this.collection = collection;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        long id = parseSingleLongArgument(arguments, "id");
        Flat removed = collection.removeById(id);
        if (removed == null) {
            System.out.println("Элемент с id " + id + " не найден.");
        } else {
            System.out.println("Элемент удален: " + removed);
        }
        return true;
    }
}

/**
 * Команда clear.
 */
class ClearCommand extends BaseCommand {
    private final FlatCollection collection;

    ClearCommand(FlatCollection collection) {
        super("clear", "clear : очистить коллекцию");
        this.collection = collection;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        collection.clear();
        System.out.println("Коллекция очищена.");
        return true;
    }
}

/**
 * Команда save.
 */
class SaveCommand extends BaseCommand {
    private final FlatCollection collection;
    private final FlatStorage storage;

    SaveCommand(FlatCollection collection, FlatStorage storage) {
        super("save", "save : сохранить коллекцию в файл");
        this.collection = collection;
        this.storage = storage;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        try {
            storage.save(collection.sortedElements());
            System.out.println("Коллекция сохранена в файл: " + storage.getFileName());
        } catch (StorageException exception) {
            System.out.println("Ошибка сохранения: " + exception.getMessage());
            if (exception.getCause() != null) {
                System.out.println("Причина: " + exception.getCause().getMessage());
            }
        }
        return true;
    }
}

/**
 * Команда execute_script.
 */
class ExecuteScriptCommand extends BaseCommand {
    private final CommandLineExecutor executor;
    private final Set<String> runningScripts = new HashSet<>();

    ExecuteScriptCommand(CommandLineExecutor executor) {
        super("execute_script", "execute_script file_name : выполнить команды из файла");
        this.executor = executor;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        String fileName = requireStringArgument(arguments, "file_name");
        File file = new File(fileName);

        String scriptKey;
        try {
            scriptKey = file.getCanonicalPath();
        } catch (IOException exception) {
            scriptKey = file.getAbsolutePath();
        }

        if (runningScripts.contains(scriptKey)) {
            System.out.println("Рекурсивный вызов скрипта запрещен: " + fileName);
            return true;
        }
        if (!file.exists()) {
            System.out.println("Скрипт не найден: " + fileName);
            return true;
        }
        if (!file.isFile()) {
            System.out.println("Указанный путь не является файлом скрипта: " + fileName);
            return true;
        }
        if (!file.canRead()) {
            System.out.println("Нет прав на чтение скрипта: " + fileName);
            return true;
        }

        runningScripts.add(scriptKey);
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
            InputContext scriptInput = new InputContext(scanner, false, fileName);
            boolean running = true;
            while (scanner.hasNextLine() && running) {
                String scriptLine = scanner.nextLine();
                if (scriptLine.trim().isEmpty()) {
                    continue;
                }
                System.out.println("[" + file.getName() + "] " + scriptLine);
                running = executor.executeLine(scriptLine, scriptInput);
            }
            return running;
        } catch (FileNotFoundException exception) {
            System.out.println("Не удалось открыть скрипт: " + exception.getMessage());
            return true;
        } finally {
            runningScripts.remove(scriptKey);
        }
    }
}

/**
 * Команда exit.
 */
class ExitCommand extends BaseCommand {
    ExitCommand() {
        super("exit", "exit : завершить программу без сохранения");
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        System.out.println("Завершение программы без сохранения.");
        return false;
    }
}

/**
 * Команда head.
 */
class HeadCommand extends BaseCommand {
    private final FlatCollection collection;

    HeadCommand(FlatCollection collection) {
        super("head", "head : вывести первый элемент коллекции");
        this.collection = collection;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        printFlatOrEmpty(collection.head(), "Коллекция пуста.");
        return true;
    }
}

/**
 * Команда remove_head.
 */
class RemoveHeadCommand extends BaseCommand {
    private final FlatCollection collection;

    RemoveHeadCommand(FlatCollection collection) {
        super("remove_head", "remove_head : вывести и удалить первый элемент коллекции");
        this.collection = collection;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        printFlatOrEmpty(collection.removeHead(), "Коллекция пуста.");
        return true;
    }
}

/**
 * Команда history.
 */
class HistoryCommand extends BaseCommand {
    private final CommandHistory history;

    HistoryCommand(CommandHistory history) {
        super("history", "history : вывести последние 6 команд без аргументов");
        this.history = history;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        List<String> commands = history.values();
        if (commands.isEmpty()) {
            System.out.println("История команд пуста.");
            return true;
        }
        for (String command : commands) {
            System.out.println(command);
        }
        return true;
    }
}

/**
 * Команда min_by_central_heating.
 */
class MinByCentralHeatingCommand extends BaseCommand {
    private final FlatCollection collection;

    MinByCentralHeatingCommand(FlatCollection collection) {
        super("min_by_central_heating", "min_by_central_heating : вывести элемент с минимальным centralHeating");
        this.collection = collection;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        requireNoArguments(arguments);
        printFlatOrEmpty(collection.minByCentralHeating(), "Коллекция пуста.");
        return true;
    }
}

/**
 * Команда count_less_than_central_heating.
 */
class CountLessThanCentralHeatingCommand extends BaseCommand {
    private final FlatCollection collection;

    CountLessThanCentralHeatingCommand(FlatCollection collection) {
        super(
                "count_less_than_central_heating",
                "count_less_than_central_heating centralHeating : посчитать элементы с меньшим centralHeating"
        );
        this.collection = collection;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        boolean value = parseSingleBooleanArgument(arguments, "centralHeating");
        System.out.println(collection.countLessThanCentralHeating(value));
        return true;
    }
}

/**
 * Команда filter_contains_name.
 */
class FilterContainsNameCommand extends BaseCommand {
    private final FlatCollection collection;

    FilterContainsNameCommand(FlatCollection collection) {
        super("filter_contains_name", "filter_contains_name name : вывести элементы, имя которых содержит подстроку");
        this.collection = collection;
    }

    @Override
    public boolean execute(String arguments, InputContext input) {
        String name = requireStringArgument(arguments, "name");
        List<Flat> result = collection.filterContainsName(name);
        if (result.isEmpty()) {
            System.out.println("Элементы не найдены.");
            return true;
        }
        for (Flat flat : result) {
            System.out.println(flat);
        }
        return true;
    }
}
