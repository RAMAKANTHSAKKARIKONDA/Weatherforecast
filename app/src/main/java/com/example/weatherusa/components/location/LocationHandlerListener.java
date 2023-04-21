package com.example.weatherusa.components.location;

import android.location.Location;

public interface LocationHandlerListener {

	void onLocationChanged(Location location);
	void onConnectionError();
}
