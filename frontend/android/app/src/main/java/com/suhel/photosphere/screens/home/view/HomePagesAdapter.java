package com.suhel.photosphere.screens.home.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.suhel.photosphere.screens.home.view.chatList.ChatListFragment;
import com.suhel.photosphere.screens.home.view.timeline.TimelineFragment;

public class HomePagesAdapter extends FragmentPagerAdapter {

    private TimelineFragment timelineFragment = new TimelineFragment();
//    private ChatListFragment chatListFragment = new ChatListFragment();

    public HomePagesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:

                return timelineFragment;

            default:

                return new ChatListFragment();

        }
    }

    @Override
    public int getCount() {
        return 5;
    }

}
