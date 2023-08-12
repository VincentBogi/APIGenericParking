package com.vbogi.APIGenericParking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingDTO {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("cityName")
	private String cityName;
	
	@JsonProperty("capacity")
	private Integer capacity;
	
	@JsonProperty("nbAvailablePlaces")
	private Integer nbAvailablePlaces;
	
	@JsonProperty("locationDTO")
	private LocationDTO locationDTO;
	
	public ParkingDTO(String name, String cityName, Integer capacity, Integer nbAvailablePlaces, LocationDTO locationDTO) {
		super();
		this.name = name;
		this.cityName = cityName;
		this.capacity = capacity;
		this.nbAvailablePlaces = nbAvailablePlaces;
		this.locationDTO = locationDTO;
	}
	

	public String getName() {
		return name;
	}

	public String getCityName() {
		return cityName;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public Integer getNbAvailablePlaces() {
		return nbAvailablePlaces;
	}

	public LocationDTO getLocationDTO() {
		return locationDTO;
	}
	
	
}
