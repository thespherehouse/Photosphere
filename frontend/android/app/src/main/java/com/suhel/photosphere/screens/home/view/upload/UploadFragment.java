package com.suhel.photosphere.screens.home.view.upload;

import android.content.Intent;

import com.suhel.photosphere.R;
import com.suhel.photosphere.base.view.BaseFragment;
import com.suhel.photosphere.databinding.FragmentUploadBinding;
import com.suhel.photosphere.screens.createPost.view.CreatePostActivity;
import com.suhel.photosphere.screens.home.contract.UploadContract;
import com.suhel.photosphere.screens.home.di.upload.UploadComponent;
import com.suhel.photosphere.screens.home.di.upload.UploadModule;
import com.suhel.photosphere.screens.home.presenter.upload.UploadPresenter;
import com.suhel.photosphere.screens.home.view.HomeActivity;

public class UploadFragment extends BaseFragment<HomeActivity, FragmentUploadBinding, UploadPresenter, UploadComponent> implements UploadContract.View {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_upload;
    }

    @Override
    protected UploadComponent createComponent(HomeActivity activity) {
        return activity.getComponent().uploadComponentBuilder().addModule(new UploadModule(this)).build();
    }

    @Override
    protected void inject(UploadComponent component) {
        component.inject(this);
    }

    @Override
    protected void onCreated() {
        super.onCreated();
        binding.btnCreateNewPost.setOnClickListener(v -> startActivity(new Intent(getContext(), CreatePostActivity.class)));
    }

}
