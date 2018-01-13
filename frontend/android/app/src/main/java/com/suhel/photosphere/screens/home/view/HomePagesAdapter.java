package com.suhel.photosphere.screens.home.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.suhel.photosphere.screens.home.view.groups.ChatListFragment;
import com.suhel.photosphere.screens.home.view.timeline.TimelineFragment;
import com.suhel.photosphere.screens.home.view.upload.UploadFragment;

public class HomePagesAdapter extends FragmentPagerAdapter {

    private TimelineFragment timelineFragment = new TimelineFragment();
    private UploadFragment uploadFragment = new UploadFragment();
    private ChatListFragment chatListFragment = new ChatListFragment();

    public HomePagesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:

                return timelineFragment;

            case 2:

                return uploadFragment;

            case 3:

                return chatListFragment;

            default:

                return new ChatListFragment();

        }
    }

    @Override
    public int getCount() {
        return 5;
    }

}
