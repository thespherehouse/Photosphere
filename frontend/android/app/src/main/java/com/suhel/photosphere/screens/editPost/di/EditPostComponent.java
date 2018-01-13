package com.suhel.photosphere.screens.editPost.di;


import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.editPost.view.EditPostActivity;

import dagger.Subcomponent;

@EditPostScope
@Subcomponent(modules = EditPostModule.class)
public interface EditPostComponent extends BaseSubcomponent {

    void inject(EditPostActivity activity);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<EditPostComponent, EditPostModule> {

    }

}
