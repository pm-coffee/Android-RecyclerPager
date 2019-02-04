package com.github.pmcoffee.android.recyclerpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;

public class TabLayoutSupport {
    
    private static final String TAG = TabLayoutSupport.class.getSimpleName();

    public static void setupWithViewPager(
            @NonNull TabLayout tabLayout,
            @NonNull RecyclerPager recyclerPager,
            @NonNull TabLayoutAdapter tabLayoutAdapter,
            @Nullable UpdateTabsForCustomViewProcess process) {
    
        updateTabs(tabLayout, tabLayoutAdapter, process);
        
        final TabLayoutOnPageChangeListener listener
                = new TabLayoutOnPageChangeListener(tabLayout, recyclerPager);
        recyclerPager.addOnScrollListener(listener);
        recyclerPager.addOnPageChangedListener(listener);
        tabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(recyclerPager, listener));
    }
    
    public static void updateTabs(@NonNull TabLayout tabLayout, @NonNull TabLayoutAdapter tabLayoutAdapter, @Nullable UpdateTabsForCustomViewProcess process){
        RecyclerPagerLogger.d(TAG, "updateTab() before tabLayout.getTabCount() = " + tabLayout.getTabCount() + " tabLayoutAdapter.getItemCount() = " + tabLayoutAdapter.getItemCount());
        if(tabLayout.getTabCount() > tabLayoutAdapter.getItemCount()){
            while(tabLayout.getTabCount() != tabLayoutAdapter.getItemCount()){
                tabLayout.removeTabAt(tabLayout.getTabCount() - 1);
            }
        }
        RecyclerPagerLogger.d(TAG, "updateTab() after tabLayout.getTabCount() = " + tabLayout.getTabCount() + " tabLayoutAdapter.getItemCount() = " + tabLayoutAdapter.getItemCount());
        
        final int count = tabLayoutAdapter.getItemCount();
        for (int i = 0; i < count; i++) {
            RecyclerPagerLogger.d(TAG, "updateTab() i = " + i);
            if (tabLayout.getTabCount() > i) {
                tabLayout.getTabAt(i).setText(tabLayoutAdapter.getPageTitle(i));
                if(process != null) {
                    process.updateTab(tabLayout.getTabAt(i));
                }
            }else{
                TabLayout.Tab tab = tabLayout.newTab().setText(tabLayoutAdapter.getPageTitle(i));
                tabLayout.addTab(tab);
                if(process != null) {
                    process.updateTab(tab);
                }
            }
        }
    }

    public interface TabLayoutAdapter {
        String getPageTitle(int position);

        int getItemCount();
    }
    
    public interface UpdateTabsForCustomViewProcess{
        void updateTab(TabLayout.Tab tab);
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
        private int beforePosition;
        private int mPagerLeftBeforeScroll;
        private boolean mPageChangeTabSelectingNow;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout, RecyclerPager viewPager) {
            RecyclerPagerLogger.d(TAG + ":TabLayoutOnPageChange", "　");
    
            this.mTabLayoutRef = new WeakReference<>(tabLayout);
            this.mViewPagerRef = new WeakReference<>(viewPager);
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                RecyclerPagerLogger.d(TAG, "onScrollStateChanged SCROLL_STATE_IDLE");
                beforePosition = -1;
                mPagerLeftBeforeScroll = 0;
            } else if (beforePosition < 0) {
                beforePosition = ((RecyclerPager) recyclerView).getCurrentPosition();
                mPagerLeftBeforeScroll = recyclerView.getPaddingLeft();
            }
            RecyclerPagerLogger.d(TAG, "beforePosition = " + beforePosition);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if(recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE){
                RecyclerPagerLogger.d(TAG, "onScrolled SCROLL_STATE_IDLE");
            }
            TabLayout tabLayout = this.mTabLayoutRef.get();
            final RecyclerPager recyclerPager = (RecyclerPager) recyclerView;
            final int pagerWidth = recyclerView.getWidth()
                    - recyclerView.getPaddingLeft()
                    - recyclerView.getPaddingRight();
            final View centerXChild = ViewUtils.getCenterXChild(recyclerPager);
            if (centerXChild == null) {
                return;
            }
//            int centerChildPosition = recyclerPager.getChildAdapterPosition(centerXChild);
            int centerChildPosition = recyclerPager.getCurrentPosition();
            RecyclerPagerLogger.d(TAG, "onScrolled centerChildPosition = " + centerChildPosition);
            float offset = mPagerLeftBeforeScroll - centerXChild.getLeft() + pagerWidth * (centerChildPosition - beforePosition);
            float positionOffset = offset * 1f / pagerWidth;
            if(recyclerPager.getAdapter() == null) {
                return;
            }
            if (tabLayout != null) {
                if (positionOffset < 0) {
                    try {
                        int position = beforePosition + (int) Math.floor(positionOffset);
                        positionOffset = positionOffset - (int) Math.floor(positionOffset);
                        if(position >= 0) {
                            tabLayout.setScrollPosition(position,
                                    positionOffset,
                                    false);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                } else {
                    int position = beforePosition + (int) (positionOffset);
                    positionOffset = positionOffset - (int) (positionOffset);
                    if(position >= 0) {
                        tabLayout.setScrollPosition(position,
                                positionOffset,
                                false);
                    }
                }
            }
        }

        @Override
        public void OnPageChanged(int oldPosition, int newPosition) {
            RecyclerPagerLogger.d(TAG + ":OnPageChanged()", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
    
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
