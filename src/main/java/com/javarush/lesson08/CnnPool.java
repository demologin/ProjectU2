package com.javarush.lesson08;

import com.javarush.lesson07.Cnn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CnnPool {

    private static final List<Connection> realConnection = new ArrayList<>();
    private static final BlockingQueue<Connection> connectionQueue = new LinkedBlockingQueue<>();
    public static final int POOL_SIZE = 10;

    public static Connection get() throws SQLException {
        if (realConnection.isEmpty()) {
            init();
        }
        try {
            return connectionQueue.take();
        } catch (InterruptedException e) {
            throw new SQLException(e);
        }
    }

    private static void init() throws SQLException {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = Cnn.get();
            realConnection.add(connection);

            Connection proxyConnection = getProxyConnection(connection);
            connectionQueue.add(proxyConnection);

        }

    }

    private static Connection getProxyConnection(Connection connection) {
        return (Connection) Proxy.newProxyInstance(
                CnnPool.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (!method.getName().equals("close")) {
                        return method.invoke(connection, args);
                    } else {
                        connectionQueue.add((Connection) proxy);
                        return null;
                    }
                }
        );
    }

    public static void close() throws SQLException {
        for (Connection connection : realConnection) {
            connection.close();
        }
        realConnection.clear();
    }
}
