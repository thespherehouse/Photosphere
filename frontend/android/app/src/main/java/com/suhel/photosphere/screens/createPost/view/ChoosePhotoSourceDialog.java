package com.suhel.photosphere.screens.createPost.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.photosphere.R;
import com.suhel.photosphere.databinding.DialogChoosePhotoSourceBinding;

public class ChoosePhotoSourceDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private DialogChoosePhotoSourceBinding binding;
    private OnClickListener listener;

    public OnClickListener getListener() {
        return listener;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = DialogChoosePhotoSourceBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnFromCamera.setOnClickListener(this);
        binding.btnFromGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnFromCamera:

                if (listener != null) {
                    listener.onCamera();
                }
                dismiss();
                break;

            case R.id.btnFromGallery:

                if (listener != null) {
                    listener.onGallery();
                }
                dismiss();
                break;

        }
    }


    public interface OnClickListener {

        void onCamera();

        void onGallery();

    }

}
