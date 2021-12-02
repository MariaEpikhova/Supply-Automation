package com.framework.recievers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.framework.common.observable_list.ObservableList;

public class ConnectionManager extends Thread {
    private int port;
    private ObservableList<UserConnection> users = new ObservableList<>();

    public ConnectionManager(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started at " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                users.add(new UserConnection(socket, users));
                users.addListener(updatedUsers -> System.out.println(updatedUsers.size()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
