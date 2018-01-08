package com.suhel.photosphere.base.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.di.BaseSubcomponent;

import javax.inject.Inject;

public abstract class BaseFragment<A extends BaseActivity, V extends ViewDataBinding, P, C extends BaseSubcomponent>
        extends Fragment {

    protected A activity;

    protected V binding;

    @Inject
    protected P presenter;

    private C component;

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        activity = (A) getActivity();
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)).getRoot();
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inject(component = createComponent(activity));
        onCreated();
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract C createComponent(A activity);

    protected abstract void inject(C component);

    protected void onPreCreate() {

    }

    protected void onCreated() {
    }

}
