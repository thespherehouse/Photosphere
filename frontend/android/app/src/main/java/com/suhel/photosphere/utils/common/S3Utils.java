package com.suhel.photosphere.utils.common;

public class S3Utils {

    public static String getFileUrl(String bucket, String key) {
        return Constants.Network.STORAGE_URL + "/" + bucket + "/" + key;
    }

    public static String getFileUrl(String key) {
        return getFileUrl(Constants.Storage.S3BUCKET, key);
    }

    public static String getImageUrl(String key, ImageType type) {
        return getFileUrl(key) + "/" + type.getFileName();
    }

    public enum ImageType {

        Original,
        Thumbnail;

        public String getFileName() {
            switch (this) {

                case Original:
                    return "original.jpg";

                case Thumbnail:
                    return "thumbnail.jpg";

                default:
                    return "";
            }
        }

    }

}
