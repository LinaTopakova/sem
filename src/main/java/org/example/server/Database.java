package org.example.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test"; // Проверьте порт и имя базы данных
        String user = "yourUsername"; // Замените на ваше имя пользователя
        String password = "yourPassword"; // Замените на ваш пароль

        try {
            // Загрузка драйвера
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Установка соединения
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Соединение успешно установлено!");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Ошибка подключения: " + e.getMessage());
        }
    }
}
