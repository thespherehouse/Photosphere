package com.suhel.photosphere.common;

import android.view.View;

public class SubActivityManager {

    private SubActivity[] subActivities;
    private int currentVisible = 0;

    public SubActivityManager(SubActivity... subActivities) {
        this.subActivities = subActivities;
    }

    public int getCurrentVisible() {
        return currentVisible;
    }

    public void setCurrentVisible(int currentVisible) {
        if (currentVisible != this.currentVisible && currentVisible >= 0 && currentVisible < subActivities.length) {
            subActivities[this.currentVisible].onPause();
            subActivities[this.currentVisible].getRootView().setVisibility(View.GONE);
            subActivities[currentVisible].getRootView().setVisibility(View.VISIBLE);
            subActivities[currentVisible].onResume();
            this.currentVisible = currentVisible;
        }
    }

    public SubActivity getSubActivity(int position) {
        return subActivities[position];
    }

}
