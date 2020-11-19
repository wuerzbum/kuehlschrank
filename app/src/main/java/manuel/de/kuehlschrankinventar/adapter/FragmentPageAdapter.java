package manuel.de.kuehlschrankinventar.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import manuel.de.kuehlschrankinventar.ansichten.MyFragmentAnsicht;

public class FragmentPageAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private ArrayList<MyFragmentAnsicht> fragments;

    public FragmentPageAdapter(@NonNull FragmentManager fm, ArrayList<String> tabNames, ArrayList<MyFragmentAnsicht> fragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        this.mNumOfTabs = tabNames.size();
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public void updateFragment(int position) {
        fragments.get(position).update();
    }
}