package input;

import java.util.Scanner;

/**
 * Небольшая обертка над {@link Scanner}.
 *
 * <p>Она нужна, чтобы один и тот же ввод работал и из консоли, и из скрипта.</p>
 */
public class InputContext {
    private final Scanner scanner;
    private final boolean interactive;
    private final String sourceName;

    /**
     * Создает контекст ввода.
     *
     * @param scanner источник строк
     * @param interactive является ли ввод интерактивным
     * @param sourceName имя источника для сообщений об ошибках
     */
    public InputContext(Scanner scanner, boolean interactive, String sourceName) {
        this.scanner = scanner;
        this.interactive = interactive;
        this.sourceName = sourceName;
    }

    /**
     * Возвращает Scanner текущего источника.
     *
     * @return используемый Scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Показывает, является ли ввод интерактивным.
     *
     * @return {@code true}, если ввод идет от пользователя
     */
    public boolean isInteractive() {
        return interactive;
    }

    /**
     * Возвращает имя источника ввода.
     *
     * @return имя источника ввода
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * Читает строку, предварительно выводя приглашение в интерактивном режиме.
     *
     * @param inputMessage приглашение к вводу
     * @return прочитанная строка
     * @throws InputReadException если строк больше нет
     */
    public String readLine(String inputMessage) throws InputReadException {
        if (interactive) {
            System.out.print(inputMessage);
        }
        if (!scanner.hasNextLine()) {
            throw new InputReadException("Неожиданный конец ввода: " + sourceName);
        }
        return scanner.nextLine();
    }
}
