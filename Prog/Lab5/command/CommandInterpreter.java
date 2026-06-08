package command;

import collection.FlatCollection;
import input.FlatReader;
import input.InputContext;
import input.InputReadException;
import storage.FlatStorage;

import java.util.Scanner;

/**
 * Читает строки команд и запускает нужные команды.
 *
 * <p>Он не делает всю работу сам: только находит команду в списке и вызывает ее.</p>
 */
public class CommandInterpreter implements CommandLineExecutor {
    private final CommandHistory history = new CommandHistory();
    private final CommandRegistry registry = new CommandRegistry();

    /**
     * Создает обработчик команд и регистрирует команды лабораторной работы.
     *
     * @param collection коллекция квартир
     * @param storage хранилище коллекции
     * @param flatReader считыватель составного объекта Flat
     */
    public CommandInterpreter(FlatCollection collection, FlatStorage storage, FlatReader flatReader) {
        registerCommands(collection, storage, flatReader);
    }

    /**
     * Запускает интерактивный цикл чтения команд.
     */
    public void runInteractive() {
        Scanner scanner = new Scanner(System.in);
        InputContext input = new InputContext(scanner, true, "console");
        boolean running = true;

        System.out.println("Коллекция готова к работе. Введите help, чтобы увидеть список команд.");
        while (running) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                System.out.println();
                break;
            }
            running = executeLine(scanner.nextLine(), input);
        }
    }

    @Override
    public boolean executeLine(String line, InputContext input) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return true;
        }

        String commandName = extractCommandName(trimmed).toLowerCase();
        String arguments = extractArguments(trimmed);
        history.add(commandName);

        Command command = registry.find(commandName);
        if (command == null) {
            System.out.println("Неизвестная команда: " + commandName + ". Введите help для справки.");
            return true;
        }

        try {
            return command.execute(arguments, input);
        } catch (InputReadException exception) {
            System.out.println("Ввод команды прерван: " + exception.getMessage());
            return true;
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
            return true;
        } catch (IllegalStateException exception) {
            System.out.println("Ошибка состояния программы: " + exception.getMessage());
            return true;
        }
    }

    private void registerCommands(FlatCollection collection, FlatStorage storage, FlatReader flatReader) {
        registry.register(new HelpCommand(registry));
        registry.register(new InfoCommand(collection, storage));
        registry.register(new ShowCommand(collection));
        registry.register(new AddCommand(collection, flatReader));
        registry.register(new UpdateCommand(collection, flatReader));
        registry.register(new RemoveByIdCommand(collection));
        registry.register(new ClearCommand(collection));
        registry.register(new SaveCommand(collection, storage));
        registry.register(new ExecuteScriptCommand(this));
        registry.register(new ExitCommand());
        registry.register(new HeadCommand(collection));
        registry.register(new RemoveHeadCommand(collection));
        registry.register(new HistoryCommand(history));
        registry.register(new MinByCentralHeatingCommand(collection));
        registry.register(new CountLessThanCentralHeatingCommand(collection));
        registry.register(new FilterContainsNameCommand(collection));
    }

    private String extractCommandName(String line) {
        int firstSpace = line.indexOf(' ');
        if (firstSpace == -1) {
            return line;
        }
        return line.substring(0, firstSpace);
    }

    private String extractArguments(String line) {
        int firstSpace = line.indexOf(' ');
        if (firstSpace == -1) {
            return "";
        }
        return line.substring(firstSpace + 1).trim();
    }
}
