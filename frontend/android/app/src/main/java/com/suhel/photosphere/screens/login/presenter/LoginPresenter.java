package com.suhel.photosphere.screens.login.presenter;

import com.suhel.photosphere.screens.login.contract.LoginContract;
import com.suhel.photosphere.service.rest.RestService;

public abstract class LoginPresenter implements LoginContract.Presenter {

    protected LoginContract.View view;
    protected RestService restService;

    public LoginPresenter(LoginContract.View view, RestService restService) {
        this.view = view;
        this.restService = restService;
    }

}
