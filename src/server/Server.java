package server;

import command.Command;
import command.CommandFactory;
import utils.Request;
import command.CommandType;
import utils.Response;
import utils.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

private final static int PORT = 8090;
private static DatagramSocket socket;

    public static void main(String[] args){

        try{
            configureServer();
        } catch (SocketException e) {
            System.out.println("Порт " + PORT + " занят, сервер не может быть запущен.");
            return;
        }

        byte[] requestBytes = new byte[65000];

        DatagramPacket data = new DatagramPacket(requestBytes, requestBytes.length);

        while (true){
            try {
                socket.receive(data);

                Request request = (Request) Serializer.deserialize(requestBytes);
                System.out.println("Server echo: command " + request.getType());
                Response answer = processRequest(request);

                CommandType type = answer.getType();

                if (type != CommandType.EXIT)
                    send(answer, data);
                else {
                    System.out.println("Работа сервера завершена");
                    return;
                }

            } catch (IOException | ClassNotFoundException e){
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void configureServer() throws SocketException {
        socket = new DatagramSocket(PORT);
        System.out.println("Сервер запущен. Port: " + PORT);
    }

    private static Response processRequest(Request request){

        String[] args = (request.getCommandInfo() == null) ? null : request.getCommandInfo();
        Command command = CommandFactory.createCommand(request.getType(), args);

        String result = command.execute();

        return new Response(request.getType(), result);
    }

    private static void send(Response response, DatagramPacket received){
        try{
            byte[] responseBytes = Serializer.serialize(response);
            DatagramPacket answer = new DatagramPacket(responseBytes, responseBytes.length,
                    received.getAddress(), received.getPort());
            socket.send(answer);

            System.out.println("Ответ отправлен клиенту.");

        } catch (IOException e){
            System.out.println("Ошибка отправления: " + e.getMessage());
        }

    }
}
