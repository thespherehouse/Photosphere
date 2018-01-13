package com.suhel.photosphere.screens.createPost.di;


import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.createPost.view.CreatePostActivity;

import dagger.Subcomponent;

@CreatePostScope
@Subcomponent(modules = CreatePostModule.class)
public interface CreatePostComponent extends BaseSubcomponent {

    void inject(CreatePostActivity activity);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<CreatePostComponent, CreatePostModule> {

    }

}
