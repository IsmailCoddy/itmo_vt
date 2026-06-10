package input;

import java.util.Scanner;


public class InputContext {
    private final Scanner scanner;
    private final boolean interactive;
    private final String sourceName;


    public InputContext(Scanner scanner, boolean interactive, String sourceName) {
        this.scanner = scanner;
        this.interactive = interactive;
        this.sourceName = sourceName;
    }


    public Scanner getScanner() {
        return scanner;
    }


    public boolean isInteractive() {
        return interactive;
    }


    public String getSourceName() {
        return sourceName;
    }


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
