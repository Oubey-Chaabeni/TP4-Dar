package server;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class server {
    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(1234));
        System.out.println("Server Started!");

        DateTimeFormatter format =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        Set<SocketAddress> clients = new HashSet<>();

        while (true) {

            byte[] buffer = new byte[1024];

            DatagramPacket packet = new DatagramPacket(
                    buffer,
                    buffer.length
            );

            socket.receive(packet);

            String message = new String(
                    packet.getData(),
                    0,
                    packet.getLength()
            );

            SocketAddress sender = packet.getSocketAddress();
            String ip = packet.getAddress().getHostAddress();
            int port = packet.getPort();
            String timestamp = LocalDateTime.now().format(format);

            System.out.println(
                    "[" + ip + ":" + port + "] " +
                    message +
                    " (" + timestamp + ")"
            );

            // register the sender if not already known
            clients.add(sender);

            // broadcast to everyone except the sender
            byte[] data = message.getBytes();

            for (SocketAddress client : clients) {
                if (!client.equals(sender)) {
                    socket.send(new DatagramPacket(
                            data,
                            data.length,
                            client
                    ));
                }
            }
        }
    }
}