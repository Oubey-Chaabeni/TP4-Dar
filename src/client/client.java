package client;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

import thread.recieve;

public class client {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        // start the thread that receives messages in real time
        recieve r = new recieve(socket);
        r.start();

        // join message: lets the server register this client's address
        byte[] join = (name + " joined the chat").getBytes();

        socket.send(new DatagramPacket(
                join,
                join.length,
                InetAddress.getByName("localhost"),
                1234
        ));

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