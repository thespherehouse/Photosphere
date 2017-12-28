package com.suhel.photosphere.utils.common;

public interface Constants {

    interface UI {
        float minimumAspectRatio = 12.0f / 16.0f;
        float maximumAspectRatio = 16.0f / 9.0f;
    }

    interface Network {

        String REST_URL = "http://www.thespherehouse.xyz/photosphere/";
        String STORAGE_URL = "https://s3-us-west-2.amazonaws.com";

    }

    interface Storage {

        String S3BUCKET = "thespherehouse";

    }

    interface Intent {

        String Post = "Post";

    }

}
