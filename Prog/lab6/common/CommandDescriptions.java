package common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class CommandDescriptions {
    private static final List<String> CLIENT_COMMANDS = Collections.unmodifiableList(Arrays.asList(
            "help : вывести справку по доступным командам",
            "info : вывести информацию о коллекции",
            "show : вывести все элементы коллекции",
            "add : добавить новый элемент",
            "update id : обновить элемент по id",
            "remove_by_id id : удалить элемент по id",
            "clear : очистить коллекцию",
            "execute_script file_name : выполнить команды из файла",
            "exit : завершить клиентское приложение",
            "head : вывести первый элемент коллекции",
            "remove_head : вывести и удалить первый элемент коллекции",
            "history : вывести последние 6 команд без аргументов",
            "min_by_central_heating : вывести элемент с минимальным centralHeating",
            "count_less_than_central_heating centralHeating : посчитать элементы с меньшим centralHeating",
            "filter_contains_name name : вывести элементы, имя которых содержит подстроку"
    ));

    private CommandDescriptions() {
    }

    public static List<String> clientCommands() {
        return CLIENT_COMMANDS;
    }
}
