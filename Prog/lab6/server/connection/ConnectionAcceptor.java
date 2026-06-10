package server.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class ConnectionAcceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionAcceptor.class);

    private final ServerSocketChannel serverChannel;


    public ConnectionAcceptor(ServerSocketChannel serverChannel) {
        this.serverChannel = serverChannel;
    }


    public void acceptReadyConnections(Selector selector) throws IOException {
        SocketChannel clientChannel;
        while ((clientChannel = serverChannel.accept()) != null) {
            clientChannel.configureBlocking(false);
            ClientConnection connection = new ClientConnection(clientChannel);
            clientChannel.register(selector, SelectionKey.OP_READ, connection);
            LOGGER.info("Получено новое подключение: {}", connection.getRemoteAddress());
        }
    }
}
