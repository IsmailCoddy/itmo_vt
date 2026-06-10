package client.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class CommandHistory {
    private static final int HISTORY_LIMIT = 6;
    private final Deque<String> commands = new ArrayDeque<>();


    public void add(String commandName) {
        if (commandName == null || commandName.trim().isEmpty()) {
            return;
        }
        commands.addLast(commandName);
        while (commands.size() > HISTORY_LIMIT) {
            commands.removeFirst();
        }
    }


    public List<String> values() {
        return new ArrayList<>(commands);
    }
}
