package com.suhel.photosphere.screens.home.view.chatList;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;

import com.suhel.photosphere.R;
import com.suhel.photosphere.base.view.BaseFragment;
import com.suhel.photosphere.databinding.FragmentChatListBinding;
import com.suhel.photosphere.screens.home.contract.ChatListContract;
import com.suhel.photosphere.screens.home.di.chatList.ChatListComponent;
import com.suhel.photosphere.screens.home.di.chatList.ChatListModule;
import com.suhel.photosphere.screens.home.presenter.chatList.ChatListPresenter;
import com.suhel.photosphere.screens.home.view.HomeActivity;

public class ChatListFragment extends BaseFragment<HomeActivity, FragmentChatListBinding, ChatListPresenter, ChatListComponent> implements ChatListContract.View {

    private ChatListAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_chat_list;
    }

    @Override
    protected ChatListComponent createComponent(HomeActivity activity) {
        return activity.getComponent().chatListComponentBuilder().addModule(new ChatListModule(this)).build();
    }

    @Override
    protected void inject(ChatListComponent component) {
        component.inject(this);
    }

    @Override
    protected void onCreated() {
        super.onCreated();
    }

}
