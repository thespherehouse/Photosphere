package com.suhel.photosphere.screens.home.contract;

import com.suhel.photosphere.model.response.Group;

import java.util.List;

public interface ChatListContract {

    interface View {

        void onNewGroups(List<Group> groups);

        void onMoreGroups(List<Group> groups);

        void onLoadError();

        void onShowBusy();

        void onHideBusy();

    }

    interface Presenter {

        void getNewGroups();

        void getMoreGroups(int skip, int limit);

    }

}
