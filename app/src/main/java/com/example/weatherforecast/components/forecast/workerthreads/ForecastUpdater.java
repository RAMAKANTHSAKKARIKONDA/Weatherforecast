package com.example.weatherforecast.components.forecast.workerthreads;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Handler;
import android.os.Message;

import com.example.weatherforecast.components.MessageStatus;
import com.example.weatherforecast.components.forecast.workerthreads.listeners.ForecastUpdaterListener;
import com.example.weatherforecast.util.InternetUtil;

public class ForecastUpdater extends Thread {
	
	private static final String URI = 
		"https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";

	private static final String APP_URL =
		"";

	private static final SimpleDateFormat sdf = 
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

	private final Location location;
	private final Handler  handler;
	
	private String forecast = "";
	private String USER_AGENT;

	/**
	 * @param location
	 * @return
	 */
	private String getParameters(Location location) {
		
		String parameters = "";
		try {
			StringBuffer sb = new StringBuffer()
			
				.append("?whichClient=NDFDgen")
				.append("&lat=")
				.append(location.getLatitude())
				.append("&lon=")
				.append(location.getLongitude())
				.append("&product=time-series&begin=")
				.append(URLEncoder.encode(sdf.format(new Date()), "UTF-8"))
				
				.append("&maxt=maxt")	// Maximum Temperature
				.append("&mint=mint")	// Minimum Temperature
//				.append("&temp=temp")	// 3 Hourly Temperature
				.append("&dew=dew")		// Dewpoint Temperature
				.append("&appt=appt")	// Apparent Temperature

					
				.append("&wx=wx")		// Weather
				.append("&icons=icons")	// Weather Icons
					


				.append("&wwa=wwa")		// Watches, Warnings, and Advisories

	

				.append("&Submit=Submit");
			
			parameters = sb.toString();
			
		} catch (UnsupportedEncodingException e) {
			// do nothing
		}
					
		return parameters;
	}

	/**
	 * 
	 * @param location
	 * @param dialog
	 * @param listener 
	 */
	public ForecastUpdater
		(
				Location location, 
				final ProgressDialog dialog, 
				final ForecastUpdaterListener listener
		) {
		super();

		this.location = location;
		this.handler  = new Handler() {
			
			@Override
			public void handleMessage(Message message) {
				
				try {
					dialog.dismiss();
				} catch (Exception e) {
					// TODO: handle orientation change

				}
				
				if (message.what == MessageStatus.OK) {
					listener.onForecastAvailable(forecast);
				} else {
					listener.onConnectionError();
				}
			}
		};
	}

	@Override
	public void run() {
		
		String url = URI + getParameters(location);
		forecast = InternetUtil.getGetResponse(url, USER_AGENT);
		
		handler.sendEmptyMessage(MessageStatus.OK);
	}
}
