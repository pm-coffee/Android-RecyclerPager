package com.github.pmcoffee.android.recyclerpager;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

public class TabLayoutSupport {
    
    private static final String TAG = TabLayoutSupport.class.getSimpleName();

    public static void setupWithViewPager(@NonNull TabLayout tabLayout
            , @NonNull RecyclerPager viewPager
            , @NonNull ViewPagerTabLayoutAdapter viewPagerTabLayoutAdapter) {
        tabLayout.removeAllTabs();
        int i = 0;

        for (int count = viewPagerTabLayoutAdapter.getItemCount(); i < count; ++i) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPagerTabLayoutAdapter.getPageTitle(i)));
        }
        final TabLayoutOnPageChangeListener listener
                = new TabLayoutOnPageChangeListener(tabLayout, viewPager);
        viewPager.addOnScrollListener(listener);
        viewPager.addOnPageChangedListener(listener);
        tabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager, listener));
    }

    public interface ViewPagerTabLayoutAdapter {
        String getPageTitle(int position);

        int getItemCount();
    }

    public static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        private final RecyclerPager mViewPager;
        private final TabLayoutOnPageChangeListener mOnPageChangeListener;

        public ViewPagerOnTabSelectedListener(RecyclerPager viewPager, TabLayoutOnPageChangeListener listener) {
            this.mViewPager = viewPager;
            this.mOnPageChangeListener = listener;
        }

        public void onTabSelected(TabLayout.Tab tab) {
            if(!this.mOnPageChangeListener.isPageChangeTabSelecting()) {
                this.mViewPager.smoothScrollToPosition(tab.getPosition());
            }
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    public static class TabLayoutOnPageChangeListener
            extends RecyclerView.OnScrollListener
            implements RecyclerPager.OnPageChangedListener {
        private final WeakReference<TabLayout> mTabLayoutRef;
        private final WeakReference<RecyclerPager> mViewPagerRef;
        private int mPositionBeforeScroll;
        private int mPagerLeftBeforeScroll;
        private boolean mPageChangeTabSelectingNow;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout, RecyclerPager viewPager) {
            Log.d(TAG + ":TabLayoutOnPageChange", "　");
    
            this.mTabLayoutRef = new WeakReference<>(tabLayout);
            this.mViewPagerRef = new WeakReference<>(viewPager);
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                mPositionBeforeScroll = -1;
                mPagerLeftBeforeScroll = 0;
            } else if (mPositionBeforeScroll < 0) {
                mPositionBeforeScroll = ((RecyclerPager) recyclerView).getCurrentPosition();
                mPagerLeftBeforeScroll = recyclerView.getPaddingLeft();
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            TabLayout tabLayout = this.mTabLayoutRef.get();
            final RecyclerPager viewPager = (RecyclerPager) recyclerView;
            final int pagerWidth = recyclerView.getWidth()
                    - recyclerView.getPaddingLeft()
                    - recyclerView.getPaddingRight();
            final View centerXChild = ViewUtils.getCenterXChild(viewPager);
            if (centerXChild == null) {
                return;
            }
            int centerChildPosition = viewPager.getChildAdapterPosition(centerXChild);
            float offset = mPagerLeftBeforeScroll - centerXChild.getLeft()
                    + pagerWidth * (centerChildPosition - mPositionBeforeScroll);
            final float positionOffset = offset * 1f / pagerWidth;
            if (tabLayout != null) {
                if (positionOffset < 0) {
                    try {
                        tabLayout.setScrollPosition(mPositionBeforeScroll
                                + (int) Math.floor(positionOffset),
                            positionOffset - (int) Math.floor(positionOffset),
                            false);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                } else {
                    tabLayout.setScrollPosition(mPositionBeforeScroll + (int) (positionOffset),
                            positionOffset - (int) (positionOffset),
                            false);
                }
            }
        }

        @Override
        public void OnPageChanged(int oldPosition, int newPosition) {
            Log.d(TAG + ":OnPageChanged()", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
    
            if (mViewPagerRef.get() == null) {
                return;
            }
            TabLayout tabLayout = this.mTabLayoutRef.get();
            if (tabLayout != null && tabLayout.getTabAt(newPosition) != null) {
                mPageChangeTabSelectingNow = true;
                tabLayout.getTabAt(newPosition).select();
                mPageChangeTabSelectingNow = false;
            }
        }

        public boolean isPageChangeTabSelecting(){
            return mPageChangeTabSelectingNow;
        }
    }
}
