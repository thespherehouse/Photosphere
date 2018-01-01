package com.suhel.photosphere.screens.comments.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.suhel.photosphere.utils.DimensUtils;

public class CommentBarBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private int toolbarHeight;

    public CommentBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = DimensUtils.getToolbarSize(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout ll, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout ll, View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) ll.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = ll.getHeight() + fabBottomMargin;
            float ratio = dependency.getY() / (float) toolbarHeight;
            ll.setTranslationY(-distanceToScroll * ratio);
        }
        return true;
    }

}
