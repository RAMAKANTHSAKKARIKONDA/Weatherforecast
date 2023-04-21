package com.example.weatherusa.components.location;

import com.example.weatherusa.components.location.workerthreads.GoogleGeocoderThread;
import com.example.weatherusa.util.location.DefaultLocationListener;
import com.example.weatherusa.util.location.LocationGetter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class LocationHandler {
	
	private static final long  MIN_TIME     = 600000;	// 10 minutes
	private static final float MIN_DISTANCE = 1000;		// 1000 meters

	private final LocationManager         locationManager;
	private final LocationHandlerListener locationHandlerListener;
	
	private String provider = null;
	

	private LocationListener locationListener = new DefaultLocationListener() {
		
		@Override
		public void onLocationChanged(Location location) {
			locationManager.removeUpdates(this);
			locationHandlerListener.onLocationChanged(location);
		}		
	};

	/**
	 * @param lm
	 * @param ll
	 */
	public LocationHandler(LocationManager lm, LocationHandlerListener ll) {
		this.locationManager         = lm;
		this.locationHandlerListener = ll;
	}

	/**
	 * 
	 * @return
	 */
	public Location getLastKnownLocation() {
		return LocationGetter.getLastKnownLocation(locationManager);
	}
	
	/**
	 * 
	 */
	public void requestLocationUpdate() {
		
		locationManager.requestLocationUpdates(
				provider, 
				MIN_TIME, 
				MIN_DISTANCE, 
				locationListener
			);
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasProvider() {
		
		provider = locationManager.getBestProvider(new Criteria(), true);
		return provider != null;
	}


	public void setLocationUsingZIP(String zip) {
		new GoogleGeocoderThread(this, zip).start();
	}

	/**
	 * @param location
	 */
	public void setLocation(Location location) {
		
		if (location.getLatitude() != 0 && location.getLongitude() != 0) {
		
			LocationGetter.setLastKnownLocation(location);
			locationHandlerListener.onLocationChanged(location);
		} else {
			locationHandlerListener.onLocationChanged(null);
		}
	}

	public void onConnectionError() {
		locationHandlerListener.onConnectionError();
	}
}
