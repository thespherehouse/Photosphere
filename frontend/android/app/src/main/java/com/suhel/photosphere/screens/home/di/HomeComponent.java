package com.suhel.photosphere.screens.home.di;


import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.home.view.HomeActivity;

import dagger.Subcomponent;

@HomeScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent extends BaseSubcomponent {

    void inject(HomeActivity activity);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<HomeComponent, HomeModule> {

    }

}
