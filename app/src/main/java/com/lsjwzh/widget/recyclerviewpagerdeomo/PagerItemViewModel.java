package com.lsjwzh.widget.recyclerviewpagerdeomo;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class PagerItemViewModel {
	
	private final MutableLiveData<List<Cheeses>> cheeseListLiveData = new MutableLiveData<>();
	
	private final MutableLiveData<String> itemListSizeLiveData = new MutableLiveData<>();
	
	
	public PagerItemViewModel() {
		cheeseListLiveData.observeForever(cheeses -> {
			itemListSizeLiveData.postValue(cheeses.size() + "");
		});
		
		List<Cheeses> tmpList = new ArrayList<>();
		for(int i=0; i<20; i++){
			tmpList.add(new Cheeses());
		}
		cheeseListLiveData.postValue(tmpList);
		
	}
	
	public MutableLiveData<List<Cheeses>> getCheeseListLiveData() {
		return cheeseListLiveData;
	}
	
	public void setCheeseListLiveData(MutableLiveData<List<Cheeses>> cheeseListLiveData) {
		this.cheeseListLiveData.postValue(cheeseListLiveData.getValue());
	}
	
	public MutableLiveData<String> getItemListSizeLiveData() {
		return itemListSizeLiveData;
	}
}
