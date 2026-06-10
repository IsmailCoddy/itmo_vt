package server.connection;

import common.dto.CommandResponse;
import common.net.MessageFrame;
import common.net.NetworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;


public class ResponseWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseWriter.class);


    public void prepare(ClientConnection connection, CommandResponse response) throws NetworkException {
        connection.setWriteBuffer(MessageFrame.pack(response));
    }


    public boolean write(ClientConnection connection) throws IOException {
        ByteBuffer buffer = connection.getWriteBuffer();
        connection.getChannel().write(buffer);
        if (!buffer.hasRemaining()) {
            LOGGER.info("Ответ отправлен клиенту {}", connection.getRemoteAddress());
            return true;
        }
        return false;
    }
}
