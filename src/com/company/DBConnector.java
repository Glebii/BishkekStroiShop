package com.company;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    private   final String USER_NAME = "root";
    private   final String PASSWORD = "";
    private   final String URL = "jdbc:mysql://127.0.0.1:3306/bishkekstroi";
    protected  Statement statement;
    protected  Connection connection;
    public  void getConnectionToDB() throws SQLException, IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(this.URL, this.USER_NAME, this.PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new RuntimeException();
        }
        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new RuntimeException();
        }
    }
    public void closeConnections() throws SQLException {
        try {
            this.connection.close();
            this.statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

