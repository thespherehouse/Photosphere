package com.suhel.photosphere.custom.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public final class LifecycleManager implements Application.ActivityLifecycleCallbacks {

    private int createdCount = 0, startedCount = 0, resumedCount = 0;

    @Override
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        createdCount++;
    }

    @Override
    public final void onActivityStarted(Activity activity) {
        startedCount++;
    }

    @Override
    public final void onActivityResumed(Activity activity) {
        resumedCount++;
    }

    @Override
    public final void onActivityPaused(Activity activity) {
        resumedCount--;
    }

    @Override
    public final void onActivityStopped(Activity activity) {
        startedCount--;
    }

    @Override
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public final void onActivityDestroyed(Activity activity) {
        createdCount--;
    }

    public int getCreatedCount() {
        return createdCount;
    }

    public int getStartedCount() {
        return startedCount;
    }

    public int getResumedCount() {
        return resumedCount;
    }

}
