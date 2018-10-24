package com.lsjwzh.widget.recyclerviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.BehaviorSubject;

/**
 * RecyclerViewPager
 *
 * @author Green
 */
public class RecyclerViewPager extends RecyclerView {
	public static final boolean DEBUG = BuildConfig.DEBUG;
	
	private static final String TAG = RecyclerViewPager.class.getSimpleName();
	
	private RecyclerViewPagerAdapter<?> mViewPagerAdapter;
	private List<OnPageChangedListener> mOnPageChangedListeners;
	
	private final BehaviorSubject<Integer> currentItemPositionSubject = BehaviorSubject.createDefault(0);
	private int oldPosition;
	
	public RecyclerViewPager(Context context) {
		this(context, null);
	}
	
	public RecyclerViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public RecyclerViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttrs(context, attrs, defStyle);
		
		PagerSnapHelper pagerSnapHelper = new PagerSnapHelper(){};
		pagerSnapHelper.attachToRecyclerView(this);
		
		oldPosition = getCurrentPosition();
		currentItemPositionSubject.onNext(getCurrentPosition());
		
		currentItemPositionSubject.subscribe(new DisposableObserver<Integer>() {
			@Override
			public void onNext(Integer position) {
				if (position >= 0 && position < getItemCount()) {
					if (mOnPageChangedListeners != null) {
						for (OnPageChangedListener onPageChangedListener : mOnPageChangedListeners) {
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
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					currentItemPositionSubject.onNext(getCurrentPosition());
				}
			}
			
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				
			}
		});
	}
	
	private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewPager, defStyle,
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
	
	public RecyclerViewPagerAdapter getWrapperAdapter() {
		return mViewPagerAdapter;
	}
	
	@Override
	public void scrollToPosition(int position) {
		if (DEBUG) {
			Log.d(TAG + ":scrollToPosition", "scrollToPosition:" + position);
		}
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
		return curPosition;
	}
	
	public void addOnPageChangedListener(@NonNull OnPageChangedListener listener) {
		if (mOnPageChangedListeners == null) {
			mOnPageChangedListeners = new ArrayList<>();
		}
		mOnPageChangedListeners.add(listener);
	}
	
	@SuppressWarnings("unused")
	public void removeOnPageChangedListener(@NonNull OnPageChangedListener listener) {
		if (mOnPageChangedListeners != null) {
			mOnPageChangedListeners.remove(listener);
		}
	}
	
	@SuppressWarnings("unused")
	public void clearOnPageChangedListeners() {
		if (mOnPageChangedListeners != null) {
			mOnPageChangedListeners.clear();
		}
	}
	
	@SuppressWarnings("unchecked")
	@NonNull
	protected RecyclerViewPagerAdapter ensureRecyclerViewPagerAdapter(Adapter adapter) {
		return (adapter instanceof RecyclerViewPagerAdapter)
				? (RecyclerViewPagerAdapter) adapter
				: new RecyclerViewPagerAdapter(this, adapter);
		
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
	
	
}
