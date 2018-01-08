package com.suhel.photosphere.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.suhel.photosphere.common.FileStore;
import com.suhel.photosphere.model.response.User;

public class UserStore extends FileStore<User> {

    public UserStore(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected String getFileName() {
        return "user";
    }

}
