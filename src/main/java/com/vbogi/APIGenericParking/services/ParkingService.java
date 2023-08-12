package com.vbogi.APIGenericParking.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vbogi.APIGenericParking.models.Parking;
import com.vbogi.APIGenericParking.models.SearchZone;
import com.vbogi.APIGenericParking.modules.city.APICity;

@Service
@Qualifier("parkingService")
public class ParkingService implements IParkingService {

	@Override
	public List<Parking> getParkingData(APICity<?> apiCity, SearchZone searchZone) throws Exception {
		List<Parking> results = new ArrayList<Parking>();
		results = apiCity.getParkingsData(searchZone);
	
		return results;
	}
	
}
