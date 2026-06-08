package command;

import input.InputContext;
import input.InputReadException;

/**
 * Общий интерфейс для всех консольных команд.
 *
 * <p>У каждой команды есть имя, описание для `help` и метод выполнения.</p>
 */
public interface Command {
    /**
     * Возвращает имя команды.
     *
     * @return имя команды
     */
    String getName();

    /**
     * Возвращает строку справки.
     *
     * @return описание команды
     */
    String getDescription();

    /**
     * Выполняет команду.
     *
     * @param arguments аргументы из той же строки, что и имя команды
     * @param input текущий источник ввода
     * @return {@code true}, если приложение должно продолжать работу
     * @throws InputReadException если во время чтения составного объекта закончился ввод
     */
    boolean execute(String arguments, InputContext input) throws InputReadException;
}
