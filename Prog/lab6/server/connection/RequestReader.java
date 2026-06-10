package server.connection;

import common.dto.CommandRequest;
import common.net.MessageFrame;
import common.net.NetworkException;
import common.net.ObjectSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;


public class RequestReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestReader.class);


    public CommandRequest read(ClientConnection connection) throws IOException, NetworkException {
        while (true) {
            ByteBuffer target = currentTarget(connection);
            int read = connection.getChannel().read(target);
            if (read == -1) {
                throw new IOException("Клиент закрыл соединение до отправки полного запроса");
            }
            if (read == 0) {
                return null;
            }

            if (!target.hasRemaining()) {
                if (connection.getBodyBuffer() == null) {
                    prepareBodyBuffer(connection);
                } else {
                    return deserializeRequest(connection);
                }
            }
        }
    }

    private ByteBuffer currentTarget(ClientConnection connection) {
        if (connection.getBodyBuffer() == null) {
            return connection.getLengthBuffer();
        }
        return connection.getBodyBuffer();
    }

    private void prepareBodyBuffer(ClientConnection connection) throws NetworkException {
        ByteBuffer lengthBuffer = connection.getLengthBuffer();
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();
        if (length <= 0 || length > MessageFrame.MAX_MESSAGE_SIZE) {
            throw new NetworkException("Некорректная длина сетевого сообщения: " + length);
        }
        connection.setBodyBuffer(ByteBuffer.allocate(length));
    }

    private CommandRequest deserializeRequest(ClientConnection connection) throws NetworkException {
        ByteBuffer bodyBuffer = connection.getBodyBuffer();
        bodyBuffer.flip();
        byte[] bytes = new byte[bodyBuffer.remaining()];
        bodyBuffer.get(bytes);
        try {
            Object object = ObjectSerializer.deserialize(bytes);
            if (!(object instanceof CommandRequest)) {
                throw new NetworkException("В запросе пришел объект другого класса");
            }
            CommandRequest request = (CommandRequest) object;
            LOGGER.info("Получен новый запрос {} от {}", request.getType(), connection.getRemoteAddress());
            return request;
        } catch (IOException | ClassNotFoundException exception) {
            throw new NetworkException("Не удалось десериализовать запрос", exception);
        }
    }
}
