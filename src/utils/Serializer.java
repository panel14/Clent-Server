package utils;

import java.io.*;

/**
 * Класс для сериализации и десериализации объектов
 * */
public class Serializer {

    /**
     * Метод сериализации объекта
     * @param command - сериализуемый объект
     * @return byte[] - сериализованное байтовое представление объекта
     * */
    public static byte[] serialize(Object command) throws IOException {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(bytes);
        objStream.writeObject(command);

        return bytes.toByteArray();
    }

    /**
     * Метод десериализации объекта
     * @param bytes - сериализованное байтовое представление объекта
     * @return Object - десереализованный объект
     * */
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
        ObjectInputStream objStream = new ObjectInputStream(bytesIn);

        return objStream.readObject();
    }
}
