package com.github.pmcoffee.android.recyclerpagerdemo.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public interface OnRecyclerViewItemClickListener<VH extends RecyclerView.ViewHolder> {
  void onItemClick(View view, int position, VH viewHolder);
}
