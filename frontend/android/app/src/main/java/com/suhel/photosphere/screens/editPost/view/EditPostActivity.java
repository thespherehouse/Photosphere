package com.suhel.photosphere.screens.editPost.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.suhel.photosphere.R;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.view.BaseActivity;
import com.suhel.photosphere.databinding.ActivityEditPostBinding;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.editPost.contract.EditPostContract;
import com.suhel.photosphere.screens.editPost.di.EditPostComponent;
import com.suhel.photosphere.screens.editPost.di.EditPostModule;
import com.suhel.photosphere.screens.editPost.presenter.EditPostPresenter;
import com.suhel.photosphere.utils.Constants;
import com.suhel.photosphere.utils.S3Utils;

public class EditPostActivity extends BaseActivity<ActivityEditPostBinding, EditPostPresenter, EditPostComponent> implements EditPostContract.View {

    private boolean isBusy = false;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_edit_post;
    }

    @Override
    protected EditPostComponent createComponent(AppContract contract) {
        return contract.getEditPostComponent().addModule(new EditPostModule(this)).build();
    }

    @Override
    protected void inject(EditPostComponent component) {
        component.inject(this);
    }

    @Override
    protected void onActivityCreated() {
        super.onActivityCreated();
        if (getIntent().hasExtra(Constants.IntentKey.Post)) {
            presenter.setPost((Post) getIntent().getSerializableExtra(Constants.IntentKey.Post));
        } else {
            onBackPressed();
        }
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.txtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_post, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isUploadEnabled = (!isBusy && !getPostTitle().isEmpty() && !getPostDescription().isEmpty());
        menu.getItem(0).setEnabled(isUploadEnabled);
        Drawable drawable = menu.getItem(0).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, isUploadEnabled ? R.color.colorAccent : R.color.colorMedium), PorterDuff.Mode.SRC_ATOP);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.btnSave:
                presenter.edit(getPostTitle(), getPostDescription());
                return true;

        }
        return false;
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
    public void onUpdateUI(Post post) {
        setResult(RESULT_CANCELED);
        binding.imgPhoto.setAspectRatio(post.getAspectRatio());
        binding.imgPhoto.setImageURI(S3Utils.getImageUrl(post.getUrl(), S3Utils.ImageType.Thumbnail));
        binding.txtTitle.setText(post.getTitle(), TextView.BufferType.EDITABLE);
        binding.txtDescription.setText(post.getDescription(), TextView.BufferType.EDITABLE);
    }

    @Override
    public void onEditSuccess(Post post) {
        isBusy = false;
        Intent intent = new Intent();
        intent.putExtra(Constants.IntentKey.Post, post);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    public void onEditFailed() {
        Toast.makeText(this, "There was a problem, please retry", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowBusy() {
        isBusy = true;
        binding.tilTitle.setEnabled(false);
        binding.txtTitle.setEnabled(false);
        binding.tilDescription.setEnabled(false);
        binding.txtDescription.setEnabled(false);
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onHideBusy() {
        isBusy = false;
        binding.tilTitle.setEnabled(true);
        binding.txtTitle.setEnabled(true);
        binding.tilDescription.setEnabled(true);
        binding.txtDescription.setEnabled(true);
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        if (!isBusy)
            super.onBackPressed();
    }

}
