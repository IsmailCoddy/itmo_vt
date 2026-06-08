import app.ConsoleApplication;

/**
 * Главный класс программы.
 *
 * <p>Тут только проверяется аргумент с файлом и запускается основное приложение.
 * Основной код вынесен в другие классы, чтобы в `main` не было каши.</p>
 */
public class Main {
    private Main() {
    }

    /**
     * Запускает программу.
     *
     * @param args аргументы командной строки; первый аргумент - путь к файлу
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите имя JSON-файла первым аргументом командной строки.");
            System.out.println("Пример запуска: java Main data.json");
            return;
        }

        if (args.length > 1) {
            System.out.println("Предупреждение: лишние аргументы командной строки будут проигнорированы.");
        }

        ConsoleApplication application = new ConsoleApplication(args[0]);
        application.run();
    }
}
