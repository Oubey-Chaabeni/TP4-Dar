package client;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

public class client {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        while (true) {

            System.out.print("> ");
            String msg = sc.nextLine();

            byte[] buffer = (name + " : " + msg).getBytes();

            DatagramPacket packet = new DatagramPacket(
                    buffer,
                    buffer.length,
                    InetAddress.getByName("localhost"),
                    1234
            );

            socket.send(packet);
        }
    }
}