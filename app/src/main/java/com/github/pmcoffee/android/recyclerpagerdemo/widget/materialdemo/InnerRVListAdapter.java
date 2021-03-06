package com.github.pmcoffee.android.recyclerpagerdemo.widget.materialdemo;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.pmcoffee.android.recyclerpagerdemo.R;
import com.github.pmcoffee.android.recyclerpagerdemo.databinding.ListItemBinding;
import com.github.pmcoffee.android.recyclerpagerdemo.model.Cheeses;

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
