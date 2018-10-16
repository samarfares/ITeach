package com.example.tcc.iteach;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabsAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentlist = new ArrayList<>();
    private final List<String> fragmentListTitles = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles.get(position);
    }
    public void AddFragment (Fragment fragment, String title){
        fragmentlist.add(fragment);
        fragmentListTitles.add(title);
    }
}

