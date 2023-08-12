package com.vbogi.APIGenericParking.models;

import java.util.Objects;

import com.vbogi.APIGenericParking.dtos.LocationDTO;

public class Location {
	
	private String city;
	
	private Double latitude;
	
	private Double longitude; 

	
	public Location(String city, Double latitude, Double longitude) {
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}
	
	public LocationDTO toLocationDTO() {
		return new LocationDTO(latitude, longitude);
	}


	@Override
	public int hashCode() {
		return Objects.hash(city, latitude, longitude);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(city, other.city) && Objects.equals(latitude, other.latitude)
				&& Objects.equals(longitude, other.longitude);
	}


	@Override
	public String toString() {
		return "Location [city=" + city + ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	
	

}
