package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CreateAccController {
    @FXML
    private Hyperlink create_btn;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    public void exit(){
        System.exit(0);
    }

    private Connection connect;
    private PreparedStatement statement;
    private ResultSet result;


    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUsername(TextField username) {

    }

    public void loginAcc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml")); // Замените на ваш FXML файл
        Scene chatScene = new Scene(loader.load());

        // Получаем текущее окно через источник события
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(chatScene);
    }
}
