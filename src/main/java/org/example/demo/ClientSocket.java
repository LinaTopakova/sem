package org.example.demo;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientSocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
        System.exit(0);
    }
}