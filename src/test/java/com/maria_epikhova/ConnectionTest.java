package com.maria_epikhova;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.framework.EasyTcpApplication;
import com.framework.annotations.EasyTcpMain;

import org.junit.jupiter.api.Test;

@EasyTcpMain
public class ConnectionTest {

    public ConnectionTest() {
        EasyTcpApplication.run(ConnectionTest.class, 4321);
    }

    @Test
    void testConnection() {
        try {
            Socket socket = new Socket("localhost", 4321);
            System.out.println(socket.isConnected());
            assertNotEquals(null, socket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMultiConnections() throws UnknownHostException, IOException {
        Socket connection1 = new Socket("localhost", 4321);
        Socket connection2 = new Socket("localhost", 4321);
        Socket connection3 = new Socket("localhost", 4321);

        assertEquals(true, connection1.isConnected());
        assertEquals(true, connection2.isConnected());
        assertEquals(true, connection3.isConnected());

    }
}
