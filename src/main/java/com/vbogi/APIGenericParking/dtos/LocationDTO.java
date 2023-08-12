package com.vbogi.APIGenericParking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDTO {
	
	@JsonProperty("latitude")
	private Double latitude;
	
	@JsonProperty("longitude")
	private Double longitude; 

	
	public LocationDTO(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}
	
}
