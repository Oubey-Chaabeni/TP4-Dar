package server;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class server{
    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(1234);
        System.out.println("Server Started!");

        DateTimeFormatter format =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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

            String ip = packet.getAddress().getHostAddress();
            String timestamp = LocalDateTime.now().format(format);

            System.out.println(
                    "[" + ip + "] " +
                    message +
                    " (" + timestamp + ")"
            );
        }
    }
}