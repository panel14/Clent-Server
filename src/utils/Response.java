package utils;

import command.CommandType;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = 40L;

    private CommandType type;
    private String serverAnswer;

    public Response(CommandType type, String serverAnswer){
        this.type = type;
        this.serverAnswer = serverAnswer;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String getServerAnswer() {
        return serverAnswer;
    }

    public void setServerAnswer(String serverAnswer) {
        this.serverAnswer = serverAnswer;
    }
}
