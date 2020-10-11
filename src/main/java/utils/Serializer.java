package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Serializer {

    public static byte[] Serialize(Object object) throws IOException {
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        ObjectOutputStream ostream = new ObjectOutputStream(bstream);
        ostream.writeObject(object);
        ostream.flush();
        ostream.close();

        return bstream.toByteArray();
    }

    public static Object Deserialize(byte[] data) throws IOException, ClassNotFoundException {
        Object result = null;

        ByteArrayInputStream bstream = new ByteArrayInputStream(data);
        ObjectInputStream ostream = new ObjectInputStream(bstream);

        result = ostream.readObject();

        ostream.close();

        return result;
    }
}