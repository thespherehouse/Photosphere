package com.suhel.photosphere.service.realtime;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neovisionaries.ws.client.WebSocket;

import okhttp3.Response;

public abstract class WSListener<T> {

    protected Gson gson = new Gson();

    public final void onMessage(String json) throws Exception {
        Parsed<T> parsed = tryParse(json);
        if (parsed != null) {
            new Handler(Looper.getMainLooper()).post(() -> onReceived(Domain.fromString(parsed.getDomain()), Event.fromString(parsed.getEvent()), parsed.getData()));
        }
    }

    protected abstract Parsed<T> tryParse(String json) throws JsonSyntaxException;

    public final void onConnected() {
        new Handler(Looper.getMainLooper()).post(this::onConnection);
    }

    public final void onDisconnected() {
        new Handler(Looper.getMainLooper()).post(this::onDisconnection);
    }

    public final void onFailure() {
        new Handler(Looper.getMainLooper()).post(this::onDisconnection);
    }

    public void onConnection() {
    }

    public abstract void onReceived(Domain domain, Event event, T data);

    public void onDisconnection() {
    }

    public enum Domain {

        POST,
        LIKE,
        COMMENT,
        MESSAGE;

        @NonNull
        static Domain fromString(String domain) {
            switch (domain) {

                case "post":
                    return POST;
                case "like":
                    return LIKE;
                case "comment":
                    return COMMENT;
                default:
                    return MESSAGE;

            }
        }

        public String toString() {
            switch (this) {

                case POST:
                    return "post";
                case LIKE:
                    return "like";
                case COMMENT:
                    return "comment";
                default:
                    return "message";

            }
        }

    }

    public enum Event {

        CREATE,
        READ,
        UPDATE,
        DELETE;

        @NonNull
        static Event fromString(String event) {
            switch (event) {

                case "create":
                    return CREATE;
                case "read":
                    return READ;
                case "update":
                    return UPDATE;
                default:
                    return DELETE;

            }
        }

        public String toString() {
            switch (this) {

                case CREATE:
                    return "create";
                case READ:
                    return "read";
                case UPDATE:
                    return "update";
                default:
                    return "delete";

            }
        }

    }

    public class Parsed<T> {

        @SerializedName("domain")
        @Expose
        private String domain;

        @SerializedName("event")
        @Expose
        private String event;

        @SerializedName("data")
        @Expose
        private T data;

        public String getDomain() {
            return domain;
        }

        public String getEvent() {
            return event;
        }

        public T getData() {
            return data;
        }

    }

}
