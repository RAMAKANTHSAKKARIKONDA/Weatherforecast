package com.example.weatherusa.components.forecast;

public interface ForecastHandlerListener {

	public void onForecastAvailable(String forecast);
	public void onForecastUnavailable();
	public void onConnectionError();
}
