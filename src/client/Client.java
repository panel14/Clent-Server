package client;

import utils.FileConverter;
import utils.Request;
import utils.Response;
import utils.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {

    final private static String LOCALHOST = "localhost";
    final private static int PORT = 8090;
    final public static int TIMEOUT = 10;

    private static SocketAddress address;
    private static DatagramChannel channel;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        address = new InetSocketAddress(LOCALHOST, PORT);
        try {

            channel = DatagramChannel.open();
            channel.configureBlocking(false);

            while (true){
                System.out.println("Введите команду:");

                String[] command = scanner.nextLine().split(" ");
                if (command[0].equals("exit")){
                    Request exitRequest = RequestManager.makeRequest(command);
                    send(exitRequest);
                    channel.close();
                    System.out.println("Завершение работы.");
                    return;
                }

                if (command[0].equals("execute_script")) {
                    if (command[1].equals("")) {
                        System.out.println("Неверный ввод команды.");
                        continue;
                    }
                    String commandsStr = FileConverter.readFile(command[1]);

                    if (commandsStr.equals("#fail#")) {
                        System.out.println("Не удалось считать файл по заданному пути.");
                        continue;
                    }

                    String[] commandsArr = commandsStr.split("\n");
                    for (String com : commandsArr){
                        Request scriptReq =RequestManager.makeRequest(com.split(" "));
                        if (scriptReq != null)
                            send(scriptReq);
                        else System.out.println("Неизвестная команда");

                        receiveAnswer();
                    }
                }

                Request order = RequestManager.makeRequest(command);
                if (order != null){
                    send(order);
                }
                else {
                    System.out.println("Неизвестная команда.");
                    continue;
                }
                receiveAnswer();

            }

        } catch (IOException e){
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void send(Request request){
        try {
            byte[] requestBytes = Serializer.serialize(request);
            ByteBuffer buffer = ByteBuffer.wrap(requestBytes);
            channel.send(buffer, address);                //TODO check channel for null
            buffer.clear();
        }
        catch (IOException e){
            System.out.println("Ошибка отправления: " + e.getMessage());
        }
    }

    private static void receiveAnswer(){
        try {
            boolean isReceived = false;
            ByteBuffer buffer = ByteBuffer.wrap(new byte[65000]);

            for (int i = 0; i < TIMEOUT; i++){
                address = channel.receive(buffer);

                if (address != null){
                    Response response = (Response) Serializer.deserialize(buffer.array());
                    System.out.println(response.getServerAnswer());
                    buffer.clear();

                    isReceived = true;
                    break;
                }

                Thread.sleep(1000);
            }

            if (!isReceived)
                System.out.println("Время ожидания истекло. Ответ от сервера не был получен.");

        } catch (IOException e){
            System.out.println("Ошибка приема сообщения: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка десериализации:" + e.getMessage());

        } catch (InterruptedException e) {
            System.out.println("Ошибка ожидания ответа: " + e.getMessage());
        }
    }
}
