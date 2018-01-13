package com.suhel.photosphere.screens.home.di.upload;

import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.home.view.timeline.TimelineFragment;
import com.suhel.photosphere.screens.home.view.upload.UploadFragment;

import dagger.Subcomponent;

@UploadScope
@Subcomponent(modules = UploadModule.class)
public interface UploadComponent extends BaseSubcomponent {

    void inject(UploadFragment fragment);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<UploadComponent, UploadModule> {
    }

}
