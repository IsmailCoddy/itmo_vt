package command;

import input.InputContext;

/**
 * Интерфейс для выполнения одной строки команды.
 *
 * <p>Он нужен для скриптов: строка из файла выполняется так же, как строка из консоли.</p>
 */
public interface CommandLineExecutor {
    /**
     * Выполняет одну строку команды.
     *
     * @param line строка команды
     * @param input текущий источник ввода
     * @return {@code true}, если приложение должно продолжать работу
     */
    boolean executeLine(String line, InputContext input);
}
