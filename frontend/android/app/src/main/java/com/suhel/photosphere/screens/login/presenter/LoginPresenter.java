package com.suhel.photosphere.screens.login.presenter;

import com.suhel.photosphere.screens.login.contract.LoginContract;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.utils.UserStore;

public abstract class LoginPresenter implements LoginContract.Presenter {

    protected LoginContract.View view;
    protected RestService restService;
    protected UserStore userStore;

    public LoginPresenter(LoginContract.View view, RestService restService, UserStore userStore) {
        this.view = view;
        this.restService = restService;
        this.userStore = userStore;
    }

}
