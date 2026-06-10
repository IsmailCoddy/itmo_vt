package server;


public class ServerMain {
    private static final int DEFAULT_PORT = 5555;

    private ServerMain() {
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите JSON-файл первым аргументом.");
            System.out.println("Пример: mvn exec:java -Dexec.mainClass=server.ServerMain -Dexec.args=\"data.json 5555\"");
            return;
        }

        int port = DEFAULT_PORT;
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
                if (port <= 0 || port > 65535) {
                    System.out.println("Ошибка: порт должен быть от 1 до 65535.");
                    return;
                }
            } catch (NumberFormatException exception) {
                System.out.println("Ошибка: порт должен быть целым числом.");
                return;
            }
        }
        if (args.length > 2) {
            System.out.println("Предупреждение: лишние аргументы будут проигнорированы.");
        }

        new ServerApplication(args[0], port).run();
    }
}
