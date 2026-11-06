package thread;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class recieve extends Thread {

    private DatagramSocket socket;

    public recieve(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
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

                System.out.println("\n" + message);
                System.out.print("> ");
            }
        } catch (Exception e) {
            System.out.println("Receive thread stopped.");
        }
    }
}