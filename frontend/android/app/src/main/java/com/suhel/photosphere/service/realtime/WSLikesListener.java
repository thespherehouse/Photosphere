package com.suhel.photosphere.service.realtime;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.suhel.photosphere.model.realtime.RealtimeLike;

public abstract class WSLikesListener extends WSListener<RealtimeLike> {

    @Override
    protected final Parsed<RealtimeLike> tryParse(String json) throws JsonSyntaxException {
        return gson.fromJson(json, new TypeToken<Parsed<RealtimeLike>>() {
        }.getType());
    }

    @Override
    public final void onReceived(Domain domain, Event event, RealtimeLike data) {
        if (domain != Domain.LIKE)
            return;
        switch (event) {

            case CREATE:

                onLike(data);
                break;

            case DELETE:

                onUnlike(data);
                break;

        }
    }

    public abstract void onLike(RealtimeLike data);

    public abstract void onUnlike(RealtimeLike data);

}
