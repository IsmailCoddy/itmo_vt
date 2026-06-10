package client;


public class ClientMain {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 5555;

    private ClientMain() {
    }


    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;

        if (args.length >= 1) {
            host = args[0];
        }
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

        new ClientApplication(host, port).run();
    }
}
