package com.example.projectmain.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectmain.Fragment.DiscoverFragment;
import com.example.projectmain.Fragment.FollowFragment;
import com.example.projectmain.Fragment.ShareFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // positon vị trí của từng tablayout
        if(position == 0)
            return DiscoverFragment.newInstance();
        if (position == 2)
            return ShareFragment.newInstance();
        else
            return FollowFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
