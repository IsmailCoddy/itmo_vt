package server;

import collection.CollectionManager;
import collection.FlatCollection;
import common.dto.CommandRequest;
import common.dto.CommandResponse;
import common.net.NetworkException;
import model.Flat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.command.ServerCommandProcessor;
import server.connection.ClientConnection;
import server.connection.ConnectionAcceptor;
import server.connection.RequestReader;
import server.connection.ResponseWriter;
import storage.FlatStorage;
import storage.JsonStorage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.List;


public class ServerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);
    private static final int SELECT_TIMEOUT_MILLIS = 500;

    private final String fileName;
    private final int port;

    private ServerCommandProcessor processor;
    private boolean running = true;


    public ServerApplication(String fileName, int port) {
        this.fileName = fileName;
        this.port = port;
    }


    public void run() {
        FlatStorage storage = new JsonStorage(fileName);
        List<Flat> loadedFlats = storage.load();
        FlatCollection collection = new CollectionManager(loadedFlats);
        processor = new ServerCommandProcessor(collection, storage);

        LOGGER.info("Сервер запускается. Файл: {}, порт: {}, элементов загружено: {}", fileName, port, collection.size());
        System.out.println("Сервер запущен. Порт: " + port);
        System.out.println("Серверные команды: save, exit, help");

        try (
                Selector selector = Selector.open();
                ServerSocketChannel serverChannel = ServerSocketChannel.open()
        ) {
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            ConnectionAcceptor acceptor = new ConnectionAcceptor(serverChannel);
            RequestReader requestReader = new RequestReader();
            ResponseWriter responseWriter = new ResponseWriter();
            ServerConsole console = new ServerConsole();

            while (running) {
                selector.select(SELECT_TIMEOUT_MILLIS);
                processSelectedKeys(selector, acceptor, requestReader, responseWriter);
                processServerConsole(console);
            }
        } catch (IOException exception) {
            LOGGER.error("Ошибка работы сервера", exception);
            System.out.println("Ошибка работы сервера: " + exception.getMessage());
        } finally {
            String saveMessage = processor.saveCollection();
            LOGGER.info("Завершение сервера. {}", saveMessage);
            System.out.println(saveMessage);
        }
    }

    private void processSelectedKeys(
            Selector selector,
            ConnectionAcceptor acceptor,
            RequestReader requestReader,
            ResponseWriter responseWriter
    ) {
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();

            if (!key.isValid()) {
                continue;
            }

            try {
                if (key.isAcceptable()) {
                    acceptor.acceptReadyConnections(selector);
                } else if (key.isReadable()) {
                    readAndProcessRequest(key, requestReader, responseWriter);
                } else if (key.isWritable()) {
                    writeResponse(key, responseWriter);
                }
            } catch (IOException | NetworkException exception) {
                LOGGER.warn("Ошибка обработки клиента", exception);
                closeKey(key);
            }
        }
    }

    private void readAndProcessRequest(
            SelectionKey key,
            RequestReader requestReader,
            ResponseWriter responseWriter
    ) throws IOException, NetworkException {
        ClientConnection connection = (ClientConnection) key.attachment();
        CommandRequest request = requestReader.read(connection);
        if (request == null) {
            return;
        }

        CommandResponse response = processor.execute(request);
        LOGGER.info("Команда {} обработана. Успех: {}", request.getType(), response.isSuccess());
        responseWriter.prepare(connection, response);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void writeResponse(SelectionKey key, ResponseWriter responseWriter) throws IOException {
        ClientConnection connection = (ClientConnection) key.attachment();
        if (responseWriter.write(connection)) {
            closeKey(key);
        }
    }

    private void processServerConsole(ServerConsole console) {
        try {
            ServerConsoleCommand command = console.pollCommand();
            if (command == null) {
                return;
            }

            switch (command) {
                case SAVE:
                    String saveMessage = processor.saveCollection();
                    LOGGER.info("Выполнена серверная команда save. {}", saveMessage);
                    System.out.println(saveMessage);
                    break;
                case EXIT:
                    LOGGER.info("Получена серверная команда exit");
                    running = false;
                    break;
                case HELP:
                    System.out.println("Серверные команды:");
                    System.out.println("save : сохранить коллекцию в файл");
                    System.out.println("exit : сохранить коллекцию и завершить сервер");
                    System.out.println("help : вывести справку по серверным командам");
                    break;
                case UNKNOWN:
                    System.out.println("Неизвестная серверная команда. Доступны: save, exit, help");
                    break;
                default:
                    break;
            }
        } catch (IOException exception) {
            LOGGER.warn("Не удалось прочитать команду серверной консоли", exception);
        }
    }

    private void closeKey(SelectionKey key) {
        try {
            key.channel().close();
        } catch (IOException exception) {
            LOGGER.warn("Не удалось закрыть канал клиента", exception);
        }
        key.cancel();
    }
}
