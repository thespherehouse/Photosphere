package com.suhel.photosphere.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.suhel.photosphere.R;

public class ShadowLayout extends FrameLayout {

    @ColorInt
    private int colorInner, colorShadow;
    private float cornerRadius, shadowSpread, shadowDX, shadowDY;
    private Paint paint;
    private Rect rect;
    private RectF rectF;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShadowLayout, defStyleAttr, 0);

        try {
            colorInner = a.getColor(R.styleable.ShadowLayout_colorInner, Color.WHITE);
            colorShadow = a.getColor(R.styleable.ShadowLayout_colorShadow, Color.GRAY);
            cornerRadius = a.getDimension(R.styleable.ShadowLayout_cornerRadius, 0);
            shadowSpread = a.getDimension(R.styleable.ShadowLayout_shadowSpread, 10);
            shadowDX = a.getDimension(R.styleable.ShadowLayout_shadowDX, 0);
            shadowDY = a.getDimension(R.styleable.ShadowLayout_shadowDY, 0);
        } finally {
            a.recycle();
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colorInner);
        paint.setStyle(Paint.Style.FILL);
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        paint.setShadowLayer(shadowSpread, shadowDX, shadowDY, colorShadow);
        rect = new Rect();
        rectF = new RectF();
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getDrawingRect(rect);
        rectF.set(rect);
        rectF.inset(shadowSpread, shadowSpread);
        canvas.drawRoundRect(rectF.left - shadowDX, rectF.top - shadowDY, rectF.right - shadowDX, rectF.bottom - shadowDY, cornerRadius, cornerRadius, paint);
    }

}
