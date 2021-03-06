package com.github.pmcoffee.android.recyclerpagerdemo.widget.materialdemo;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.github.pmcoffee.android.recyclerpagerdemo.BuildConfig;
import com.github.pmcoffee.android.recyclerpagerdemo.R;
import com.github.pmcoffee.android.recyclerpager.RecyclerPager;
import com.github.pmcoffee.android.recyclerpager.TabLayoutSupport;

public class MaterialDemoActivity extends AppCompatActivity {
	private MaterialDemoViewModel viewModel;
	protected RecyclerPager mRecyclerView;
	private TabLayout tabLayout;
	private MaterialDemoListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
				.create(MaterialDemoViewModel.class);
		
		setContentView(R.layout.demo_material);
		setSupportActionBar(findViewById(R.id.toolbar));
		initViewPager();
		initTabLayout();
		observeViewModel();
		
	}
	
	private void initTabLayout() {
		tabLayout = findViewById(R.id.tabs);
		TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, mAdapter, null);
	}
	
	protected void initViewPager() {
		mRecyclerView = findViewById(R.id.viewpager);
		mAdapter = new MaterialDemoListAdapter(this);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLongClickable(true);
		mRecyclerView.addOnPageChangedListener((oldPosition, newPosition) -> {
			if(BuildConfig.DEBUG) {
				Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
			}
		});
		
		
		
	}
	
	private void observeViewModel(){
		viewModel.itemViewModelListLiveData.observe(this, itemViewData -> {
			if (itemViewData != null) {
				((MaterialDemoListAdapter) mRecyclerView.getAdapter()).submitList(itemViewData);
				TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, mAdapter, null);
			}
			
		});
	}
	
	
}
