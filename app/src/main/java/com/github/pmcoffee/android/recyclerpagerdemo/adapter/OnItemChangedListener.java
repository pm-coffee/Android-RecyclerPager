package com.github.pmcoffee.android.recyclerpagerdemo.adapter;

public interface OnItemChangedListener {

  boolean onItemMove(int fromPosition, int toPosition);

  void onItemDismiss(int position);
}
