package com.suhel.photosphere.custom.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

public class TintableImageButton extends AppCompatImageButton {

    public TintableImageButton(Context context) {
        super(context);
    }

    public TintableImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TintableImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColorFilterResource(@ColorRes int colorFilterRes) {
        setColorFilter(ContextCompat.getColor(getContext(), colorFilterRes));
    }

}
