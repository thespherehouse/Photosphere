package com.suhel.photosphere.screens.home.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.suhel.photosphere.R;

public class LinearMenuBar extends LinearLayout {

    private static final int EXCEPTION = 2;

    private int selection = 0;
    private ImageView[] menuItems;

    private OnSelectionListener listener;

    @ColorInt
    private int colorUnselected, colorSelected;

    public LinearMenuBar(Context context) {
        super(context);
    }

    public LinearMenuBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearMenuBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LinearMenuBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        handleSelection(selection);
        this.selection = selection;
        if (listener != null) {
            listener.onMenuItemSelected(selection);
        }
    }

    public OnSelectionListener getListener() {
        return listener;
    }

    public void setListener(OnSelectionListener listener) {
        this.listener = listener;
    }

    private void handleSelection(int selection) {
        menuItems[this.selection].setColorFilter(colorUnselected);
        menuItems[selection].setColorFilter(colorSelected);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        colorUnselected = ContextCompat.getColor(getContext(), R.color.colorMenuUnselected);
        colorSelected = ContextCompat.getColor(getContext(), R.color.colorMenuSelected);

        for (int i = 0; i < getChildCount(); i++)
            if (!(getChildAt(i) instanceof ImageView))
                throw new IllegalStateException("Only ImageView and its descendents allowed");

        menuItems = new ImageView[getChildCount()];

        for (int i = 0; i < getChildCount(); i++) {
            menuItems[i] = (ImageView) getChildAt(i);
            menuItems[i].setOnClickListener(new IndexedOnClickListener(i) {

                @Override
                public void onClick(ImageView view, int index) {
                    if (index != EXCEPTION)
                        setSelection(index);
                }

            });
        }
        handleSelection(0);
    }


    public interface OnSelectionListener {

        void onMenuItemSelected(int index);

    }

    private abstract class IndexedOnClickListener implements OnClickListener {

        private int index;

        IndexedOnClickListener(int index) {
            this.index = index;
        }

        @Override
        public final void onClick(View view) {
            onClick((ImageView) view, index);
        }

        public abstract void onClick(ImageView view, int index);

    }

}
