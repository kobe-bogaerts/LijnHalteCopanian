package com.kolllor3.lijnhaltecopanian.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kolllor3.lijnhaltecopanian.R;
import com.kolllor3.lijnhaltecopanian.RealTimeFragment;
import com.kolllor3.lijnhaltecopanian.TimeTableFragment;


public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private int haltenummer;
    private int entiteitnummer;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    public void setHaltenummer(int haltenummer) {
        this.haltenummer = haltenummer;
    }

    public void setEntiteitnummer(int entiteitnummer) {
        this.entiteitnummer = entiteitnummer;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a TimeTableFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return RealTimeFragment.newInstance(haltenummer,entiteitnummer);
            case 1:
                return TimeTableFragment.newInstance(haltenummer, entiteitnummer);
        }
        return TimeTableFragment.newInstance(haltenummer, entiteitnummer);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}