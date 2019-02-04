package com.github.pmcoffee.android.recyclerpager;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.BehaviorSubject;

/**
 * RecyclerViewPager
 *
 * @author Green
 */
public class RecyclerPager extends RecyclerView {
	
	private static final String TAG = RecyclerPager.class.getSimpleName();
	
	private RecyclerPagerAdapter<?> mViewPagerAdapter;
	private final List<OnPageChangedListener> mOnPageChangedListenerList = new ArrayList<>();
	
	private final BehaviorSubject<Integer> currentItemPositionSubject = BehaviorSubject.createDefault(0);
	private int oldPosition;
	
	public RecyclerPager(Context context) {
		this(context, null);
	}
	
	public RecyclerPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public RecyclerPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttrs(context, attrs, defStyle);
		
		PagerSnapHelper pagerSnapHelper = new PagerSnapHelper(){};
		pagerSnapHelper.attachToRecyclerView(this);
		
		oldPosition = getCurrentPosition();
		currentItemPositionSubject.onNext(getCurrentPosition());
		
		currentItemPositionSubject.subscribe(new DisposableObserver<Integer>() {
			@Override
			public void onNext(Integer position) {
				RecyclerPagerLogger.d(TAG, "currentItemPositionSubject onNext = " + position);
				if (position >= 0 && position < getItemCount()) {
					if (mOnPageChangedListenerList != null) {
						for (OnPageChangedListener onPageChangedListener : mOnPageChangedListenerList) {
							if (onPageChangedListener != null) {
								onPageChangedListener.OnPageChanged(oldPosition, position);
							}
						}
					}
					oldPosition = position;
				}
			}
			
			@Override
			public void onError(Throwable e) {
			
			}
			
			@Override
			public void onComplete() {
			
			}
		});
		
		
		addOnScrollListener(new OnScrollListener() {
			/**
			 * <li>not called when use scrollToPosition().</li>
			 * <li>called when swipe by user</li>
			 */
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					currentItemPositionSubject.onNext(getCurrentPosition());
				}
			}
			
			/**
			 * <li>called when use scrollToPosition().</li>
			 * <li>not called when swipe by user</li>
			 */
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
					currentItemPositionSubject.onNext(getCurrentPosition());
				}
			}
		});
	}
	
	private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerPager, defStyle,
				0);
		a.recycle();
	}
	
	
	@Override
	public void setAdapter(Adapter adapter) {
		mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
		super.setAdapter(mViewPagerAdapter);
	}
	
	@Override
	public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
		mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
		super.swapAdapter(mViewPagerAdapter, removeAndRecycleExistingViews);
	}
	
	@Override
	public Adapter getAdapter() {
		if (mViewPagerAdapter != null) {
			return mViewPagerAdapter.mAdapter;
		}
		return null;
	}
	
	public RecyclerPagerAdapter getWrapperAdapter() {
		return mViewPagerAdapter;
	}
	
	@Override
	public void scrollToPosition(int position) {
		RecyclerPagerLogger.d(TAG + ":scrollToPosition", "scrollToPosition:" + position);
		super.scrollToPosition(position);
	}
	
	private int getItemCount() {
		return mViewPagerAdapter == null ? 0 : mViewPagerAdapter.getItemCount();
	}
	
	/**
	 * get item position in center of viewpager
	 */
	public int getCurrentPosition() {
		int curPosition;
		if (getLayoutManager().canScrollHorizontally()) {
			curPosition = ViewUtils.getCenterXChildPosition(this);
		} else {
			curPosition = ViewUtils.getCenterYChildPosition(this);
		}
		if (curPosition < 0) {
			curPosition = currentItemPositionSubject.hasValue()? currentItemPositionSubject.getValue():0;
		}
		RecyclerPagerLogger.d(TAG, "getCurrentPosition() pos = " + curPosition);
		return curPosition;
	}
	
	public void addOnPageChangedListener(@NonNull OnPageChangedListener listener) {
		mOnPageChangedListenerList.add(listener);
		RecyclerPagerLogger.d(TAG, "mOnPageChangedListenerList.size = " + mOnPageChangedListenerList.size());
	}
	
	@SuppressWarnings("unused")
	public void removeOnPageChangedListener(@NonNull OnPageChangedListener listener) {
		mOnPageChangedListenerList.remove(listener);
		RecyclerPagerLogger.d(TAG, "mOnPageChangedListenerList.size = " + mOnPageChangedListenerList.size());
	}
	
	@SuppressWarnings("unused")
	public void clearOnPageChangedListeners() {
		mOnPageChangedListenerList.clear();
		RecyclerPagerLogger.d(TAG, "mOnPageChangedListenerList.size = " + mOnPageChangedListenerList.size());
	}
	
	@SuppressWarnings("unchecked")
	@NonNull
	protected RecyclerPagerAdapter ensureRecyclerViewPagerAdapter(Adapter adapter) {
		return (adapter instanceof RecyclerPagerAdapter)
				? (RecyclerPagerAdapter) adapter
				: new RecyclerPagerAdapter(this, adapter);
		
	}
	
	public interface OnPageChangedListener {
		/**
		 * Fires when viewpager changes it's page
		 *
		 * @param oldPosition old position
		 * @param newPosition new position
		 */
		void OnPageChanged(int oldPosition, int newPosition);
	}
	
	public void onItemSizeChanged(){
		Adapter adapter = getAdapter();
		if(adapter != null) {
			if(adapter.getItemCount() == 0){
				currentItemPositionSubject.onNext(0);
			}else if (currentItemPositionSubject.hasValue()
					&& currentItemPositionSubject.getValue() > adapter.getItemCount()) {
				currentItemPositionSubject.onNext(adapter.getItemCount() - 1);
			}
		}
	}
	
}
