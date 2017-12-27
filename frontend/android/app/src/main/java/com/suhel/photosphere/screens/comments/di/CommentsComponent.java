package com.suhel.photosphere.screens.comments.di;


import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.comments.view.CommentsActivity;

import dagger.Subcomponent;

@CommentsScope
@Subcomponent(modules = CommentsModule.class)
public interface CommentsComponent extends BaseSubcomponent {

    void inject(CommentsActivity activity);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<CommentsComponent, CommentsModule> {

    }

}
