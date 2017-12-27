package com.suhel.photosphere.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.suhel.photosphere.R;
import com.suhel.photosphere.utils.common.Fonts;

public class SimpleTextView extends AppCompatTextView {

    public SimpleTextView(Context context) {
        this(context, null);
    }

    public SimpleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SimpleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SimpleTextView, defStyleAttr, 0);
        try {
            int fontType = a.getInt(R.styleable.SimpleTextView_fontType, 1);
            setTypeface(Fonts.byType(context, fontType));
        } finally {
            a.recycle();
        }
    }

}
