package com.lsjwzh.util;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageViewDataBindingAdapters {
	
	//region app:picasso
	@BindingAdapter("picassoResId")
	public static void setImageResource(ImageView imageView, @DrawableRes int resource){
		imageView.setImageResource(resource);
		Picasso.with(imageView.getContext())
				.load(resource)
				.centerInside()
				.fit()
				.into(imageView);
	}
	
	
	@BindingAdapter("picassoUriString")
	public static void setImageUriString(ImageView view, String imageUri) {
		if (imageUri != null && !imageUri.isEmpty()) {
			Uri uri = Uri.parse(imageUri);
			Picasso.with(view.getContext())
					.load(uri)
					.centerInside()
					.fit()
					.into(view);
		}
	}
	
	@BindingAdapter("picassoUri")
	public static void setImageUri(ImageView view, Uri imageUri) {
		Picasso.with(view.getContext())
				.load(imageUri)
				.centerInside()
				.fit()
				.into(view);
	}
	
	//endregion android:src
	
}
