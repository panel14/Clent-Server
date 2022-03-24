package client;

import command.CommandType;
import utils.Request;
import utils.Response;
import utils.User;

import java.util.Scanner;
/**
 * Класс идентификации пользователя
 * */
public class UserIdentify {
    private static final Scanner in = new Scanner(System.in);

    /**
     * Метод для получения данных пользователя
     * @return user - если пользователь подтверждён/зарегистрирован,
     *         null - если пользователь не был подтверждён или регистрация завершилась неудачей
     * */
    public static User getUser() {
        System.out.println("Чтобы пользоваться предложением, введите логин и пароль\n" +
                "Если вы не зарегистрированы, введите \"reg\"\nЕсли вы являетесь зарегистрированным пользователем - " +
                "введите \"auth\"\n" +
                "Для выхода из приложения введите \"exit\"");
        while (true){
            System.out.println("Команда:");
            String mode = in.nextLine();
            switch (mode) {
                case "reg":
                    String[] userData = getUserData();
                    User newUser =  getUser("registration", new User(userData[0], userData[1]));
                    if (newUser == null) continue;
                    else return newUser;
                case "auth":
                    userData = getUserData();
                    User old =  getUser("authentication", new User(userData[0], userData[1]));
                    if (old == null) continue;
                    else return old;
                default:
                    System.out.println("Неверный ввод команды. Повторите ввод");
                    break;
            }
        }
    }

    private static User getUser(String typeStr, User user){
        Request register = new Request(CommandType.getTypeByString(typeStr), user);
        Client.send(register);
        Response response = Client.receiveAnswer(true);
        if (response == null || !response.isGucci())
           return null;
        else return user;
    }

    private static boolean isInvalid(String login, String regex) {
        if (login.equals("")) return true;
        return !login.matches(regex);
    }

    private static String[] getUserData() {
        String login;
        String loginRegex = "^[a-zA-z0-9_]{3,16}$";
        System.out.print("Введите логин.\nЛогин может содержать строчные и заглавные латинские буквы," +
                "цифры и знак \"_\".\nДлина логина от 3 до 16 символов:\n");
        while (isInvalid(login = in.nextLine(), loginRegex))
            System.out.println("Неверный формат ввода логина. Повторите попытку:");

        String password;
        String passwordRegex = "^.{5,20}$";
        System.out.print("Введите пароль. Длина пароля от 8 до 20 символов:\n");
        while (isInvalid(password = in.nextLine(), passwordRegex))
            System.out.println("Неверный формат ввода пароля. Повторите попытку:");

        return new String[]{ login, password };
    }
}
