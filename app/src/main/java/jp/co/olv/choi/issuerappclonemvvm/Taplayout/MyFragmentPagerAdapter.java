package jp.co.olv.choi.issuerappclonemvvm.Taplayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jp.co.olv.choi.issuerappclonemvvm.R;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private CharSequence[] tabTitles = new CharSequence[2];

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles[0] = context.getResources().getString(R.string.left_tab_title);
        tabTitles[1] = context.getResources().getString(R.string.right_tab_title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LeftFragment();
            case 1:
                return new RightFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}