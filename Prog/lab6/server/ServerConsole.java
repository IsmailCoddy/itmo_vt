package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class ServerConsole {
    private final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.UTF_8)
    );


    public ServerConsoleCommand pollCommand() throws IOException {
        if (!reader.ready()) {
            return null;
        }

        String line = reader.readLine();
        if (line == null) {
            return ServerConsoleCommand.EXIT;
        }

        String command = line.trim().toLowerCase();
        if (command.isEmpty()) {
            return null;
        }
        if ("save".equals(command)) {
            return ServerConsoleCommand.SAVE;
        }
        if ("exit".equals(command)) {
            return ServerConsoleCommand.EXIT;
        }
        if ("help".equals(command)) {
            return ServerConsoleCommand.HELP;
        }
        return ServerConsoleCommand.UNKNOWN;
    }
}
