package com.suhel.photosphere.service.realtime;

import android.support.annotation.NonNull;

import org.json.JSONObject;

public abstract class SocketIOClient {

    final void onData(Event event, Object... args) {
        try {
            JSONObject json = (JSONObject) args[0];
            if (json.has("domain") && json.has("data")) {
                onReceive(Domain.fromString(json.getString("domain")), event, json.getJSONObject("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onConnect();

    public abstract void onDisconnect();

    public abstract void onReceive(Domain domain, Event event, JSONObject data);

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
        static Event fromString(String domain) {
            switch (domain) {

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

}
