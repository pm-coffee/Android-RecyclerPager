package com.github.pmcoffee.android.recyclerpagerdemo.widget.materialdemo;

import androidx.lifecycle.LifecycleOwner;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.pmcoffee.android.recyclerpagerdemo.R;
import com.github.pmcoffee.android.recyclerpagerdemo.databinding.PagerItemBinding;
import com.github.pmcoffee.android.recyclerpager.TabLayoutSupport;


public class MaterialDemoListAdapter extends ListAdapter<PagerItemViewModel, MaterialDemoListAdapter.ViewHolder>
		implements TabLayoutSupport.TabLayoutAdapter {
	
	private static final String TAG = MaterialDemoListAdapter.class.getSimpleName();
	private final LifecycleOwner lifecycleOwner;
	
	private static final DiffUtil.ItemCallback<PagerItemViewModel> diffCallback = new DiffUtil.ItemCallback<PagerItemViewModel>() {
		@Override
		public boolean areItemsTheSame(@NonNull PagerItemViewModel itemViewData, @NonNull PagerItemViewModel t1) {
			return false;
		}
		
		@Override
		public boolean areContentsTheSame(@NonNull PagerItemViewModel itemViewData, @NonNull PagerItemViewModel t1) {
			return false;
		}
	};
	
	
	
	public MaterialDemoListAdapter(LifecycleOwner lifecycleOwner) {
		super(diffCallback);
		this.lifecycleOwner = lifecycleOwner;
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		return new ViewHolder(
				DataBindingUtil.inflate(
						LayoutInflater.from(viewGroup.getContext()),
						R.layout.pager_item,
						viewGroup,
						false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		PagerItemBinding itemBinding = viewHolder.getPagerItemBinding();
		
		PagerItemViewModel pagerItemViewModel = getItem(i);
		itemBinding.setViewModel(pagerItemViewModel);
		
		InnerRVListAdapter innerAdapter = new InnerRVListAdapter();
		if(pagerItemViewModel.getCheeseListLiveData().getValue() != null) {
			innerAdapter.submitList(pagerItemViewModel.getCheeseListLiveData().getValue());
		}
		itemBinding.cheeseRecyclerView.setAdapter(innerAdapter);
		
		pagerItemViewModel.getCheeseListLiveData().observe(lifecycleOwner, cheeses -> {
			if (cheeses != null) {
				innerAdapter.submitList(cheeses);
			}
		});
		
		
	}
	
	@Override
	public String getPageTitle(int position) {
		return "hoge:" + position;
	}
	
	class ViewHolder extends RecyclerView.ViewHolder{
		
		PagerItemBinding pagerItemBinding;
		
		public ViewHolder(PagerItemBinding listItemBinding) {
			super(listItemBinding.getRoot());
			this.pagerItemBinding = listItemBinding;
		}
		
		public PagerItemBinding getPagerItemBinding() {
			return pagerItemBinding;
		}
	}
}