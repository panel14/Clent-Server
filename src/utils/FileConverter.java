package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс для чтения файлов
 * */
public class FileConverter {

    /**
     * Метод, читает файл по указанному пути
     * @param path - путь к файлу
     * @return String - прочитанный файл, "#fail#" если файл прочитать не удалось
     * */
    public static String readFile(String path){

        StringBuilder file = new StringBuilder();
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(path))) {
            int data = in.read();
            char ch;
            while (data != -1) {
                ch = (char) data;
                file.append(ch);
                data = in.read();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "#fail#";
        }
        return file.toString();
    }
}
