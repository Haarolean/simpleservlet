package ru.cronfire.test.servlets1.util.database;

import org.apache.log4j.Level;
import ru.cronfire.test.servlets1.Main;

import java.sql.*;
import java.util.Properties;

public class PostgreSQL implements Database {

    private final String hostname;
    private final int port;
    private final String database;
    private final String user;
    private final String password;
    private Connection connection = null;

    public PostgreSQL(String hostname, int port, String database, String user, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        try {
            getConnection();
        } catch (Exception e) {
            Main.getLogger().log(Level.ERROR, "AAAH EXCEPTION!", e);
        }
    }

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        if (connection != null && !connection.isClosed() && connection.isValid(3)) {
            return connection;
        }
        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        info.put("useUnicode", "true");
        info.put("characterEncoding", "utf8");
        return DriverManager.getConnection("jdbc:postgresql://" + hostname + ":" + port + "/" + database, info);
    }

    @Override
    public void closeConnection() throws SQLException {
        if (connection == null) return;
        if (connection.isClosed()) return;
        connection.close();
    }

    @Override
    public ResultSet exec(String query) throws SQLException, ClassNotFoundException {
        Statement statement = getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     *  Run query
     * @param query SQL query (INSERT, UPDATE or DELETE)
     * @throws SQLException sql exception
     * @throws ClassNotFoundException in case driver is N/A
     */
    @Override
    public void update(String query) throws SQLException, ClassNotFoundException {
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

}
