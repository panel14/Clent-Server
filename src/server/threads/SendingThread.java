package server.threads;

import server.service.ThreadInfo;
import utils.Response;
import utils.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class SendingThread implements Runnable {

    private final ThreadInfo info;
    private final Response response;

    public SendingThread(ThreadInfo info, Response response){
        this.info = info;
        this.response = response;
    }

    @Override
    public void run() {
        send(response, info.socket);
        System.out.println("Поток " + Thread.currentThread().getName() + ": ответ отправлен клиенту");
    }

    private void send(Response response, DatagramSocket socket){
        try{
            byte[] responseBytes = Serializer.serialize(response);
            DatagramPacket answer = new DatagramPacket(responseBytes, responseBytes.length,
                    info.address, info.port);
            socket.send(answer);

        } catch (IOException e){
            System.out.println("Ошибка отправления: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }

    }
}
