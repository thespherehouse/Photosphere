package com.suhel.photosphere.utils;

public interface Constants {

    interface UI {
        float minimumAspectRatio = 12.0f / 16.0f;
        float maximumAspectRatio = 16.0f / 9.0f;
    }

    interface Network {

        String DEBUG_BASE = "http://192.168.0.3:3000";
        String PROD_BASE = "http://www.thespherehouse.xyz";

        String CURRENT_BASE = PROD_BASE;

        String REST_URL = CURRENT_BASE + "/photosphere/api/v1/";
        String SOCKET_IO_URL = CURRENT_BASE;

        String STORAGE_URL = "https://s3-us-west-2.amazonaws.com";

    }

    interface Storage {

        String DEBUG_BUCKET = "mightycandy";
        String PROD_BUCKET = "thespherehouse";

        String CURRENT_BUCKET = PROD_BUCKET;

        String S3BUCKET = CURRENT_BUCKET;

    }

    interface Intent {

        String Post = "Post";

    }

}
