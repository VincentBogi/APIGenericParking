package com.vbogi.APIGenericParking.modules.city;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.vbogi.APIGenericParking.models.Parking;
import com.vbogi.APIGenericParking.models.SearchZone;
import com.vbogi.APIGenericParking.modules.city.dtos.MarseilleParkingDto;

@Component
public class APIMarseille extends APICity<MarseilleParkingDto> {
	
	public APIMarseille() {
		super(CityConstants.MARRSEILLE);
	}

	@Override
	public List<Parking> getParkingsData(SearchZone searchZone) throws Exception{
		List<Parking> parkings = new ArrayList<Parking>();
		return parkings;
	}

	@Override
	public Parking transformDataToParking(SearchZone searchZone, MarseilleParkingDto apiData) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}
	
}
