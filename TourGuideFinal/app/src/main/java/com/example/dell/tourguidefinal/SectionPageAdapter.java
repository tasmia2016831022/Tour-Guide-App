package com.example.dell.tourguidefinal;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter{
    private final List<Fragment> mfragmentList = new ArrayList<>();
    private final List<String>mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String Title)
    {
        mfragmentList.add(fragment);
        mFragmentTitleList.add(Title);
    }
    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
