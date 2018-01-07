package com.suhel.photosphere.service.realtime;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.suhel.photosphere.model.realtime.RealtimeComment;

public abstract class WSCommentsListener extends WSListener<RealtimeComment> {

    @Override
    protected final Parsed<RealtimeComment> tryParse(String json) throws JsonSyntaxException {
        return gson.fromJson(json, new TypeToken<Parsed<RealtimeComment>>() {
        }.getType());
    }

    @Override
    public final void onReceived(Domain domain, Event event, RealtimeComment data) {
        if (domain != Domain.COMMENT)
            return;
        switch (event) {

            case CREATE:

                onCreateComment(data);
                break;

            case UPDATE:

                onEditComment(data);
                break;

            case DELETE:

                onDeleteComment(data);
                break;

        }
    }

    public abstract void onCreateComment(RealtimeComment data);

    public abstract void onEditComment(RealtimeComment data);

    public abstract void onDeleteComment(RealtimeComment data);

}
