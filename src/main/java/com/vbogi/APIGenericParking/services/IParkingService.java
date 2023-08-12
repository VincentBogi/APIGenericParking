package com.vbogi.APIGenericParking.services;

import java.util.List;

import com.vbogi.APIGenericParking.models.Parking;
import com.vbogi.APIGenericParking.models.SearchZone;
import com.vbogi.APIGenericParking.modules.city.APICity;

public interface IParkingService {
	public List<Parking> getParkingData(APICity<?> apiCity, SearchZone searchZone) throws Exception;
}
