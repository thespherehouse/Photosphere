package com.suhel.photosphere.screens.login.di;


import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.login.view.LoginActivity;

import dagger.Subcomponent;

@LoginScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent extends BaseSubcomponent {

    void inject(LoginActivity activity);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<LoginComponent, LoginModule> {

    }

}
