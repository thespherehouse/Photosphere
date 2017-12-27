package com.suhel.photosphere.screens.login.contract;

import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;

public interface LoginContract {

    interface View {

        void loginWithFacebookSDK(FacebookCallback<LoginResult> callback);

        void showFacebookBusy();

        void hideFacebookBusy();

        void onLoginSuccess();

        void onLoginFailed();

    }

    interface Presenter {

        void socialLoginFacebook();

    }

}
