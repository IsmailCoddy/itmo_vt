package client.net;

import common.dto.CommandRequest;
import common.dto.CommandResponse;
import common.net.MessageFrame;
import common.net.NetworkException;
import common.net.ObjectSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class ClientTransport {
    private static final int CONNECT_TIMEOUT_MILLIS = 3000;
    private static final int READ_TIMEOUT_MILLIS = 7000;

    private final String host;
    private final int port;


    public ClientTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public CommandResponse send(CommandRequest request) throws NetworkException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), CONNECT_TIMEOUT_MILLIS);
            socket.setSoTimeout(READ_TIMEOUT_MILLIS);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            byte[] requestBytes = ObjectSerializer.serialize(request);
            output.writeInt(requestBytes.length);
            output.write(requestBytes);
            output.flush();

            int responseLength = input.readInt();
            if (responseLength <= 0 || responseLength > MessageFrame.MAX_MESSAGE_SIZE) {
                throw new NetworkException("Сервер вернул некорректную длину ответа: " + responseLength);
            }

            byte[] responseBytes = new byte[responseLength];
            input.readFully(responseBytes);
            Object object = ObjectSerializer.deserialize(responseBytes);
            if (!(object instanceof CommandResponse)) {
                throw new NetworkException("Сервер вернул объект другого класса");
            }
            return (CommandResponse) object;
        } catch (SocketTimeoutException exception) {
            throw new NetworkException("Сервер не ответил вовремя", exception);
        } catch (IOException exception) {
            throw new NetworkException("Сервер временно недоступен", exception);
        } catch (ClassNotFoundException exception) {
            throw new NetworkException("Не удалось прочитать класс ответа сервера", exception);
        }
    }
}
