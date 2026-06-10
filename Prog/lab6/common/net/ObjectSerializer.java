package common.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public final class ObjectSerializer {
    private ObjectSerializer() {
    }


    public static byte[] serialize(Serializable object) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(object);
        }
        return byteStream.toByteArray();
    }


    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objectStream.readObject();
        }
    }
}
