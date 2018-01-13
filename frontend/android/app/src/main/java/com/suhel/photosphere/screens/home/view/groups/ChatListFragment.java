package com.suhel.photosphere.screens.home.view.groups;

import android.support.v7.widget.LinearLayoutManager;

import com.suhel.photosphere.R;
import com.suhel.photosphere.base.view.BaseFragment;
import com.suhel.photosphere.databinding.FragmentChatListBinding;
import com.suhel.photosphere.model.response.Group;
import com.suhel.photosphere.screens.home.contract.ChatListContract;
import com.suhel.photosphere.screens.home.di.groups.ChatListComponent;
import com.suhel.photosphere.screens.home.di.groups.ChatListModule;
import com.suhel.photosphere.screens.home.presenter.groups.ChatListPresenter;
import com.suhel.photosphere.screens.home.view.HomeActivity;

import java.util.List;

public class ChatListFragment extends BaseFragment<HomeActivity, FragmentChatListBinding, ChatListPresenter, ChatListComponent> implements ChatListContract.View {

    private GroupsAdapter adapter = new GroupsAdapter();

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
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(adapter);
        presenter.getNewGroups();
    }

    @Override
    public void onNewGroups(List<Group> groups) {
        adapter.setData(groups);
    }

    @Override
    public void onMoreGroups(List<Group> groups) {

    }

    @Override
    public void onLoadError() {

    }

    @Override
    public void onShowBusy() {

    }

    @Override
    public void onHideBusy() {

    }

}
