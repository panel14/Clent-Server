package server.threads;

import command.Command;
import command.CommandFactory;
import command.CommandType;
import utils.Pair;
import server.service.DataBase;
import server.service.ThreadInfo;
import utils.Request;
import utils.Response;
import utils.User;

import java.util.concurrent.ExecutorService;

public class ProcessingThread implements Runnable {

    private final ThreadInfo info;
    private final ExecutorService sending;
    private final Request request;

    public ProcessingThread(ThreadInfo info, ExecutorService sending, Request request) {
        this.info = info;
        this.sending = sending;
        this.request = request;
    }

    @Override
    public void run() {
        Response response;
        if (request.getType() == CommandType.AUTH){
            User user = request.getUser();
            Pair<Boolean, String> answer = DataBase.isAuthUser(user);
            response = new Response(request.getType(), answer.getValue(), answer.getKey());

        } else if (request.getType() == CommandType.REGISTER) {
            User user = request.getUser();;
            Pair<Boolean, String> answer = DataBase.registerUser(user);
            response = new Response(request.getType(), answer.getValue(), answer.getKey());
        }
        else {
            Pair<Boolean, String> isAuthUser = DataBase.isAuthUser(request.getUser());
            if (isAuthUser.getKey())
                response = processRequest(request);
            else response = new Response(request.getType(), "Запрещено выполнение команды " +
                    "неавторизованному пользователю", false);
        }

        sending.execute(new SendingThread(info, response));
        System.out.println("Поток " + Thread.currentThread().getName() + ": получен ответ " +
                response.getServerAnswer().trim());
    }

    private Response processRequest(Request request) {

        String[] args = (request.getCommandInfo() == null) ? null : request.getCommandInfo();
        Command command = CommandFactory.createCommand(request.getType(), args, request.getUser());
        String result = command.execute();
        return new Response(request.getType(), result, true);
    }
}
