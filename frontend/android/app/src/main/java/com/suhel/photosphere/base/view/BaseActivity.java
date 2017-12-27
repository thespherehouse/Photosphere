package com.suhel.photosphere.base.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.di.BaseSubcomponent;

import javax.inject.Inject;

public abstract class BaseActivity<V extends ViewDataBinding, P, C extends BaseSubcomponent>
        extends AppCompatActivity {

    protected V binding;

    @Inject
    protected P presenter;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getComponent((AppContract) getApplication()));
        onPreCreate();
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        onActivityCreated();
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract C getComponent(AppContract contract);

    protected abstract void inject(C component);

    protected void onPreCreate() {

    }

    protected void onActivityCreated() {
    }

}
