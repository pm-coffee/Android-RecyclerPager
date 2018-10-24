package com.lsjwzh.widget.recyclerviewpagerdeomo;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lsjwzh.BuildConfig;
import com.lsjwzh.R;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lsjwzh.widget.recyclerviewpager.TabLayoutSupport;

public class MaterialDemoActivity extends AppCompatActivity {
	private MaterialDemoViewModel viewModel;
	protected RecyclerViewPager mRecyclerView;
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
		//给TabLayout增加Tab, 并关联ViewPager
		tabLayout = findViewById(R.id.tabs);
		TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, mAdapter);
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
				TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, mAdapter);
			}
			
		});
	}
	
	
}
