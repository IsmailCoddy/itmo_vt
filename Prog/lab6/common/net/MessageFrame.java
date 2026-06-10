package common.net;

import java.io.Serializable;
import java.nio.ByteBuffer;


public final class MessageFrame {
    public static final int LENGTH_BYTES = Integer.BYTES;
    public static final int MAX_MESSAGE_SIZE = 10 * 1024 * 1024;

    private MessageFrame() {
    }


    public static ByteBuffer pack(Serializable object) throws NetworkException {
        try {
            byte[] bytes = ObjectSerializer.serialize(object);
            if (bytes.length > MAX_MESSAGE_SIZE) {
                throw new NetworkException("Сообщение слишком большое: " + bytes.length + " байт");
            }
            ByteBuffer buffer = ByteBuffer.allocate(LENGTH_BYTES + bytes.length);
            buffer.putInt(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer;
        } catch (Exception exception) {
            if (exception instanceof NetworkException) {
                throw (NetworkException) exception;
            }
            throw new NetworkException("Не удалось подготовить объект к отправке", exception);
        }
    }
}
