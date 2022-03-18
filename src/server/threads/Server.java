package server.threads;

import server.service.DataBase;
import server.service.ThreadInfo;
import state.CollectionStorage;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

private static int port;
private static DatagramSocket socket;
private final static Scanner serviceIn = new Scanner(System.in);
private static final String LOCALHOST = "localhost";

private static final ExecutorService processing = Executors.newFixedThreadPool(8);
private static final ExecutorService sending = Executors.newCachedThreadPool();

    public static void main(String[] args){


/*        if (NumberUtils.isParsable(args[0]))
            port = Integer.parseInt(args[0]);
        else port = 8090;*/

        port = 8090;

        try{
            configureServer();
        } catch (SocketException e) {
            System.out.println("Порт " + port + " занят, сервер не может быть запущен.");
            return;
        }

        byte[] requestBytes = new byte[65000];
        DatagramPacket data = new DatagramPacket(requestBytes, requestBytes.length);

        String serviceLine;
        while (true){
            try {
                if (System.in.available() != 0) {
                serviceLine = serviceIn.nextLine();
                    if (serviceLine.equals("exit")) {
                        DataBase.saveCollection(CollectionStorage.storage.getAllCollection());
                        socket.close();
                        processing.shutdown();
                        sending.shutdown();
                        if (processing.awaitTermination(2, TimeUnit.SECONDS))
                            System.out.println("Поток обработки успешно завершён");
                        else {
                            processing.shutdownNow();
                            System.out.println("Поток обработки принудительно завершён");
                        }
                        if (sending.awaitTermination(2, TimeUnit.SECONDS))
                            System.out.println("Поток отправки успешно завершён");
                        else {
                            sending.shutdownNow();
                            System.out.println("Поток отправки принудительно завершён");
                        }
                        return;
                    }
                }

                socket.receive(data);

                InetAddress address = data.getAddress();
                port = data.getPort();

                ThreadInfo info = new ThreadInfo(socket, address, port);
                Thread receptionThread = new Thread(new ReceptionThread(info, data, processing, sending));
                receptionThread.start();

            } catch (IOException e){
                System.out.println("Ошибка: " + e.getMessage());

            } catch (InterruptedException e) {
                System.out.println("Ошибка прерывания потока.");
            }
        }
    }

    private static void configureServer() throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("Сервер запущен. Port: " + port);
    }
}
