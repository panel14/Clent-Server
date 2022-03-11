package utils;

import command.CommandType;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 30L;

    private CommandType type;
    private String[] commandInfo;

    public Request(CommandType type){
        this.type = type;
    }

    public Request(CommandType type, String[] commandInfo) {
        this.type = type;
        this.commandInfo = commandInfo;
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
}
