package com.suhel.photosphere.utils.common;

public class LanguageUtils {

    public static String plural(String word, int count) {
        return word + (count != 1 ? "s" : "");
    }

}
