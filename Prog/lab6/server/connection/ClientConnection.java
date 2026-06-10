package server.connection;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class ClientConnection {
    private final SocketChannel channel;
    private final SocketAddress remoteAddress;
    private final ByteBuffer lengthBuffer = ByteBuffer.allocate(Integer.BYTES);
    private ByteBuffer bodyBuffer;
    private ByteBuffer writeBuffer;


    public ClientConnection(SocketChannel channel) throws java.io.IOException {
        this.channel = channel;
        this.remoteAddress = channel.getRemoteAddress();
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public ByteBuffer getLengthBuffer() {
        return lengthBuffer;
    }

    public ByteBuffer getBodyBuffer() {
        return bodyBuffer;
    }

    public void setBodyBuffer(ByteBuffer bodyBuffer) {
        this.bodyBuffer = bodyBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }

    public void setWriteBuffer(ByteBuffer writeBuffer) {
        this.writeBuffer = writeBuffer;
    }
}
