package com.vbogi.APIGenericParking.modules.city;

import java.util.List;

import com.vbogi.APIGenericParking.models.Parking;
import com.vbogi.APIGenericParking.models.SearchZone;

public abstract class APICity<T> {
	private final String cityName;
	
	public APICity(String cityName) {
		this.cityName = cityName;
	}
	
	public abstract List<Parking> getParkingsData(SearchZone searchZone) throws Exception;
	public abstract Parking transformDataToParking(SearchZone searchZone, T data) throws Exception;

	public String getCityName() {
		return cityName;
	}
	
}
