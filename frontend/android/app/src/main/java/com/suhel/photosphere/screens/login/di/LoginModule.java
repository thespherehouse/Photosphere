package com.suhel.photosphere.screens.login.di;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.screens.login.contract.LoginContract;
import com.suhel.photosphere.screens.login.presenter.LoginPresenter;
import com.suhel.photosphere.screens.login.presenter.LoginPresenterImpl;
import com.suhel.photosphere.service.rest.RestService;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule extends BaseModule {

    private LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    LoginPresenter providesPresenter(RestService restService) {
        return new LoginPresenterImpl(view, restService);
    }

}
