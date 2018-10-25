package com.github.pmcoffee.android.recyclerpagerdemo.widget.fragmentmaterialdemo;

import java.util.LinkedHashMap;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.github.pmcoffee.android.recyclerpager.FragmentStatePagerAdapter;
import com.github.pmcoffee.android.recyclerpagerdemo.R;
import com.github.pmcoffee.android.recyclerpager.RecyclerPager;
import com.github.pmcoffee.android.recyclerpager.TabLayoutSupport;
import com.github.pmcoffee.android.recyclerpagerdemo.widget.SpacesItemDecoration;

public class FragmentMaterialDemoActivity extends AppCompatActivity {
    protected RecyclerPager mRecyclerView;
    private FragmentsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_material);
        setSupportActionBar(findViewById(R.id.toolbar));
        initViewPager();
        initTabLayout();

    }

    private void initTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabs);
        TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, mAdapter);
    }

    protected void initViewPager() {
        mRecyclerView = findViewById(R.id.viewpager);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(layout);
        mAdapter = new FragmentsAdapter(getSupportFragmentManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(50, mRecyclerView.getAdapter().getItemCount()));
        mRecyclerView.addOnPageChangedListener((oldPosition, newPosition) -> Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition));

    }


    class FragmentsAdapter extends FragmentStatePagerAdapter implements TabLayoutSupport.ViewPagerTabLayoutAdapter {
        LinkedHashMap<Integer, Fragment> mFragmentCache = new LinkedHashMap<>();

        public FragmentsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position, Fragment.SavedState savedState) {
            Fragment f = mFragmentCache.containsKey(position) ? mFragmentCache.get(position)
                    : new CheeseListFragment();
            Log.e("test", "getItem:" + position + " from cache" + mFragmentCache.containsKey
                    (position));
            if (savedState == null || f.getArguments() == null) {
                Bundle bundle = new Bundle();
                bundle.putInt("index", position);
                f.setArguments(bundle);
                Log.e("test", "setArguments:" + position);
            } else if (!mFragmentCache.containsKey(position)) {
                f.setInitialSavedState(savedState);
                Log.e("test", "setInitialSavedState:" + position);
            }
            mFragmentCache.put(position, f);
            return f;
        }

        @Override
        public void onDestroyItem(int position, Fragment fragment) {
            // onDestroyItem
            while (mFragmentCache.size() > 5) {
                Object[] keys = mFragmentCache.keySet().toArray();
                mFragmentCache.remove(keys[0]);
            }
        }

        @Override
        public String getPageTitle(int position) {
            return "item-" + position;
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
}