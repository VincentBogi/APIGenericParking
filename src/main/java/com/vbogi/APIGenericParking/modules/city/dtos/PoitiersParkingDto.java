package com.vbogi.APIGenericParking.modules.city.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PoitiersParkingDto {
	
	@JsonProperty("nom")
	private String name;
	
	@JsonProperty("nb_places")
	private Integer capacity;
	
	@JsonProperty("ylat")
	private Double latitude;
	
	@JsonProperty("xlong")
	private Double longitude;

	
	public String getName() {
		return name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

}
