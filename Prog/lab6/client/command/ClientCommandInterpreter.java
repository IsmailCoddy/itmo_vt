package client.command;

import client.net.ClientTransport;
import common.CommandDescriptions;
import common.dto.CommandRequest;
import common.dto.CommandResponse;
import common.dto.CommandType;
import common.dto.FlatData;
import common.net.NetworkException;
import input.FlatInputReader;
import input.InputContext;
import input.InputReadException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class ClientCommandInterpreter {
    private final ClientTransport transport;
    private final FlatInputReader flatReader;
    private final CommandHistory history = new CommandHistory();
    private final CommandResponsePrinter responsePrinter = new CommandResponsePrinter();
    private final Set<String> runningScripts = new HashSet<>();


    public ClientCommandInterpreter(ClientTransport transport, FlatInputReader flatReader) {
        this.transport = transport;
        this.flatReader = flatReader;
    }


    public boolean executeLine(String line, InputContext input) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return true;
        }

        String commandName = extractCommandName(trimmed).toLowerCase();
        String arguments = extractArguments(trimmed);
        history.add(commandName);

        try {
            return executeCommand(commandName, arguments, input);
        } catch (InputReadException exception) {
            System.out.println("Ввод команды прерван: " + exception.getMessage());
            return true;
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
            return true;
        }
    }

    private boolean executeCommand(String commandName, String arguments, InputContext input)
            throws InputReadException {
        switch (commandName) {
            case "help":
                requireNoArguments(commandName, arguments);
                printHelp();
                return true;
            case "history":
                requireNoArguments(commandName, arguments);
                printHistory();
                return true;
            case "execute_script":
                return executeScript(arguments);
            case "exit":
                requireNoArguments(commandName, arguments);
                System.out.println("Завершение клиентского приложения.");
                return false;
            case "save":
                requireNoArguments(commandName, arguments);
                System.out.println("Команда save доступна только в серверной консоли.");
                return true;
            case "info":
                requireNoArguments(commandName, arguments);
                return send(CommandRequest.simple(CommandType.INFO));
            case "show":
                requireNoArguments(commandName, arguments);
                return send(CommandRequest.simple(CommandType.SHOW));
            case "add":
                requireNoArguments(commandName, arguments);
                FlatData newFlat = flatReader.readFlatData(input);
                return send(CommandRequest.add(newFlat));
            case "update":
                long updateId = parseSingleLongArgument(arguments, "id");
                FlatData updatedFlat = flatReader.readFlatData(input);
                return send(CommandRequest.update(updateId, updatedFlat));
            case "remove_by_id":
                return send(CommandRequest.withId(CommandType.REMOVE_BY_ID, parseSingleLongArgument(arguments, "id")));
            case "clear":
                requireNoArguments(commandName, arguments);
                return send(CommandRequest.simple(CommandType.CLEAR));
            case "head":
                requireNoArguments(commandName, arguments);
                return send(CommandRequest.simple(CommandType.HEAD));
            case "remove_head":
                requireNoArguments(commandName, arguments);
                return send(CommandRequest.simple(CommandType.REMOVE_HEAD));
            case "min_by_central_heating":
                requireNoArguments(commandName, arguments);
                return send(CommandRequest.simple(CommandType.MIN_BY_CENTRAL_HEATING));
            case "count_less_than_central_heating":
                return send(CommandRequest.withBoolean(
                        CommandType.COUNT_LESS_THAN_CENTRAL_HEATING,
                        parseSingleBooleanArgument(arguments, "centralHeating")
                ));
            case "filter_contains_name":
                return send(CommandRequest.withText(
                        CommandType.FILTER_CONTAINS_NAME,
                        requireStringArgument(arguments, "name")
                ));
            default:
                System.out.println("Неизвестная команда: " + commandName + ". Введите help для справки.");
                return true;
        }
    }

    private boolean send(CommandRequest request) {
        try {
            CommandResponse response = transport.send(request);
            responsePrinter.print(response);
        } catch (NetworkException exception) {
            System.out.println("Сервер временно недоступен: " + exception.getMessage());
        }
        return true;
    }

    private boolean executeScript(String arguments) {
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
                running = executeLine(scriptLine, scriptInput);
            }
            return running;
        } catch (FileNotFoundException exception) {
            System.out.println("Не удалось открыть скрипт: " + exception.getMessage());
            return true;
        } finally {
            runningScripts.remove(scriptKey);
        }
    }

    private void printHelp() {
        System.out.println("Доступные команды:");
        for (String description : CommandDescriptions.clientCommands()) {
            System.out.println(description);
        }
    }

    private void printHistory() {
        List<String> commands = history.values();
        if (commands.isEmpty()) {
            System.out.println("История команд пуста.");
            return;
        }
        for (String command : commands) {
            System.out.println(command);
        }
    }

    private void requireNoArguments(String commandName, String arguments) {
        if (!arguments.isEmpty()) {
            throw new IllegalArgumentException("Команда " + commandName + " не принимает аргументы");
        }
    }

    private long parseSingleLongArgument(String arguments, String argumentName) {
        String raw = requireSingleToken(arguments, argumentName);
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(argumentName + " должен быть целым числом");
        }
    }

    private boolean parseSingleBooleanArgument(String arguments, String argumentName) {
        String raw = requireSingleToken(arguments, argumentName).toLowerCase();
        if ("true".equals(raw) || "yes".equals(raw) || "да".equals(raw)) {
            return true;
        }
        if ("false".equals(raw) || "no".equals(raw) || "нет".equals(raw)) {
            return false;
        }
        throw new IllegalArgumentException(argumentName + " должен быть true или false");
    }

    private String requireSingleToken(String arguments, String argumentName) {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new IllegalArgumentException("Не указан аргумент " + argumentName);
        }
        String[] tokens = arguments.trim().split("\\s+");
        if (tokens.length != 1) {
            throw new IllegalArgumentException("Аргумент " + argumentName + " должен быть один");
        }
        return tokens[0];
    }

    private String requireStringArgument(String arguments, String argumentName) {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new IllegalArgumentException("Не указан аргумент " + argumentName);
        }
        return arguments.trim();
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
