package org.example.client;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientThread extends Thread {
    private final DatagramSocket socket;
    private final TextArea messageArea;

    public ClientThread(DatagramSocket socket, TextArea messageArea) {
        this.socket = socket;
        this.messageArea = messageArea;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024]; // буфер для получения сообщений

        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());

                Platform.runLater(() -> messageArea.appendText(message + "\n"));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        socket.close();
    }
}