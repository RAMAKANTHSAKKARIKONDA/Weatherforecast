package com.example.weatherforecast.components.location.workerthreads;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.example.weatherforecast.components.MessageStatus;
import com.example.weatherforecast.components.location.LocationHandler;
import com.example.weatherforecast.util.location.LocationGetter;

public class GoogleGeocoderHandler extends Handler {
	
	private LocationHandler locationHandler;
	
	/**
	 * @param locationHandler
	 */
	public GoogleGeocoderHandler(LocationHandler locationHandler) {
		this.locationHandler = locationHandler;
	}

	public void handleMessage(Message message) {
		
		if (message.what == MessageStatus.ERROR_CONNECTING_TO_SERVER) {
			locationHandler.onConnectionError();
		}
		
		Bundle bundle = message.getData();
		if (bundle != null) {
			
			double latitude  = bundle.getDouble("latitude");
			double longitude = bundle.getDouble("longitude");			
			
			Location location = LocationGetter.getLocation(latitude, longitude);
			locationHandler.setLocation(location);
		}
	}

}
