package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private Label messageLabel;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    public void createAcc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));
        Scene registrationScene = new Scene(loader.load());

        // Получаем текущее окно через источник события
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(registrationScene);
    }

    public void loginAcc() {
        String user = username.getText();
        String pass = password.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            messageLabel.setText("Пожалуйста, заполните все поля.");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root")) {
            if (isUser (connection, user, pass)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
                Scene chatScene = new Scene(loader.load());


                ChatController chatController = loader.getController();
                chatController.setUsername(user);



                chatController.initializeChat(HelloApplication.getSocket(), HelloApplication.getAddress(), HelloApplication.getServerPort());

                Stage stage = (Stage) username.getScene().getWindow();
                stage.setScene(chatScene);
            } else {
                messageLabel.setText("Неверный username или password");
                username.clear();
                password.clear();
            }
        } catch (SQLException e) {
            messageLabel.setText("Ошибка при подключении к базе данных: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            messageLabel.setText("Ошибка загрузки страницы: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isUser (Connection connection, String user, String pass) throws SQLException {
        String query = "SELECT COUNT(*) FROM account WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}