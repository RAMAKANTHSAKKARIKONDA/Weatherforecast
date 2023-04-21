package com.example.weatherforecast.components.forecast.workerthreads.listeners;

public interface ForecastUpdaterListener {

	void onForecastAvailable(String forecast);
	void onConnectionError();
}
