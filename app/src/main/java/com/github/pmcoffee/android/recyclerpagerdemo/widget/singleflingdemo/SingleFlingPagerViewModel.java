package com.github.pmcoffee.android.recyclerpagerdemo.widget.singleflingdemo;

import androidx.lifecycle.ViewModel;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

public class SingleFlingPagerViewModel extends ViewModel {
	
	public ObservableField<String> pageObs = new ObservableField<>("0");
	public ObservableField<String> tabSizeObs = new ObservableField<>("0");
	
	
	public SingleFlingPagerViewModel() {
		tabSizeObs.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
			@Override
			public void onPropertyChanged(Observable sender, int propertyId) {
				if (Integer.parseInt(pageObs.get()) >= Integer.parseInt(tabSizeObs.get())) {
					pageObs.set((Integer.parseInt(tabSizeObs.get()) - 1) + "");
				}
			}
		});
	}
}
