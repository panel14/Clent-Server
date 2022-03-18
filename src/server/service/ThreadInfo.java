package server.service;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class ThreadInfo {
    public DatagramSocket socket;
    public InetAddress address;
    public int port;

    public ThreadInfo(DatagramSocket socket, InetAddress address, int port) {
        this.socket = socket;
        this.address = address;
        this.port = port;
    }
}
