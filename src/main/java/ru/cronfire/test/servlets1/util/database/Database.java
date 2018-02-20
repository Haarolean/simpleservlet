package ru.cronfire.test.servlets1.util.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database {
    Connection getConnection() throws ClassNotFoundException, SQLException;

    void closeConnection() throws SQLException;

    ResultSet exec(String query) throws SQLException, ClassNotFoundException;

    void update(String query) throws SQLException, ClassNotFoundException;
}
