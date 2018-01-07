package com.suhel.photosphere.service.realtime;

import com.neovisionaries.ws.client.ThreadType;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketListener;
import com.neovisionaries.ws.client.WebSocketState;
import com.suhel.photosphere.service.storage.Store;
import com.suhel.photosphere.utils.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

public class WS implements WebSocketListener {

    private static WebSocket ws;
    private WebSocketFactory wsf = new WebSocketFactory();
    private Store store;
    private Set<WSListener> listeners = new HashSet<>();

    public WS(Store store) {
        this.store = store;
    }

    public void connect() {
//        disconnect();

        if (ws != null)
            return;

        String token = store.get(Store.TOKEN);

        if (token == null || token.isEmpty())
            return;

        String url = Constants.Network.SOCKET_IO_URL + "?token=" + token;

        try {
            ws = wsf.createSocket(url);
            ws.addListener(this);
            ws.setPingInterval(1000);
            ws.setPongInterval(1000);
            ws.connectAsynchronously();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (ws != null) {

            if (ws.isOpen())
                ws.disconnect();

            ws = null;
        }
    }

    public void addListener(WSListener listener) {
        listeners.add(listener);
    }

    public void removeListener(WSListener listener) {
        listeners.remove(listener);
    }

    public void removeAllListeners() {
        listeners.clear();
    }


    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        Timber.tag("Socket").e("Connected");
        for (WSListener listener : listeners)
            listener.onConnected();
    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {
        cause.printStackTrace();
        Timber.tag("Socket").e("Error");
        for (WSListener listener : listeners)
            listener.onFailure();
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        Timber.tag("Socket").e("Disconnected");
        for (WSListener listener : listeners)
            listener.onDisconnected();
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        Timber.tag("Socket").e("Message");
        for (WSListener listener : listeners)
            listener.onMessage(text);
    }

    @Override
    public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        Timber.tag("Socket").e("Ping");
    }

    @Override
    public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        Timber.tag("Socket").e("Pong");
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        cause.printStackTrace();
    }

    //region Useless
    @Override
    public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {

    }

    @Override
    public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {

    }

    @Override
    public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onThreadCreated(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

    }

    @Override
    public void onThreadStarted(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

    }

    @Override
    public void onThreadStopping(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

    }

    @Override
    public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {

    }

    @Override
    public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {

    }

    @Override
    public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {

    }

    @Override
    public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {

    }

    @Override
    public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {

    }
    //endregion

}
