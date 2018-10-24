package com.lsjwzh;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

public class MyApp extends Application {
	private static final String TAG = MyApp.class.getSimpleName();
	private static final MyApp ourInstance = new MyApp();
	
	public static MyApp getInstance() {
		return ourInstance;
	}
	
	public MyApp() {
		super();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Picasso.Builder builder = new Picasso.Builder(this);
		builder.executor(Executors.newSingleThreadExecutor());
		builder.listener((picasso, uri, exception) -> {
			if(BuildConfig.DEBUG) {
//				Log.e(TAG + ":onImageLoadFailed()", "Picasso Error. uri = " + uri, exception);
			}
		});
		Picasso picasso = builder.build();
//		picasso.setLoggingEnabled(BuildConfig.DEBUG);
		picasso.setIndicatorsEnabled(BuildConfig.DEBUG);
		Picasso.setSingletonInstance(picasso);
		
	}
	
	/**
	 * fetch used Picasso. tag is ImageView hashCode.
	 * @param iv
	 * @param imageURL
	 */
	public void loadImage(@NonNull ImageView iv, @NonNull String imageURL){
		int hashTag = iv.hashCode();
		Log.d(TAG+":loadImage()", hashTag + ": " + imageURL);
		
		if(imageURL.isEmpty()) return;
		
		Picasso.with(iv.getContext()).cancelTag(hashTag);
		
		Picasso.with(iv.getContext()).load(imageURL).noPlaceholder()
				//				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE)
				.tag(hashTag).into(iv);
	}
	
	/**
	 * load and cache only
	 * @param context
	 * @param imageURL
	 * @param tag
	 */
	public void loadImage(Context context, @NonNull String imageURL, @NonNull String tag){
		Picasso.with(context).cancelTag(tag);
		Picasso.with(context).load(imageURL).noPlaceholder()
				.tag(tag).fetch();
	}
}
