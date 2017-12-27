package com.suhel.photosphere.screens.login.presenter;

import android.os.Bundle;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.screens.login.contract.LoginContract;
import com.suhel.photosphere.service.rest.RestService;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenterImpl extends LoginPresenter {

    private GraphRequest.GraphJSONObjectCallback graphCallback = new GraphRequest.GraphJSONObjectCallback() {

        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                String name = object.getString("name");
                String email = object.getString("email");
                String socialId = object.getString("id");
                registerSocialFacebook(name, email, socialId);
            } catch (JSONException e) {
                e.printStackTrace();
                view.hideFacebookBusy();
            }
        }

    };

    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), graphCallback);
            Bundle graphParams = new Bundle();
            graphParams.putString("fields", "id,name,email");
            graphRequest.setParameters(graphParams);
            graphRequest.executeAsync();
        }

        @Override
        public void onCancel() {
            view.hideFacebookBusy();
        }

        @Override
        public void onError(FacebookException error) {
            view.hideFacebookBusy();
        }

    };

    public LoginPresenterImpl(LoginContract.View view, RestService restService) {
        super(view, restService);
    }

    @Override
    public void socialLoginFacebook() {
        view.showFacebookBusy();
        view.loginWithFacebookSDK(facebookCallback);
    }

    private void registerSocialFacebook(String name, String email, String socialId) {
        restService.registerSocial(name, email, socialId, 1, new ApiSubscriber.Callback<Void>() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(Void data) {
                view.onLoginSuccess();
            }

            @Override
            public void onError(ApiError apiError) {
                view.onLoginFailed();
            }

            @Override
            public void onEnd() {
                view.hideFacebookBusy();
            }

        });
    }

}
