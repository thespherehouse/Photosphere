package com.suhel.photosphere.service.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Store {

    public static final String TOKEN = "token";

    private static final String FILE_NAME = "store";
    private Context context;

    public Store(Context context) {
        this.context = context;
    }

    @SuppressLint("ApplySharedPref")
    public void put(@Field String field, @NonNull String value) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(field, value)
                .commit();
    }

    @Nullable
    public String get(@Field String field) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getString(field, null);
    }

    @SuppressLint("ApplySharedPref")
    public void remove(@Field String field) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(field)
                .commit();
    }

    @StringDef({TOKEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Field {
    }

}
