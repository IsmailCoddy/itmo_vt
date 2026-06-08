package command;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Список всех доступных команд.
 *
 * <p>Команды складываются в `Map`, чтобы потом быстро найти нужную по имени.</p>
 */
public class CommandRegistry {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    /**
     * Создает пустой список команд.
     */
    public CommandRegistry() {
    }

    /**
     * Регистрирует команду.
     *
     * @param command команда
     */
    public void register(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    /**
     * Ищет команду по имени.
     *
     * @param commandName имя команды
     * @return команда или {@code null}
     */
    public Command find(String commandName) {
        return commands.get(commandName.toLowerCase());
    }

    /**
     * Возвращает все команды в порядке регистрации.
     *
     * @return доступные команды
     */
    public Collection<Command> allCommands() {
        return Collections.unmodifiableCollection(commands.values());
    }
}
