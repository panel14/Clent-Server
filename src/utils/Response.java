package utils;

import command.CommandType;

import java.io.Serializable;


public class Response implements Serializable {

    private static final long serialVersionUID = 40L;

    private CommandType type;
    private String serverAnswer;
    private boolean isGucci;

    public Response(CommandType type, String serverAnswer, boolean isGucci){
        this.type = type;
        this.serverAnswer = serverAnswer;
        this.isGucci= isGucci;
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

    public boolean isGucci() {
        return isGucci;
    }

    public void setGucci(boolean gucci) {
        isGucci = gucci;
    }
}
