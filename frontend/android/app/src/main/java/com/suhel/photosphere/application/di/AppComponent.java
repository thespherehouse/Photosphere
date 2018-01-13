package com.suhel.photosphere.application.di;

import com.suhel.photosphere.application.instance.App;
import com.suhel.photosphere.base.di.BaseComponent;
import com.suhel.photosphere.screens.comments.di.CommentsComponent;
import com.suhel.photosphere.screens.createPost.di.CreatePostComponent;
import com.suhel.photosphere.screens.editPost.di.EditPostComponent;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.screens.login.di.LoginComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent extends BaseComponent {

    void inject(App app);

    LoginComponent.Builder getLoginComponent();

    HomeComponent.Builder getTimelineComponent();

    CommentsComponent.Builder getCommentsComponent();

    CreatePostComponent.Builder getCreatePostComponent();

    EditPostComponent.Builder getEditPostComponent();

}
