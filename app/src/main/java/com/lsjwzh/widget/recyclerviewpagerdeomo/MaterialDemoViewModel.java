package com.lsjwzh.widget.recyclerviewpagerdeomo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MaterialDemoViewModel extends AndroidViewModel {
	
	public final MutableLiveData<List<PagerItemViewModel>> itemViewModelListLiveData;
	
	public MaterialDemoViewModel(@NonNull Application application) {
		super(application);
		
		itemViewModelListLiveData = new MutableLiveData<>();
		List<PagerItemViewModel> tmpList = new ArrayList<>();
		for(int i=0; i<10; i++){
			tmpList.add(new PagerItemViewModel());
		}
		itemViewModelListLiveData.postValue(tmpList);
	}
	
	
	@Override
	protected void onCleared() {
		super.onCleared();
	}
}
