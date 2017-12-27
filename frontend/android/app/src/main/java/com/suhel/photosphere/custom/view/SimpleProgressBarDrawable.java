package com.suhel.photosphere.custom.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.drawee.drawable.DrawableUtils;

public class SimpleProgressBarDrawable extends Drawable {

    private int level = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF circleRect = new RectF();
    private float radius;

    public SimpleProgressBarDrawable(@ColorInt int color, float radius, float thickness) {
        this.radius = radius;
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thickness);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float angle = ((level * 360.0f) / 10000) - 90;
        canvas.drawArc(circleRect, -90, angle, false, paint);
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return DrawableUtils.getOpacityFromColor(paint.getColor());
    }

    @Override
    protected boolean onLevelChange(int level) {
        if (this.level == level)
            return false;
        this.level = level;
        circleRect.set(getBounds().centerX() - radius,
                getBounds().centerY() - radius,
                getBounds().centerX() + radius,
                getBounds().centerY() + radius);
        return true;
    }

}
