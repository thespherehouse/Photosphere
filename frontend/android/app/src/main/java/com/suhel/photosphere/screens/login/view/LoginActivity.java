package com.suhel.photosphere.screens.login.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.suhel.photosphere.R;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.view.BaseActivity;
import com.suhel.photosphere.custom.module.GlideApp;
import com.suhel.photosphere.databinding.ActivityLoginBinding;
import com.suhel.photosphere.screens.home.view.HomeActivity;
import com.suhel.photosphere.screens.login.contract.LoginContract;
import com.suhel.photosphere.screens.login.di.LoginComponent;
import com.suhel.photosphere.screens.login.di.LoginModule;
import com.suhel.photosphere.screens.login.presenter.LoginPresenter;

import java.util.Arrays;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginPresenter, LoginComponent> implements LoginContract.View {

    private CallbackManager callbackManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginComponent getComponent(AppContract contract) {
        return contract.getLoginComponent().addModule(new LoginModule(this)).build();
    }

    @Override
    protected void inject(LoginComponent component) {
        component.inject(this);
    }

    @Override
    protected void onActivityCreated() {
        binding.setPresenter(presenter);
        GlideApp.with(this)
                .load("https://source.unsplash.com/random")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.backdrop);
    }

    @Override
    public void loginWithFacebookSDK(FacebookCallback<LoginResult> callback) {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, callback);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showFacebookBusy() {
        binding.progressFacebook.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFacebookBusy() {
        binding.progressFacebook.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onLoginFailed() {
        Snackbar.make(binding.getRoot(), "Some weird problem occured", Snackbar.LENGTH_SHORT).show();
    }

}
