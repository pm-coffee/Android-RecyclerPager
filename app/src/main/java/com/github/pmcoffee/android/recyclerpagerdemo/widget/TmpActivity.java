package com.github.pmcoffee.android.recyclerpagerdemo.widget;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.pmcoffee.android.recyclerpagerdemo.R;
import com.github.pmcoffee.android.recyclerpagerdemo.databinding.TmpBinding;
import com.github.pmcoffee.android.recyclerpagerdemo.model.Cheeses;

import java.util.ArrayList;
import java.util.List;

public class TmpActivity extends AppCompatActivity {
	
	TmpBinding binding;
	InnerRVListAdapter adapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		binding = DataBindingUtil.setContentView(this, R.layout.tmp);
		
		
		adapter = new InnerRVListAdapter();
		binding.recyclerView.setAdapter(adapter);
		
		List<Cheeses> tmpList = new ArrayList<>();
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		tmpList.add(new Cheeses());
		adapter.submitList(tmpList);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}
