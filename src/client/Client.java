package client;
import utils.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {

    final private static String LOCALHOST = "localhost";
    private static int port;
    final public static int TIMEOUT = 50;

    private static SocketAddress address;
    private static DatagramChannel channel;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

/*        if (NumberUtils.isParsable(args[0]))
            port = Integer.parseInt(args[0]);
        else port = 8090;*/

        port = 8090;

        address = new InetSocketAddress(LOCALHOST, port);
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);

            User user = UserIdentify.getUser();

            while (true){
                System.out.println("Введите команду:");

                String[] command = scanner.nextLine().split(" ");
                if (command[0].equals("exit")){
                    Request exitRequest = RequestManager.makeRequest(command, user);
                    send(exitRequest);
                    channel.close();
                    System.out.println("Завершение работы.");
                    return;
                }

                if (command[0].equals("back")){
                    Request outRequest = RequestManager.makeRequest(new String[]{"exit"}, user);
                    send(outRequest);
                    receiveAnswer();
                    user = UserIdentify.getUser();
                    continue;
                }

                if (command[0].equals("execute_script")) {
                    if (command[1].equals("")) {
                        System.out.println("Неверный ввод команды.");
                        continue;
                    }
                    String path = "src/resources/" + command[1];
                    String commandsStr = FileConverter.readFile(path);

                    if (commandsStr.equals("#fail#")) {
                        System.out.println("Не удалось считать файл по заданному пути.");
                        continue;
                    }

                    String[] commandsArr = commandsStr.split("\n");
                    for (String com : commandsArr) {
                        String trimmed = com.trim();
                        Request scriptReq = RequestManager.makeRequest(trimmed.split(" "), user);
                        if (scriptReq != null)
                            send(scriptReq);
                        else System.out.println("Неизвестная команда");
                        receiveAnswer();
                    }
                    continue;
                }

                Request order = RequestManager.makeRequest(command, user);
                if (order != null){
                    send(order);
                }
                else {
                    System.out.println("Неизвестная команда.");
                    continue;
                }
                Response response = receiveAnswer();
                if (response == null) user = UserIdentify.getUser();
            }

        } catch (IOException e){
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static void send(Request request){
        try {
            byte[] requestBytes = Serializer.serialize(request);
            ByteBuffer buffer = ByteBuffer.wrap(requestBytes);
            if (address == null)
                address = new InetSocketAddress(LOCALHOST, port);
            channel.send(buffer, address);
            buffer.clear();
        }
        catch (IOException e){
            System.out.println("Ошибка отправления: " + e.getMessage());
        }
    }

    public static Response receiveAnswer(){
        try {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[65000]);

            for (int i = 0; i < TIMEOUT; i++){
                address = channel.receive(buffer);

                if (address != null){
                    Response response = (Response) Serializer.deserialize(buffer.array());
                    System.out.println(response.getServerAnswer());
                    buffer.clear();

                    return response;
                }
                Thread.sleep(100);
            }

            System.out.println("Время ожидания истекло. Ответ от сервера не был получен.");

        } catch (IOException e){
            System.out.println("Ошибка приема сообщения: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка десериализации:" + e.getMessage());

        } catch (InterruptedException e) {
            System.out.println("Ошибка ожидания ответа: " + e.getMessage());
        }
        return null;
    }
}
