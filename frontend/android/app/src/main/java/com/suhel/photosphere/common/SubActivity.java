package com.suhel.photosphere.common;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.ref.WeakReference;

public abstract class SubActivity<A extends AppCompatActivity, P, V extends ViewDataBinding> {

    private WeakReference<A> activity;
    private WeakReference<P> presenter;
    private WeakReference<V> binding;

    public SubActivity(A activity, P presenter, V binding) {
        this.activity = new WeakReference<>(activity);
        this.presenter = new WeakReference<>(presenter);
        this.binding = new WeakReference<>(binding);
        onCreate();
    }

    public void onCreate() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    @NonNull
    protected abstract View getRootView();

    public A getActivity() {
        return activity.get();
    }

    public Context getContext() {
        return activity.get();
    }

    public P getPresenter() {
        return presenter.get();
    }

    public V getBinding() {
        return binding.get();
    }

}
