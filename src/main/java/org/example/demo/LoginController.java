package org.example.demo;

//import com.sun.javafx.tk.quantum.PaintRenderJob;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class LoginController  {
    @FXML
    private Hyperlink create_btn;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    public void createAcc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml")); // Замените на ваш FXML файл
        Scene chatScene = new Scene(loader.load());

        // Получаем текущее окно через источник события
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(chatScene);
    }
}