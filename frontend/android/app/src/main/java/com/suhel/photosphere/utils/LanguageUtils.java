package com.suhel.photosphere.utils;

public class LanguageUtils {

    public static String plural(String word, int count) {
        return word + (count != 1 ? "s" : "");
    }

}
