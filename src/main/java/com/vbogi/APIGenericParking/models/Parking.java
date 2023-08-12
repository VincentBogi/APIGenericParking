package com.vbogi.APIGenericParking.models;

import java.util.Objects;

import com.vbogi.APIGenericParking.dtos.ParkingDTO;

public class Parking {
	
	private String name;
	
	private String cityName;
	
	private Integer capacity;
	
	private Integer nbAvailablePlaces;
	
	private Location location;
	
	public Parking(String name, String cityName, Integer capacity, Integer nbAvailablePlaces, Location location) {
		super();
		this.name = name;
		this.cityName = cityName;
		this.capacity = capacity;
		this.nbAvailablePlaces = nbAvailablePlaces;
		this.location = location;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getNbAvailablePlaces() {
		return nbAvailablePlaces;
	}

	public void setNbAvailablePlaces(Integer nbAvailablePlaces) {
		this.nbAvailablePlaces = nbAvailablePlaces;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	

	public ParkingDTO toParkingDTO() {
		return new ParkingDTO(name, cityName, capacity, nbAvailablePlaces, location.toLocationDTO());
	}


	@Override
	public String toString() {
		return "Parking [name=" + name + ", cityName=" + cityName + ", capacity=" + capacity + ", nbAvailablePlaces="
				+ nbAvailablePlaces + ", location=" + location + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(cityName, location, name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parking other = (Parking) obj;
		return Objects.equals(cityName, other.cityName) && Objects.equals(location, other.location)
				&& Objects.equals(name, other.name);
	}
	
	
	
	
	
	
}
