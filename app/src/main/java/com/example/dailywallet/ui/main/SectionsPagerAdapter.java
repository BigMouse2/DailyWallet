package com.example.dailywallet.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.dailywallet.R;
import com.example.dailywallet.ui.main.fragment.About;
import com.example.dailywallet.ui.main.fragment.Category;
import com.example.dailywallet.ui.main.fragment.Settings;
import com.example.dailywallet.ui.main.fragment.Wallet;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.wallet, R.string.category, R.string.settings, R.string.about};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        switch(position){
            case 0: fragment = Wallet.newInstance("wallet");
                break;
            case 1: fragment = new Category();
                break;
            case 2: fragment = new Settings();
                break;
            case 3: fragment = new About();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show total pages.
        return 4;
    }
}