package com.suhel.photosphere.screens.home.view.upload;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.photosphere.R;
import com.suhel.photosphere.databinding.DialogDeletePostBinding;

public class DeletePostDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private DialogDeletePostBinding binding;
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
        return (binding = DialogDeletePostBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnDelete.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnDelete:

                if (listener != null) {
                    listener.onDelete();
                }
                dismiss();
                break;

            case R.id.btnCancel:

                dismiss();
                break;

        }
    }


    public interface OnClickListener {

        void onDelete();

    }

}
