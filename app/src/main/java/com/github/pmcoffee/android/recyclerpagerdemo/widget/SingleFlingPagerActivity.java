package com.github.pmcoffee.android.recyclerpagerdemo.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.pmcoffee.android.recyclerpagerdemo.R;
import com.github.pmcoffee.android.recyclerpager.RecyclerViewPager;
import com.github.pmcoffee.android.recyclerpager.TabLayoutSupport;

public class SingleFlingPagerActivity extends Activity {
    protected RecyclerViewPager mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_single_fling_pager);
        initViewPager();
    
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    
        TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, new TabLayoutSupport.ViewPagerTabLayoutAdapter() {
            @Override
            public String getPageTitle(int position) {
                return ""+position;
            }
    
            @Override
            public int getItemCount() {
                return mRecyclerView.getWrapperAdapter().getItemCount();
            }
        });
    }

    protected void initViewPager() {
        mRecyclerView = findViewById(R.id.viewpager);
        mRecyclerView.setAdapter(new LayoutAdapter(this, mRecyclerView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//                updateState(scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                        v.setScaleX(1 - rate * 0.1f);

                    } else {
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                        v.setScaleX(0.9f + rate * 0.1f);
                    }
                }
            }
        });
        mRecyclerView.addOnPageChangedListener((oldPosition, newPosition) -> Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition));

        mRecyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (mRecyclerView.getChildCount() < 3) {
                if (mRecyclerView.getChildAt(1) != null) {
                    if (mRecyclerView.getCurrentPosition() == 0) {
                        View v1 = mRecyclerView.getChildAt(1);
                        v1.setScaleY(0.9f);
                        v1.setScaleX(0.9f);
                    } else {
                        View v1 = mRecyclerView.getChildAt(0);
                        v1.setScaleY(0.9f);
                        v1.setScaleX(0.9f);
                    }
                }
            } else {
                if (mRecyclerView.getChildAt(0) != null) {
                    View v0 = mRecyclerView.getChildAt(0);
                    v0.setScaleY(0.9f);
                    v0.setScaleX(0.9f);
                }
                if (mRecyclerView.getChildAt(2) != null) {
                    View v2 = mRecyclerView.getChildAt(2);
                    v2.setScaleY(0.9f);
                    v2.setScaleX(0.9f);
                }
            }

        });
    }
}
