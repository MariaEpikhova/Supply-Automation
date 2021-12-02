package com.framework.recievers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.common.observable_list.ObservableList;
import com.framework.interfaces.SecurityConfig;
import com.framework.models.EntrypointModel;
import com.framework.models.Request;
import com.framework.models.Response;
import com.framework.modules.EntrypointModule;
import com.framework.modules.SecurityModule;
import com.maria_epikhova.exceptions.ServerException;

public class UserConnection extends Thread {
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final ObservableList<UserConnection> users;
    private final Socket socket;

    public UserConnection(Socket socket, ObservableList<UserConnection> users) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.users = users;

        start();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String requestJson = (String) in.readObject();
                String action = objectMapper.readTree(requestJson).get("action").asText();
                List<EntrypointModel> entrypoints = EntrypointModule.getAllEntrypointsActions();
                for (EntrypointModel entrypoint : entrypoints) {
                    if (Objects.equals(entrypoint.getAction(), action)) {
                        try {
                            Request<?> req = Request.fromJson(requestJson, entrypoint.getRequestDataType());
                            if (entrypoint.isSecured()) {
                                List<SecurityConfig> configs = SecurityModule.getConfigs();
                                boolean forbidden = false;
                                for (SecurityConfig config : configs) {
                                    if (!config.auth(req)) {
                                        forbidden = true;
                                        break;
                                    }
                                }
                                if (forbidden) {
                                    notifyUser(new Response(action, null, "Forbidden!"));
                                } else {
                                    sendData(entrypoint, req);
                                }
                            } else {
                                sendData(entrypoint, req);
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            notifyUser(new Response(action, null, "Bad request!"));
                        }
                        break;
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
                users.remove(this);
                return;
            }

        }
    }

    private void sendData(EntrypointModel entrypoint, Request<?> req) {
        try {
            Object data = entrypoint.getOnCall().call(req);
            System.out.println(data);
            notifyUser(new Response(req.getAction(), data));

        } catch (ServerException e) {
            notifyUser(new Response(req.getAction(), null, e.getMessage()));
        }
    }

    public void notifyUser(Response response) {
        try {
            out.writeObject(new ObjectMapper().writeValueAsString(response));
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
