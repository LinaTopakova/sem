package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    private DatagramSocket socket;
    private InetAddress address;
    private int serverPort;
    private final String identifier = "Jeff";

    @FXML
    private TextArea messageArea;

    @FXML
    private TextField inputBox;

    @FXML
    private Text userText;


    public void initializeChat(DatagramSocket socket, InetAddress address, int serverPort) {
        this.socket = socket;
        this.address = address;
        this.serverPort = serverPort;
        messageArea.setEditable(false);

        // Запуск потока для получения сообщений
        org.example.client.ClientThread clientThread = new org.example.client.ClientThread(socket, messageArea);
        clientThread.start();


        try {
            byte[] uuid = ("init;" + identifier).getBytes();
            DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, address, serverPort);
            socket.send(initialize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Обработка ввода сообщения
        inputBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String temp = identifier + ";" + inputBox.getText(); // сообщение для отправки
                messageArea.appendText( userText.getText() + ": "+ inputBox.getText() + "\n");
                byte[] msg = temp.getBytes(); // преобразование в байты
                inputBox.setText("");


                DatagramPacket send = new DatagramPacket(msg, msg.length, address, serverPort);
                try {
                    socket.send(send);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setUsername(String user) {
        userText.setText(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void exit(){
        System.exit(0);
    }
}
