package com.suhel.photosphere.screens.createPost.view;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.suhel.photosphere.R;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.view.ImageChooserActivity;
import com.suhel.photosphere.databinding.ActivityCreatePostBinding;
import com.suhel.photosphere.screens.createPost.contract.CreatePostContract;
import com.suhel.photosphere.screens.createPost.di.CreatePostComponent;
import com.suhel.photosphere.screens.createPost.di.CreatePostModule;
import com.suhel.photosphere.screens.createPost.presenter.CreatePostPresenter;

import java.io.File;
import java.util.List;

public class CreatePostActivity extends ImageChooserActivity<ActivityCreatePostBinding, CreatePostPresenter, CreatePostComponent> implements CreatePostContract.View {

    private ChoosePhotoSourceDialog dialog = new ChoosePhotoSourceDialog();
    private File chosenFile = null;
    private boolean isUploading = false;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_create_post;
    }

    @Override
    protected CreatePostComponent createComponent(AppContract contract) {
        return contract.getCreatePostComponent().addModule(new CreatePostModule(this)).build();
    }

    @Override
    protected void inject(CreatePostComponent component) {
        component.inject(this);
    }

    @Override
    protected void onSelectPhotos(List<File> chosenOnes) {
        chosenFile = chosenOnes.get(0);
        binding.imgPhoto.setImageURI(Uri.fromFile(chosenFile));
    }

    @Override
    protected void onActivityCreated() {
        super.onActivityCreated();
        dialog.setListener(new ChoosePhotoSourceDialog.OnClickListener() {

            @Override
            public void onCamera() {
                chooseCamera();
            }

            @Override
            public void onGallery() {
                chooseGallery();
            }

        });
        binding.btnAddPhoto.setOnClickListener(view -> {
            if (!isUploading)
                dialog.show(getSupportFragmentManager(), "choose");
        });
        binding.btnUpload.setOnClickListener(view -> {
            if (!isUploading && chosenFile != null && validate())
                presenter.upload(getPostTitle(), getPostDescription(), chosenFile);
        });
    }

    private boolean validate() {
        boolean isValid = true;

        if (getPostTitle().isEmpty()) {
            isValid = false;
            binding.tilTitle.setError("Title is mandatory");
        } else {
            binding.tilTitle.setError(null);
        }

        if (getPostDescription().isEmpty()) {
            isValid = false;
            binding.tilDescription.setError("Title is mandatory");
        } else {
            binding.tilDescription.setError(null);
        }

        return isValid;
    }

    @NonNull
    private String getPostTitle() {
        return binding.txtTitle.getText().toString().trim();
    }

    @NonNull
    private String getPostDescription() {
        return binding.txtDescription.getText().toString().trim();
    }

    @Override
    public void onUploadProgressUpdate(int percent) {
        binding.progress.setProgress(percent);
    }

    @Override
    public void onUploadSuccess() {
        binding.btnUpload.setText("Upload");
    }

    @Override
    public void onUploadFailed() {
        binding.btnUpload.setText("Retry");
    }

    @Override
    public void onShowBusy() {
        isUploading = true;
        binding.progress.setVisibility(View.VISIBLE);
        binding.progress.setProgress(0);
        binding.tilTitle.setEnabled(false);
        binding.txtTitle.setEnabled(false);
        binding.tilDescription.setEnabled(false);
        binding.txtDescription.setEnabled(false);
        binding.btnUpload.setEnabled(false);
        binding.btnUpload.setText("Uploading");
    }

    @Override
    public void onHideBusy() {
        isUploading = false;
        binding.progress.setVisibility(View.INVISIBLE);
        binding.tilTitle.setEnabled(true);
        binding.txtTitle.setEnabled(true);
        binding.tilDescription.setEnabled(true);
        binding.txtDescription.setEnabled(true);
        binding.btnUpload.setEnabled(true);
    }

}
