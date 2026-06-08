package app;

import collection.CollectionManager;
import collection.FlatCollection;
import command.CommandInterpreter;
import input.FlatInputReader;
import input.FlatReader;
import model.Flat;
import storage.FlatStorage;
import storage.JsonStorage;

import java.util.List;

/**
 * Класс, который собирает приложение перед запуском.
 *
 * <p>Здесь создаются хранилище, коллекция, считыватель объектов и обработчик
 * команд. Так в `Main` остается только старт программы.</p>
 */
public class ConsoleApplication {
    private final String fileName;

    /**
     * Запоминает имя файла, с которым будет работать программа.
     *
     * @param fileName путь к JSON-файлу коллекции
     */
    public ConsoleApplication(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Загружает коллекцию из файла и запускает команды.
     */
    public void run() {
        FlatStorage storage = new JsonStorage(fileName);
        List<Flat> loadedFlats = storage.load();
        FlatCollection collection = new CollectionManager(loadedFlats);
        FlatReader flatReader = new FlatInputReader();

        System.out.println("Загружено элементов: " + collection.size());
        CommandInterpreter interpreter = new CommandInterpreter(collection, storage, flatReader);
        interpreter.runInteractive();
    }
}
