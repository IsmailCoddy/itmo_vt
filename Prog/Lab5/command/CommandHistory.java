package command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Хранит историю последних команд.
 *
 * <p>Нужны только последние 6 команд и только их имена, без аргументов.</p>
 */
public class CommandHistory {
    private static final int HISTORY_LIMIT = 6;
    private final Deque<String> commands = new ArrayDeque<>();

    /**
     * Создает пустую историю команд.
     */
    public CommandHistory() {
    }

    /**
     * Добавляет команду в историю.
     *
     * @param commandName имя команды без аргументов
     */
    public void add(String commandName) {
        if (commandName == null || commandName.trim().isEmpty()) {
            return;
        }
        commands.addLast(commandName);
        while (commands.size() > HISTORY_LIMIT) {
            commands.removeFirst();
        }
    }

    /**
     * Возвращает сохраненные команды.
     *
     * @return команды в порядке от старой к новой
     */
    public List<String> values() {
        return new ArrayList<>(commands);
    }
}
