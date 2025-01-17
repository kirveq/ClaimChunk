package com.cjburkey.claimchunk.data.newdata;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

public class MysqlConnection implements Closeable, AutoCloseable {

    private final Supplier<Connection> newConnection;
    private Connection connection;

    MysqlConnection(Supplier<Connection> newConnection) {
        this.newConnection = newConnection;
        refresh();
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void refresh() {
        close();
        connection = newConnection.get();
    }

    public Connection get() {
        return connection;
    }

    @SuppressWarnings("WeakerAccess")
    public Connection refreshGet() {
        refresh();
        return get();
    }

    @SuppressWarnings("WeakerAccess")
    public boolean isInvalid() {
        if (connection == null) refresh();
        return connection == null;
    }

}
