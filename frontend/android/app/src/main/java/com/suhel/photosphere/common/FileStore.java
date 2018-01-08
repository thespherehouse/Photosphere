package com.suhel.photosphere.common;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class FileStore<T extends Serializable> {

    private Context context;

    public FileStore(Context context) {
        this.context = context;
    }

    @NonNull
    protected abstract String getFileName();

    public final void save(T data) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(getFileName(), Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception ignore) {

        }
    }

    public final T retrieve() {
        try {
            FileInputStream fileInputStream = context.openFileInput(getFileName());
            ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);
            T temp = (T) objectOutputStream.readObject();
            objectOutputStream.close();
            fileInputStream.close();
            return temp;
        } catch (Exception ignore) {

        }
        return null;
    }

    public final void remove() {
        try {
            context.deleteFile(getFileName());
        } catch (Exception ignore) {

        }
    }

}
