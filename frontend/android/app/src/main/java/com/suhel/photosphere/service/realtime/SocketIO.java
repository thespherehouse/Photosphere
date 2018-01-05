package com.suhel.photosphere.service.realtime;

import android.support.annotation.NonNull;
import android.util.Log;

import com.suhel.photosphere.service.storage.Store;
import com.suhel.photosphere.utils.Constants;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketIO {

    // Single socket
    private static Socket io = null;
    private Set<SocketIOClient> clients = new HashSet<>();
    private Store store;
    private IO.Options opts;

    public SocketIO(Store store) {
        this.store = store;
    }

    public void connect() {
        if (io != null && io.connected())
            return;

        try {
            String token = store.get(Store.TOKEN);
            if (token == null || token.isEmpty())
                throw new IllegalStateException("User must be logged in");

            opts = new IO.Options();
            opts.query = "token=" + token;
            opts.path = "/realtime";
            opts.reconnectionDelay = 5000;

            io = IO.socket(Constants.Network.SOCKET_IO_URL, opts);
            io.on(Socket.EVENT_CONNECT, args -> socketConnected());
            io.on(Socket.EVENT_DISCONNECT, args -> socketDisconnected());
            io.on(SocketIOClient.Event.CREATE.toString(), args -> {
                for (SocketIOClient client : clients)
                    client.onData(SocketIOClient.Event.CREATE, args);
            });
            io.on(SocketIOClient.Event.READ.toString(), args -> {
                for (SocketIOClient client : clients)
                    client.onData(SocketIOClient.Event.READ, args);
            });
            io.on(SocketIOClient.Event.UPDATE.toString(), args -> {
                for (SocketIOClient client : clients)
                    client.onData(SocketIOClient.Event.UPDATE, args);
            });
            io.on(SocketIOClient.Event.DELETE.toString(), args -> {
                for (SocketIOClient client : clients)
                    client.onData(SocketIOClient.Event.DELETE, args);
            });
            io.on(Socket.EVENT_ERROR, args -> Log.e("Socket.IO", "Error"));
            io.on(Socket.EVENT_CONNECT_ERROR, args -> Log.e("Socket.IO", args[0].toString()));
            io.on(Socket.EVENT_CONNECT_TIMEOUT, args -> Log.e("Socket.IO", "Connect timeout"));
            io.on(Socket.EVENT_CONNECTING, args -> Log.e("Socket.IO", "Connecting"));
            io.on(Socket.EVENT_MESSAGE, args -> Log.e("Socket.IO", "Messaging"));
            io.on(Socket.EVENT_PING, args -> Log.e("Socket.IO", "Ping"));
            io.on(Socket.EVENT_PONG, args -> Log.e("Socket.IO", "Pong"));
            io.on(Socket.EVENT_RECONNECT, args -> Log.e("Socket.IO", "Reconnecting"));
            io.on(Socket.EVENT_RECONNECT_ATTEMPT, args -> Log.e("Socket.IO", args[0].toString()));
            io.on(Socket.EVENT_RECONNECT_ERROR, args -> Log.e("Socket.IO", args[0].toString()));
            io.on(Socket.EVENT_RECONNECT_FAILED, args -> Log.e("Socket.IO", "Reconnect failed"));
            io.on(Socket.EVENT_RECONNECTING, args -> Log.e("Socket.IO", args[0].toString()));
            io.connect();
        } catch (URISyntaxException e) {
            io = null;
            e.printStackTrace();
        }
    }

    private void socketConnected() {
        Log.e("Socket.IO", "Connected");
        for (SocketIOClient client : clients)
            client.onConnect();
    }

    private void socketDisconnected() {
        Log.e("Socket.IO", "Disconnected");
        for (SocketIOClient client : clients)
            client.onDisconnect();
    }

    public void disconnect() {
        if (io != null) {
            io.off();
            if (io.connected())
                io.disconnect();
            io = null;
        }
    }

    public void add(@NonNull SocketIOClient listener) {
        clients.add(listener);
    }

    public void remove(@NonNull SocketIOClient listener) {
        clients.remove(listener);
    }

}
