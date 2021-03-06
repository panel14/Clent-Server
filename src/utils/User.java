package utils;

import java.io.Serializable;

/**
 * Класс для хранения информации о пользователе
 * */
public class User implements Serializable {

    private static final long serialVersionUID = 50L;

    public String login;
    public String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
