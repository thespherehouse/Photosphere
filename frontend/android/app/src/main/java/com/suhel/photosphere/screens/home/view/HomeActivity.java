package com.suhel.photosphere.screens.home.view;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.suhel.photosphere.R;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.view.BaseActivity;
import com.suhel.photosphere.databinding.ActivityHomeBinding;
import com.suhel.photosphere.screens.home.contract.HomeContract;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.screens.home.di.HomeModule;
import com.suhel.photosphere.screens.home.presenter.HomePresenter;
import com.suhel.photosphere.screens.login.view.LoginActivity;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomePresenter, HomeComponent> implements HomeContract.View {

    private HomePagesAdapter pagesAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected HomeComponent createComponent(AppContract contract) {
        return contract.getTimelineComponent().addModule(new HomeModule(this)).build();
    }

    @Override
    protected void inject(HomeComponent component) {
        component.inject(this);
    }

    @Override
    protected void onPreCreate() {
        presenter.checkLoginStatus();
    }

    @Override
    protected void onActivityCreated() {
        pagesAdapter = new HomePagesAdapter(getSupportFragmentManager());
        binding.pager.setOffscreenPageLimit(4);
        binding.pager.setAdapter(pagesAdapter);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.menu.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        binding.menu.setListener(index -> binding.pager.setCurrentItem(index));
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
