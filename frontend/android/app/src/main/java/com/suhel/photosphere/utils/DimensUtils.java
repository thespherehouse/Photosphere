package com.suhel.photosphere.utils;

import android.content.Context;
import android.util.TypedValue;

public class DimensUtils {

    public static int getToolbarSize(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        else
            return 0;
    }

}
