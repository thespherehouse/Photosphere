package com.suhel.photosphere.base.model;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class ProgressRequestBody extends RequestBody {

    private static final int SEGMENT_SIZE = 2048;

    private File file;
    private long size;
    private OnProgressUpdateListener listener;

    public ProgressRequestBody(File file, OnProgressUpdateListener listener) {
        if (file == null)
            throw new IllegalArgumentException("File must not be null");
        this.file = file;
        this.listener = listener;
        this.size = file.length();
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(getMimeType(file.getAbsolutePath()));
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {

            source = Okio.source(file);
            long transferred = 0, read;

            while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1) {
                transferred += read;
                sink.flush();
                if (listener != null) {
                    listener.onProgressUpdate(transferred, size);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (source != null)
                    source.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public interface OnProgressUpdateListener {

        void onProgressUpdate(long transferred, long total);

    }

}
