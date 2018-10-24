package com.lsjwzh.widget.recyclerviewpagerdeomo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lsjwzh.R;
import com.lsjwzh.databinding.TmpBinding;

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
