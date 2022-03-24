package utils;

import command.CommandType;

import java.io.Serializable;
/**
 * Класс хранит всю информацию о запросе клиента.
 * Используется для обмена данными между клиентом и сервером
 * */
public class Request implements Serializable {

    private static final long serialVersionUID = 30L;

    private CommandType type;
    private String[] commandInfo;
    private User user;

    public Request(CommandType type) {
        this.type = type;
    }

    public Request(CommandType type, User user) {
        this.type = type;
        this.user = user;
    }

    public Request(CommandType type, String[] commandInfo, User user) {
        this.type = type;
        this.commandInfo = commandInfo;
        this.user = user;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String[] getCommandInfo() {
        return commandInfo;
    }

    public void setCommandInfo(String[] commandInfo) {
        this.commandInfo = commandInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
