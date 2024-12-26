package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HelloApplication extends Application {
    private static DatagramSocket socket;
    private static InetAddress address;
    private static final int SERVER_PORT = 8000;

    public static void main(String[] args) {
        launch(args);
    }

    static {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            fxmlLoader.setController(new LoginController());

            InputStream icon = getClass().getResourceAsStream("/image/icon.png");
            assert icon != null;
            primaryStage.getIcons().add(new Image(icon));

            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static DatagramSocket getSocket() {
        return socket;
    }

    public static InetAddress getAddress() {
        return address;
    }

    public static int getServerPort() {
        return SERVER_PORT;
    }
}