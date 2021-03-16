package ru.mknet.stickers.dao;

import org.springframework.stereotype.Component;
import ru.mknet.stickers.service.SecretsService;

import java.sql.*;
import java.util.HashMap;

@Component
public class SQLConnector {


    private  Connection connection;
    private  Statement statement;

    private final String DB_JDBC = SecretsService.getProperty("DB_BILLING");
    private final String USER = SecretsService.getProperty("DB_USER");
    private final String VALUE = SecretsService.getProperty("DB_PASS");

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_JDBC, USER, VALUE);
            this.statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String,String> getLoginAndPassByContract(String contract) {

        connect();

        HashMap<String,String> map = new HashMap<>();

        String query = "SELECT a.login_alias AS login, l.pswd AS password FROM `user_login_1` l " +
                "INNER JOIN `user_alias_1` a ON a.login_id = l.id " +
                "INNER JOIN `contract` c ON l.cid = c.id WHERE c.title =" + contract;
        try (ResultSet set = statement.executeQuery(query)) {

            while (set.next())
                map.put(set.getString("login"), set.getString("password"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        disconnect();

        return map;
    }
}



