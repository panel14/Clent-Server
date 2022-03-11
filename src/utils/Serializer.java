package utils;

import java.io.*;

public class Serializer {

    public static byte[] serialize(Object command) throws IOException {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(bytes);
        objStream.writeObject(command);

        return bytes.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
        ObjectInputStream objStream = new ObjectInputStream(bytesIn);

        return objStream.readObject();
    }
}
