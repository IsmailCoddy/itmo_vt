package client;

import client.command.ClientCommandInterpreter;
import client.net.ClientTransport;
import input.FlatInputReader;
import input.InputContext;

import java.util.Scanner;


public class ClientApplication {
    private final String host;
    private final int port;


    public ClientApplication(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run() {
        ClientTransport transport = new ClientTransport(host, port);
        ClientCommandInterpreter interpreter = new ClientCommandInterpreter(transport, new FlatInputReader());

        Scanner scanner = new Scanner(System.in);
        InputContext input = new InputContext(scanner, true, "console");
        boolean running = true;

        System.out.println("Клиент запущен. Сервер: " + host + ":" + port);
        System.out.println("Введите help, чтобы увидеть список команд.");
        while (running) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                System.out.println();
                break;
            }
            running = interpreter.executeLine(scanner.nextLine(), input);
        }
    }
}
