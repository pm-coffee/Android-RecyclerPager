package com.github.pmcoffee.android.recyclerpagerdemo.widget.singleflingdemo;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

public class SingleFlingPagerViewModel extends ViewModel {
	
	public ObservableField<String> pageObs = new ObservableField<>("0");
	
}
