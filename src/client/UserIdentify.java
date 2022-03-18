package client;

import command.CommandType;
import utils.Request;
import utils.Response;
import utils.User;

import java.util.Scanner;

public class UserIdentify {
    private static final Scanner in = new Scanner(System.in);

    public static User getUser() {
        System.out.println("Чтобы пользоваться предложением, введите логин и пароль\n" +
                "Если вы не зарегистрированы, введите \"reg\"\nЕсли вы являетесь зарегистрированным пользователем - " +
                "введите \"auth\"");
        while (true){
            System.out.println("Команда:");
            String mode = in.nextLine();

            String login;
            String loginRegex = "^[a-zA-z0-9_]{3,16}$";
            System.out.print("Введите логин.\nЛогин может содержать строчные и заглавные латинские буквы," +
                    "цифры и знак \"_\". Длина логина от 3 до 16 символов:\n");
            while (isInvalid(login = in.nextLine(), loginRegex))
                System.out.println("Неверный формат ввода логина. Повторите попытку:");

            String password;
            String passwordRegex = "^.{5,20}$";
            System.out.print("Введите пароль. Длина пароля от 8 до 20 символов:\n");
            while (isInvalid(password = in.nextLine(), passwordRegex))
                System.out.println("Неверный формат ввода пароля. Повторите попытку:");

            switch (mode) {
                case "reg":
                    User newUser =  getUser("registration", new User(login, password));
                    if (newUser == null) continue;
                    else return newUser;
                case "auth":
                    User old =  getUser("authentication", new User(login, password));
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
        Response response = Client.receiveAnswer();
        if (response == null || !response.isGucci())
           return null;
        else return user;
    }

    private static boolean isInvalid(String login, String regex) {
        if (login.equals("")) return true;
        return !login.matches(regex);
    }
}
