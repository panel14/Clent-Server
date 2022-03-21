package server.threads;

import server.service.ThreadInfo;
import utils.Request;
import utils.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.ExecutorService;

public class ReceptionThread implements Runnable {

    private final ThreadInfo info;
    private final byte[] data;

    private final ExecutorService processing;
    private final ExecutorService sending;

    public ReceptionThread(ThreadInfo info, byte[] data, ExecutorService processing, ExecutorService sending) {
        this.info = info;
        this.data = data;
        this.processing = processing;
        this.sending = sending;
    }

    @Override
    public void run() {
        try {
            Request request = (Request) Serializer.deserialize(data);
            processing.execute(new ProcessingThread(info, sending, request));
            System.out.println("NEW_THREAD_CHAIN\nПоток " + Thread.currentThread().getName() + ": получена команда " +
                    request.getType());

        } catch (IOException | ClassNotFoundException e){
            System.out.println(Thread.currentThread().getName() + ": Ошибка десериализации запроса.");
        }
    }
}
