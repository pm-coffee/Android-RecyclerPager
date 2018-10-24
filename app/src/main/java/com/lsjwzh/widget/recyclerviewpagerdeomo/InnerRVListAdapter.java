package com.lsjwzh.widget.recyclerviewpagerdeomo;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsjwzh.R;
import com.lsjwzh.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class InnerRVListAdapter extends ListAdapter<Cheeses, InnerRVListAdapter.ViewHolder> {
	
	private static final String TAG = InnerRVListAdapter.class.getSimpleName();

	private static final DiffUtil.ItemCallback<Cheeses> diffCallback = new DiffUtil.ItemCallback<Cheeses>() {
		@Override
		public boolean areItemsTheSame(@NonNull Cheeses cheeses, @NonNull Cheeses t1) {
			return false;
		}
		
		@Override
		public boolean areContentsTheSame(@NonNull Cheeses cheeses, @NonNull Cheeses t1) {
			return false;
		}
	};
	
	public InnerRVListAdapter() {
		super(diffCallback);
		
		List<Cheeses> tmpList = new ArrayList<>();
		tmpList.add(new Cheeses());
	}
	
	@Override
	public void submitList(@Nullable List<Cheeses> list) {
		super.submitList(new ArrayList<>(list));
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		
		Log.d(TAG + ":onCreateViewHolder", "pos=" + i);
		
		return new ViewHolder(
				DataBindingUtil.inflate(
						LayoutInflater.from(viewGroup.getContext()),
						R.layout.list_item,
						viewGroup,
						false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		ListItemBinding listItemBinding = viewHolder.getListItemBinding();
		listItemBinding.setViewData(getItem(i));
	}
	
	
	class ViewHolder extends RecyclerView.ViewHolder{
		
		ListItemBinding listItemBinding;
		
		public ViewHolder(ListItemBinding listItemBinding) {
			super(listItemBinding.getRoot());
			this.listItemBinding = listItemBinding;
		}
		
		public ListItemBinding getListItemBinding() {
			return listItemBinding;
		}
	}
}
