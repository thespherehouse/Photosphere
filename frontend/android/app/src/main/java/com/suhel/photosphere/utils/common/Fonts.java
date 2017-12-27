package com.suhel.photosphere.utils.common;

import android.content.Context;
import android.graphics.Typeface;

public class Fonts {

    private static final String[] names = new String[]{
            "fonts/Roboto-Light.ttf",
            "fonts/Roboto-Regular.ttf",
            "fonts/Roboto-Medium.ttf",
            "fonts/Roboto-Bold.ttf"
    };

    public static Typeface byType(Context context, int type) {
        return Typeface.createFromAsset(context.getAssets(), names[type]);
    }

}
