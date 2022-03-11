package command;

import java.io.Serializable;

public class HelpCommand extends Command implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String execute() {
        return "help - справка по всем командам\n" +
                "info - справка по коллекции (тип, дата создания, количество элементов)\n" +
                "show - вывести все элементы коллекции\n" +
                "insert null {element} - добавить новый элемент с заданным ключом\n" +
                "update id {element} - обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_key null - удалить элемент из коллекции по его ключу\n" +
                "clear - очистить коллекцию\n" +
                "execute_script file_name - считать и исполнить скрипт из указанного файла\n" +
                "exit - завершить программу (клиент)\n" +
                "remove_greater {element} - удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} - удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "history - вывести последние 8 команд (без аргументов)\n" +
                "average_of_students_count - вывести среднее значение поля studentsCount для всех элементов коллекции\n" +
                "filter_starts_with_name name - вывести элементы, значение поля name которых начинается с заданной подстроки\n" +
                "print_descending - вывести элементы коллекции в порядке убывания\n";
    }
}
