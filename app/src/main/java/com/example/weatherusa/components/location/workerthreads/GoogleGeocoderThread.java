package com.example.weatherusa.components.location.workerthreads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.weatherusa.components.MessageStatus;
import com.example.weatherusa.components.location.LocationHandler;
import com.example.weatherusa.util.InternetUtil;


public class GoogleGeocoderThread extends Thread {
	
	private static final String URI = "https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid={API key}";
	
	private final Handler handler;
	private final String  zip;

	/**
	 *
	 * @return
	 */
	private String getGeocoderResponse() {
		return InternetUtil.getGetResponse(URI + "?address=" + zip);
	}

	/**
	 * @param json
	 * @return
	 */
	private Bundle getLocationBundle(JSONObject json) {
		
		Bundle bundle = null;
		
		try {
			
			JSONObject coordinates = json
				.getJSONArray("results")
				.getJSONObject(0)
				.getJSONObject("geometry")
				.getJSONObject("location");
			
			double latitude  = coordinates.getDouble("lat");
			double longitude = coordinates.getDouble("lng");
			
			bundle = new Bundle();
			bundle.putDouble("latitude",  latitude);
			bundle.putDouble("longitude", longitude);
			
		} catch (JSONException e) {
			// do nothing
		}
	
		return bundle;
	}

	/**
	 * 
	 * @param locationHandler
	 * @param zip
	 */
	public GoogleGeocoderThread(LocationHandler locationHandler, String zip) {
		
		this.handler = new GoogleGeocoderHandler(locationHandler);
		this.zip     = zip;
	}

	@Override
	public void run() {
		
		Message message = Message.obtain();
		Bundle  bundle  = null;
		
		message.what = MessageStatus.OK;
		
		try {
			
			String     response = getGeocoderResponse();
			JSONObject json     = new JSONObject(response);
			
			bundle = getLocationBundle(json); 
			
		} catch (JSONException e) {
			// do nothing
		}
		
		message.setData(bundle);
		handler.sendMessage(message);
	}

}
