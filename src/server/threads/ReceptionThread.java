package server.threads;

import server.service.ThreadInfo;
import utils.Request;
import utils.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.ExecutorService;

public class ReceptionThread implements Runnable {

    private ThreadInfo info;
    private DatagramPacket packet;

    private ExecutorService processing;
    private ExecutorService sending;



    public ReceptionThread(ThreadInfo info, DatagramPacket packet, ExecutorService processing, ExecutorService sending) {
        this.info = info;
        this.packet = packet;
        this.processing = processing;
        this.sending = sending;
    }

    @Override
    public void run() {
        try {
            Request request = (Request) Serializer.deserialize(packet.getData());
            processing.execute(new ProcessingThread(info, sending, request));
            System.out.println("NEW_THEAD_CHAIN\nПоток " + Thread.currentThread().getName() + ": получена команда " +
                    request.getType());

        } catch (IOException | ClassNotFoundException e){
            System.out.println(Thread.currentThread().getName() + ": Ошибка десериализации запроса.");
        }
    }
}
