package com.suhel.photosphere.base.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.annotation.NonNull;

import com.suhel.photosphere.base.di.BaseSubcomponent;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyImage;

public abstract class ImageChooserActivity<V extends ViewDataBinding, P, C extends BaseSubcomponent>
        extends BaseActivity<V, P, C> {

    private EasyImage.Callbacks easyImageCallback = new EasyImage.Callbacks() {

        @Override
        public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            e.printStackTrace();
        }

        @Override
        public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
            if (!imageFiles.isEmpty())
                onSelectPhotos(imageFiles);
        }

        @Override
        public void onCanceled(EasyImage.ImageSource source, int type) {
            if (source == EasyImage.ImageSource.CAMERA) {
                File lastKnownFile = EasyImage.lastlyTakenButCanceledPhoto(ImageChooserActivity.this);
                if (lastKnownFile != null)
                    lastKnownFile.delete();
            }
        }

    };

    @Override
    protected void onActivityCreated() {
        super.onActivityCreated();

    }

    public final void chooseCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        else
            onCameraPermissionGranted();
    }

    public final void chooseGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        else
            onGalleryPermissionGranted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED)
                isGranted = false;
        }

        if (isGranted) {
            if (requestCode == 100)
                onCameraPermissionGranted();
            else if (requestCode == 101)
                onGalleryPermissionGranted();
        }
    }

    private void onCameraPermissionGranted() {
        EasyImage.openCamera(this, 0);
    }

    private void onGalleryPermissionGranted() {
        EasyImage.openGallery(this, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, easyImageCallback);
    }

    protected abstract void onSelectPhotos(List<File> chosenOnes);

}
