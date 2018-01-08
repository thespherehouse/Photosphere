package com.suhel.photosphere.screens.home.contract;

import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.realtime.RealtimeLike;
import com.suhel.photosphere.model.response.Message;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.model.response.User;
import com.suhel.photosphere.screens.home.di.HomeComponent;

import java.util.List;

public interface HomeContract {

    interface View {

        void redirectToLogin();

    }

    interface Presenter {

        void checkLoginStatus();

    }

}
