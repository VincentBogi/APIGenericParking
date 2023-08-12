package com.vbogi.APIGenericParking.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("APIGoogleMap")
public class LocationServiceAPIGoogleMap implements ILocationService {

	@Override
	public String getCityFromCoordinates(Double latitude, Double longitude) {
		// implementation get city name using GoogleMap
		return null;
	}

}
