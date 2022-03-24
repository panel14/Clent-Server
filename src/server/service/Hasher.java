package server.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Класс для получения хеш-строки из пароля пользователя
 * */
public class Hasher {
    public static final String ALGO = "SHA-512";

    /**
     * Метод, кодирует строку алгоритмом SHA-512
     * @param string - строка для кодирования
     * */
    public static String encodeStringSHA512(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGO);
            byte[] digestByte = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            BigInteger valueRepresent = new BigInteger(1, digestByte);
            StringBuilder hash = new StringBuilder(valueRepresent.toString(16));
            while (hash.length() < 32)
                hash.insert(0, "0");
            return hash.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Ошибка хеширования: " + e.getMessage());
            return null;
        }
    }
}
