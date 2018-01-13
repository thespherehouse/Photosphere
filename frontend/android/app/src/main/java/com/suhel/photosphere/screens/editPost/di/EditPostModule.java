package com.suhel.photosphere.screens.editPost.di;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.screens.editPost.contract.EditPostContract;
import com.suhel.photosphere.screens.editPost.presenter.EditPostPresenter;
import com.suhel.photosphere.screens.editPost.presenter.EditPostPresenterImpl;
import com.suhel.photosphere.service.rest.RestService;

import dagger.Module;
import dagger.Provides;

@Module
public class EditPostModule extends BaseModule {

    private EditPostContract.View view;

    public EditPostModule(EditPostContract.View view) {
        this.view = view;
    }

    @Provides
    EditPostPresenter providesPresenter(RestService restService) {
        return new EditPostPresenterImpl(view, restService);
    }

}
