package com.suhel.photosphere.service.realtime;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.suhel.photosphere.model.response.Message;

public abstract class WSMessagesListener extends WSListener<Message> {

    @Override
    protected final Parsed<Message> tryParse(String json) throws JsonSyntaxException {
        return gson.fromJson(json, new TypeToken<Parsed<Message>>() {
        }.getType());
    }

    @Override
    public final void onReceived(Domain domain, Event event, Message data) {
        if (domain != Domain.MESSAGE)
            return;
        switch (event) {

            case CREATE:

                onNewMessage(data);
                break;

        }
    }

    public abstract void onNewMessage(Message data);

}
