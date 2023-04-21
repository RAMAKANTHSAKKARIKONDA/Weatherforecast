package com.example.weatherusa.components.forecast.workerthreads.listeners;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public interface WeatherIconSetterListener {

	public void onIconAvailable(ImageView imageView, Drawable icon);
}
