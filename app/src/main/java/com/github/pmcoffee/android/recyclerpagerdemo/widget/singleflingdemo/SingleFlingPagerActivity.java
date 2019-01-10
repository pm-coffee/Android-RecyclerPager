package com.github.pmcoffee.android.recyclerpagerdemo.widget.singleflingdemo;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.pmcoffee.android.recyclerpagerdemo.R;
import com.github.pmcoffee.android.recyclerpager.TabLayoutSupport;
import com.github.pmcoffee.android.recyclerpagerdemo.databinding.DemoSingleFlingPagerBinding;
import com.github.pmcoffee.android.recyclerpagerdemo.widget.LayoutAdapter;

public class SingleFlingPagerActivity extends AppCompatActivity {
	
	private static final String TAG = SingleFlingPagerActivity.class.getSimpleName();
	
	private DemoSingleFlingPagerBinding binding;
	private SingleFlingPagerViewModel viewModel;
	
	private LayoutAdapter recyclerPagerAdapter;
	
	private void setup(){
		setupBinding();
		setupEditText();
		setupButtons();
		setupRecyclerPager();
		setupTabLayout();
	}
	
	private void setupBinding(){
		binding = DataBindingUtil.setContentView(this, R.layout.demo_single_fling_pager);
		viewModel = ViewModelProviders.of(this).get(SingleFlingPagerViewModel.class);
		binding.setViewModel(viewModel);
		binding.setLifecycleOwner(this);
		
	}
	
	private void setupEditText(){
		viewModel.pageObs.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
			@Override
			public void onPropertyChanged(Observable sender, int propertyId) {
				Log.d(TAG, "viewModel.pageObs = " + viewModel.pageObs.get());
				if(viewModel.pageObs.get() == null) return;
				
				if(viewModel.pageObs.get().equals("")) {
					viewModel.pageObs.set("0");
				}else if(Integer.parseInt(viewModel.pageObs.get()) < 0){
					viewModel.pageObs.set("0");
					
				}else if(Integer.parseInt(viewModel.pageObs.get()) >= recyclerPagerAdapter.getItemCount()){
					if(recyclerPagerAdapter.getItemCount() == 0){
						viewModel.pageObs.set("0");
					}else {
						viewModel.pageObs.set((recyclerPagerAdapter.getItemCount() - 1) + "");
					}
					
				}else{
					binding.recyclerPager.scrollToPosition(Integer.parseInt(viewModel.pageObs.get()));
				}
			}
		});
	}
	
	private void setupButtons(){
		binding.addButton.setOnClickListener(v -> {
			recyclerPagerAdapter.addItem(recyclerPagerAdapter.getItemCount());
			viewModel.tabSizeObs.set((recyclerPagerAdapter.getItemCount()-1) + "");
			TabLayoutSupport.updateTabs(binding.tabLayout, recyclerPagerAdapter, null);
		});
		
		binding.delButton.setOnClickListener(v -> {
			if(recyclerPagerAdapter.getItemCount() > 0) {
				recyclerPagerAdapter.removeItem(recyclerPagerAdapter.getItemCount() - 1);
			}
			viewModel.tabSizeObs.set((recyclerPagerAdapter.getItemCount()-1) + "");
			TabLayoutSupport.updateTabs(binding.tabLayout, recyclerPagerAdapter, null);
		});
	}
	
	private void setupRecyclerPager() {
		recyclerPagerAdapter = new LayoutAdapter(this, binding.recyclerPager, 5);
		viewModel.tabSizeObs.set((recyclerPagerAdapter.getItemCount()-1) + "");
		binding.recyclerPager.setAdapter(recyclerPagerAdapter);
		binding.recyclerPager.setHasFixedSize(true);
		binding.recyclerPager.setLongClickable(true);
		binding.recyclerPager.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//                updateState(scrollState);
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int i, int i2) {
				int childCount = binding.recyclerPager.getChildCount();
				if(childCount == 0) return;
				int width = binding.recyclerPager.getChildAt(0).getWidth();
				int padding = (binding.recyclerPager.getWidth() - width) / 2;
				
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
		binding.recyclerPager.addOnPageChangedListener((oldPosition, newPosition) -> {
			Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
			viewModel.pageObs.set(newPosition + "");
		});
		
		binding.recyclerPager.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
			if (binding.recyclerPager.getChildCount() < 3) {
				if (binding.recyclerPager.getChildAt(1) != null) {
					if (binding.recyclerPager.getCurrentPosition() == 0) {
						View v1 = binding.recyclerPager.getChildAt(1);
						v1.setScaleY(0.9f);
						v1.setScaleX(0.9f);
					} else {
						View v1 = binding.recyclerPager.getChildAt(0);
						v1.setScaleY(0.9f);
						v1.setScaleX(0.9f);
					}
				}
			} else {
				if (binding.recyclerPager.getChildAt(0) != null) {
					View v0 = binding.recyclerPager.getChildAt(0);
					v0.setScaleY(0.9f);
					v0.setScaleX(0.9f);
				}
				if (binding.recyclerPager.getChildAt(2) != null) {
					View v2 = binding.recyclerPager.getChildAt(2);
					v2.setScaleY(0.9f);
					v2.setScaleX(0.9f);
				}
			}
			
		});
	}
	
	private void setupTabLayout(){
		binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
		
		TabLayoutSupport.setupWithViewPager(binding.tabLayout, binding.recyclerPager, recyclerPagerAdapter, null);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setup();
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		TabLayoutSupport.updateTabs(binding.tabLayout, recyclerPagerAdapter, null);
	}
}
