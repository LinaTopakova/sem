package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
//import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CreateAccController implements Initializable {

    @FXML
    private TextField age;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginAcc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene registrationScene = new Scene(loader.load());

        // Получаем текущее окно через источник события
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(registrationScene);
    }

    @FXML
    public void createAccount() {
        String user = username.getText();
        String pass = password.getText();
        String userAge = age.getText();

        if (user.isEmpty() || pass.isEmpty() || userAge.isEmpty()) {
            messageLabel.setText("Пожалуйста, заполните все поля.");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root")) {

            String checkUser = "SELECT COUNT(*) FROM account WHERE username = ?";
            try (PreparedStatement checkUserSql = connection.prepareStatement(checkUser)) {
                checkUserSql.setString(1, user);

                try (ResultSet rs = checkUserSql.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        messageLabel.setText("Пользователь с таким именем уже существует.");
                        username.clear();
                        password.clear();
                        age.clear();
                        return;
                    }
                }
            }

            String sql = "INSERT INTO account (username, password, age) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, user);
                pstmt.setString(2, pass);
                pstmt.setString(3, userAge);
                pstmt.executeUpdate();
            }

            messageLabel.setText("Аккаунт успешно создан!");

            username.clear();
            password.clear();
            age.clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Scene chatScene = new Scene(loader.load());

            // Получаем контроллер из загруженного FXML
            ChatController chatController = loader.getController();
            chatController.setUsername(user);



            chatController.initializeChat(HelloApplication.getSocket(), HelloApplication.getAddress(), HelloApplication.getServerPort());

            Stage stage = (Stage) username.getScene().getWindow();
            stage.setScene(chatScene);

        } catch (SQLException e) {
            messageLabel.setText("Ошибка при создании аккаунта: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            messageLabel.setText("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}